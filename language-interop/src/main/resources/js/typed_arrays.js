function arrays(arr) {

    var javaIntArray = Java.to(arr, "int[]");
    print(javaIntArray[0]);
    print(javaIntArray[1]);
    print(javaIntArray[2]);
}

arrays([100, "1654", true]);
