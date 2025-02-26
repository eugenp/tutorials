package openai;

import java.util.List;
import java.util.Scanner;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatCompletion;
import com.openai.models.ChatCompletionCreateParams;
import com.openai.models.ChatCompletionCreateParams.Builder;
import com.openai.models.ChatCompletionMessage;
import com.openai.models.ChatModel;

public class BaeldungOpenAiConversation {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println(Consts.INITIAL_MESSAGE);

        String userMessage = scanner.next();

        OpenAIClient client = OpenAIOkHttpClient.fromEnv();

        Builder createParamsBuilder = ChatCompletionCreateParams.builder()
            .model(ChatModel.GPT_4O_MINI)
            .maxCompletionTokens(2048)
            .addDeveloperMessage(Consts.DEVELOPER_MESSAGE)
            .addUserMessage(userMessage);

        do {

            List<ChatCompletionMessage> messages = client.chat()
                .completions()
                .create(createParamsBuilder.build())
                .choices()
                .stream()
                .map(ChatCompletion.Choice::message)
                .toList();

            messages.stream()
                .flatMap(message -> message.content()
                    .stream())
                .forEach(System.out::println);

            System.out.println("-----------------------------------");
            System.out.println(Consts.AI_MESSAGE);

            String userMessageConversation = scanner.next();

            if ("exit".equalsIgnoreCase(userMessageConversation)) {
                scanner.close();
                return;
            }

            messages.forEach(createParamsBuilder::addMessage);
            createParamsBuilder.addDeveloperMessage(Consts.DEVELOPER_MESSAGE_CONVERSATION)
                .addUserMessage(userMessageConversation);

        } while (true);

    }

}
