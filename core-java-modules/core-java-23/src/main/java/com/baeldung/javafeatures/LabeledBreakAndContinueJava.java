void main() {

    System.out.println("Basics:");
    showBasicBehaviour();

    // ---

    List<List<String>> listOfLists = List.of((List.of("here")));

    var findByBreak = findByBreak(listOfLists, "target");
    System.out.println("findByBreak: " + findByBreak);

    var findByStreams = findByStreams(listOfLists, "target");
    System.out.println("findByStreams: " + findByStreams);
}

private void showBasicBehaviour() {
    outer:
    // <-- // label
    for (int i = 0; i < 5; i++) {
        for (int j = 0; j < 5; j++) {
            System.out.println(i + ", " + j);
            if (j == 2) {
                break outer; // exits outer loop
            }
        }
    }
}

private boolean findByBreak(List<List<String>> listOfLists, String target) {
    boolean containsExists = false;

    outer:
    for (List<String> parent : listOfLists) {
        for (String child : parent) {
            if (child.contains(target)) {
                containsExists = true;
                break outer;
            }
        }
    }

    return containsExists;
}

private boolean findByStreams(List<List<String>> listOfLists, String target) {
    return listOfLists.stream()
        .flatMap(Collection::stream)
        .anyMatch(child -> child.contains(target));
}
