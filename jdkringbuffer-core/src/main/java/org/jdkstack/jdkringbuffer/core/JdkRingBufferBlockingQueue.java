package org.jdkstack.jdkringbuffer.core;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 * @param <E> e.
 */
public class JdkRingBufferBlockingQueue<E> extends AbstractRingBufferBlockingQueue<E> {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public JdkRingBufferBlockingQueue() {
    super(Constants.CAPACITY);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param capacity capacity.
   */
  public JdkRingBufferBlockingQueue(final int capacity) {
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
    // 循环向环形数组插入数据,直到能插入为止.
    while (!offer(e)) {
      // 如果不能插入,则等待.
      fullAwait();
      // 检查线程是否抛出线程中断.
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
