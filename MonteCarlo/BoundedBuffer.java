package MonteCarlo;

import java.util.concurrent.Semaphore;

public class BoundedBuffer<E> implements Buffer<E>{
    private static final int BUFFER_SIZE = 5;
    private E[] buffer;
    private int in, out;
    private Semaphore mutex;
    private Semaphore empty;
    private Semaphore full;

    public BoundedBuffer(){
        in = 0;
        out = 0;
        mutex = new Semaphore(1);
        empty = new Semaphore(BUFFER_SIZE);
        full = new Semaphore(0);
        buffer = (E[]) new Object[BUFFER_SIZE];
        
    }

    public void insert(E item, int id){
        try {
            empty.acquire();
            mutex.acquire();
            buffer[in] = item;
            in = (in + 1) % BUFFER_SIZE;

            mutex.release();
            full.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public E remove() {
        E item = null; // Initialize item with a default value
    
        try {
            full.acquire();
            mutex.acquire();
            item = buffer[out];
            out = (out + 1) % BUFFER_SIZE;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mutex.release();
            empty.release();
        }
    
        return item;
    }
    
}