package org.jdkstack.jdkringbuffer.core;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import org.jdkstack.jdkringbuffer.api.RingBufferBlockingQueue;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 * @param <E> e .
 */
public abstract class AbstractRingBufferBlockingQueueV2<E> extends AbstractBlockingQueue<E>
    implements BlockingQueue<E>, RingBufferBlockingQueue {
  /** 环形数组. */
  private final Entry<E>[] buffer;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  protected AbstractRingBufferBlockingQueueV2() {
    this(Constants.CAPACITY);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param capacity e.
   */
  @SuppressWarnings("unchecked")
  protected AbstractRingBufferBlockingQueueV2(final int capacity) {
    super(capacity, capacity - 1);
    buffer = new Entry[capacity];
    for (int i = 0; i < capacity; i++) {
      buffer[i] = new Entry<>(i);
    }
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param e e.
   * @return boolean e.
   */
  @Override
  public boolean offer(final E e) {
    final int tailSeq = this.tail.get();
    final Entry<E> cell = buffer[tailSeq & index];
    final int seq = cell.getSeq();
    final int dif = seq - tailSeq;
    boolean flag = false;
    if (dif == 0) {
      if (this.tail.compareAndSet(tailSeq, tailSeq + 1)) {
        cell.setEntry(e);
        cell.setSeq(tailSeq + 1);
        flag = true;
      } else {
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
   * @return boolean e.
   */
  @Override
  public E poll() {
    final int headSeq = this.head.get();
    final Entry<E> cell = buffer[headSeq & index];
    final int seq = cell.getSeq();
    final int dif = seq - (headSeq + 1);
    E e = null;
    if (dif == 0) {
      if (this.head.compareAndSet(headSeq, headSeq + 1)) {
        e = cell.getEntry();
        cell.setEntry(null);
        cell.setSeq(headSeq + index + 1);
      } else {
        LockSupport.parkNanos(1L);
      }
    }
    return e;
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
  public E poll(final long timeout, final TimeUnit unit) {
    throw new UnsupportedOperationException("未实现.");
  }

  @Override
  public final E peek() {
    return buffer[head.get() & index].getEntry();
  }

  @Override
  public final int size() {
    return Math.max(tail.get() - head.get(), 0);
  }

  @Override
  public final boolean isEmpty() {
    return head.get() == tail.get();
  }
}
