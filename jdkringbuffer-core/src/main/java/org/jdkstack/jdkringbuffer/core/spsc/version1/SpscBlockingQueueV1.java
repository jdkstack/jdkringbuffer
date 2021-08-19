package org.jdkstack.jdkringbuffer.core.spsc.version1;

import org.jdkstack.jdkringbuffer.core.Constants;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 * @param <E> e.
 */
public class SpscBlockingQueueV1<E> extends AbstractSpscBlockingQueueV1<E> {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public SpscBlockingQueueV1() {
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
  public SpscBlockingQueueV1(final int capacity) {
    super(capacity);
  }
}
