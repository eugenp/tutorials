package com.baeldung.mesos.executors;

import org.apache.mesos.Executor;
import org.apache.mesos.ExecutorDriver;
import org.apache.mesos.MesosExecutorDriver;
import org.apache.mesos.Protos;
import org.apache.mesos.Protos.TaskInfo;

public class HelloWorldExecutor implements Executor {
    @Override
    public void registered(ExecutorDriver driver, Protos.ExecutorInfo executorInfo, Protos.FrameworkInfo frameworkInfo, Protos.SlaveInfo slaveInfo) {
    }

    @Override
    public void reregistered(ExecutorDriver driver, Protos.SlaveInfo slaveInfo) {
    }

    @Override
    public void disconnected(ExecutorDriver driver) {
    }

    @Override
    public void launchTask(ExecutorDriver driver, TaskInfo task) {

        Protos.TaskStatus status = Protos.TaskStatus.newBuilder().setTaskId(task.getTaskId())
                .setState(Protos.TaskState.TASK_RUNNING).build();
        driver.sendStatusUpdate(status);

        String myStatus = "Hello Framework";
        driver.sendFrameworkMessage(myStatus.getBytes());

        System.out.println("Hello World!!!");

        status = Protos.TaskStatus.newBuilder().setTaskId(task.getTaskId())
                .setState(Protos.TaskState.TASK_FINISHED).build();
        driver.sendStatusUpdate(status);
    }

    @Override
    public void killTask(ExecutorDriver driver, Protos.TaskID taskId) {
    }

    @Override
    public void frameworkMessage(ExecutorDriver driver, byte[] data) {
    }

    @Override
    public void shutdown(ExecutorDriver driver) {
    }

    @Override
    public void error(ExecutorDriver driver, String message) {
    }

    public static void main(String[] args) {
        MesosExecutorDriver driver = new MesosExecutorDriver(new HelloWorldExecutor());
        System.exit(driver.run() == Protos.Status.DRIVER_STOPPED ? 0 : 1);
    }
}
