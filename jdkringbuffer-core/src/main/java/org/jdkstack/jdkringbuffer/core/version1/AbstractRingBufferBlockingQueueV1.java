package org.jdkstack.jdkringbuffer.core.version1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import org.jdkstack.jdkringbuffer.api.RingBufferBlockingQueue;
import org.jdkstack.jdkringbuffer.core.AbstractBlockingQueue;
import org.jdkstack.jdkringbuffer.core.Constants;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 * @param <E> e .
 */
public abstract class AbstractRingBufferBlockingQueueV1<E> extends AbstractBlockingQueue<E>
    implements BlockingQueue<E>, RingBufferBlockingQueue {
  /** 环形数组. */
  private final E[] ringBuffer;
  /** 环形数组入队时,是否被其他线程抢先放入了值. */
  private final AtomicInteger tailLock = new AtomicInteger(0);
  /** 环形数组出队时,是否被其他线程抢先获取了值. */
  private final AtomicInteger headLock = new AtomicInteger(0);

  protected AbstractRingBufferBlockingQueueV1() {
    this(Constants.CAPACITY);
  }

  @SuppressWarnings("unchecked")
  protected AbstractRingBufferBlockingQueueV1(final int capacity) {
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
    // 环形数组一共设置的元素的总个数(自增+1).
    final int tailSeq = this.tail.get();
    final int queueStart = tailSeq - this.capacity;
    boolean flag = false;
    // 检查环形数组是否满了. 检查是否被其他线程修改.
    if (0 > queueStart || this.head.get() > queueStart) {
      if (this.tailLock.compareAndSet(tailSeq, tailSeq + 1)) {
        // 向环形数组设置元素,取模后向对应的下标设置元素.
        final int tailSlot = tailSeq & index;
        this.ringBuffer[tailSlot] = e;
        this.tail.getAndIncrement();
        flag = true;
      } else {
        // 如果被占用,暂停1nanos.
        LockSupport.parkNanos(1L);
      }
    }
    return flag;
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
    final int headSeq = this.head.get();
    E e = null;
    // 检查队列中是否有元素.   检查是否被其他线程修改,如果返回true,则没有修改,可以更新值.
    if (0 > headSeq || this.tail.get() > headSeq) {
      if (this.headLock.compareAndSet(headSeq, headSeq + 1)) {
        // 获取环形数组索引位置.
        final int bufferIndex = headSeq & index;
        // 根据索引位置获取元素.
        e = this.ringBuffer[bufferIndex];
        // 将索引位置设置为空.
        this.ringBuffer[bufferIndex] = null;
        // poll计数.
        this.head.getAndIncrement();
      } else {
        LockSupport.parkNanos(1L);
      }
    }
    return e;
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
  public final E peek() {
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
  public final int size() {
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
  public final boolean isEmpty() {
    return tail.get() == head.get();
  }
}
