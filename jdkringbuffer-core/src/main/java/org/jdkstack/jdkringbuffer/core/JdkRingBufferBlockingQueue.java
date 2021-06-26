package org.jdkstack.jdkringbuffer.core;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 * @param <E> e.
 */
@SuppressWarnings("java:S1162")
public class JdkRingBufferBlockingQueue<E> extends AbstractRingBufferBlockingQueue<E> {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public JdkRingBufferBlockingQueue() {
    super();
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
    while (!offer(e)) {
      final Thread t = Thread.currentThread();
      while (isFull() && !t.isInterrupted()) {
        Thread.onSpinWait();
      }
      if (t.isInterrupted()) {
        throw new InterruptedException("线程中断.");
      }
    }
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @return E e.
   */
  @Override
  public E take() throws InterruptedException {
    while (true) {
      E pollObj = poll();
      if (pollObj != null) {
        return pollObj;
      }
      final Thread t = Thread.currentThread();
      while (isEmpty() && !t.isInterrupted()) {
        Thread.onSpinWait();
      }
      if (t.isInterrupted()) {
        throw new InterruptedException("线程中断.");
      }
    }
  }

  @Override
  public E poll(long timeout, TimeUnit unit) throws InterruptedException {
    return null;
  }

  @Override
  public int remainingCapacity() {
    return 0;
  }

  @Override
  public int drainTo(Collection<? super E> c) {
    return 0;
  }

  @Override
  public int drainTo(Collection<? super E> c, int maxElements) {
    return 0;
  }

  @Override
  public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
    return false;
  }
}
