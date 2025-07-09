package com.baeldung.quizzard;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.PromptRunner;
import com.embabel.agent.core.CoreToolGroups;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.agent.domain.library.Blog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.Charset;

@Agent(
    name = "quizzard",
    description = "Generate multiple choice quizzes from documents"
)
class QuizGeneratorAgent {

    private final Resource promptTemplate;

    QuizGeneratorAgent(@Value("classpath:prompt-templates/quiz-generation.txt") Resource promptTemplate) {
        this.promptTemplate = promptTemplate;
    }

    @Action(toolGroups = CoreToolGroups.WEB)
    Blog fetchBlogContent(UserInput userInput) {
        return PromptRunner
            .usingLlm()
            .createObject(
                "Fetch the blog content from the URL given in the following request: '%s'".formatted(userInput),
                Blog.class
            );
    }

    @Action
    @AchievesGoal(description = "Quiz has been generated")
    Quiz generateQuiz(Blog blog) throws IOException {
        String prompt = promptTemplate
            .getContentAsString(Charset.defaultCharset())
            .formatted(
                blog.getTitle(),
                blog.getContent()
            );
        return PromptRunner
            .usingLlm()
            .createObject(
                prompt,
                Quiz.class
            );
    }

}