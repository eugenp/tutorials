public String escapeSpecialCharacters(String data) {
    if (data == null) {
        throw new IllegalArgumentException("Input data cannot be null");
    }
    // Replace all line breaks with spaces first
    data = data.replaceAll("\\R", " ");
    String escapedData = data;

    if (data.contains(",") || data.contains("\"") || data.contains("'")) {
        data = data.replace("\"", "\"\"");
        escapedData = "\"" + data + "\"";
    }
    return escapedData;
}

public static void main(String[] args) {
    System.out.println(escapeSpecialCharacters("hi\nbye,bye")); // "hi bye,bye"
}
