package org.jdkstack.jdkringbuffer.core.mpsc.version2;

import org.jdkstack.jdkringbuffer.core.AbstractLockBlockingQueueV2;
import org.jdkstack.jdkringbuffer.core.Entry;

/**
 * 多生产单消费MPSC阻塞队列.
 *
 * <p>生产线程安全处理使用CAS锁.
 *
 * @author admin
 * @param <E> e .
 */
public abstract class AbstractMpscBlockingQueueV2<E> extends AbstractLockBlockingQueueV2<E> {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param capacity e.
   */
  protected AbstractMpscBlockingQueueV2(final int capacity) {
    super(capacity);
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
      e = cell.getEntry();
      cell.setEntry(null);
      cell.setSeq(headSeq + index + 1);
      this.head.getAndIncrement();
    }
    return e;
  }
}
