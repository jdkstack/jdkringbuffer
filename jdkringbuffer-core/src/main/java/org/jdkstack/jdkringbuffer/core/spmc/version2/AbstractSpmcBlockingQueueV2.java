package org.jdkstack.jdkringbuffer.core.spmc.version2;

import org.jdkstack.jdkringbuffer.core.AbstractLockBlockingQueueV2;
import org.jdkstack.jdkringbuffer.core.Entry;

/**
 * 单生产多消费SPMC阻塞队列.
 *
 * <p>消费线程安全处理使用CAS锁.
 *
 * @author admin
 * @param <E> e .
 */
public abstract class AbstractSpmcBlockingQueueV2<E> extends AbstractLockBlockingQueueV2<E> {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param capacity e.
   */
  protected AbstractSpmcBlockingQueueV2(final int capacity) {
    super(capacity);
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
}
