package openai;

import java.util.Scanner;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatCompletionCreateParams;
import com.openai.models.ChatCompletionCreateParams.Builder;
import com.openai.models.ChatModel;

public class BaeldungOpenAiCompletion {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println(Consts.INITIAL_MESSAGE);

        String userMessage = scanner.next();

        OpenAIClient client = OpenAIOkHttpClient.fromEnv();

        Builder createParams = ChatCompletionCreateParams.builder()
            .model(ChatModel.GPT_4O_MINI)
            .addDeveloperMessage(Consts.DEVELOPER_MESSAGE)
            .addUserMessage(userMessage);

        client.chat()
            .completions()
            .create(createParams.build())
            .choices()
            .stream()
            .flatMap(choice -> choice.message()
                .content()
                .stream())
            .forEach(System.out::println);

        scanner.close();

    }

}
