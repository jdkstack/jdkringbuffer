package org.jdkstack.jdkringbuffer.jmh.lmax.util;

import com.lmax.disruptor.WorkHandler;
import org.openjdk.jmh.infra.Blackhole;

public class SimpleWorkHandler implements WorkHandler<SimpleEvent> {
  private final Blackhole bh;

  public SimpleWorkHandler(final Blackhole bh) {
    this.bh = bh;
  }

  @Override
  public void onEvent(SimpleEvent event) throws Exception {
    bh.consume(event);
  }
}
