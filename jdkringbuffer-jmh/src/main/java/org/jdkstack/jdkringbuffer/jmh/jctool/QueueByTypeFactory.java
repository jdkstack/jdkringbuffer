package org.jdkstack.jdkringbuffer.jmh.jctool;

import java.lang.reflect.Constructor;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedTransferQueue;
import org.jctools.queues.MpmcArrayQueue;
import org.jctools.queues.MpscArrayQueue;
import org.jctools.queues.MpscCompoundQueue;
import org.jctools.queues.MpscLinkedQueue;
import org.jctools.queues.SpmcArrayQueue;
import org.jctools.queues.SpscArrayQueue;
import org.jctools.queues.SpscGrowableArrayQueue;
import org.jctools.queues.SpscLinkedQueue;

public class QueueByTypeFactory {
  public static final int QUEUE_CAPACITY = 1 << Integer.getInteger("pow2.capacity", 17);
  public static final int QUEUE_TYPE = Integer.getInteger("q.type", 0);

  public static <T> Queue<T> createQueue() {
    final int queueCapacity = QUEUE_CAPACITY;
    return createQueue(queueCapacity);
  }

  public static <T> Queue<T> createQueue(final int queueCapacity) {
    int queueType = QUEUE_TYPE;
    return createQueue(queueType, queueCapacity);
  }

  public static <T> Queue<T> createQueue(int queueType, final int queueCapacity) {
    switch (queueType) {
      case -99:
        return new ArrayDeque<T>(queueCapacity);
      case -3:
        return new ArrayBlockingQueue<T>(queueCapacity);
      case -2:
        return new LinkedTransferQueue<T>();
      case -1:
        return new ConcurrentLinkedQueue<T>();
      case 3:
        return new SpscArrayQueue<T>(queueCapacity);
      case 31:
        return new SpscLinkedQueue<T>();
      case 32:
        return new SpscGrowableArrayQueue<T>(queueCapacity);
      case 5:
        return new SpmcArrayQueue<T>(queueCapacity);
      case 6:
        return new MpscArrayQueue<T>(queueCapacity);
      case 61:
        return new MpscCompoundQueue<T>(queueCapacity);
      case 63:
        return new MpscLinkedQueue<T>();
      case 7:
        return new MpmcArrayQueue<T>(queueCapacity);
      default:
    }

    throw new IllegalArgumentException("Type: " + queueType);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public static <T> Queue<T> createQueue(String queueType, final int queueCapacity) {
    try {
      int qType = Integer.valueOf(queueType);
      return createQueue(qType, queueCapacity);
    } catch (NumberFormatException e) {
    }
    Class qClass = queueClass(queueType);
    Constructor constructor;
    Exception ex;
    try {
      constructor = qClass.getConstructor(Integer.TYPE);
      return (Queue<T>) constructor.newInstance(queueCapacity);
    } catch (Exception e) {
      ex = e;
    }
    try {
      constructor = qClass.getConstructor();
      return (Queue<T>) constructor.newInstance();
    } catch (Exception e) {
      ex = e;
    }
    throw new IllegalArgumentException("Failed to construct queue:" + qClass.getName(), ex);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public static <T> Queue<T> createQueue(
      String queueType, final int chunkSize, final int queueCapacity) {
    Class qClass = queueClass(queueType);
    Constructor constructor;
    Exception ex;
    try {
      constructor = qClass.getConstructor(Integer.TYPE, Integer.TYPE);
      return (Queue<T>) constructor.newInstance(chunkSize, queueCapacity);
    } catch (Exception e) {
      ex = e;
    }

    throw new IllegalArgumentException("Failed to construct queue:" + qClass.getName(), ex);
  }

  @SuppressWarnings("rawtypes")
  private static Class queueClass(String queueType) {
    try {
      return Class.forName("org.jctools.queues." + queueType);
    } catch (ClassNotFoundException e) {
    }
    try {
      return Class.forName("org.jctools.queues.atomic." + queueType);
    } catch (ClassNotFoundException e) {
    }
    try {
      return Class.forName("java.util." + queueType);
    } catch (ClassNotFoundException e) {
    }
    try {
      return Class.forName("java.util.concurrent." + queueType);
    } catch (ClassNotFoundException e) {
    }
    try {
      return Class.forName(queueType);
    } catch (ClassNotFoundException e) {
    }
    throw new IllegalArgumentException("class not found:");
  }

  public static <T> Queue<T> buildQ(String qType, String qCapacity) {
    try {
      int capacity = Integer.valueOf(qCapacity);
      return createQueue(qType, capacity);
    } catch (Exception e) {
    }

    try {
      String[] args = qCapacity.split("\\.");
      int chunk = Integer.valueOf(args[0]);
      int capacity = Integer.valueOf(args[1]);
      return createQueue(qType, chunk, capacity);
    } catch (Exception e) {
      throw new IllegalArgumentException("Failed to parse qCapacity", e);
    }
  }
}
