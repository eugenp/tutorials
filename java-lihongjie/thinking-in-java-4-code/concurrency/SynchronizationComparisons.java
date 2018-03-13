//: concurrency/SynchronizationComparisons.java
// Comparing the performance of explicit Locks
// and Atomics versus the synchronized keyword.
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.*;
import static net.mindview.util.Print.*;

abstract class Accumulator {
  public static long cycles = 50000L;
  // Number of Modifiers and Readers during each test:
  private static final int N = 4;
  public static ExecutorService exec =
    Executors.newFixedThreadPool(N*2);
  private static CyclicBarrier barrier =
    new CyclicBarrier(N*2 + 1);
  protected volatile int index = 0;
  protected volatile long value = 0;
  protected long duration = 0;
  protected String id = "error";
  protected final static int SIZE = 100000;
  protected static int[] preLoaded = new int[SIZE];
  static {
    // Load the array of random numbers:
    Random rand = new Random(47);
    for(int i = 0; i < SIZE; i++)
      preLoaded[i] = rand.nextInt();
  }
  public abstract void accumulate();
  public abstract long read();
  private class Modifier implements Runnable {
    public void run() {
      for(long i = 0; i < cycles; i++)
        accumulate();
      try {
        barrier.await();
      } catch(Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
  private class Reader implements Runnable {
    private volatile long value;
    public void run() {
      for(long i = 0; i < cycles; i++)
        value = read();
      try {
        barrier.await();
      } catch(Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
  public void timedTest() {
    long start = System.nanoTime();
    for(int i = 0; i < N; i++) {
      exec.execute(new Modifier());
      exec.execute(new Reader());
    }
    try {
      barrier.await();
    } catch(Exception e) {
      throw new RuntimeException(e);
    }
    duration = System.nanoTime() - start;
    printf("%-13s: %13d\n", id, duration);
  }
  public static void
  report(Accumulator acc1, Accumulator acc2) {
    printf("%-22s: %.2f\n", acc1.id + "/" + acc2.id,
      (double)acc1.duration/(double)acc2.duration);
  }
}

class BaseLine extends Accumulator {
  { id = "BaseLine"; }
  public void accumulate() {
    value += preLoaded[index++];
    if(index >= SIZE) index = 0;
  }
  public long read() { return value; }
}

class SynchronizedTest extends Accumulator {
  { id = "synchronized"; }
  public synchronized void accumulate() {
    value += preLoaded[index++];
    if(index >= SIZE) index = 0;
  }
  public synchronized long read() {
    return value;
  }
}

class LockTest extends Accumulator {
  { id = "Lock"; }
  private Lock lock = new ReentrantLock();
  public void accumulate() {
    lock.lock();
    try {
      value += preLoaded[index++];
      if(index >= SIZE) index = 0;
    } finally {
      lock.unlock();
    }
  }
  public long read() {
    lock.lock();
    try {
      return value;
    } finally {
      lock.unlock();
    }
  }
}

class AtomicTest extends Accumulator {
  { id = "Atomic"; }
  private AtomicInteger index = new AtomicInteger(0);
  private AtomicLong value = new AtomicLong(0);
  public void accumulate() {
    // Oops! Relying on more than one Atomic at
    // a time doesn't work. But it still gives us
    // a performance indicator:
    int i = index.getAndIncrement();
    value.getAndAdd(preLoaded[i]);
    if(++i >= SIZE)
      index.set(0);
  }
  public long read() { return value.get(); }
}

public class SynchronizationComparisons {
  static BaseLine baseLine = new BaseLine();
  static SynchronizedTest synch = new SynchronizedTest();
  static LockTest lock = new LockTest();
  static AtomicTest atomic = new AtomicTest();
  static void test() {
    print("============================");
    printf("%-12s : %13d\n", "Cycles", Accumulator.cycles);
    baseLine.timedTest();
    synch.timedTest();
    lock.timedTest();
    atomic.timedTest();
    Accumulator.report(synch, baseLine);
    Accumulator.report(lock, baseLine);
    Accumulator.report(atomic, baseLine);
    Accumulator.report(synch, lock);
    Accumulator.report(synch, atomic);
    Accumulator.report(lock, atomic);
  }
  public static void main(String[] args) {
    int iterations = 5; // Default
    if(args.length > 0) // Optionally change iterations
      iterations = new Integer(args[0]);
    // The first time fills the thread pool:
    print("Warmup");
    baseLine.timedTest();
    // Now the initial test doesn't include the cost
    // of starting the threads for the first time.
    // Produce multiple data points:
    for(int i = 0; i < iterations; i++) {
      test();
      Accumulator.cycles *= 2;
    }
    Accumulator.exec.shutdown();
  }
} /* Output: (Sample)
Warmup
BaseLine     :      34237033
============================
Cycles       :         50000
BaseLine     :      20966632
synchronized :      24326555
Lock         :      53669950
Atomic       :      30552487
synchronized/BaseLine : 1.16
Lock/BaseLine         : 2.56
Atomic/BaseLine       : 1.46
synchronized/Lock     : 0.45
synchronized/Atomic   : 0.79
Lock/Atomic           : 1.76
============================
Cycles       :        100000
BaseLine     :      41512818
synchronized :      43843003
Lock         :      87430386
Atomic       :      51892350
synchronized/BaseLine : 1.06
Lock/BaseLine         : 2.11
Atomic/BaseLine       : 1.25
synchronized/Lock     : 0.50
synchronized/Atomic   : 0.84
Lock/Atomic           : 1.68
============================
Cycles       :        200000
BaseLine     :      80176670
synchronized :    5455046661
Lock         :     177686829
Atomic       :     101789194
synchronized/BaseLine : 68.04
Lock/BaseLine         : 2.22
Atomic/BaseLine       : 1.27
synchronized/Lock     : 30.70
synchronized/Atomic   : 53.59
Lock/Atomic           : 1.75
============================
Cycles       :        400000
BaseLine     :     160383513
synchronized :     780052493
Lock         :     362187652
Atomic       :     202030984
synchronized/BaseLine : 4.86
Lock/BaseLine         : 2.26
Atomic/BaseLine       : 1.26
synchronized/Lock     : 2.15
synchronized/Atomic   : 3.86
Lock/Atomic           : 1.79
============================
Cycles       :        800000
BaseLine     :     322064955
synchronized :     336155014
Lock         :     704615531
Atomic       :     393231542
synchronized/BaseLine : 1.04
Lock/BaseLine         : 2.19
Atomic/BaseLine       : 1.22
synchronized/Lock     : 0.47
synchronized/Atomic   : 0.85
Lock/Atomic           : 1.79
============================
Cycles       :       1600000
BaseLine     :     650004120
synchronized :   52235762925
Lock         :    1419602771
Atomic       :     796950171
synchronized/BaseLine : 80.36
Lock/BaseLine         : 2.18
Atomic/BaseLine       : 1.23
synchronized/Lock     : 36.80
synchronized/Atomic   : 65.54
Lock/Atomic           : 1.78
============================
Cycles       :       3200000
BaseLine     :    1285664519
synchronized :   96336767661
Lock         :    2846988654
Atomic       :    1590545726
synchronized/BaseLine : 74.93
Lock/BaseLine         : 2.21
Atomic/BaseLine       : 1.24
synchronized/Lock     : 33.84
synchronized/Atomic   : 60.57
Lock/Atomic           : 1.79
*///:~
