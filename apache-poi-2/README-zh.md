# Apache POI

This module contains articles about Apache POI.

## 用Apache POI在Java中处理Microsoft Word

1. 概述

    [Apache POI](https://poi.apache.org/)是一个Java库，用于处理基于Office开放式XML标准（OOXML）和微软的OLE 2复合文档格式（OLE2）的各种文件格式。

    本教程的重点是Apache POI对Microsoft Word的支持，它是最常用的Office文件格式。它讲述了格式化和生成MS Word文件所需的步骤以及如何解析这个文件。

2. Maven依赖性

    Apache POI处理MS Word文件所需的唯一依赖性是：

    ```xml
    <dependency>
        <groupId>org.apache.poi</groupId>
        <artifactId>poi-ooxml</artifactId>
        <version>5.2.3</version>
    </dependency>
    ```

    请点击[这里](https://search.maven.org/classic/#search%7Cga%7C1%7Cg%3A%22org.apache.poi%22%20AND%20a%3A%22poi-ooxml%22)获取该工件的最新版本。

3. 准备工作

    现在让我们看一下用于促进生成MS Word文件的一些元素。

    1. 资源文件

        我们将收集三个文本文件的内容，并把它们写进一个MS Word文件--命名为rest-with-spring.docx。

        此外，logo-leaf.png文件被用来在该新文件中插入一个图像。所有这些文件确实存在于classpath中，并由几个静态变量表示：

        ```java
        public static String logo = "logo-leaf.png";
        public static String paragraph1 = "poi-word-para1.txt";
        public static String paragraph2 = "poi-word-para2.txt";
        public static String paragraph3 = "poi-word-para3.txt";
        public static String output = "rest-with-spring.docx";
        ```

        对于那些好奇的人来说，资源库中的这些资源文件的内容，其链接在本教程的最后一节中给出，是从网站的这个[课程页面](https://www.baeldung.com/rest-with-spring-course?utm_source=blog&utm_medium=web&utm_content=menu&utm_campaign=rws)中提取的。
    2. 助手方法

        由用于生成MS Word文件的逻辑组成的主要方法，将在下一节中描述，它使用了一个辅助方法：

        ```java
        public String convertTextFileToString(String fileName) {
            try (Stream<String> stream 
              = Files.lines(Paths.get(ClassLoader.getSystemResource(fileName).toURI()))) {
                return stream.collect(Collectors.joining(" "));
            } catch (IOException | URISyntaxException e) {
                return null;
            }
        }
        ```

        该方法提取位于classpath上的一个文本文件中的内容，该文件的名称是传入的String参数。然后，它将这个文件中的行连接起来，并返回连接后的字符串。

4. MS Word文件的生成

    本节给出了关于如何格式化和生成Microsoft Word文件的说明。在处理文件的任何部分之前，我们需要有一个XWPFDocument实例：

    `XWPFDocument document = new XWPFDocument();`

    1. 格式化标题和副标题

        为了创建标题，我们需要首先实例化XWPFParagraph类，并在新对象上设置对齐：

        ```java
        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);
        ```

        段落的内容需要被包裹在一个XWPFRun对象中。我们可以配置这个对象来设置一个文本值和它的相关样式：

        ```java
        XWPFRun titleRun = title.createRun();
        titleRun.setText("Build Your REST API with Spring");
        titleRun.setColor("009933");
        titleRun.setBold(true);
        titleRun.setFontFamily("Courier");
        titleRun.setFontSize(20);
        ```

        人们应该能够从名字中推断出这些设置方法的目的。

        以类似的方式，我们创建一个包围副标题的XWPFParagraph实例：

        ```java
        XWPFParagraph subTitle = document.createParagraph();
        subTitle.setAlignment(ParagraphAlignment.CENTER);
        ```

        让我们也对副标题进行格式化：

        ```java
        XWPFRun subTitleRun = subTitle.createRun();
        subTitleRun.setText("from HTTP fundamentals to API Mastery");
        subTitleRun.setColor("00CC44");
        subTitleRun.setFontFamily("Courier");
        subTitleRun.setFontSize(16);
        subTitleRun.setTextPosition(20);
        subTitleRun.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
        ```

        setTextPosition方法设置了副标题和后续图片之间的距离，而setUnderline则决定了下划线的模式。

        注意，我们对标题和副标题的内容都进行了硬编码，因为这些语句太短，没有理由使用辅助方法。

    2. 插入图片

        图片也需要被包裹在一个XWPFParagraph实例中。我们希望图片水平居中，并放置在副标题下，因此必须在上面给出的代码下面放入以下片段：

        ```java
        XWPFParagraph image = document.createParagraph();
        image.setAlignment(ParagraphAlignment.CENTER);
        ```

        下面是如何设置这个图像和它下面的文本之间的距离：

        ```java
        XWPFRun imageRun = image.createRun();
        imageRun.setTextPosition(20);
        ```

        从classpath上的一个文件中提取一个图像，然后以指定的尺寸插入MS Word文件中：

        ```java
        Path imagePath = Paths.get(ClassLoader.getSystemResource(logo).toURI());
        imageRun.addPicture(Files.newInputStream(imagePath),
          XWPFDocument.PICTURE_TYPE_PNG, imagePath.getFileName().toString(),
          Units.toEMU(50), Units.toEMU(50));
        ```

    3. 格式化段落

        下面是我们如何创建第一个段落，其内容取自poi-word-para1.txt文件：

        ```java
        XWPFParagraph para1 = document.createParagraph();
        para1.setAlignment(ParagraphAlignment.BOTH);
        String string1 = convertTextFileToString(paragraph1);
        XWPFRun para1Run = para1.createRun();
        para1Run.setText(string1);
        ```

        显然，创建一个段落与创建标题或副标题相似。这里唯一的区别是使用了辅助方法而不是硬编码的字符串。

        以类似的方式，我们可以使用文件poi-word-para2.txt和poi-word-para3.txt的内容创建另外两个段落：

        ```java
        XWPFParagraph para2 = document.createParagraph();
        para2.setAlignment(ParagraphAlignment.RIGHT);
        String string2 = convertTextFileToString(paragraph2);
        XWPFRun para2Run = para2.createRun();
        para2Run.setText(string2);
        para2Run.setItalic(true);

        XWPFParagraph para3 = document.createParagraph();
        para3.setAlignment(ParagraphAlignment.LEFT);
        String string3 = convertTextFileToString(paragraph3);
        XWPFRun para3Run = para3.createRun();
        para3Run.setText(string3);
        ```

        这三个段落的创建几乎是一样的，除了一些样式，如对齐或斜体。

    4. 生成MS Word文件

        现在我们准备从文档变量中写出一个Microsoft Word文件到内存中：

        ```java
        FileOutputStream out = new FileOutputStream(output);
        document.write(out);
        out.close();
        document.close();
        ```

        本节中所有的代码片段都被包裹在一个名为handleSimpleDoc的方法中。

5. 解析和测试

    本节概述了MS Word文件的解析和对结果的验证。

    1. 准备工作

        我们在测试类中声明一个静态字段：

        `static WordDocument wordDocument;`

        这个字段用来引用类的一个实例，该实例包含了第3和第4节中的所有代码片段。

        在解析和测试之前，我们需要初始化上面声明的静态变量，并通过调用handleSimpleDoc方法在当前工作目录下生成rest-with-spring.docx文件：

        ```java
        @BeforeClass
        public static void generateMSWordFile() throws Exception {
            WordTest.wordDocument = new WordDocument();
            wordDocument.handleSimpleDoc();
        }
        ```

        让我们进入最后一步：解析MS Word文件并对结果进行验证。

    2. 解析MS Word文件和验证

        首先，我们从项目目录中给定的MS Word文件中提取内容，并将这些内容存储在XWPFParagraph的List中：

        ```java
        Path msWordPath = Paths.get(WordDocument.output);
        XWPFDocument document = new XWPFDocument(Files.newInputStream(msWordPath));
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        document.close();
        ```

        接下来，让我们确保标题的内容和风格与我们之前设置的相同：

        ```java
        XWPFParagraph title = paragraphs.get(0);
        XWPFRun titleRun = title.getRuns().get(0);
        
        assertEquals("Build Your REST API with Spring", title.getText());
        assertEquals("009933", titleRun.getColor());
        assertTrue(titleRun.isBold());
        assertEquals("Courier", titleRun.getFontFamily());
        assertEquals(20, titleRun.getFontSize());
        ```

        为了简单起见，我们只验证文件中其他部分的内容，不考虑样式。对其样式的验证与我们对标题所做的类似：

        ```java
        assertEquals("from HTTP fundamentals to API Mastery",
          paragraphs.get(1).getText());
        assertEquals("What makes a good API?", paragraphs.get(3).getText());
        assertEquals(wordDocument.convertTextFileToString
          (WordDocument.paragraph1), paragraphs.get(4).getText());
        assertEquals(wordDocument.convertTextFileToString
          (WordDocument.paragraph2), paragraphs.get(5).getText());
        assertEquals(wordDocument.convertTextFileToString
          (WordDocument.paragraph3), paragraphs.get(6).getText());
        ```

        现在我们可以确信，rest-with-spring.docx文件的创建已经成功了。

6. 结语

本教程介绍了Apache POI对Microsoft Word格式的支持。它经历了生成一个MS Word文件和验证其内容所需的步骤。

## Relevant Articles

- [Adding a Column to an Excel Sheet Using Apache POI](https://www.baeldung.com/java-excel-add-column)
- [Add an Image to a Cell in an Excel File With Java](https://www.baeldung.com/java-add-image-excel)
- [Numeric Format Using POI](https://www.baeldung.com/apache-poi-numeric-format)
- [x] [Microsoft Word Processing in Java with Apache POI](https://www.baeldung.com/java-microsoft-word-with-apache-poi)
- [Creating a MS PowerPoint Presentation in Java](https://www.baeldung.com/apache-poi-slideshow)
- [Finding the Last Row in an Excel Spreadsheet From Java](https://www.baeldung.com/java-excel-find-last-row)
- [Setting Formulas in Excel with Apache POI](https://www.baeldung.com/java-apache-poi-set-formulas)
- [Set the Date Format Using Apache POI](https://www.baeldung.com/java-apache-poi-date-format)
- More articles: [[<-- prev]](../apache-poi/README-zh.md)

## Code

所有这些例子和代码片段的实现都可以在[GitHub](https://github.com/eugenp/tutorials/tree/master/apache-poi-2)项目中找到。
