package org.jdkstack.jdkringbuffer.jmh.lmax.util;

import com.lmax.disruptor.EventHandler;
import org.openjdk.jmh.infra.Blackhole;

public class SimpleEventHandler implements EventHandler<String> {
  private final Blackhole bh;

  public SimpleEventHandler(final Blackhole bh) {
    this.bh = bh;
  }

  @Override
  public void onEvent(final String event, final long sequence, final boolean endOfBatch) {
    // bh.consume(event);
  }
}
