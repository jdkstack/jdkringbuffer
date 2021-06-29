package org.jdkstack.jdkringbuffer.api;

import java.util.function.BooleanSupplier;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
@FunctionalInterface
public interface RingBufferBlockingQueue extends BooleanSupplier {

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @return boolean .
   */
  boolean isFull();

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @return boolean .
   */
  @Override
  default boolean getAsBoolean() {
    return false;
  }
}
