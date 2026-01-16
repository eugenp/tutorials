void main() {
    List<String> authors = List.of("James", "Alex", "John", "Alex", "Daniel", "Eugen");
    for (String name : authors) {
        IO.println(name + ": " + name.length());
    }
}

