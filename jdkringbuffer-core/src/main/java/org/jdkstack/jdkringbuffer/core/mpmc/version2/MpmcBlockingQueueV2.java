package org.jdkstack.jdkringbuffer.core.mpmc.version2;

import org.jdkstack.jdkringbuffer.core.AbstractLockBlockingQueueV2;
import org.jdkstack.jdkringbuffer.core.Constants;

/**
 * 多生产多消费MPMC阻塞队列.
 *
 * <p>线程安全处理使用CAS锁.
 *
 * @author admin
 * @param <E> e .
 */
public class MpmcBlockingQueueV2<E> extends AbstractLockBlockingQueueV2<E> {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public MpmcBlockingQueueV2() {
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
  public MpmcBlockingQueueV2(final int capacity) {
    super(capacity);
  }
}
