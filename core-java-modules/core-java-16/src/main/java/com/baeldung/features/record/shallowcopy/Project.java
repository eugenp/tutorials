package com.baeldung.features.record.shallowcopy;

import java.util.List;

public record Project(List<Task> tasks) {
}
