package org.jdkstack.jdkringbuffer.core;

import java.util.AbstractQueue;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractBlockingQueue<E> extends AbstractQueue<E> {
  /** 环形数组的容量. */
  protected final int capacity;
  /** 环形数组入队时,当前元素的坐标. */
  protected final int index;
  /** 环形数组出队时,总共出队了多少个元素. */
  protected final AtomicInteger head = new AtomicInteger(0);
  /** 环形数组入队时,总共入队了多少个元素. */
  protected final AtomicInteger tail = new AtomicInteger(0);

  protected AbstractBlockingQueue(final int capacity, final int index) {
    this.capacity = capacity;
    this.index = index;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @return boolean e.
   */
  public E take() throws InterruptedException {
    // 循环从环形数组中获取元素,直到能获取到为止.
    while (true) {
      // 拿到一个元素.
      E e = poll();
      // 如果元素不为空,返回元素.
      if (e != null) {
        return e;
      } else {
        // 如果不能获取,则等待.
        emptyAwait();
        // 检查线程是否抛出线程中断.
        final Thread t = Thread.currentThread();
        if (t.isInterrupted()) {
          throw new InterruptedException("线程中断.");
        }
      }
    }
  }

  private void emptyAwait() {
    final Thread t = Thread.currentThread();
    while (isEmpty() && !t.isInterrupted()) {
      Thread.onSpinWait();
    }
  }
}
