package org.jdkstack.jdkringbuffer.core.version1;

import org.jdkstack.jdkringbuffer.core.Constants;
import org.jdkstack.jdkringbuffer.core.version1.AbstractRingBufferBlockingQueueV1;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 * @param <E> e.
 */
public class JdkRingBufferBlockingQueueV1<E> extends AbstractRingBufferBlockingQueueV1<E> {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public JdkRingBufferBlockingQueueV1() {
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
  public JdkRingBufferBlockingQueueV1(final int capacity) {
    super(capacity);
  }
}
