package DiningPhilosophers;

public class Main {
    public static void main(String[] args) {
        DiningPhilosophers diningPhilosophers = new DiningPhilosophers();

        for (int i = 0; i < 5; i++) {
            final int philosopherIndex = i;
            new Thread(() -> {
                try {
                    while (true) {
                        // Philosopher is thinking
                        System.out.println("Philosopher " + philosopherIndex + " is thinking");
                        Thread.sleep((long) (Math.random() * 1000)); // Simulate thinking

                        // Philosopher wants to eat
                        System.out.println("Philosopher " + philosopherIndex + " wants to eat");
                        diningPhilosophers.takeForks(philosopherIndex);
                        System.out.println("Philosopher " + philosopherIndex + " is eating");
                        Thread.sleep((long) (Math.random() * 1000)); // Simulate eating

                        // Philosopher is done eating, return forks
                        System.out.println("Philosopher " + philosopherIndex + " is done eating");
                        diningPhilosophers.returnForks(philosopherIndex);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
