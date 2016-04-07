package forkJoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

public class CustomRecursiveAction extends RecursiveAction {

    private long workLoad = 0;

    public CustomRecursiveAction(long workLoad) {
        this.workLoad = workLoad;
    }

    @Override
    protected void compute() {

        if(this.workLoad > 16) {
            ForkJoinTask.invokeAll(createSubtasks());
        } else {
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private List<CustomRecursiveAction> createSubtasks() {
        List<CustomRecursiveAction> subtasks =
                new ArrayList<CustomRecursiveAction>();

        subtasks.add(new CustomRecursiveAction(this.workLoad / 2));
        subtasks.add(new CustomRecursiveAction(this.workLoad / 2));

        return subtasks;
    }
}
