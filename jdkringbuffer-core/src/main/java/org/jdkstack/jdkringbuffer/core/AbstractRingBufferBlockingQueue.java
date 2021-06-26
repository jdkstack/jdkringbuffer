package org.jdkstack.jdkringbuffer.core;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import jdk.internal.vm.annotation.Contended;
import org.jdkstack.jdkringbuffer.api.RingBufferBlockingQueue;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 * @param <E> e .
 */
@SuppressWarnings("java:S134")
public abstract class AbstractRingBufferBlockingQueue<E> extends AbstractQueue<E>
    implements BlockingQueue<E>, RingBufferBlockingQueue<E> {
  /** 环形数组的容量. */
  private final int capacity;
  /** 环形数组. */
  private final E[] ringBuffer;
  /** 环形数组入队时,当前元素的坐标. */
  private final int index;
  /** 环形数组入队时,总共入队了多少个元素. */
  private final AtomicInteger tail = new AtomicInteger();
  /** 环形数组入队时,是否被其他线程抢先放入了值. */
  private final AtomicInteger tailLock = new AtomicInteger(0);
  /** 环形数组出队时,总共出队了多少个元素. */
  private final AtomicInteger head = new AtomicInteger();
  /** 环形数组出队时,是否被其他线程抢先获取了值. */
  private final AtomicInteger headLock = new AtomicInteger(0);
  /** 环形数组入队时,查看出队了多少个元素. */
  @Contended private int out;
  /** 环形数组出队时,查看入队了多少个元素. */
  @Contended private int in;

  protected AbstractRingBufferBlockingQueue() {
    this(Constants.CAPACITY);
  }

  @SuppressWarnings("unchecked")
  protected AbstractRingBufferBlockingQueue(final int capacity) {
    this.capacity = capacity;
    this.index = capacity - 1;
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
      final int tailSeq = tail.get();
      final int queueStart = tailSeq - capacity;
      // 检查环形数组是否满了.
      if ((out > queueStart) || ((out = head.get()) > queueStart)) {
        // 检查是否被其他线程修改.
        if (tailLock.compareAndSet(tailSeq, tailSeq + 1)) {
          try {
            // 向环形数组设置元素,取模后向对应的下表设置元素.
            final int tailSlot = tailSeq & index;
            ringBuffer[tailSlot] = e;
            return true;
          } finally {
            tail.getAndIncrement();
          }
        } // 其他线程修改.
      } else {
        // 超过最大容量.
        return false;
      }
      LockSupport.parkNanos(1L);
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
  public E poll() {
    while (true) {
      final int headSeq = this.head.get();
      // 检查队列中是否有元素.
      if ((in > headSeq) || (in = tail.get()) > headSeq) {
        // 检查是否被其他线程修改.
        if (headLock.compareAndSet(headSeq, headSeq + 1)) {
          try {
            final int pollSlot = headSeq & index;
            final E pollObj = ringBuffer[pollSlot];
            ringBuffer[pollSlot] = null;
            return pollObj;
          } finally {
            this.head.getAndIncrement();
          }
        } // 被其他线程修改了.
      } else {
        // 没有元素.
        return null;
      }
      LockSupport.parkNanos(1L);
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

  /**
   * This is a class description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @return boolean e.
   */
  @Override
  public boolean isFull() {
    final int queueStart = tail.get() - capacity;
    return head.get() == queueStart;
  }

  /**
   * This is a class description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @return Iterator<E> e.
   */
  @Override
  public Iterator<E> iterator() {
    throw new UnsupportedOperationException("未实现.");
  }
}
