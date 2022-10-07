package com.baeldung.features;

import com.baeldung.features.record.shallowcopy.Project;
import com.baeldung.features.record.shallowcopy.Task;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class RecordShallowCopyUnitTest {

    @Test
    void whenModifyingList_thenCopyShouldChange() {
        var tasks = new ArrayList<Task>();
        tasks.add(new Task("The Title", "The description"));
        var project = new Project(tasks);

        var anotherTask = new Task("Another Title", "Another description");
        tasks.add(anotherTask);

        assertThat(project.tasks())
          .hasSize(2)
          .contains(anotherTask);
    }
}
