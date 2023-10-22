package Synchronization;
import java.util.Date;

public class Producer implements Runnable {
    private Buffer<Integer> buffer;

    public Producer(Buffer<Integer> buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        Date message;

        while(true){
            SleepUtilities.nap(1000);
            int randomNumber = (int) (Math.random() * 100);
            buffer.insert(randomNumber);
            System.out.println("PRODUCER PRODUCED " + randomNumber);
        }
    }
    
}
