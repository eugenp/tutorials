# PDF

This module contains articles about PDF files.

## Java中的PDF转换

1. 简介

    在这篇快速文章中，我们将重点讨论在Java中进行PDF文件和其他格式的程序化转换。

    更具体地说，我们将描述如何通过使用多个Java开源库将PDF保存为图像文件，如PNG或JPEG，将PDF转换成Microsoft Word文档，导出为HTML，并提取文本。

2. Maven的依赖性

    我们要看的第一个库是[Pdf2Dom](http://cssbox.sourceforge.net/pdf2dom/documentation.php)。让我们先来看看我们需要添加到项目中的Maven依赖项：

    ```xml
    <dependency>
        <groupId>org.apache.pdfbox</groupId>
        <artifactId>pdfbox-tools</artifactId>
        <version>2.0.25</version>
    </dependency>
    <dependency>
        <groupId>net.sf.cssbox</groupId>
        <artifactId>pdf2dom</artifactId>
        <version>2.0.1</version>
    </dependency>
    ```

    我们将使用第一个依赖关系来加载选定的PDF文件。第二个依赖关系负责转换本身。最新的版本可以在这里找到：[pdfbox-tools](https://search.maven.org/classic/#search%7Cga%7C1%7Ca%3A%22pdfbox-tools%22)和[pdf2dom](https://search.maven.org/classic/#search%7Cga%7C1%7Cpdf2dom)。

    更重要的是，我们将使用iText从PDF文件中提取文本，并使用POI来创建.docx文档。

    让我们来看看我们需要在项目中加入的Maven依赖项：

    ```xml
    <dependency>
        <groupId>com.itextpdf</groupId>
        <artifactId>itextpdf</artifactId>
        <version>5.5.10</version>
    </dependency>
    <dependency>
        <groupId>com.itextpdf.tool</groupId>
        <artifactId>xmlworker</artifactId>
        <version>5.5.10</version>
    </dependency>
    <dependency>
        <groupId>org.apache.poi</groupId>
        <artifactId>poi-ooxml</artifactId>
        <version>3.15</version>
    </dependency>
    <dependency>
        <groupId>org.apache.poi</groupId>
        <artifactId>poi-scratchpad</artifactId>
        <version>3.15</version>
    </dependency>
    ```

    最新版本的[iText](https://search.maven.org/classic/#search%7Cga%7C1%7Cg%3A%22com.itextpdf%22)可以在这里找到，你可以在这里寻找[Apache POI](https://search.maven.org/classic/#search%7Cga%7C1%7Cpoi)。

3. PDF和HTML的转换

    为了处理HTML文件，我们将使用Pdf2Dom--一个PDF解析器，它可以将文档转换为HTML DOM表示。获得的DOM树可以被序列化为一个HTML文件或进一步处理。

    为了将PDF转换为HTML，我们需要使用iText提供的XMLWorker库。

    1. PDF到HTML

        让我们看一下从PDF到HTML的简单转换：

        ```java
        private void generateHTMLFromPDF(String filename) {
            PDDocument pdf = PDDocument.load(new File(filename));
            Writer output = new PrintWriter("src/output/pdf.html", "utf-8");
            new PDFDomTree().writeText(pdf, output);
            
            output.close();
        }
        ```

        在上面的代码片段中，我们使用PDFBox的加载API来加载PDF文件。装入PDF后，我们使用解析器来解析文件，并写到java.io.Writer指定的输出。

        请注意，将PDF转换为HTML从来都不是一个100%的、像素对像素的结果。其结果取决于特定PDF文件的复杂性和结构。

        - [ ] **ERROR**：`21:09:02.659 [main] INFO  org.fit.pdfdom.PathDrawer - Filled curved paths are not yet supported by Pdf2Dom.` 转换失败。

    2. HTML转PDF

        现在，让我们看一下从HTML到PDF的转换：

        ```java
        private static void generatePDFFromHTML(String filename) {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document,
            new FileOutputStream("src/output/html.pdf"));
            document.open();
            XMLWorkerHelper.getInstance().parseXHtml(writer, document,
            new FileInputStream(filename));
            document.close();
        }
        ```

        注意，在将HTML转换为PDF时，你需要确保HTML的所有标签都正确启动和关闭，否则PDF将无法创建。这种方法的积极方面是，创建的PDF将与HTML文件中的内容完全相同。

4. PDF到图像的转换

    有许多方法可以将PDF文件转换为图像。最流行的解决方案之一是名为[Apache PDFBox](https://pdfbox.apache.org/)的。这个库是一个用于处理PDF文件的开源Java工具。对于图像到PDF的转换，我们将再次使用iText。

    1. PDF到图像

        要开始将PDF转换为图像，我们需要使用上一节中提到的依赖关系--pdfbox-tools。

        让我们看一下代码示例：

        ```java
        private void generateImageFromPDF(String filename, String extension) {
            PDDocument document = PDDocument.load(new File(filename));
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            for (int page = 0; page < document.getNumberOfPages(); ++page) {
                BufferedImage bim = pdfRenderer.renderImageWithDPI(
                page, 300, ImageType.RGB);
                ImageIOUtil.writeImage(
                bim, String.format("src/output/pdf-%d.%s", page + 1, extension), 300);
            }
            document.close();
        }
        ```

        在上述代码中，有几个重要部分。我们需要使用PDFRenderer，以便将PDF渲染成一个BufferedImage。另外，PDF文件的每一页都需要被单独渲染。

        最后，我们使用Apache PDFBox工具中的ImageIOUtil来写一个图像，扩展名由我们指定。可能的文件格式是jpeg、jpg、gif、tiff或png。

        请注意，Apache PDFBox是一个高级工具--我们可以从头开始创建我们自己的PDF文件，在PDF文件中填写表格，签署和/或加密PDF文件。

    2. 图像转PDF

        让我们来看看这个代码例子：

        ```java
        private static void generatePDFFromImage(String filename, String extension) {
            Document document = new Document();
            String input = filename + "." + extension;
            String output = "src/output/" + extension + ".pdf";
            FileOutputStream fos = new FileOutputStream(output);

            PdfWriter writer = PdfWriter.getInstance(document, fos);
            writer.open();
            document.open();
            document.add(Image.getInstance((new URL(input))));
            document.close();
            writer.close();
        }
        ```

        请注意，我们可以以文件形式提供图像，也可以从URL加载图像，如上面的例子所示。此外，我们可以使用的输出文件的扩展名是jpeg、jpg、gif、tiff或png。

        - [ ] **ERROR** `java.net.UnknownHostException: cdn2.baeldung.netdna-cdn.com` 图片转PDF失败

5. PDF到文本的转换

    为了从PDF文件中提取原始文本，我们还将再次使用Apache PDFBox。对于文本到PDF的转换，我们要使用iText。

    1. PDF到文本

        我们创建了一个名为generateTxtFromPDF(...)的方法，并将其分为三个主要部分：加载PDF文件，提取文本，以及最终创建文件。

        让我们从加载部分开始：

        ```java
        File f = new File(filename);
        String parsedText;
        PDFParser parser = new PDFParser(new RandomAccessFile(f, "r"));
        parser.parse();
        ```

        为了读取一个PDF文件，我们使用PDFParser，有一个 "r"（读取）选项。此外，我们还需要使用parser.parse()方法，使PDF被解析为一个流，并填充到COSDocument对象中。

        让我们来看看提取文本的部分：

        ```java
        COSDocument cosDoc = parser.getDocument();
        PDFTextStripper pdfStripper = new PDFTextStripper();
        PDDocument pdDoc = new PDDocument(cosDoc);
        parsedText = pdfStripper.getText(pdDoc);
        ```

        在第一行中，我们将在cosDoc变量中保存COSDocument。然后它将被用来构造PDocument，这是PDF文档的内存表示。最后，我们将使用PDFTextStripper来返回文档的原始文本。在所有这些操作之后，我们需要使用close()方法来关闭所有使用的流。

        在最后一部分，我们将使用简单的Java PrintWriter将文本保存到新创建的文件中：

        ```java
        PrintWriter pw = new PrintWriter("src/output/pdf.txt");
        pw.print(parsedText);
        pw.close();
        ```

        请注意，你不能在纯文本文件中保留格式，因为它只包含文本。

    2. 文本到PDF

        将文本文件转换为PDF是有点棘手的。为了保持文件的格式，你需要应用额外的规则。

        在下面的例子中，我们没有考虑到文件的格式问题。

        首先，我们需要定义PDF文件的大小、版本和输出文件。让我们来看看这个代码例子：

        ```java
        Document pdfDoc = new Document(PageSize.A4);
        PdfWriter.getInstance(pdfDoc, new FileOutputStream("src/output/txt.pdf"))
        .setPdfVersion(PdfWriter.PDF_VERSION_1_7);
        pdfDoc.open();
        ```

        在下一步，我们将定义字体和用于生成新段落的命令：

        ```java
        Font myfont = new Font();
        myfont.setStyle(Font.NORMAL);
        myfont.setSize(11);
        pdfDoc.add(new Paragraph("\n"));
        ```

        最后，我们要在新创建的PDF文件中添加段落：

        ```java
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String strLine;
        while ((strLine = br.readLine()) != null) {
            Paragraph para = new Paragraph(strLine + "\n", myfont);
            para.setAlignment(Element.ALIGN_JUSTIFIED);
            pdfDoc.add(para);
        }
        pdfDoc.close();
        br.close();
        ```

6. PDF到Docx的转换

    从Word文档中创建PDF文件并不容易，我们在这里不涉及这个话题。我们推荐第三方库来做这件事，比如[jWordConvert](https://www.qoppa.com/wordconvert/)。

    要从PDF创建Microsoft Word文件，我们需要两个库。这两个库都是开源的。第一个是iText，它用于从PDF文件中提取文本。第二个是POI，用于创建.docx文档。

    让我们看一下PDF加载部分的代码片段：

    ```java
    XWPFDocument doc = new XWPFDocument();
    String pdf = filename;
    PdfReader reader = new PdfReader(pdf);
    PdfReaderContentParser parser = new PdfReaderContentParser(reader);
    ```

    加载PDF后，我们需要在循环中分别读取和渲染每一页，然后写到输出文件中：

    ```java
    for (int i = 1; i <= reader.getNumberOfPages(); i++) {
        TextExtractionStrategy strategy =
        parser.processContent(i, new SimpleTextExtractionStrategy());
        String text = strategy.getResultantText();
        XWPFParagraph p = doc.createParagraph();
        XWPFRun run = p.createRun();
        run.setText(text);
        run.addBreak(BreakType.PAGE);
    }
    FileOutputStream out = new FileOutputStream("src/output/pdf.docx");
    doc.write(out);
    // Close all open files
    ```

    请注意，使用SimpleTextExtractionStrategy()提取策略，我们会失去所有的格式化规则。为了解决这个问题，请使用这里描述的提取策略，以实现一个更复杂的解决方案。

    - [x] **ERROR**: Exception in thread "main" java.lang.NoSuchMethodError: org.apache.poi.util.StringUtil.isNotBlank(Ljava/lang/CharSequence;)Z

        poi依赖包版本不一致引起：`<poi-scratchpad.version>=<poi-ooxml.version>` 应改为一个参数。

7. PDF to X 商业库

    在前面的章节中，我们介绍了开放源码库。还有一些库值得注意，但它们是付费的：

    - [jPDFImages](https://www.qoppa.com/pdfimages/) - jPDFImages可以从PDF文档的页面中创建图像，并将其导出为JPEG、TIFF或PNG图像。
    - [JPEDAL](https://www.idrsolutions.com/jpedal/) - JPedal是一个积极开发的、非常有能力的原生Java PDF库SDK，用于打印、查看和转换文件
    - [pdfcrowd](http://pdfcrowd.com/web-html-to-pdf-java/) - 这是另一个Web/HTML到PDF和PDF到Web/HTML的转换库，有先进的GUI

8. 结语

    在这篇文章中，我们讨论了将PDF文件转换成各种格式的方法。

## Relevant Articles

- [x] [PDF Conversions in Java](https://www.baeldung.com/pdf-conversions-java)
- [Creating PDF Files in Java](https://www.baeldung.com/java-pdf-creation)
- [Generating PDF Files Using Thymeleaf](https://www.baeldung.com/thymeleaf-generate-pdf)
- [Java Convert PDF to Base64](https://www.baeldung.com/java-convert-pdf-to-base64)
- [HTML to PDF Using OpenPDF](https://www.baeldung.com/java-html-to-pdf)
- [Reading PDF File Using Java](https://www.baeldung.com/java-pdf-file-read)

## Code

本教程的完整实现可以在[GitHub项目](https://github.com/eugenp/tutorials/tree/master/pdf)中找到 - 这是一个基于Maven的项目。为了测试，只需简单地运行示例，在输出文件夹中查看结果。
