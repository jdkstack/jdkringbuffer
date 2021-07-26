package org.jdkstack.jdkringbuffer.core;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 * @param <E> e.
 */
public class JdkRingBufferBlockingQueue<E> extends AbstractRingBufferBlockingQueue<E> {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public JdkRingBufferBlockingQueue() {
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
  public JdkRingBufferBlockingQueue(final int capacity) {
    super(capacity);
  }
}
