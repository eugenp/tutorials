package com.baeldung.annotations;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Contract;

public class Demo {

    @Contract("_ -> new")
    Person fromName(String name) {
        return new Person().withName(name);
    }

    @Contract(" -> fail")
    void alwaysFail() {
        throw new RuntimeException();
    }

    @Contract(" -> fail")
    void doNothingWithWrongContract() {

    }

    @Contract("_, null -> null; null, _ -> param2; _, !null -> !null")
    String concatenateOnlyIfSecondArgumentIsNotNull(String head, String tail) {
        if (tail == null) {
            return null;
        }
        if (head == null) {
            return tail;
        }
        return head + tail;
    }

    void uselessNullCheck() {
        String head = "1234";
        String tail = "5678";
        String concatenation = concatenateOnlyIfSecondArgumentIsNotNull(head, tail);
        if (concatenation != null) {
            System.out.println(concatenation);
        }
    }

    void uselessNullCheckOnInferredAnnotation() {
        if (StringUtils.isEmpty(null)) {
            System.out.println("baeldung");
        }
    }

    @Contract(pure = true)
    String replace(String string, char oldChar, char newChar) {
        return string.replace(oldChar, newChar);
    }

    @Contract(value = "true -> false; false -> true", pure = true)
    boolean not(boolean input) {
        return !input;
    }

    @Contract("true -> new")
    void contractExpectsWrongParameterType(List<Integer> integers) {

    }

    @Contract("_, _ -> new")
    void contractExpectsMoreParametersThanMethodHas(String s) {

    }

    @Contract("_ -> _; null -> !null")
    String secondContractClauseNotReachable(String s) {
        return "";
    }

    @Contract("_ -> true")
    void contractExpectsWrongReturnType(String s) {

    }

    // NB: the following examples demonstrate how to use the mutates attribute of the annotation
    // This attribute is currently experimental and could be changed or removed in the future
    @Contract(mutates = "param")
    void incrementArrayFirstElement(Integer[] integers) {
        if (integers.length > 0) {
            integers[0] = integers[0] + 1;
        }
    }

    @Contract(pure = true, mutates = "param")
    void impossibleToMutateParamInPureFunction(List<String> strings) {
        if (strings != null) {
            strings.forEach(System.out::println);
        }
    }

    @Contract(mutates = "param3")
    void impossibleToMutateThirdParamWhenMethodHasOnlyTwoParams(int a, int b) {

    }

    @Contract(mutates = "param")
    void impossibleToMutableImmutableType(String s) {

    }

    @Contract(mutates = "this")
    static void impossibleToMutateThisInStaticMethod() {

    }

}
