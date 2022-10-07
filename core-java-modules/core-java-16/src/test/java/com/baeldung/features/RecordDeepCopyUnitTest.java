package com.baeldung.features;

import com.baeldung.features.record.deepcopy.Project;
import com.baeldung.features.record.deepcopy.Task;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class RecordDeepCopyUnitTest {

    @Test
    void whenModifyingList_thenCopyShouldChange() {
        var tasks = new ArrayList<Task>();
        tasks.add(new Task("The Title", "The description"));
        var project = new Project(tasks);
        var copy = project.copy();

        var anotherTask = new Task("Another Title", "Another description");
        tasks.add(anotherTask);

        assertThat(project.tasks()).hasSize(1)
          .doesNotContain(anotherTask);

        assertThat(copy.tasks()).hasSize(1)
          .doesNotContain(anotherTask);
    }
}
