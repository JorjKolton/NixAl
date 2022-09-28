package com.nixal.ssobchenko.model;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Factory {
    private static final Logger LOGGER = LoggerFactory.getLogger(Factory.class);
    private static final Random RANDOM = new Random();

    private int fuel;
    private int detail;

    @SneakyThrows
    public void startFactory() {
        Thread firstRobot = new Thread(robot1);
        firstRobot.setDaemon(true);
        final long start = System.currentTimeMillis();
        firstRobot.start();

        Thread secondRobot2 = new Thread(robot23);
        secondRobot2.setName("Robot 2");
        Thread thirdRobot3 = new Thread(robot23);
        thirdRobot3.setName("Robot 3");
        secondRobot2.start();
        thirdRobot3.start();
        secondRobot2.join();
        thirdRobot3.join();

        Thread fourthRobot = new Thread(robot4);
        fourthRobot.start();
        fourthRobot.join();

        Thread fifthRobot = new Thread(robot5);
        fifthRobot.start();
        fifthRobot.join();

        final long end = System.currentTimeMillis();
        System.out.println("Duration: " + (end - start));
    }

    public synchronized void addFuel(int fuel) {
        this.fuel += fuel;
    }

    public synchronized void takeFuel(int fuel) {
        this.fuel -= fuel;
    }

    public synchronized void addDetail(int detail) {
        this.detail += detail;
    }

    Runnable robot1 = new Runnable() {

        @SneakyThrows
        @Override
        public void run() {
            int plug = 0;
            int min = 500;
            int max = 1000;
            System.out.println("Robot 1 start work");
            while (plug < 50) {
                TimeUnit.SECONDS.sleep(3);
                int addingFuel = RANDOM.nextInt(max - min + 1) + min;
                addFuel(addingFuel);
                LOGGER.info("Robot 1 added {} fuel. Fuel = {}", addingFuel, fuel);
                plug++;
            }
        }
    };

    Runnable robot23 = new Runnable() {

        @SneakyThrows
        @Override
        public void run() {
            int min = 10;
            int max = 20;
            System.out.println(Thread.currentThread().getName() + " start work");
            while (detail < 100) {
                int addingDetail = RANDOM.nextInt(max - min + 1) + min;
                addDetail(addingDetail);
                LOGGER.info("{} collected the detail for {}. Detail = {}", Thread.currentThread().getName(),
                        addingDetail, detail);
                TimeUnit.SECONDS.sleep(2);
            }
            System.out.println(Thread.currentThread().getName() + " finished work");
        }
    };

    Runnable robot4 = new Runnable() {
        private int chip;

        @SneakyThrows
        @Override
        public void run() {
            int min = 25;
            int max = 35;
            System.out.println("Robot 4 start work");
            while (chip < 100) {
                if (RANDOM.nextInt(101) <= 30) {
                    LOGGER.info("Robot 4 broke the chip");
                } else {
                    int programmingChip = RANDOM.nextInt(max - min + 1) + min;
                    chip += programmingChip;
                    LOGGER.info("Robot 4 programmed the chip for {}. Chip = {}", programmingChip, chip);
                }
                TimeUnit.SECONDS.sleep(1);
            }
            System.out.println("Robot 4 finished work");
        }
    };

    Runnable robot5 = new Runnable() {
        private int finishedDetail = 0;

        @SneakyThrows
        @Override
        public void run() {
            int min = 350;
            int max = 700;
            System.out.println("Robot 5 start work");
            while (finishedDetail < 100) {
                int requiredFuel = RANDOM.nextInt(max - min + 1) + min;
                if (requiredFuel <= fuel) {
                    takeFuel(requiredFuel);
                    finishedDetail += 10;
                    LOGGER.info("Robot 5 took {} fuel. Fuel = {}", requiredFuel, fuel);
                } else {
                    LOGGER.info("Not enough fuel to run the Robot 5");
                }
                TimeUnit.SECONDS.sleep(1);
            }
            System.out.println("Robot 5 finished work");
        }
    };
}