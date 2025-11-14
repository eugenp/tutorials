package com.baeldung.mcp.mcpclientoauth2;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {

    private final ChatClient chatClient;

    public CalculatorController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/calculate")
    public String calculate(@RequestParam String expression, @RegisteredOAuth2AuthorizedClient("authserver") OAuth2AuthorizedClient authorizedClient) {

        String prompt = String.format("Please calculate the following mathematical expression using the available calculator tools: %s", expression);

        return chatClient.prompt()
            .user(prompt)
            .call()
            .content();
    }

    @GetMapping("/")
    public String home() {
        return """
            <html>
            <body>
                <h1>MCP Calculator with OAuth2</h1>
                <p>Try these examples:</p>
                <ul>
                    <li><a href="/calculate?expression=5 + 3">/calculate?expression=5 + 3</a></li>
                    <li><a href="/calculate?expression=10 - 4">/calculate?expression=10 - 4</a></li>
                    <li><a href="/calculate?expression=6 * 7">/calculate?expression=6 * 7</a></li>
                    <li><a href="/calculate?expression=15 / 3">/calculate?expression=15 / 3</a></li>
                </ul>
                <p>Note: You'll be redirected to login if not authenticated.</p>
            </body>
            </html>
            """;
    }
}