package org.jdkstack.jdkringbuffer.core.spsc.version4;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import org.jdkstack.jdkringbuffer.api.RingBufferBlockingQueue;
import org.jdkstack.jdkringbuffer.core.AbstractBlockingQueue;
import org.jdkstack.jdkringbuffer.core.Constants;

/**
 * 单生产单消费SPSC阻塞队列.
 *
 * <p>不需要处理线程安全,不使用CAS锁.
 *
 * @author admin
 * @param <E> e .
 */
public abstract class AbstractSpscBlockingQueueV2<E> extends AbstractBlockingQueue<E>
    implements BlockingQueue<E>, RingBufferBlockingQueue {
  /** 环形数组. */
  private final E[] ringBuffer;

  protected AbstractSpscBlockingQueueV2() {
    this(Constants.CAPACITY);
  }

  @SuppressWarnings("unchecked")
  protected AbstractSpscBlockingQueueV2(final int capacity) {
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
    // 检查环形数组是否满了.
    if (0 > queueStart || this.head.get() > queueStart) {
      // 向环形数组设置元素,取模后向对应的下标设置元素.
      final int tailSlot = tailSeq & index;
      this.ringBuffer[tailSlot] = e;
      this.tail.getAndIncrement();
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
   * @return E e.
   */
  @Override
  public E poll() {
    final int headSeq = this.head.get();
    E e = null;
    // 检查队列中是否有元素.
    if (0 > headSeq || this.tail.get() > headSeq) {
      // 获取环形数组索引位置.
      final int bufferIndex = headSeq & index;
      // 根据索引位置获取元素.
      e = this.ringBuffer[bufferIndex];
      // 将索引位置设置为空.
      this.ringBuffer[bufferIndex] = null;
      // poll计数.
      this.head.getAndIncrement();
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
