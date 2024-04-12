package com.baeldung.finallykeyword;

public class FinallyNotExecutedCases {

    public void callingSystemExit() {
        try {
            System.out.println("Inside try");
            System.exit(1);
        } finally {
            System.out.println("Inside finally");
        }
    }

    public void callingRuntimeHalt() {
        try {
            System.out.println("Inside try");
            Runtime.getRuntime()
                .halt(1);
        } finally {
            System.out.println("Inside finally");
        }
    }

    public void infiniteLoop() {
        try {
            System.out.println("Inside try");
            while (true) {
            }
        } finally {
            System.out.println("Inside finally");
        }
    }

    public void daemonThread() throws InterruptedException {
        Runnable runnable = () -> {
            try {
                System.out.println("Inside try");
            } finally {
                try {
                    Thread.sleep(1000);
                    System.out.println("Inside finally");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread regular = new Thread(runnable);
        Thread daemon = new Thread(runnable);
        daemon.setDaemon(true);
        regular.start();
        Thread.sleep(300);
        daemon.start();
    }
}
