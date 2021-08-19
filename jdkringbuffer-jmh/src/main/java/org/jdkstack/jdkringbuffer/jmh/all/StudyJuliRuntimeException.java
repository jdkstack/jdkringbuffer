package org.jdkstack.jdkringbuffer.jmh.all;

/**
 * .
 *
 * @author admin
 */
public class StudyJuliRuntimeException extends RuntimeException {

  private static final long serialVersionUID = 1;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public StudyJuliRuntimeException() {
    //
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param message message.
   */
  public StudyJuliRuntimeException(final String message) {
    super(message);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param cause cause.
   * @param message message.
   */
  public StudyJuliRuntimeException(final String message, final Throwable cause) {
    super(message, cause);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param cause cause.
   */
  public StudyJuliRuntimeException(final Throwable cause) {
    super(cause);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param cause cause.
   * @param message message.
   * @param enableSuppression enableSuppression.
   * @param writableStackTrace writableStackTrace.
   */
  public StudyJuliRuntimeException(
      final String message,
      final Throwable cause,
      final boolean enableSuppression,
      final boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
