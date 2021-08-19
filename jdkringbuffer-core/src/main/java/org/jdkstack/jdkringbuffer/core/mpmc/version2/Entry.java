package org.jdkstack.jdkringbuffer.core.mpmc.version2;

import java.util.concurrent.atomic.AtomicInteger;
import jdk.internal.vm.annotation.Contended;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 * @param <E> e .
 */
@Contended
public class Entry<E> {
  /** 元素序列号,代表数组的下标 . */
  private final AtomicInteger seq = new AtomicInteger(0);
  /** 元素,代表数组元素. */
  private E er;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param i i .
   */
  public Entry(final int i) {
    seq.set(i);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @return E e.
   */
  public E getEntry() {
    return er;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param entry e.
   */
  public void setEntry(final E entry) {
    this.er = entry;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @return int e.
   */
  public int getSeq() {
    return seq.get();
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param seq e.
   */
  public void setSeq(final int seq) {
    this.seq.set(seq);
  }
}
