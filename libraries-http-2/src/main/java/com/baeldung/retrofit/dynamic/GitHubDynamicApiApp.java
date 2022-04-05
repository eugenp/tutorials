package com.baeldung.retrofit.dynamic;

import java.io.IOException;
import java.util.List;

public class GitHubDynamicApiApp {

    public static void main(String[] args) throws IOException {
        String url = "https://api.github.com/users/eugenp/repos";
        List<String> topContributors = new GitHubDynamicApiService().getTopContributors(url);
        topContributors.forEach(System.out::println);
    }
}
