package org.jdkstack.jdkringbuffer.core.mpmc.version1;

import org.jdkstack.jdkringbuffer.core.AbstractLockBlockingQueueV1;

/**
 * 多生产多消费MPMC阻塞队列.
 *
 * <p>线程安全处理使用CAS锁.
 *
 * @author admin
 * @param <E> e .
 */
public abstract class AbstractMpmcBlockingQueueV1<E> extends AbstractLockBlockingQueueV1<E> {

  protected AbstractMpmcBlockingQueueV1(final int capacity) {
    super(capacity);
  }
}
