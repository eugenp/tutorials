package com.baeldung.springai.moderation;

import org.springframework.ai.moderation.*;
import org.springframework.ai.openai.OpenAiModerationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TextModerationService {

    private final OpenAiModerationModel openAiModerationModel;

    @Autowired
    public TextModerationService(OpenAiModerationModel openAiModerationModel) {
        this.openAiModerationModel = openAiModerationModel;
    }

    public String moderate(String text) {
        ModerationPrompt moderationRequest = new ModerationPrompt(text);
        ModerationResponse response = openAiModerationModel.call(moderationRequest);
        Moderation output = response.getResult().getOutput();

        return output.getResults().stream()
          .map(this::buildModerationResult)
          .collect(Collectors.joining("\n"));
    }

    private String buildModerationResult(ModerationResult moderationResult) {

        Categories categories = moderationResult.getCategories();

        String violations = Stream.of(
              Map.entry("Sexual", categories.isSexual()),
              Map.entry("Hate", categories.isHate()),
              Map.entry("Harassment", categories.isHarassment()),
              Map.entry("Self-Harm", categories.isSelfHarm()),
              Map.entry("Sexual/Minors", categories.isSexualMinors()),
              Map.entry("Hate/Threatening", categories.isHateThreatening()),
              Map.entry("Violence/Graphic", categories.isViolenceGraphic()),
              Map.entry("Self-Harm/Intent", categories.isSelfHarmIntent()),
              Map.entry("Self-Harm/Instructions", categories.isSelfHarmInstructions()),
              Map.entry("Harassment/Threatening", categories.isHarassmentThreatening()),
              Map.entry("Violence", categories.isViolence()))
          .filter(entry -> Boolean.TRUE.equals(entry.getValue()))
          .map(Map.Entry::getKey)
          .collect(Collectors.joining(", "));

        return violations.isEmpty()
          ? "No category violations detected."
          : "Violated categories: " + violations;
    }
}
