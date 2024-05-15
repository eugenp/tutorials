package com.example;

import java.util.LinkedList;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/palindrome")
@Produces("text/plain")
public class PalindromeResource {


    private final MeterRegistry registry;

    private final LinkedList<String> list = new LinkedList<>();


    public PalindromeResource(MeterRegistry registry) {
        this.registry = registry;
        registry.gaugeCollectionSize("palindrome.list.size", Tags.empty(), list);
    }

    @GET
    @Path("check/{input}")
    public boolean checkPalindrome(String input) {
        Timer.Sample sample = Timer.start(registry);
        list.add(input);
        boolean result = isPalindrome(input);
        sample.stop(registry.timer("palindrome.timer"));
        return result;
    }

    private boolean isPalindrome(String input) {
        registry.counter("palindrome.counter").increment();
        int left = 0;
        int right = input.length() - 1;

        while (left < right) {
            if (input.charAt(left) != input.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    @DELETE
    @Path("empty-list")
    public void emptyList() {
        list.clear();
    }

    @GET
    @Path("/clearmetrics")
    public Response clearAllPollerMetrics() {
        registry.clear();
        list.clear();
        registry.gaugeCollectionSize("palindrome.list.size", Tags.empty(), list);
        return Response.ok().build();
    }
}

