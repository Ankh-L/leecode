package lock;

import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;

import java.util.concurrent.*;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author linyw
 */
public class LockTest {
    ReentrantLock reentrantLock = new ReentrantLock();
    Object lockObj = new Object();
    volatile int count = 0;

    @Test
    public void SemaphoreTest() throws InterruptedException, ExecutionException {
        final Semaphore semaphore = new Semaphore(4);
        CompletableFuture<Void> v1 = CompletableFuture.runAsync(() -> {
            // 获取1个许可
            semaphore.acquireUninterruptibly();
            try {
                //do short work
                TimeUnit.SECONDS.sleep(3);
                System.out.println("short work finish");
            } catch (InterruptedException e) {
            } finally {
                // 释放1个许可
                semaphore.release();
            }
        });

        CompletableFuture<Void> v2 = CompletableFuture.runAsync(() -> {
            // 获取1个许可
            semaphore.acquireUninterruptibly(4);
            try {
                //do long work
                TimeUnit.SECONDS.sleep(5);
                System.out.println("long work finish");
            } catch (InterruptedException e) {
            } finally {
                // 释放1个许可
                semaphore.release(4);
            }
        });
        CompletableFuture.allOf(v1, v2).get();

    }

    @Test
    public void nomalAdd() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(200);
        for (int i = 0; i < 200; i++) {
            CompletableFuture.runAsync(() -> {
                count++;
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        System.out.println(count);
    }

    class MySync extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            return super.tryAcquire(arg);
        }

        @Override
        protected boolean tryRelease(int arg) {
            return super.tryRelease(arg);
        }

        @Override
        protected int tryAcquireShared(int arg) {
            return super.tryAcquireShared(arg);
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            return super.tryReleaseShared(arg);
        }
    }

    @Test
    public void reentrantTest() {
        synchronized (lockObj) {
            synchronized (lockObj) {
                System.out.println("can reentrant");
            }
        }
    }

    @Test
    public void f() {
        reentrantLock.lock();
        try {

        } finally {
            reentrantLock.unlock();
        }
    }
}
