package org.jdkstack.jdkringbuffer.core.mpmc.version1;

import org.jdkstack.jdkringbuffer.core.AbstractLockBlockingQueueV1;
import org.jdkstack.jdkringbuffer.core.Constants;

/**
 * 多生产多消费MPMC阻塞队列.
 *
 * <p>线程安全处理使用CAS锁.
 *
 * @author admin
 * @param <E> e .
 */
public class MpmcBlockingQueueV1<E> extends AbstractLockBlockingQueueV1<E> {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public MpmcBlockingQueueV1() {
    super(Constants.CAPACITY);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param capacity capacity.
   */
  public MpmcBlockingQueueV1(final int capacity) {
    super(capacity);
  }
}
