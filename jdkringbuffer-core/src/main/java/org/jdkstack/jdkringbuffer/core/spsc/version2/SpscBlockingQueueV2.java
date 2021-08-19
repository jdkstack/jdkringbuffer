package org.jdkstack.jdkringbuffer.core.spsc.version2;

import org.jdkstack.jdkringbuffer.core.AbstractLockBlockingQueueV2;
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
public class SpscBlockingQueueV2<E> extends AbstractLockBlockingQueueV2<E> {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public SpscBlockingQueueV2() {
    super(Constants.CAPACITY);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param capacity e.
   */
  public SpscBlockingQueueV2(final int capacity) {
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
}
