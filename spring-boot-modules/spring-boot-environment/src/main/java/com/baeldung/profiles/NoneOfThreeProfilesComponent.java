package com.baeldung.profiles;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!a & !b & !c")
public class NoneOfThreeProfilesComponent {

}