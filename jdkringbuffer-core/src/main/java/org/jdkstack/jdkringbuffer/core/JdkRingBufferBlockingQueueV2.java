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
      final Thread t = Thread.currentThread();
      if (t.isInterrupted()) {
        throw new InterruptedException("线程中断.");
      }
    }
  }


  private void fullAwait() {
    final Thread t = Thread.currentThread();
    while (isFull() && !t.isInterrupted()) {
      Thread.onSpinWait();
    }
  }
}
