package org.jdkstack.jdkringbuffer.core.spmc.version1;

import org.jdkstack.jdkringbuffer.core.Constants;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 * @param <E> e.
 */
public class SpmcBlockingQueueV1<E> extends AbstractSpmcBlockingQueueV1<E> {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public SpmcBlockingQueueV1() {
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
  public SpmcBlockingQueueV1(final int capacity) {
    super(capacity);
  }
}
