package org.jdkstack.jdkringbuffer.jmh.lmax.util;

import com.lmax.disruptor.EventHandler;
import org.openjdk.jmh.infra.Blackhole;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class SimpleEventHandler implements EventHandler<SimpleEvent> {
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
  public SimpleEventHandler(final Blackhole bh) {
    this.bh = bh;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param event event.
   * @param sequence sequence.
   * @param endOfBatch endOfBatch.
   */
  @Override
  public void onEvent(final SimpleEvent event, final long sequence, final boolean endOfBatch) {
    //
    bh.consume(event);
  }
}
