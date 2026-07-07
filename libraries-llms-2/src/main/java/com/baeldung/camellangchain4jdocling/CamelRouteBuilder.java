package com.baeldung.camellangchain4jdocling;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

class CamelRouteBuilder extends RouteBuilder {

    @Override
    public void configure() {

        from("file:documents?include=.*\\.(pdf|docx|pptx|html|md)&noop=true&idempotent=true").routeId("document-analysis-workflow")
            .log("Processing document: ${header.CamelFileName}")
            .setBody(simple("${body.file.absolutePath}"))
            .to("docling:CONVERT_TO_MARKDOWN?useDoclingServe=true&doclingServeUrl=http://localhost:5001&contentInBody=true")
            .setProperty("convertedMarkdown", body())
            .setHeader(Exchange.FILE_NAME, simple("${header.CamelFileName.replaceFirst('\\.[^.]+$', '')}.md"))
            .to("file:output")
            .setBody(simple("""
                You are a helpful document analysis assistant. Please analyze
                the following document and provide:
                1. A brief summary (2-3 sentences)
                2. Key topics and main points
                3. Any important findings or conclusions
                
                Document content:
                ${exchangeProperty.convertedMarkdown}
                """))
            .to("langchain4j-chat:analysis?chatModel=#chatModel")
            .setHeader(Exchange.FILE_NAME, simple("${header.CamelFileName.replaceFirst('\\.[^.]+$', '')}-analysis.md"))
            .to("file:analysis");
    }
}
