package baeldungassistant;

import static com.theokanning.openai.utils.TikTokensUtil.ModelEnum.GPT_3_5_TURBO_0301;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class BaeldungLearningAssistant {

    private static final String OPENAI_TOKEN = "OPENAI_TOKEN";
    private static final String PROMPT = "You're helping me to create a curriculum"
                                         + "to learn programming."
                                         + "I want to use Baedlung website as the base."
                                         + "I will tell you the topic,"
                                         + "and you should return me the list of articles"
                                         + "and tutorials with links."
                                         + "Order the articles from beginner to more advanced,"
                                         + "so I can learn them one-by-one."
                                         + "Use only the articles from www.baeldung.com.";
    private static final int MAX_TOKENS = 900;
    private static final String GREETING = "Hello!\nWhat do you want to learn?\n";
    private static final String MODEL = GPT_3_5_TURBO_0301.getName();

    public static void main(String[] args) {
        String token = System.getenv(OPENAI_TOKEN);
        OpenAiService service = new OpenAiService(token);

        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), PROMPT);
        messages.add(systemMessage);

        System.out.print(GREETING);
        Scanner scanner = new Scanner(System.in);
        ChatMessage firstMsg = new ChatMessage(ChatMessageRole.USER.value(), scanner.nextLine());
        messages.add(firstMsg);

        while (true) {
            ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
              .builder()
              .model(MODEL)
              .maxTokens(MAX_TOKENS)
              .messages(messages)
              .build();
            ChatCompletionResult result = service.createChatCompletion(chatCompletionRequest);
            long usedTokens = result.getUsage().getTotalTokens();
            ChatMessage response = result.getChoices().get(0).getMessage();

            messages.add(response);

            System.out.println(response.getContent());
            System.out.println("Total tokens used: " + usedTokens);
            System.out.print("Anything else?\n");
            String nextLine = scanner.nextLine();
            if (nextLine.equalsIgnoreCase("exit")) {
                scanner.close();
                System.exit(0);
            }
            messages.add(new ChatMessage(ChatMessageRole.USER.value(), nextLine));
        }
    }

}
