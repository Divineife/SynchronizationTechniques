package DiningPhilosophers;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers {
    public enum State  {
        THINKING, HUNGRY , EATING
    }
    private Lock lock = new ReentrantLock();
    State[] states = new State[5];
    Condition[] self = new Condition[5];
    
    public DiningPhilosophers(){
        
        for (int i=0; i<5; i++ ){
            states[i] = State.THINKING;
            self[i] = lock.newCondition();
        }
    }

    public void takeForks(int i){
        lock.lock();
        try {
            states[i] = State.HUNGRY;
            test(i);
            System.out.println("Your state is " + states[i]);
            while (states[i] != State.EATING) {
                try {
                    self[i].await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            lock.unlock();
        }
    }
    

    public synchronized void returnForks(int i){
        states[i] = State.THINKING;
        test((i + 4) % 5);
        test((i + 1) % 5);
        notifyAll();
    }

    private void test(int i) {
        lock.lock(); 
        try {
            if (states[(i + 1) % 5] != State.EATING && 
                states[(i + 4) % 5] != State.EATING && 
                states[i] == State.HUNGRY) {
                states[i] = State.EATING;
                self[i].signal();
            }
        } finally {
            lock.unlock();
    }
}

}
