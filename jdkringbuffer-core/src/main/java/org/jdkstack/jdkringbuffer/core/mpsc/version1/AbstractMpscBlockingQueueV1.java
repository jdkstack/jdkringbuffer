package org.jdkstack.jdkringbuffer.core.mpsc.version1;

import org.jdkstack.jdkringbuffer.core.AbstractLockBlockingQueueV1;

/**
 * 多生产单消费MPSC阻塞队列.
 *
 * <p>生产线程安全处理使用CAS锁.
 *
 * @author admin
 * @param <E> e .
 */
public abstract class AbstractMpscBlockingQueueV1<E> extends AbstractLockBlockingQueueV1<E> {

  protected AbstractMpscBlockingQueueV1(final int capacity) {
    super(capacity);
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
      // poll计数.
      this.head.getAndIncrement();
      // 获取环形数组索引位置.
      final int bufferIndex = headSeq & index;
      // 根据索引位置获取元素.
      e = this.ringBuffer[bufferIndex];
      // 将索引位置设置为空.
      this.ringBuffer[bufferIndex] = null;
    }
    return e;
  }
}
