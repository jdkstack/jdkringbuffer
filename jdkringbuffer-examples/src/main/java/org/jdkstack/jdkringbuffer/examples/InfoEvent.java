package org.jdkstack.jdkringbuffer.examples;

/**
 * .
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class InfoEvent {
  /** . */
  private long id;
  /** . */
  private String value;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public InfoEvent() {
    //
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param id id.
   * @param value value.
   */
  public InfoEvent(final long id, final String value) {
    this.id = id;
    this.value = value;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @return long long.
   */
  public long getId() {
    return id;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param id id.
   */
  public void setId(final long id) {
    this.id = id;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @return String String.
   */
  public String getValue() {
    return value;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param value value.
   */
  public void setValue(final String value) {
    this.value = value;
  }
}
