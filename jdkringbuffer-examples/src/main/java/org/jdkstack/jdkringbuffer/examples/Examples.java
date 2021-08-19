package org.jdkstack.jdkringbuffer.examples;

import org.jdkstack.jdkringbuffer.core.Constants;
import org.jdkstack.jdkringbuffer.core.mpmc.version2.MpmcBlockingQueueV2;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
@SuppressWarnings("all")
public final class Examples {

  private Examples() {
    //
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param args args.
   */
  public static void main(final String... args) {
    final MpmcBlockingQueueV2<InfoEvent> queue = new MpmcBlockingQueueV2<>(1024);
    new Thread(
            () -> {
              final long startTime = System.currentTimeMillis();
              int pcnt = 0;
              while (pcnt < Constants.NUM) {
                InfoEvent kafkaInfoEvent = new InfoEvent(pcnt, pcnt + "info");
                try {
                  queue.put(kafkaInfoEvent);
                } catch (InterruptedException ignorede) {
                  Thread.currentThread().interrupt();
                }
                pcnt++;
              }
              long endTime = System.currentTimeMillis();
              System.out.println("消耗时间 ： " + (endTime - startTime) + "毫秒");
            })
        .start();

    new Thread(
            () -> {
              final long startTime = System.currentTimeMillis();
              int cnt = 0;
              while (cnt < Constants.NUM) {
                try {
                  InfoEvent kafkaInfoEvent = queue.take();
                  if (kafkaInfoEvent != null) {
                    cnt++;
                  }
                } catch (InterruptedException ignored) {
                  //
                  Thread.currentThread().interrupt();
                }
              }
              long endTime = System.currentTimeMillis();
              System.out.println(cnt + "消耗时间 ： " + (endTime - startTime) + "毫秒");
            })
        .start();
  }
}
