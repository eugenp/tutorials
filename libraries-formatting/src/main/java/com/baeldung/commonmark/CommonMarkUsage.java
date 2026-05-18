package com.baeldung.commonmark;

import org.commonmark.node.Document;
import org.commonmark.node.Heading;
import org.commonmark.node.Node;
import org.commonmark.node.Text;
import org.commonmark.parser.IncludeSourceSpans;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlNodeRendererContext;
import org.commonmark.renderer.html.HtmlNodeRendererFactory;
import org.commonmark.renderer.html.HtmlRenderer;
import org.commonmark.renderer.markdown.MarkdownRenderer;

public class CommonMarkUsage {

    public static String markDownToHtml(String markdown) {
        Parser parser = Parser.builder()
            .build();
        Node node = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder()
            .build();
        return renderer.render(node);

    }

    public static int processParsedNode(String markdown) {
        Parser parser = Parser.builder()
            .build();
        Node node = parser.parse(markdown);
        WordCountVisitor visitor = new WordCountVisitor();
        node.accept(visitor);
        return visitor.wordCount;

    }

    public static String htmlToMarkDown(String htmlHeading) {
        // Build Document
        Heading heading = new Heading();
        heading.setLevel(2);

        heading.appendChild(new Text(htmlHeading));
        Document document = new Document();
        document.appendChild(heading);

        // Render to markdown
        MarkdownRenderer renderer = MarkdownRenderer.builder()
            .build();
        return renderer.render(document);
    }

    public static String sourcePosition(String source) {
        Parser parser = Parser.builder()
            .includeSourceSpans(IncludeSourceSpans.BLOCKS_AND_INLINES)
            .build();
        Node node = parser.parse(source);

        return "";
    }

    public static String changingHtmlAttribute(String source) {
        Parser parser = Parser.builder()
            .build();
        Node node = parser.parse(source);
        HtmlRenderer renderer = HtmlRenderer.builder()
            .attributeProviderFactory(new AttributeProviderFactory() {
                @Override
                public AttributeProvider create(AttributeProviderContext context) {
                    return new ImageAttributeProvider();
                }
            })
            .build();

        return renderer.render(node);
    }

    public static String customizingHtmlRendering(String source) {
        Parser parser = Parser.builder()
            .build();
        Node node = parser.parse(source);
        HtmlRenderer renderer = HtmlRenderer.builder().nodeRendererFactory(new HtmlNodeRendererFactory() {
                @Override
                public NodeRenderer create(HtmlNodeRendererContext context) {
                    return new IndentedCodeBlockNodeRenderer(context);
                }
            })
            .build();

        return renderer.render(node);
    }

}
