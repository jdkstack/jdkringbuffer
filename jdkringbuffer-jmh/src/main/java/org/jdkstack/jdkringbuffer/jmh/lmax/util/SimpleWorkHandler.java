package org.jdkstack.jdkringbuffer.jmh.lmax.util;

import com.lmax.disruptor.WorkHandler;
import org.openjdk.jmh.infra.Blackhole;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class SimpleWorkHandler implements WorkHandler<SimpleEvent> {
  /** . */
  private final Blackhole bh;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param bh bh.
   */
  public SimpleWorkHandler(final Blackhole bh) {
    this.bh = bh;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param event event.
   */
  @Override
  public void onEvent(final SimpleEvent event) throws Exception {
    bh.consume(event);
  }
}
