package com.baeldung.selenium.stale;

import org.openqa.selenium.WebElement;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class WebElementUtils {

    private WebElementUtils(){
    }

    public static void callMethod(WebElement element, Consumer<WebElement> method) {
        method.accept(element);
    }

    public static <U> void callMethod(WebElement element, BiConsumer<WebElement, U> method, U parameter) {
        method.accept(element, parameter);
    }

    public static <T> T callMethodWithReturn(WebElement element, Function<WebElement, T> method) {
        return method.apply(element);
    }

    public static <T, U> T callMethodWithReturn(WebElement element, BiFunction<WebElement, U, T> method, U parameter) {
        return method.apply(element, parameter);
    }
}