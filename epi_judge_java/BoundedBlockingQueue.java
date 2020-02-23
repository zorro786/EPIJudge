import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Abdul Qadeer (abqadeer@cisco.com) on Jan, 2020
 */
class BoundedBlockingQueue {

    private final Lock lock;
    private final Condition notEmpty;
    private final Condition notFull;
    private final int[] q;
    private int size = 0;
    private final int cap;
    private int head, tail;

    public BoundedBlockingQueue(int capacity) {
        lock = new ReentrantLock();
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();
        q = new int[capacity];
        cap = capacity;
        head = tail = 0;
    }

    public void enqueue(int element) throws InterruptedException {
        lock.lock();
        try {
            while (size == cap) {
                notFull.await();
            }
            q[tail] = element;
            size++;
            tail = (tail+1)%cap;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public int dequeue() throws InterruptedException {
        synchronized (lock) {
            while (size == 0) {
                notEmpty.await();
            }
            int res = q[head];
            head = (head+1)%cap;
            size--;
            notFull.signal();
            return res;
        }
    }

    public int size() {
        return size;
    }

    public static void main(String args[]) throws InterruptedException {
        BoundedBlockingQueue bbq = new BoundedBlockingQueue(1);
        bbq.enqueue(1);
    }
}
