package com.baeldung.hexagonalarchitecture.domain.helper;

import com.baeldung.hexagonalarchitecture.domain.Greeting;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

public class RandomGreetingPicker {

        private Random random;

        public RandomGreetingPicker() {
                this.random = new Random();
        }

        /**
         * Picks a random greeting from the  list of greetings.
         *
         * @param greetingList greetings to pick from
         * @return a greeting from the list, or an empty optional if the list was empty
         */
        public Optional<Greeting> pickGreeting(List<Greeting> greetingList) {
                Objects.requireNonNull(greetingList);
                if (greetingList.isEmpty()) {
                        return Optional.empty();
                }
                int randomIndex = random.nextInt(greetingList.size());
                Greeting randomGreeting = greetingList.get(randomIndex);
                return Optional.of(randomGreeting);
        }

}
