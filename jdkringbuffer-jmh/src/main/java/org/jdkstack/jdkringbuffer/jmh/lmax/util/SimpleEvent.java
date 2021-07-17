package org.jdkstack.jdkringbuffer.jmh.lmax.util;

public class SimpleEvent {
  private long value = Long.MIN_VALUE;

  public long getValue() {
    return value;
  }

  public void setValue(final long value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "SimpleEvent{" + "value=" + value + '}';
  }
}
