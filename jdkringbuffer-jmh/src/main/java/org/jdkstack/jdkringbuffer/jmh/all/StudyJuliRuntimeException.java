package org.jdkstack.jdkringbuffer.jmh.all;

/**
 * .
 *
 * @author admin
 */
public class StudyJuliRuntimeException extends RuntimeException {

  private static final long serialVersionUID = 1;

  public StudyJuliRuntimeException() {}

  public StudyJuliRuntimeException(String message) {
    super(message);
  }

  public StudyJuliRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  public StudyJuliRuntimeException(Throwable cause) {
    super(cause);
  }

  public StudyJuliRuntimeException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
