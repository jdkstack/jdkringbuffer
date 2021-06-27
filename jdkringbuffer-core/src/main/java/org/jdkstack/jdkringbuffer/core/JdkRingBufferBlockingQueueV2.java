package org.jdkstack.jdkringbuffer.core;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 * @param <E> e.
 */
public class JdkRingBufferBlockingQueueV2<E> extends AbstractRingBufferBlockingQueueV2<E> {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public JdkRingBufferBlockingQueueV2() {
    super(Constants.CAPACITY);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param capacity e.
   */
  public JdkRingBufferBlockingQueueV2(final int capacity) {
    super(capacity);
  }


  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param e e.
   */
  @Override
  public void put(final E e) throws InterruptedException {
    while (!offer(e)) {
      fullAwait();
    }
  }


  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @return boolean e.
   */
  @Override
  public E take() throws InterruptedException {
    while (true) {
      E e = poll();
      if (e != null) {
        return e;
      } else {
        emptyAwait();
      }
    }
  }

  @SuppressWarnings("java:S1162")
  private void fullAwait() throws InterruptedException {
    final Thread t = Thread.currentThread();
    while (isFull() && !t.isInterrupted()) {
      Thread.onSpinWait();
    }
    if (t.isInterrupted()) {
      throw new InterruptedException("线程中断.");
    }
  }

  @SuppressWarnings("java:S1162")
  private void emptyAwait() throws InterruptedException {
    final Thread t = Thread.currentThread();
    while (isEmpty() && !t.isInterrupted()) {
      Thread.onSpinWait();
    }
    if (t.isInterrupted()) {
      throw new InterruptedException("线程中断.");
    }
  }
}
