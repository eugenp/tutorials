package com.baeldung.langchain;

import static dev.langchain4j.data.document.FileSystemDocumentLoader.loadDocument;
import static java.time.Duration.ofSeconds;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Test;

import dev.langchain4j.chain.ConversationalRetrievalChain;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.retriever.EmbeddingStoreRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

public class ChatWithDocumentLiveTests {

    @Test
    public void givenDocument_whenPrompted_thenValidResponse() {

        EmbeddingModel embeddingModel = new AllMiniLmL6V2EmbeddingModel();

        EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
            .documentSplitter(DocumentSplitters.recursive(500, 0))
            .embeddingModel(embeddingModel)
            .embeddingStore(embeddingStore)
            .build();

        Document document = loadDocument(toPath("src/test/resources/example-files/simpson's_adventures.txt"));
        ingestor.ingest(document);

        ChatLanguageModel chatModel = OpenAiChatModel.builder()
            .apiKey(Constants.OPEN_API_KEY)
            .timeout(ofSeconds(60))
            .build();

        ConversationalRetrievalChain chain = ConversationalRetrievalChain.builder()
            .chatLanguageModel(chatModel)
            .retriever(EmbeddingStoreRetriever.from(embeddingStore, embeddingModel))
            // .chatMemory() // you can override default chat memory
            // .promptTemplate() // you can override default prompt template
            .build();

        String answer = chain.execute("Who is Simpson?");

        Logger.getGlobal()
            .info(answer);
        Assert.assertNotNull(answer);

    }

    private static Path toPath(String fileName) {
        try {
            URL fileUrl = new File(fileName).toURI()
                .toURL();
            System.out.println(new File(fileName).toURI()
                .toURL());
            return Paths.get(fileUrl.toURI());
        } catch (URISyntaxException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}
