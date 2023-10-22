package Synchronization;

public class SleepUtilities {
    public static void nap(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

