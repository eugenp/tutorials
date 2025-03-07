package com.baeldung.openai;

import java.util.Scanner;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.*;
import com.openai.models.Thread;

public class BaeldungOpenAiAssistant {

    private static final String ASSISTANT_NAME = "Baeldung Tutor";

    public static void main(String[] args) throws InterruptedException {

        Scanner scanner = new Scanner(System.in);

        System.out.println(Consts.INITIAL_MESSAGE);

        String userMessage = scanner.next();

        OpenAIClient client = OpenAIOkHttpClient.fromEnv();

        Assistant assistant = client.beta()
            .assistants()
            .create(BetaAssistantCreateParams.builder()
                .name(ASSISTANT_NAME)
                .instructions(Consts.ASSISTANT_INSTRUCTION)
                .model(ChatModel.GPT_4O_MINI)
                .build());

        Thread thread =
            client.beta()
            .threads()
            .create(BetaThreadCreateParams.builder()
                .build());

        client.beta()
            .threads()
            .messages()
            .create(BetaThreadMessageCreateParams.builder()
                .threadId(thread.id())
                .role(BetaThreadMessageCreateParams.Role.USER)
                .content(userMessage)
                .build());

        Run run = client.beta()
            .threads()
            .runs()
            .create(BetaThreadRunCreateParams.builder()
                .threadId(thread.id())
                .assistantId(assistant.id())
                .instructions(Consts.DEVELOPER_MESSAGE)
                .build());

        while (run.status()
            .equals(RunStatus.QUEUED)
            || run.status()
                .equals(RunStatus.IN_PROGRESS)) {
            System.out.println("Polling run...");
            java.lang.Thread.sleep(500);
            run = client.beta()
                .threads()
                .runs()
                .retrieve(BetaThreadRunRetrieveParams.builder()
                    .threadId(thread.id())
                    .runId(run.id())
                    .build());
        }

        System.out.println("Run completed with status: " + run.status() + "\n");

        if (!run.status()
            .equals(RunStatus.COMPLETED)) {
            scanner.close();
            return;
        }

        BetaThreadMessageListPage page = client.beta()
            .threads()
            .messages()
            .list(BetaThreadMessageListParams.builder()
                .threadId(thread.id())
                .order(BetaThreadMessageListParams.Order.ASC)
                .build());

        page.autoPager()
            .stream()
            .forEach(currentMessage -> {
                System.out.println(currentMessage.role()
                    .toString()
                    .toUpperCase());
                currentMessage.content()
                    .stream()
                    .flatMap(content -> content.text()
                        .stream())
                    .forEach(textBlock -> System.out.println(textBlock.text()
                        .value()));
                System.out.println();
            });

        AssistantDeleted assistantDeleted = client.beta()
            .assistants()
            .delete(BetaAssistantDeleteParams.builder()
                .assistantId(assistant.id())
                .build());

        System.out.println("Assistant deleted: " + assistantDeleted.deleted());

        scanner.close();
    }

}
