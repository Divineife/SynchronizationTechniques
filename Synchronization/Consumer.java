package Synchronization;

import java.util.Date;

public class Consumer implements Runnable{
    private Buffer<Integer> buffer;
    
    public Consumer(Buffer<Integer> buffer){
        this.buffer = buffer;
    }
    
    @Override
    public void run(){
        int randomNumber;

        while(true){
            SleepUtilities.nap(3000);
            randomNumber = buffer.remove();
            System.out.println("CONSUMER CONSUMED " + randomNumber);
        }
    }
    
}
