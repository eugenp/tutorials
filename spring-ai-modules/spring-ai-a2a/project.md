

===== pom.xml =====

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>com.baeldung</groupId>
        <artifactId>spring-ai-modules</artifactId>
        <version>0.0.1</version>
        <relativePath>../pom.xml</relativePath>
    </parent>

    <groupId>com.baeldung</groupId>
    <artifactId>spring-ai-a2a</artifactId>
    <version>0.0.1</version>
    <name>spring-ai-a2a</name>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webmvc</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.ai</groupId>
            <artifactId>spring-ai-starter-model-openai</artifactId>
            <version>${spring-ai.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springaicommunity</groupId>
            <artifactId>spring-ai-a2a-server-autoconfigure</artifactId>
            <version>${spring-ai-a2a-server-config.version}</version>
        </dependency>
        <dependency>
            <groupId>io.github.a2asdk</groupId>
            <artifactId>a2a-java-sdk-client</artifactId>
            <version>${a2a-client.version}</version>
        </dependency>
    </dependencies>

    <properties>
        <java.version>21</java.version>
        <spring-boot.version>4.0.6</spring-boot.version>
        <spring-ai.version>2.0.0</spring-ai.version>
        <spring-ai-a2a-server-config.version>0.3.0</spring-ai-a2a-server-config.version>
        <a2a-client.version>0.3.3.Final</a2a-client.version>
    </properties>

    <profiles>
        <profile>
            <id>skills-matcher-server</id>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <properties>
                <spring.boot.mainclass>com.baeldung.a2a.server.skillsmatcher.SkillsMatcherServer</spring.boot.mainclass>
            </properties>
        </profile>
        <profile>
            <id>salary-evaluator-server</id>
            <properties>
                <spring.boot.mainclass>com.baeldung.a2a.server.salaryevaluator.SalaryEvaluatorServer</spring.boot.mainclass>
            </properties>
        </profile>
        <profile>
            <id>job-screening-orchestrator-client</id>
            <properties>
                <spring.boot.mainclass>com.baeldung.a2a.client.orchestrator.jobscreening.JobScreeningOrchestrator</spring.boot.mainclass>
            </properties>
        </profile>
    </profiles>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <mainClass>${spring.boot.mainclass}</mainClass>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>

===== src/main/java/com/baeldung/a2a/client/orchestrator/jobscreening/AgentRegistry.java =====

package com.baeldung.a2a.client.orchestrator.jobscreening;

import io.a2a.A2A;
import io.a2a.spec.AgentCard;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
class AgentRegistry {

    private final Map<String, AgentCard> agentCards = new HashMap<>();

    AgentRegistry(@Value("${remote.agents.urls}") List<String> agentUrls) throws URISyntaxException {
        for (String url : agentUrls) {
            String path = new URI(url).getPath();
            AgentCard card = A2A.getAgentCard(url, path + ".well-known/agent-card.json", null);
            agentCards.put(card.name(), card);
        }
    }

    AgentCard get(String agentName) {
        return agentCards.get(agentName);
    }

    String describeAgents() {
        return agentCards
            .values()
            .stream()
            .map(card -> "- " + card.name() + ": " + card.description())
            .collect(Collectors.joining("\n"));
    }
}

===== src/main/java/com/baeldung/a2a/client/orchestrator/jobscreening/Application.java =====

package com.baeldung.a2a.client.orchestrator.jobscreening;

import org.springaicommunity.a2a.server.autoconfigure.A2AServerAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(exclude = A2AServerAutoConfiguration.class)
@PropertySource("classpath:application-job-screening-orchestrator.properties")
class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

===== src/main/java/com/baeldung/a2a/client/orchestrator/jobscreening/ChatClientConfiguration.java =====

package com.baeldung.a2a.client.orchestrator.jobscreening;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ChatClientConfiguration {

    @Bean
    ChatClient chatClient(
        ChatClient.Builder chatClientBuilder,
        AgentRegistry agentRegistry,
        RemoteAgentTools remoteAgentTools
    ) {
        return chatClientBuilder
            .defaultSystem("""
                You are a job-screening orchestrator for recruiters.
                You do not evaluate candidates yourself. Instead, you delegate
                to the following remote agents:

                %s
                
                Once all agents have responded, combine their responses into a short screening summary.
                """.formatted(agentRegistry.describeAgents()))
            .defaultTools(remoteAgentTools)
            .build();
    }
}

===== src/main/java/com/baeldung/a2a/client/orchestrator/jobscreening/JobScreeningController.java =====

package com.baeldung.a2a.client.orchestrator.jobscreening;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class JobScreeningController {

    private final ChatClient chatClient;

    JobScreeningController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @PostMapping("/screenings")
    ScreeningResponse screenCandidate(@RequestBody ScreeningRequest screeningRequest) {
        String verdict = chatClient
            .prompt()
            .user(screeningRequest.toString())
            .call()
            .content();
        return new ScreeningResponse(verdict);
    }

    record ScreeningRequest(
        String name,
        String email,
        String jobTitle,
        String requiredSkills,
        String candidateSkills,
        int expectedSalary
    ) {}

    record ScreeningResponse(
        String verdict
    ) {}
}

===== src/main/java/com/baeldung/a2a/client/orchestrator/jobscreening/RemoteAgentClient.java =====

package com.baeldung.a2a.client.orchestrator.jobscreening;

import io.a2a.A2A;
import io.a2a.client.Client;
import io.a2a.client.ClientEvent;
import io.a2a.client.TaskEvent;
import io.a2a.client.config.ClientConfig;
import io.a2a.client.transport.jsonrpc.JSONRPCTransport;
import io.a2a.client.transport.jsonrpc.JSONRPCTransportConfig;
import io.a2a.spec.AgentCard;
import io.a2a.spec.Artifact;
import io.a2a.spec.Message;
import io.a2a.spec.Part;
import io.a2a.spec.TextPart;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Component
class RemoteAgentClient {

    private final AgentRegistry agentRegistry;

    RemoteAgentClient(AgentRegistry agentRegistry) {
        this.agentRegistry = agentRegistry;
    }

    String sendMessage(String agentName, String task)
        throws ExecutionException, InterruptedException, TimeoutException {
        AgentCard agentCard = agentRegistry.get(agentName);

        CompletableFuture<String> response = new CompletableFuture<>();
        BiConsumer<ClientEvent, AgentCard> responseConsumer = (event, card) -> {
            TaskEvent taskEvent = (TaskEvent) event;
            response.complete(taskEvent.getTask()
                .getArtifacts()
                .stream()
                .map(Artifact::parts)
                .map(this::extractText)
                .collect(Collectors.joining("\n")));
        };

        Client client = Client.builder(agentCard)
            .clientConfig(new ClientConfig.Builder()
                .setAcceptedOutputModes(List.of("text"))
                .build())
            .withTransport(JSONRPCTransport.class, new JSONRPCTransportConfig())
            .addConsumers(List.of(responseConsumer))
            .streamingErrorHandler(response::completeExceptionally)
            .build();

        Message message = A2A.toUserMessage(task);
        client.sendMessage(message);
        return response.get(60, TimeUnit.SECONDS);
    }

    private String extractText(List<Part<?>> parts) {
        return parts
            .stream()
            .filter(TextPart.class::isInstance)
            .map(TextPart.class::cast)
            .map(TextPart::getText)
            .collect(Collectors.joining("\n"));
    }
}

===== src/main/java/com/baeldung/a2a/client/orchestrator/jobscreening/RemoteAgentTools.java =====

package com.baeldung.a2a.client.orchestrator.jobscreening;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Service
class RemoteAgentTools {

    private final RemoteAgentClient remoteAgentClient;

    RemoteAgentTools(RemoteAgentClient remoteAgentClient) {
        this.remoteAgentClient = remoteAgentClient;
    }

    @Tool(
        name = "send-message-to-agent",
        description = "Sends a task to a remote agent and returns its response."
    )
    String sendMessageToAgent(
        @ToolParam(description = "Name of the remote agent") String agentName,
        @ToolParam(description = "The task to perform") String task
    ) throws ExecutionException, InterruptedException, TimeoutException {
        return remoteAgentClient.sendMessage(agentName, task);
    }
}

===== src/main/java/com/baeldung/a2a/server/backgroundchecker/Application.java =====

package com.baeldung.a2a.server.backgroundchecker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-background-checker-server.properties")
class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

===== src/main/java/com/baeldung/a2a/server/backgroundchecker/BackgroundCheckerTools.java =====

package com.baeldung.a2a.server.backgroundchecker;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
class BackgroundCheckerTools {

    private static final Set<String> TRUSTED_EMAIL_DOMAINS = Set.of(
        "gmail.com",
        "baeldung.com"
    );

    @Tool(
        name = "check-background",
        description = "Runs a background check for a candidate"
    )
    BackgroundCheckResult checkBackground(
        @ToolParam(description = "The candidate's full name") String candidateName,
        @ToolParam(description = "The candidate's email address") String email
    ) {
        String domain = extractDomain(email);
        Verdict verdict = TRUSTED_EMAIL_DOMAINS.contains(domain)
            ? Verdict.CLEAR
            : Verdict.NEEDS_REVIEW;
        return new BackgroundCheckResult(verdict);
    }

    private String extractDomain(String email) {
        int atIndex = email.lastIndexOf('@');
        return atIndex < 0
            ? ""
            : email.substring(atIndex + 1).trim().toLowerCase();
    }

    record BackgroundCheckResult(
        Verdict verdict
    ) {}

    enum Verdict {
        CLEAR,
        NEEDS_REVIEW
    }
}

===== src/main/java/com/baeldung/a2a/server/backgroundchecker/ChatClientConfiguration.java =====

package com.baeldung.a2a.server.backgroundchecker;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ChatClientConfiguration {

    @Bean
    ChatClient chatClient(
        ChatClient.Builder chatClientBuilder,
        BackgroundCheckerTools backgroundCheckerTools
    ) {
        return chatClientBuilder
            .defaultSystem("""
                You are a background-check assistant for recruiters.
                Use the check-background tool to run a background check on a
                candidate using their name and email, then summarize the result.
                """)
            .defaultTools(backgroundCheckerTools)
            .build();
    }
}

===== src/main/java/com/baeldung/a2a/server/backgroundchecker/ServerConfiguration.java =====

package com.baeldung.a2a.server.backgroundchecker;

import io.a2a.server.agentexecution.AgentExecutor;
import io.a2a.spec.AgentCapabilities;
import io.a2a.spec.AgentCard;
import io.a2a.spec.AgentSkill;
import org.springaicommunity.a2a.server.executor.DefaultAgentExecutor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
class ServerConfiguration {

    @Bean
    AgentExecutor agentExecutor(ChatClient chatClient) {
        return new DefaultAgentExecutor(chatClient, (client, requestContext) -> {
            String userMessage = DefaultAgentExecutor.extractTextFromMessage(requestContext.getMessage());
            return client
                .prompt()
                .user(userMessage)
                .call()
                .content();
        });
    }

    @Bean
    AgentCard agentCard(
        @Value("${server.host}") String host,
        @Value("${server.port}") int port
    ) {
        return new AgentCard.Builder()
            .name("Background Check Agent")
            .description("Runs a background check on a candidate")
            .url(String.format("http://%s:%d/a2a/", host, port))
            .version("1.0.0")
            .capabilities(new AgentCapabilities
                .Builder()
                .streaming(false)
                .build())
            .defaultInputModes(List.of("text"))
            .defaultOutputModes(List.of("text"))
            .skills(List.of(new AgentSkill.Builder()
                .id("background_check")
                .name("Background Check")
                .description("Runs a background check on a candidate using their name and email")
                .tags(List.of("hiring", "recruiting", "compliance"))
                .build()))
            .protocolVersion("0.3.0")
            .build();
    }
}

===== src/main/java/com/baeldung/a2a/server/salaryevaluator/Application.java =====

package com.baeldung.a2a.server.salaryevaluator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-salary-evaluator-server.properties")
class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

===== src/main/java/com/baeldung/a2a/server/salaryevaluator/ChatClientConfiguration.java =====

package com.baeldung.a2a.server.salaryevaluator;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ChatClientConfiguration {

    @Bean
    ChatClient chatClient(
        ChatClient.Builder chatClientBuilder,
        SalaryEvaluatorTools salaryEvaluatorTools
    ) {
        return chatClientBuilder
            .defaultSystem("""
                You are a salary-evaluation assistant for recruiters.
                Use the evaluate-salary tool to compare a candidate's expected
                salary against the job title they've applied for.
                """)
            .defaultTools(salaryEvaluatorTools)
            .build();
    }
}

===== src/main/java/com/baeldung/a2a/server/salaryevaluator/SalaryEvaluatorTools.java =====

package com.baeldung.a2a.server.salaryevaluator;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
class SalaryEvaluatorTools {

    @Tool(
        name = "evaluate-salary",
        description = "Compares a candidate's expected salary for a given job title"
    )
    SalaryEvaluationResult evaluateSalary(
        @ToolParam(description = "The job title being applied for") String jobTitle,
        @ToolParam(description = "Candidate's expected annual salary") int expectedSalary
    ) {
        SalaryRange salaryRange = budgetLookup(jobTitle);
        Verdict verdict = expectedSalary > salaryRange.max()
            ? Verdict.ABOVE_BUDGET
            : expectedSalary < salaryRange.min()
            ? Verdict.BELOW_BUDGET
            : Verdict.WITHIN_BUDGET;
        return new SalaryEvaluationResult(verdict);
    }

    private SalaryRange budgetLookup(String jobTitle) {
        return new SalaryRange(80000, 120000);
    }

    record SalaryRange(
        int min,
        int max
    ) {}

    record SalaryEvaluationResult(
        Verdict verdict
    ) {}

    enum Verdict {
        WITHIN_BUDGET,
        ABOVE_BUDGET,
        BELOW_BUDGET
    }
}

===== src/main/java/com/baeldung/a2a/server/salaryevaluator/ServerConfiguration.java =====

package com.baeldung.a2a.server.salaryevaluator;

import io.a2a.server.agentexecution.AgentExecutor;
import io.a2a.spec.AgentCapabilities;
import io.a2a.spec.AgentCard;
import io.a2a.spec.AgentSkill;
import org.springaicommunity.a2a.server.executor.DefaultAgentExecutor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
class ServerConfiguration {

    @Bean
    AgentExecutor agentExecutor(ChatClient chatClient) {
        return new DefaultAgentExecutor(chatClient, (client, requestContext) -> {
            String userMessage = DefaultAgentExecutor.extractTextFromMessage(requestContext.getMessage());
            return client
                .prompt()
                .user(userMessage)
                .call()
                .content();
        });
    }

    @Bean
    AgentCard agentCard(
        @Value("${server.host}") String host,
        @Value("${server.port}") int port
    ) {
        return new AgentCard.Builder()
            .name("Salary Evaluator Agent")
            .description("Checks if a candidate's expected salary fits a job title's budget range")
            .url(String.format("http://%s:%d/a2a/", host, port))
            .version("1.0.0")
            .capabilities(new AgentCapabilities
                .Builder()
                .streaming(false)
                .build())
            .defaultInputModes(List.of("text"))
            .defaultOutputModes(List.of("text"))
            .skills(List.of(new AgentSkill.Builder()
                .id("salary_evaluation")
                .name("Salary Evaluation")
                .description("Compares a candidate's expected salary against a job title's budget range")
                .tags(List.of("hiring", "recruiting", "compensation"))
                .build()))
            .protocolVersion("0.3.0")
            .build();
    }
}

===== src/main/java/com/baeldung/a2a/server/skillsmatcher/Application.java =====

package com.baeldung.a2a.server.skillsmatcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-skills-matcher-server.properties")
class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

===== src/main/java/com/baeldung/a2a/server/skillsmatcher/ChatClientConfiguration.java =====

package com.baeldung.a2a.server.skillsmatcher;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ChatClientConfiguration {

    @Bean
    ChatClient chatClient(
        ChatClient.Builder chatClientBuilder,
        SkillsMatcherTools skillsMatcherTools
    ) {
        return chatClientBuilder
            .defaultSystem("""
                You are a skills-matching assistant for recruiters.
                Use the match-skills tool to compare a candidate's skills
                against a job's required skills, then summarize the result.
                """)
            .defaultTools(skillsMatcherTools)
            .build();
    }
}

===== src/main/java/com/baeldung/a2a/server/skillsmatcher/ServerConfiguration.java =====

package com.baeldung.a2a.server.skillsmatcher;

import io.a2a.server.agentexecution.AgentExecutor;
import io.a2a.spec.AgentCapabilities;
import io.a2a.spec.AgentCard;
import io.a2a.spec.AgentSkill;
import org.springaicommunity.a2a.server.executor.DefaultAgentExecutor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
class ServerConfiguration {

    @Bean
    AgentExecutor agentExecutor(ChatClient chatClient) {
        return new DefaultAgentExecutor(chatClient, (client, requestContext) -> {
            String userMessage = DefaultAgentExecutor.extractTextFromMessage(requestContext.getMessage());
            return client
                .prompt()
                .user(userMessage)
                .call()
                .content();
        });
    }

    @Bean
    AgentCard agentCard(
        @Value("${server.host}") String host,
        @Value("${server.port}") int port
    ) {
        return new AgentCard.Builder()
            .name("Skills Matcher Agent")
            .description("Evaluates how well a candidate's skills match a job's required skills")
            .url(String.format("http://%s:%d/a2a/", host, port))
            .version("1.0.0")
            .capabilities(new AgentCapabilities
                .Builder()
                .streaming(false)
                .build())
            .defaultInputModes(List.of("text"))
            .defaultOutputModes(List.of("text"))
            .skills(List.of(new AgentSkill.Builder()
                .id("skills_matching")
                .name("Skills Matching")
                .description("Compares candidate skills to job requirements and scores the fit")
                .tags(List.of("hiring", "recruiting"))
                .build()))
            .protocolVersion("0.3.0")
            .build();
    }
}

===== src/main/java/com/baeldung/a2a/server/skillsmatcher/SkillsMatcherTools.java =====

package com.baeldung.a2a.server.skillsmatcher;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
class SkillsMatcherTools {

    @Tool(
        name = "match-skills",
        description = "Compares a candidate's skills against a job's required skills and returns a fit score"
    )
    SkillsMatchResult matchSkills(
        @ToolParam(description = "Candidate skills, comma-separated") String candidateSkills,
        @ToolParam(description = "Required job skills, comma-separated") String requiredSkills
    ) {
        Set<String> candidateSkillSet = normalize(candidateSkills);
        Set<String> requiredSkillSet = normalize(requiredSkills);

        Set<String> matchedSkillSet = new HashSet<>(candidateSkillSet);
        matchedSkillSet.retainAll(requiredSkillSet);

        Set<String> missingSkillSet = new HashSet<>(requiredSkillSet);
        missingSkillSet.removeAll(candidateSkillSet);

        int score = requiredSkillSet.isEmpty() ? 0 : (matchedSkillSet.size() * 100) / requiredSkillSet.size();
        Verdict verdict = score >= 75
            ? Verdict.STRONG_MATCH
            : score >= 40
            ? Verdict.PARTIAL_MATCH
            : Verdict.WEAK_MATCH;

        return new SkillsMatchResult(score, verdict, matchedSkillSet, missingSkillSet);
    }

    private Set<String> normalize(String csv) {
        return Arrays.stream(csv.split(","))
            .map(String::trim)
            .map(String::toLowerCase)
            .filter(s -> !s.isEmpty())
            .collect(Collectors.toSet());
    }

    record SkillsMatchResult(
        int score,
        Verdict verdict,
        Set<String> matchedSkills,
        Set<String> missingSkills
    ) {}

    enum Verdict {
        STRONG_MATCH,
        PARTIAL_MATCH,
        WEAK_MATCH
    }
}

===== src/main/resources/application-background-checker-server.properties =====

server.host=localhost
server.port=8083
server.servlet.context-path=/a2a

spring.ai.openai.api-key=${OPENAI_API_KEY}
spring.ai.openai.chat.model=gpt-5.5

===== src/main/resources/application-job-screening-orchestrator.properties =====

server.port=8080

spring.ai.openai.api-key=${OPENAI_API_KEY}
spring.ai.openai.chat.model=gpt-5.5

remote.agents.urls=http://localhost:8081/a2a/,http://localhost:8082/a2a/,http://localhost:8083/a2a/

===== src/main/resources/application-salary-evaluator-server.properties =====

server.host=localhost
server.port=8082
server.servlet.context-path=/a2a

spring.ai.openai.api-key=${OPENAI_API_KEY}
spring.ai.openai.chat.model=gpt-5.5

===== src/main/resources/application-skills-matcher-server.properties =====

server.host=localhost
server.port=8081
server.servlet.context-path=/a2a

spring.ai.openai.api-key=${OPENAI_API_KEY}
spring.ai.openai.chat.model=gpt-5.5

===== src/main/resources/logback-spring.xml =====

<configuration>
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>[%d{yyyy-MM-dd HH:mm:ss}] [%p] [%c{1}] - %m%n</pattern>
        </encoder>
    </appender>

    <root level="INFO">
        <appender-ref ref="CONSOLE" />
    </root>

    <logger name="org.springframework" level="INFO" additivity="false">
        <appender-ref ref="CONSOLE" />
    </logger>
</configuration>