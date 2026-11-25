package com.baeldung.quizzard;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.OperationContext;
import com.embabel.agent.core.CoreToolGroups;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.agent.domain.library.Blog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.Charset;

@Agent(
    name = "quizzard",
    description = "Generate multiple choice quizzes from blogs"
)
class QuizGeneratorAgent {

    private final Resource promptTemplate;

    QuizGeneratorAgent(@Value("classpath:prompt-templates/quiz-generation.txt") Resource promptTemplate) {
        this.promptTemplate = promptTemplate;
    }

    @Action
    Blog fetchBlogContent(UserInput userInput, OperationContext context) {
        return context.ai()
            .withDefaultLlm()
            .withToolGroup(CoreToolGroups.WEB)
            .createObject("""
                Find the URL in the following request and fetch that page: '%s'
                If the page is returned in parts, fetch all parts.
                Return the blog's title and author. In content, write detailed study notes covering
                every section of the article: key concepts, facts and code examples, in your own words.
                Do not create questions or a quiz.
                """.formatted(userInput.getContent()),
                Blog.class
            );
    }

    @Action
    @AchievesGoal(description = "Quiz has been generated")
    Quiz generateQuiz(Blog blog, OperationContext context) throws IOException {
        String prompt = promptTemplate
            .getContentAsString(Charset.defaultCharset())
            .formatted(
                blog.getTitle(),
                blog.getContent()
            );
        return context.ai()
            .withDefaultLlm()
            .createObject(
                prompt,
                Quiz.class
            );
    }

}
