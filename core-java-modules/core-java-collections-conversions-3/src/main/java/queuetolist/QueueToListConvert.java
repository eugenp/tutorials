package queuetolist;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.stream.Collectors;

public class QueueToListConvert {

    public static ArrayList<String> convertUsingArrayListConstructor(Queue<String> queue) {
        if (queue == null) {
            return null;
        }
        return new ArrayList<>(queue);
    }

    public static List<String> convertUsingAddAllMethod(Queue<String> queue) {
        if (queue == null) {
            return null;
        }
        List<String> queueList = new ArrayList<>();
        queueList.addAll(queue);

        return queueList;
    }

    public static LinkedList<String> convertUsingLinkedListConstructor(Queue<String> queue) {
        if (queue == null) {
            return null;
        }
        return new LinkedList<>(queue);
    }

    public static List<String> convertUsingStream(Queue<String> queue) {
        if (queue == null) {
            return null;
        }
        return queue.stream().collect(Collectors.toList());
    }

    public static List<String> convertUsingGuava(Queue<String> queue) {
        if (queue == null) {
            return null;
        }
        List<String> queueList = Lists.newArrayList(queue);

        return queueList;
    }
}
