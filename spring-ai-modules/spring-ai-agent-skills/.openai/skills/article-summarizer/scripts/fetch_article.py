# This script just returns a hardcoded article for demonstration.
# Original article: https://www.baeldung.com/spring-ai-mcp-elicitations

ARTICLE = """
<h2>1. Overview</h2>
When working with <a href="https://www.baeldung.com/spring-ai-model-context-protocol-mcp#model-context-protocol-101">Model Context Protocol (MCP)</a>, there are scenarios where MCP servers need additional details from users during tool execution that weren't included in the original request. Without a standardized way to request this information, the tool has no way to communicate this back to the client and fails to execute.

<a href="https://modelcontextprotocol.io/specification/draft/client/elicitation">MCP Elicitations</a> address this issue by allowing the MCP server to pause and explicitly request the missing information from the user. This enables us to build interactive tools capable of dynamically gathering additional context.

In this tutorial, <strong>we'll explore how to implement MCP Elicitations using <a href="https://www.baeldung.com/spring-ai">Spring AI</a></strong>.
<h2>2. Creating an MCP Server</h2>
Let's start by building an MCP server that exposes a tool for fetching author details.

We'll design this tool to conditionally trigger an elicitation request for additional details if they are missing from the original request.
<h3>2.1. Dependencies and Configuration</h3>
Let’s start by adding the necessary dependencies to our project’s <em>pom.xml</em> file:
<pre><code class="language-xml">&lt;dependency&gt;
    &lt;groupId&gt;org.springframework.ai&lt;/groupId&gt;
    &lt;artifactId&gt;spring-ai-starter-mcp-server-webmvc&lt;/artifactId&gt;
    &lt;version&gt;1.1.2&lt;/version&gt;
&lt;/dependency&gt;</code></pre>
We import <a href="https://mvnrepository.com/artifact/org.springframework.ai/spring-ai-starter-mcp-server-webmvc">Spring AI’s MCP server dependency</a>, which provides the necessary classes for creating a custom HTTP-based MCP server.

Next, let's edit the <em>application.properties</em> file to configure our application as an MCP server:
<pre><code class="language-properties">spring.ai.mcp.server.name=author-server
spring.ai.mcp.server.type=SYNC
spring.ai.mcp.server.protocol=streamable</code></pre>
Here, we configure a name for our MCP server, set it to be synchronous, and specify the transport type as <a href="https://modelcontextprotocol.io/specification/2025-03-26/basic/transports#streamable-http">streamable HTTP</a>.
<h3>2.2. Defining a Tool</h3>
Next, let’s define a tool that our MCP server will expose.

We’ll create an <em>AuthorRepository</em> class that provides a method to fetch author details using an article title. If the requested article is a premium one, we'll elicit additional information from the user before returning the author details:
<pre class="add-horizontal-scrollbar"><code class="language-java">private static final Logger log = LoggerFactory.getLogger(AuthorRepository.class);

@McpTool(description = "Get Baeldung author details using an article title")
Author getAuthorByArticleTitle(
    @McpToolParam(description = "Title/name of the article") String articleTitle,
    @McpToolParam(required = false, description = "Name of user requesting author information") String username,
    @McpToolParam(required = false, description = "Reason for requesting author information") String reason,
    McpSyncRequestContext requestContext
) {
    log.info("Author requested for article: {}", articleTitle);
    if (isPremiumArticle(articleTitle)) {
        log.info("Article is premium, further information required");
        if ((isBlank(username) || isBlank(reason)) &amp;&amp; requestContext.elicitEnabled()) {
            log.info("Required details missing, initiating elicitation");
            StructuredElicitResult&lt;PremiumArticleAccessRequest&gt; elicitResult = requestContext.elicit(
                e -&gt; e.message("Baeldung username and reason required."),
                PremiumArticleAccessRequest.class
            );
            if (McpSchema.ElicitResult.Action.ACCEPT.equals(elicitResult.action())) {
                username = elicitResult.structuredContent().username();
                reason = elicitResult.structuredContent().reason();
                log.info("Elicitation accepted - username: {}, reason: {}", username, reason);
            }
        }
        if (isSubscriber(username) &amp;&amp; isValidReason(reason)) {
            log.info("Access granted, returning author details");
            return new Author("John Doe", "john.doe@baeldung.com");
        }
    }
    return null;
}

record Author(String name, String email) {
}

record PremiumArticleAccessRequest(String username, String reason) {
}</code></pre>
<strong>We annotate our <em>getAuthorByArticleTitle()</em> method with the <a href="https://www.baeldung.com/spring-ai-mcp-annotations#exposing-functionality-with-mcptool"><em>@McpTool</em></a> annotation to expose it as an MCP tool</strong>. The method accepts the <em>articleTitle</em> as a required parameter, along with optional <em>username</em> and <em>reason</em> parameters.

Additionally, <strong>we inject the <em>McpSyncRequestContext</em> as a method parameter, which provides access to the current request's metadata and enables us to initiate elicitation requests back to the MCP client</strong>.

If the requested article is premium and any of the optional parameters are missing, <strong>we use the <em>elicit()</em> method to trigger an elicitation request</strong>. We pass a message explaining what information is needed and a schema defining the expected response structure.

We should also note that before initiating this elicitation request, <strong>we call the <em>elicitEnabled()</em> method to check whether the connected MCP client supports elicitation</strong>, since attempting to elicit from an unsupported client would result in an error.

If the user accepts the elicitation request and provides valid details, we extract the <em>username</em> and <em>reason</em> from the result and proceed with the authorization checks before returning hardcoded author details.

For our demonstration, the <em>isPremiumArticle()</em>, <em>isSubscriber()</em>, and <em>isValidReason()</em> private methods always return <em>true</em>.
<h2>3. Creating an MCP Host</h2>
Now that we have our MCP server ready, we need an application to consume it.

<strong>We’ll be building a chatbot using <a href="https://www.baeldung.com/spring-ai-anthropics-claude-models">Anthropic’s Claude</a> model, which will act as our MCP host</strong>. Alternatively, we can use a local LLM via <a href="https://www.baeldung.com/spring-ai-ollama-hugging-face-models">Hugging Face or Ollama</a>, as the specific AI model is irrelevant for this demonstration.

<strong>We’ll be creating a new Spring Boot application in this section</strong>.
<h3>3.1. Dependencies and Configuring an LLM</h3>
First, let’s include the necessary dependency in our <em>pom.xml</em> file:
<pre><code class="language-xml">&lt;dependency&gt;
    &lt;groupId&gt;org.springframework.ai&lt;/groupId&gt;
    &lt;artifactId&gt;spring-ai-starter-model-anthropic&lt;/artifactId&gt;
    &lt;version&gt;1.1.2&lt;/version&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;org.springframework.ai&lt;/groupId&gt;
    &lt;artifactId&gt;spring-ai-starter-mcp-client&lt;/artifactId&gt;
    &lt;version&gt;1.1.2&lt;/version&gt;
&lt;/dependency&gt;</code></pre>
The <a href="https://mvnrepository.com/artifact/org.springframework.ai/spring-ai-starter-model-anthropic">Anthropic starter dependency</a> is a wrapper around the <a href="https://docs.anthropic.com/en/api/messages">Anthropic Message API</a>, and we’ll use it to interact with the Claude model in our application.

Additionally, we import the <a href="https://mvnrepository.com/artifact/org.springframework.ai/spring-ai-starter-mcp-client">MCP client starter dependency</a>, <strong>which will allow us to configure clients inside our Spring Boot application that maintain 1:1 connections with the MCP servers</strong>.

Next, let's configure our <a href="https://console.anthropic.com/settings/keys">Anthropic API key</a> and chat model in the <em>application.properties</em> file:
<pre><code class="language-properties">spring.ai.anthropic.api-key=${ANTHROPIC_API_KEY}
spring.ai.anthropic.chat.options.model=claude-opus-4-5-20251101</code></pre>
We use the <em>${}</em> property placeholder to load the value of our API Key from an <a href="https://www.baeldung.com/spring-boot-properties-env-variables#use-environment-variable-in-applicationyml-file">environment variable</a>.

Additionally, we specify <a href="https://www.anthropic.com/claude/opus">Claude Opus 4.5</a> by Anthropic, using the <em>claude-opus-4-5-20251101</em> model ID. Feel free to explore and use a <a href="https://docs.anthropic.com/en/docs/about-claude/models#model-names">different model</a> based on requirements.

With these two properties set, <strong>Spring AI automatically creates a bean of type <em>ChatModel</em>, allowing us to interact with the specified model</strong>.
<h3>3.2. Configuring an MCP Client and Enabling MCP Elicitation</h3>
Finally, to use our custom MCP server in our chatbot application, we need to configure an MCP client against it:
<pre><code class="language-properties">spring.ai.mcp.client.capabilities.elicitation={}
spring.ai.mcp.client.streamable-http.connections.author-server.url=http://localhost:8081/mcp</code></pre>
In our <em>application.properties</em> file, <strong>we first enable MCP elicitation</strong>, which allows the MCP servers to send back an elicitation request if a tool requires additional information.

Next, <strong>we configure a new client against our custom MCP server using the streamable HTTP transport type</strong>. Our configuration assumes the MCP server to be running at <em>http://localhost:8081/mcp</em>. We need to make sure to update the <em>url</em> property if it’s running on a different host or port.

During application startup, Spring AI will scan our configuration, create the MCP client, and establish a connection with the corresponding MCP server. Additionally, <strong>it creates a bean of type <em>SyncMcpToolCallbackProvider</em>, which provides a list of all the tools exposed by the configured MCP servers</strong>.
<h3>3.3. Building a Basic Chatbot</h3>
With our LLM and MCP client configured, let’s build a simple chatbot.

We'll start by creating a bean of type <em>ChatClient</em> using the auto-configured <em>ChatModel</em> and <em>SyncMcpToolCallbackProvider</em> beans:
<pre><code class="language-java">@Bean
ChatClient chatClient(ChatModel chatModel, SyncMcpToolCallbackProvider toolCallbackProvider) {
    return ChatClient
      .builder(chatModel)
      .defaultToolCallbacks(toolCallbackProvider.getToolCallbacks())
      .build();
}</code></pre>
<strong>The <em>ChatClient</em> class will act as our main entry point for interacting with our chat completion model</strong>, i.e., Claude Opus 4.5.

Next, let’s inject the <em>ChatClient</em> bean in a controller class and expose a REST API:
<pre><code class="language-java">@PostMapping("/chat")
ResponseEntity&lt;ChatResponse&gt; chat(@RequestBody ChatRequest chatRequest) {
    String answer = chatClient
      .prompt()
      .user(chatRequest.question())
      .call()
      .content();
    return ResponseEntity.ok(new ChatResponse(answer));
}

record ChatRequest(String question) {
}

record ChatResponse(String answer) {
}</code></pre>
Here, we simply pass the user’s <em>question</em> to the <em>chatClient</em> bean and return the LLM's response. We’ll use this API endpoint to interact with our chatbot later in the tutorial.
<h3>3.4. Handling Elicitation Requests</h3>
<strong>When the MCP server initiates an elicitation request, the MCP client must have a mechanism to intercept this request and provide the necessary data</strong>.

Let's define a method in our configuration class to handle these requests:
<pre><code class="language-java">private static final Logger log = LoggerFactory.getLogger(ChatbotConfiguration.class);

@McpElicitation(clients = "author-server")
ElicitResult handleElicitation(ElicitRequest elicitRequest) {
    log.info("Elicitation requested: {}", elicitRequest.message());
    log.info("Requested schema: {}", elicitRequest.requestedSchema());

    return new ElicitResult(
      ElicitResult.Action.ACCEPT,
      Map.of(
        "username", "john.smith",
        "reason", "Contacting author for article feedback"
      )
    );
}</code></pre>
<strong>We annotate our <em>handleElicitation()</em> method with <em>@McpElicitation</em>, specifying the <em>clients</em> attribute as <em>author-server</em> to link it to the client we configured earlier in our <em>application.properties</em> file</strong>.

When the MCP server triggers an elicitation request, this handler receives the <em>ElicitRequest</em> containing the message and requested schema.

In our handler, <strong>we log the elicitation details and return an <em>ElicitResult</em> with the <em>ACCEPT</em> action along with the requested details</strong>. For our demonstration, we're returning hardcoded values. In a production application, the MCP client would typically prompt the user for this information.

It's worth noting that MCP Elicitation also supports a URL mode, where the server directs users to an external URL to request sensitive details or perform protected actions. This ensures sensitive data never passes through the MCP client. However, at the time of this writing, Spring AI does not support URL mode elicitation.
<h2>4. Interacting With Our Chatbot</h2>
Now that we've built our MCP server and host application, <strong>let's interact with our chatbot and test the elicitation flow</strong>.

We’ll use the <a href="https://www.baeldung.com/httpie-http-client-command-line">HTTPie</a> CLI to invoke the chatbot’s API endpoint:
<pre class="add-horizontal-scrollbar"><code class="language-bash">http POST :8080/chat question="Who wrote the article 'Testing CORS in Spring Boot?' on Baeldung, and how can I contact them?"</code></pre>
Here, we send a simple question asking for details regarding the author who wrote a specific article. <strong>We deliberately don't specify the username or reason in our query to trigger the elicitation flow on the MCP server</strong>.

Let’s see what we get as a response:
<pre class="add-horizontal-scrollbar"><code class="language-json">{
    "answer": "The article 'Testing CORS in Spring Boot' on Baeldung was written by John Doe. You can contact him via email at [john.doe@baeldung.com](mailto:john.doe@baeldung.com)."
}</code></pre>
As we can see, the chatbot successfully returns the author's details.

Let's look at the logs of our chatbot to confirm the elicitation request was received:
<pre class="add-horizontal-scrollbar"><code class="language-bash">[2026-01-28 13:16:25] [INFO] [c.b.s.m.c.ChatbotConfiguration] - Elicitation requested: Baeldung username and reason required.
[2026-01-28 13:16:25] [INFO] [c.b.s.m.c.ChatbotConfiguration] - Requested schema: {type=object, properties={reason={type=string}, username={type=string}}, required=[reason, username]}</code></pre>
<strong>The logs confirm that our elicitation handler received the request from the MCP server</strong>, including the message and the expected schema for the response.

Now, let's also examine the logs from our MCP server to confirm the complete flow:
<pre class="add-horizontal-scrollbar"><code class="language-bash">[2026-01-28 15:28:00] [INFO] [c.b.s.m.s.AuthorRepository] - Author requested for article: Testing CORS in Spring Boot
[2026-01-28 15:28:00] [INFO] [c.b.s.m.s.AuthorRepository] - Article is premium, further information required
[2026-01-28 15:28:00] [INFO] [c.b.s.m.s.AuthorRepository] - Required details missing, initiating elicitation
[2026-01-28 15:28:00] [INFO] [c.b.s.m.s.AuthorRepository] - Elicitation accepted - username: john.smith, reason: Contacting author for article feedback
[2026-01-28 15:28:00] [INFO] [c.b.s.m.s.AuthorRepository] - Access granted, returning author details</code></pre>
Here, <strong>the tool detected that the article is premium, initiated an elicitation request to gather the missing parameters, received the hardcoded values from our elicitation handler, and finally executed the tool logic to return the author details</strong>.
<h2>5. Conclusion</h2>
In this article, we explored how to implement MCP Elicitations with Spring AI.

We built an MCP server exposing a tool that requests additional information when accessing premium content. Then, we created an MCP host application with an elicitation handler that responds to these requests.

Finally, we tested our implementation and verified the elicitation flow through the application logs. This pattern enables more interactive AI applications where tools can gather additional context from the user when needed.

As always, all the code examples used in this article are available <a href="https://github.com/eugenp/tutorials/tree/master/spring-ai-modules/spring-ai-mcp-elicitations">over on GitHub</a>.
"""

print(ARTICLE)