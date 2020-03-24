package com.baeldung.currying;

import java.time.LocalDate;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Letter {
    private String returningAddress;
    private String insideAddress;
    private LocalDate dateOfLetter;
    private String salutation;
    private String body;
    private String closing;

    Letter(String returningAddress, String insideAddress, LocalDate dateOfLetter, String salutation, String body, String closing) {
        this.returningAddress = returningAddress;
        this.insideAddress = insideAddress;
        this.dateOfLetter = dateOfLetter;
        this.salutation = salutation;
        this.body = body;
        this.closing = closing;
    }

    Letter(String salutation, String body) {
        this(null, null, LocalDate.now(), salutation, body, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Letter letter = (Letter) o;
        return Objects.equals(returningAddress, letter.returningAddress) &&
          Objects.equals(insideAddress, letter.insideAddress) &&
          Objects.equals(dateOfLetter, letter.dateOfLetter) &&
          Objects.equals(salutation, letter.salutation) &&
          Objects.equals(body, letter.body) &&
          Objects.equals(closing, letter.closing);
    }

    @Override
    public int hashCode() {
        return Objects.hash(returningAddress, insideAddress, dateOfLetter, salutation, body, closing);
    }

    @Override
    public String toString() {
        return "Letter{" + "returningAddress='" + returningAddress + '\'' + ", insideAddress='" + insideAddress + '\''
            + ", dateOfLetter=" + dateOfLetter + ", salutation='" + salutation + '\'' + ", body='" + body + '\''
            + ", closing='" + closing + '\'' + '}';
    }

    static Letter createLetter(String salutation, String body) {
        return new Letter(salutation, body);
    }

    static BiFunction<String, String, Letter> SIMPLE_LETTER_CREATOR = //
        (salutation, body) -> new Letter(salutation, body);

    static Function<String, Function<String, Letter>> SIMPLE_CURRIED_LETTER_CREATOR = //
        saluation -> body -> new Letter(saluation, body);

    static Function<String, Function<String, Function<LocalDate, Function<String, Function<String, Function<String, Letter>>>>>> LETTER_CREATOR = //
      returnAddress
          -> closing
          -> dateOfLetter
          -> insideAddress
          -> salutation
          -> body
          -> new Letter(returnAddress, insideAddress, dateOfLetter, salutation, body, closing);

    static AddReturnAddress builder() {
        return returnAddress
            -> closing
            -> dateOfLetter
            -> insideAddress
            -> salutation
            -> body
            -> new Letter(returnAddress, insideAddress, dateOfLetter, salutation, body, closing);
    }

    interface AddReturnAddress {
        Letter.AddClosing withReturnAddress(String returnAddress);
    }

    interface AddClosing {
        Letter.AddDateOfLetter withClosing(String closing);
    }

    interface AddDateOfLetter {
        Letter.AddInsideAddress withDateOfLetter(LocalDate dateOfLetter);
    }

    interface AddInsideAddress {
        Letter.AddSalutation withInsideAddress(String insideAddress);
    }

    interface AddSalutation {
        Letter.AddBody withSalutation(String salutation);
    }

    interface AddBody {
        Letter withBody(String body);
    }
}