package org.jdkstack.jdkringbuffer.core.mpsc.version1;

import org.jdkstack.jdkringbuffer.core.Constants;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 * @param <E> e.
 */
public class MpscBlockingQueueV1<E> extends AbstractMpscBlockingQueueV1<E> {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public MpscBlockingQueueV1() {
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
  public MpscBlockingQueueV1(final int capacity) {
    super(capacity);
  }
}
