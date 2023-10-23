package MonteCarlo;

public class factory {
    public static void main(String[] args) {
        Buffer<Double> buffer = new BoundedBuffer<Double>();

        Thread consumer = new Thread(new Consumer(buffer));
        
        
        for (int i = 0; i < 10; i++) {
            Thread producer = new Thread(new Producer(buffer, i));
            producer.start();
        }
        consumer.start();  
    }
}
