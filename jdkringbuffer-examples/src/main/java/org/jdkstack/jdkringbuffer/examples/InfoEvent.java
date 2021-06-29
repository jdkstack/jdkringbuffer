package org.jdkstack.jdkringbuffer.examples;

/**
 * .
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class InfoEvent {
  private long id;
  private String value;

  public InfoEvent() {
    //
  }

  public InfoEvent(final long id, final String value) {
    this.id = id;
    this.value = value;
  }

  public long getId() {
    return id;
  }

  public void setId(final long id) {
    this.id = id;
  }

  public String getValue() {
    return value;
  }

  public void setValue(final String value) {
    this.value = value;
  }
}
