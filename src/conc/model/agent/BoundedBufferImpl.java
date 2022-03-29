package conc.model.agent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBufferImpl<T> implements BoundedBuffer<T>{

    private final T[] buffer;
    private int in; // points to the next free position
    private int out; // points to the next full position
    private final Lock mutex;
    private final Condition notEmpty, notFull;

    public BoundedBufferImpl(int size) {
        in = 0;
        out = 0;
        buffer = (T[]) new Object[size];
        mutex = new ReentrantLock();
        notEmpty = mutex.newCondition();
        notFull = mutex.newCondition();
    }

    public void add(T item) throws InterruptedException {
        try {
            mutex.lock();
            if (isFull()) {
                notFull.await();
            }
            buffer[in] = item;
            in = (in + 1) % buffer.length;
            if (wasEmpty()) {
                notEmpty.signal();
            }
        } finally {
            mutex.unlock();
        }
    }

    public T get() throws InterruptedException {
        try {
            mutex.lock();
            if (isEmpty()) {
                notEmpty.await();
            }
            T item = buffer[out];
            out = (out + 1) % buffer.length;
            if (wasFull()) {
                notFull.signal();
            }
            return item;
        } finally {
            mutex.unlock();
        }
    }

    private boolean isFull() {
        return (in + 1) % buffer.length == out;
    }

    private boolean wasFull() {
        return out == (in + 2) % buffer.length;
    }

    private boolean isEmpty() {
        return in == out;
    }

    private boolean wasEmpty() {
        return in == (out + 1) % buffer.length;
    }
}
