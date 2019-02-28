package Ibox.Ibox;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

public class IboxApplication {

    public static void main(String[] args) throws IOException {

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        IboxSync iboxSync = new IboxSync();
        final Runnable task = new Runnable() {
            public void run() {
//                System.out.println("Ibox synchronizing...");
                try {
                    iboxSync.start();
                } catch (IOException e) { e.printStackTrace(); }
            }
        };
        scheduler.scheduleAtFixedRate(task, 3, 10, SECONDS);
    }

}
