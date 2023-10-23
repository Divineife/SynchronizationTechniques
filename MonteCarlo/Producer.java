package MonteCarlo;
import java.util.Date;

public class Producer implements Runnable {
    private Buffer<Double> buffer;
    private int id; 

    public Producer(Buffer<Double> buffer, int id) {
        this.buffer = buffer;
        this.id = id;
    }

    @Override
    public void run() {
        int numberOfCoordinates = 100;
        double circleRadius = 1;
        double circleCenterX = 1;
        double circleCenterY = 1;
        int pointsInsideCircle = 0;
        
        for (int i = 0; i < numberOfCoordinates; i++) {
        double xCoordinate = Math.random() * 2;
        double yCoordinate = Math.random() * 2;

        double distance = Math.sqrt(Math.pow((xCoordinate - circleCenterX), 2) + Math.pow((yCoordinate - circleCenterY), 2));
        if (distance <= circleRadius) {
            pointsInsideCircle++;
        }
        }
        double pi = 4 * ((double) pointsInsideCircle / numberOfCoordinates);
        buffer.insert(pi);
        System.out.println("Producer " + id + " produced " + pi);
    }
    
}
