package org.jdkstack.jdkringbuffer.examples;

import org.jdkstack.jdkringbuffer.core.Constants;
import org.jdkstack.jdkringbuffer.core.JdkRingBufferBlockingQueueV2;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
@SuppressWarnings({"java:S106", "java:S2142", "java:S5612"})
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
  public static void main(final String[] args) {
    final JdkRingBufferBlockingQueueV2<InfoEvent> queue = new JdkRingBufferBlockingQueueV2<>(1024);
    new Thread(
            () -> {
              final long startTime = System.currentTimeMillis();
              int pcnt = 0;
              while (pcnt < Constants.NUM) {
                InfoEvent kafkaInfoEvent = new InfoEvent(pcnt, pcnt + "info");
                try {
                  queue.put(kafkaInfoEvent);
                } catch (InterruptedException e) {
                  e.printStackTrace();
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
                } catch (InterruptedException e) {
                  //
                }
              }
              long endTime = System.currentTimeMillis();
              System.out.println(cnt + "消耗时间 ： " + (endTime - startTime) + "毫秒");
            })
        .start();
  }
}
