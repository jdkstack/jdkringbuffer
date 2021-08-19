package org.jdkstack.jdkringbuffer.core.spsc.version2;

import org.jdkstack.jdkringbuffer.core.Constants;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 * @param <E> e.
 */
public class SpscBlockingQueueV2<E> extends AbstractSpscBlockingQueueV2<E> {

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
}
