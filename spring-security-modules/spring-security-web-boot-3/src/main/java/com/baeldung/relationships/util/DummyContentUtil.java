package com.baeldung.relationships.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.baeldung.relationships.models.AppUser;
import com.baeldung.relationships.models.Tweet;

public class DummyContentUtil {
    
    public static List<AppUser> generateDummyUsers() {
        List<AppUser> appUsers = new ArrayList<>();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        appUsers.add(new AppUser("Lionel Messi", "lionel@messi.com", passwordEncoder.encode("li1234")));
        appUsers.add(new AppUser("Cristiano Ronaldo", "cristiano@ronaldo.com", passwordEncoder.encode("c1234")));
        appUsers.add(new AppUser("Neymar Dos Santos", "neymar@neymar.com", passwordEncoder.encode("n1234")));
        appUsers.add(new AppUser("Luiz Suarez", "luiz@suarez.com", passwordEncoder.encode("lu1234")));
        appUsers.add(new AppUser("Andres Iniesta", "andres@iniesta.com", passwordEncoder.encode("a1234")));
        appUsers.add(new AppUser("Ivan Rakitic", "ivan@rakitic.com", passwordEncoder.encode("i1234")));
        appUsers.add(new AppUser("Ousman Dembele", "ousman@dembele.com", passwordEncoder.encode("o1234")));
        appUsers.add(new AppUser("Sergio Busquet", "sergio@busquet.com", passwordEncoder.encode("s1234")));
        appUsers.add(new AppUser("Gerard Pique", "gerard@pique.com", passwordEncoder.encode("g1234")));
        appUsers.add(new AppUser("Ter Stergen", "ter@stergen.com", passwordEncoder.encode("t1234")));
        return appUsers;
    }

    public static List<Tweet> generateDummyTweets(List<AppUser> users) {
        List<Tweet> tweets = new ArrayList<>();
        Random random = new Random();
        IntStream.range(0, 9)
            .sequential()
            .forEach(i -> {
                Tweet twt = new Tweet(String.format("Tweet %d", i), users.get(random.nextInt(users.size()))
                    .getUsername());
                twt.getLikes()
                    .addAll(users.subList(0, random.nextInt(users.size()))
                        .stream()
                        .map(AppUser::getUsername)
                        .collect(Collectors.toSet()));
                tweets.add(twt);
            });
        return tweets;
    }

    public static Collection<GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        GrantedAuthority grantedAuthority = new GrantedAuthority() {
            public String getAuthority() {
                return "ROLE_USER";
            }
        };
        grantedAuthorities.add(grantedAuthority);
        return grantedAuthorities;
    }
}
