package org.jdkstack.jdkringbuffer.core;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import org.jdkstack.jdkringbuffer.api.RingBufferBlockingQueue;

public abstract class AbstractLockBlockingQueueV1<E> extends AbstractBlockingQueue<E>
    implements BlockingQueue<E>, RingBufferBlockingQueue {
  /** 环形数组. */
  protected final E[] ringBuffer;
  /** 环形数组入队时,是否被其他线程抢先放入了值. */
  protected final AtomicInteger tailLock = new AtomicInteger(0);
  /** 环形数组出队时,是否被其他线程抢先获取了值. */
  protected final AtomicInteger headLock = new AtomicInteger(0);

  protected AbstractLockBlockingQueueV1() {
    this(Constants.CAPACITY);
  }

  @SuppressWarnings("unchecked")
  protected AbstractLockBlockingQueueV1(final int capacity) {
    super(capacity, capacity - 1);
    // jdk 泛型数组,会有检查异常,但不影响什么,用unchecked关闭检查.
    this.ringBuffer = (E[]) new Object[capacity];
  }

  /**
   * This is a class description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param e e .
   * @return boolean b.
   */
  @Override
  public boolean offer(final E e) {
    while (true) {
      // 环形数组一共设置的元素的总个数(自增+1).
      final int tailSeq = this.tail.get();
      final int queueStart = tailSeq - this.capacity;
      // 检查环形数组是否满了. 检查是否被其他线程修改.
      if (0 > queueStart || this.head.get() > queueStart) {
        if (this.tailLock.compareAndSet(tailSeq, tailSeq + 1)) {
          // 向环形数组设置元素,取模后向对应的下标设置元素.
          final int tailSlot = tailSeq & index;
          this.ringBuffer[tailSlot] = e;
          this.tail.getAndIncrement();
          return true;
        } else {
          // 如果被占用,暂停1nanos.
          // https://blogs.oracle.com/dave/lightweight-contention-
          // management-for-efficient-compare-and-swap-operations
          LockSupport.parkNanos(1L);
        }
      } else {
        return false;
      }
    }
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @return E e.
   */
  @Override
  public E poll() {
    while (true) {
      final int headSeq = this.head.get();
      // 检查队列中是否有元素.   检查是否被其他线程修改,如果返回true,则没有修改,可以更新值.
      if (0 > headSeq || this.tail.get() > headSeq) {
        if (this.headLock.compareAndSet(headSeq, headSeq + 1)) {
          // 获取环形数组索引位置.
          final int bufferIndex = headSeq & index;
          // 根据索引位置获取元素.
          final E e = this.ringBuffer[bufferIndex];
          // 将索引位置设置为空.
          this.ringBuffer[bufferIndex] = null;
          // poll计数.
          this.head.getAndIncrement();
          return e;
        } else {
          LockSupport.parkNanos(1L);
        }
      } else {
        return null;
      }
    }
  }

  /**
   * This is a class description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @return E e.
   */
  @Override
  public E poll(final long timeout, final TimeUnit unit) {
    throw new UnsupportedOperationException("未实现.");
  }

  /**
   * This is a class description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @return E e.
   */
  @Override
  public E peek() {
    return ringBuffer[head.get() & index];
  }

  /**
   * This is a class description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @return int e.
   */
  @Override
  public int size() {
    return Math.max(tail.get() - head.get(), 0);
  }

  /**
   * This is a class description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @return boolean e.
   */
  @Override
  public boolean isEmpty() {
    return tail.get() == head.get();
  }
}
