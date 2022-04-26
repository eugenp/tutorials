package com.baeldung.javadoc;

public class CodeSnippetFormatting {

    /**
     * This is an example to show default behavior of code snippet formatting in Javadocs
     * 
     * public class Application(){
     * 
     * }
     * 
     */
    public void showCodeSnippetFormatting() {
        // do nothing
    }

    /**
     * This is an example to show usage of HTML pre tag while code snippet formatting in Javadocs
     * 
     * <pre>
     * public class Application(){
     *     List<Integer> nums = new ArrayList<>();
     * }
     * 
     * </pre>
     */
    public void showCodeSnippetFormattingUsingPRETag() {
        // do nothing
    }

    /**
     * This is an example to show usage of HTML character entities while code snippet formatting in Javadocs
     * 
     * <pre>
     * public class Application(){
     *     List&lt;Integer&gt; nums = new ArrayList<>();
     * }
     * 
     * </pre>
     */
    public void showCodeSnippetFormattingUsingCharacterEntities() {
        // do nothing
    }

    /**
     * This is an example to show usage of javadoc code tag while code snippet formatting in Javadocs
     * 
     * <pre>
     * 
     * public class Application(){
     *     {@code List<Integer> nums = new ArrayList<>(); }
     * }
     *
     * </pre>
     */
    public void showCodeSnippetFormattingUsingCodeTag() {
        // do nothing
    }

    /**
     * This is an example to show issue faced while using annotations in Javadocs
     * 
     * <pre>
     * 
     * public class Application(){
     *            @Getter
     *     {@code List<Integer> nums = new ArrayList<>(); }
     * }
     *
     * </pre>
     */
    public void showCodeSnippetFormattingIssueUsingCodeTag() {
        // do nothing
    }

    /**
     * This is an example to show usage of javadoc code tag for handling '@' character
     * 
     * <pre>
     * 
     * public class Application(){
     *     {@code @Getter}
     *     {@code List<Integer> nums = new ArrayList<>(); }
     * }
     *
     * </pre>
     */
    public void showCodeSnippetAnnotationFormattingUsingCodeTag() {
        // do nothing
    }


    /**
     * This is an example to show difference in javadoc literal and code tag
     * 
     * <p>
     * 
     * {@literal @Getter}
     * {@literal List<Integer> nums = new ArrayList<>(); }
     *   
     * <br />
     * {@code @Getter}
     * {@code List<Integer> nums = new ArrayList<>(); }
     * </p>
     */
    public void showCodeSnippetCommentsFormattingUsingCodeAndLiteralTag() {
        // do nothing
    }

    /**
     * This is an example to illustrate a basic jQuery code snippet embedded in documentation comments
     * <pre>
     * {@code <script>}
     * $document.ready(function(){
     *     console.log("Hello World!);
     * })
     * {@code </script>}
     * </pre>
     */
    public void showJSCodeSnippetUsingJavadoc() {
        // do nothing
    }

    /**
     * This is an example to illustrate an HTML code snippet embedded in documentation comments
     * <pre>{@code
     * <html>
     * <body>
     * <h1>Hello World!</h1>
     * </body>
     * </html>}
     * </pre>
     * 
     */
    public void showHTMLCodeSnippetUsingJavadoc() {
        // do nothing
    }

    /**
     * This is an example to illustrate an HTML code snippet embedded in documentation comments
     * <pre>
     * <html>
     * <body>
     * <h1>Hello World!</h1>
     * </body>
     * </html>
     * </pre>
     * 
     */
    public void showHTMLCodeSnippetIssueUsingJavadoc() {
        // do nothing
    }

}
