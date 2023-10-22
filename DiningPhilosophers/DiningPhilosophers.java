package DiningPhilosophers;

import java.util.concurrent.locks.Condition;

public class DiningPhilosophers {
    public enum State  {
        THINKING, HUNGRY , EATING
    }
    State[] states = new State[5];
    Condition[] self = new Condition[5];

    public DiningPhilosophers(){
        
        for (int i=0; i<5; i++ ){
            states[i] = State.THINKING;
        }
    }

    public synchronized void takeForks(int i){
        states[i] = State.HUNGRY;
        test(i);
        // if (states[i] != State.EATING){
        //     try {
        //         self[i].wait();
        //     } catch (InterruptedException e) {
        //         e.printStackTrace();
        //     }
        // }
        while (states[i] != State.EATING){
            try{
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public synchronized void returnForks(int i){
        states[i] = State.THINKING;
        test((i + 4) % 5);
        test((i + 1) % 5);
        notifyAll();
    }

    private void test(int i) {
        if ( (states[(i+4) % 5] != State.EATING) && (states[i] == State.HUNGRY)
        && (states[(i+1) % 5] != State.EATING)){
            states[i] = State.EATING;
            self[i].signal();
        }
    }
}
