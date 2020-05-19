package sample;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.awt.*;
import java.time.LocalTime;

public class Progress implements Runnable {

   // private int frequency;

    //public Integer setFrequency(String freq) {
        //frequency=Integer.parseInt(freq)*60*100;
        //    return frequency;
    //}


    final Service thread = new Service() {

        @Override
        protected Task createTask() {
            return new Task() {

                @Override
                protected Integer call() throws InterruptedException {
                    int i=0;
                    worker = new Thread(Progress.this, " Clock thread");
                    worker.start();
                    Thread.sleep(interval);
                    return i;
                }
            };
        }
    };

    private Thread worker;
    protected volatile boolean isRunning = false;
    private int interval;

    public Progress() {
        interval = 5000;
    }

    public Progress(String interval) {
        this.interval = setInterval(interval);
    }


    public int getInterval() {
        return interval;
    }

    public Integer setInterval(String freq) {
        interval=Integer.parseInt(freq)*60*1000;
        return interval;
    }


    public void start() {
        worker = new Thread(Progress.this, " Clock thread");
        worker.start();

    }

    public void stop() {
        isRunning = false;
    }

    public void interrupt() {

        isRunning = false;
        worker.interrupt();
    }


    @Override
    public void run() {
        isRunning = true;
        while (isRunning) {

            try {
                System.out.println(LocalTime.now());
                Thread.sleep(interval);
                isRunning = false;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Failed to complete operation");
            }

        }
        isRunning=false;

    }
}
