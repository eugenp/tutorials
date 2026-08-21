package com.baeldung.springai.agentskills.anthropic;

import javax.validation.constraints.NotNull;

public record ReportRequest(@NotNull String prompt) {
}
