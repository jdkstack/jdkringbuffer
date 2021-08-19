package org.jdkstack.jdkringbuffer.core.spsc.version3;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import org.jdkstack.jdkringbuffer.api.RingBufferBlockingQueue;
import org.jdkstack.jdkringbuffer.core.AbstractBlockingQueue;
import org.jdkstack.jdkringbuffer.core.Constants;
import org.jdkstack.jdkringbuffer.core.Entry;

/**
 * 单生产单消费SPSC阻塞队列..
 *
 * <p>不需要处理线程安全,不使用CAS锁.
 *
 * @author admin
 * @param <E> e .
 */
public abstract class AbstractSpscBlockingQueueV1<E> extends AbstractBlockingQueue<E>
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
  protected AbstractSpscBlockingQueueV1() {
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
  protected AbstractSpscBlockingQueueV1(final int capacity) {
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
      this.tail.getAndIncrement();
      cell.setEntry(e);
      cell.setSeq(tailSeq + 1);
      flag = true;
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
      this.head.getAndIncrement();
      e = cell.getEntry();
      cell.setEntry(null);
      cell.setSeq(headSeq + index + 1);
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
