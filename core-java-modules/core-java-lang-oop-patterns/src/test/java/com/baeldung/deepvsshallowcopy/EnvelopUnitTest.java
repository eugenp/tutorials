package com.baeldung.deepvsshallowcopy;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EnvelopUnitTest {

    private static void assertShallowCopy(Envelope envelope, Envelope copiedEnvelope) {
        assertThat(envelope).isNotSameAs(copiedEnvelope);
        assertThat(envelope).usingRecursiveComparison().isEqualTo(copiedEnvelope);
        assertThat(envelope.getLetter()).isSameAs(copiedEnvelope.getLetter());
    }

    @Test
    void givenExistingObject_whenCopyingUsingParameterizedConstructor_thenSuccessfullyCreatedShallowCopy() {
        String content = "Dear colleagues, I hope this letter finds you well.";
        Letter letter = new Letter(content);
        Envelope envelope = new Envelope("Baeldung", Envelope.Size.C4, letter, true);

        Envelope copiedEnvelope = new Envelope(envelope.getRecipient(), envelope.getSize(), envelope.getLetter(), envelope.isSealed());

        assertShallowCopy(envelope, copiedEnvelope);
        envelope.getLetter().setContent("No content.");
        assertThat(envelope.getLetter()).isSameAs(copiedEnvelope.getLetter());
    }

    @Test
    void givenExistingObject_whenCopyingUsingCloneMethod_thenSuccessfullyCreatedShallowCopy() {
        String content = "Dear colleagues, I hope this letter finds you well.";
        Letter letter = new Letter(content);

        Envelope envelope = new Envelope("Baeldung", Envelope.Size.C4, letter, true);

        Envelope copiedEnvelope = envelope.clone();

        assertShallowCopy(envelope, copiedEnvelope);

        envelope.getLetter().setContent("No content.");

        assertThat(envelope.getLetter()).isSameAs(copiedEnvelope.getLetter());
    }

    private static void assertDeepCopy(Envelope envelope, Envelope copiedEnvelope) {
        assertThat(envelope).isNotSameAs(copiedEnvelope);
        assertThat(envelope).usingRecursiveComparison().isEqualTo(copiedEnvelope);
        assertThat(envelope.getLetter()).isNotSameAs(copiedEnvelope.getLetter());
    }

    @Test
    void givenExistingObject_whenCopyingUsingCopyConstructor_thenSuccessfullyCreatedDeepCopy() {
        String content = "Dear colleagues, I hope this letter finds you well.";
        Letter letter = new Letter(content);

        Envelope envelope = new Envelope("Baeldung", Envelope.Size.C4, letter, true);

        Envelope copiedEnvelope = new Envelope(envelope);

        assertDeepCopy(envelope, copiedEnvelope);

        envelope.getLetter().setContent("No content.");

        assertThat(envelope.getLetter()).isNotSameAs(copiedEnvelope.getLetter());
    }

    @Test
    void givenExistingObject_whenCopyingUsingCopyFactoryMethod_thenSuccessfullyCreatedDeepCopy() {
        String content = "Dear colleagues, I hope this letter finds you well.";
        Letter letter = new Letter(content);
        Envelope envelope = new Envelope("Baeldung", Envelope.Size.C4, letter, true);

        Envelope copiedEnvelope = Envelope.copyOf(envelope);

        assertDeepCopy(envelope, copiedEnvelope);

        letter.setContent("No content.");

        assertThat(envelope.getLetter()).isNotSameAs(copiedEnvelope.getLetter());
    }

    @Test
    void givenExistingObject_whenCopyingUsingGson_thenSuccessfullyCreatedDeepCopy() {
        String content = "Dear colleagues, I hope this letter finds you well.";
        Letter letter = new Letter(content);
        Envelope envelope = new Envelope("Baeldung", Envelope.Size.C4, letter, true);

        Gson gson = new Gson();
        Envelope copiedEnvelope = gson.fromJson(gson.toJson(envelope), Envelope.class);

        assertDeepCopy(envelope, copiedEnvelope);
        envelope.getLetter().setContent("No content.");
        assertThat(envelope.getLetter()).isNotSameAs(copiedEnvelope.getLetter());
    }

}