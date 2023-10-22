package Synchronization;

public class factory {
    public static void main(String[] args) {
        Buffer<Integer> buffer = new BoundedBuffer<Integer>();

        Thread producer = new Thread(new Producer(buffer));
        Thread consumer = new Thread(new Consumer(buffer));

        producer.start();
        consumer.start();  
    }
}
