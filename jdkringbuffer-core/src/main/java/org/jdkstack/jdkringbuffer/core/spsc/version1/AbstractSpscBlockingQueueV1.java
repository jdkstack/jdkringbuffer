package org.jdkstack.jdkringbuffer.core.spsc.version1;

import org.jdkstack.jdkringbuffer.core.AbstractLockBlockingQueueV1;

/**
 * 单生产单消费SPSC阻塞队列.
 *
 * <p>不需要处理线程安全,不使用CAS锁.
 *
 * @author admin
 * @param <E> e .
 */
public abstract class AbstractSpscBlockingQueueV1<E> extends AbstractLockBlockingQueueV1<E> {

  protected AbstractSpscBlockingQueueV1(final int capacity) {
    super(capacity);
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
      flag = true;
      this.tail.getAndIncrement();
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
}
