package MonteCarlo;

import java.util.Date;

public class Consumer implements Runnable{
    private Buffer<Double> buffer;
    
    public Consumer(Buffer<Double> buffer){
        this.buffer = buffer;
    }
    
    @Override
    public void run(){
        Double pi;

        while(true){
            SleepUtilities.nap(1000);
            pi = buffer.remove();
            System.out.println("PI value is " + pi);
        }
    }
    
}
