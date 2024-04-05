package com.baeldung.deepvsshallowcopy;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EnvelopUnitTest {

    @Test
    void givenExistingObject_whenCopyingUsingParameterizedConstructor_thenSuccessfullyCreatedShallowCopy() {
        final String content = "Dear colleagues, I hope this letter finds you well.";
        final Letter letter = new Letter(content);

        final Envelope envelope = new Envelope("Baeldung", Envelope.Size.C4, letter, true);

        final Envelope copiedEnvelope = new Envelope(envelope.getRecipient(), envelope.getSize(), envelope.getLetter(), envelope.isSealed());

        assertThat(envelope)
                .isNotSameAs(copiedEnvelope);
        assertThat(envelope.getLetter())
                .isSameAs(copiedEnvelope.getLetter());

        letter.setContent("No content");

        assertThat(envelope.getLetter())
                .isSameAs(copiedEnvelope.getLetter());
    }

    @Test
    void givenExistingObject_whenCopyingUsingCloneMethod_thenSuccessfullyCreatedShallowCopy() {
        final String content = "Dear colleagues, I hope this letter finds you well.";
        final Letter letter = new Letter(content);

        final Envelope envelope = new Envelope("Baeldung", Envelope.Size.C4, letter, true);

        final Envelope copiedEnvelope = envelope.clone();

        assertThat(envelope)
                .isNotSameAs(copiedEnvelope);
        assertThat(envelope.getLetter())
                .isSameAs(copiedEnvelope.getLetter());

        letter.setContent("No content");

        assertThat(envelope.getLetter())
                .isSameAs(copiedEnvelope.getLetter());
    }

    @Test
    void givenExistingObject_whenCopyingUsingCopyConstructor_thenSuccessfullyCreatedDeepCopy() {
        final String content = "Dear colleagues, I hope this letter finds you well.";
        final Letter letter = new Letter(content);

        final Envelope envelope = new Envelope("Baeldung", Envelope.Size.C4, letter, true);

        final Envelope copiedEnvelope = new Envelope(envelope);

        assertThat(envelope)
                .isNotSameAs(copiedEnvelope);
        assertThat(envelope.getLetter())
                .isNotSameAs(copiedEnvelope.getLetter());

        letter.setContent("No content");

        assertThat(envelope.getLetter())
                .isNotSameAs(copiedEnvelope.getLetter());
    }

    @Test
    void givenExistingObject_whenCopyingUsingCopyFactoryMethod_thenSuccessfullyCreatedDeepCopy() {
        final String content = "Dear colleagues, I hope this letter finds you well.";
        final Letter letter = new Letter(content);

        final Envelope envelope = new Envelope("Baeldung", Envelope.Size.C4, letter, true);

        final Envelope copiedEnvelope = Envelope.copyOf(envelope);

        assertThat(envelope)
                .isNotSameAs(copiedEnvelope);
        assertThat(envelope.getLetter())
                .isNotSameAs(copiedEnvelope.getLetter());

        letter.setContent("No content");

        assertThat(envelope.getLetter())
                .isNotSameAs(copiedEnvelope.getLetter());
    }

    @Test
    void givenExistingObject_whenCopyingUsingGson_thenSuccessfullyCreatedDeepCopy() {
        final String content = "Dear colleagues, I hope this letter finds you well.";
        final Letter letter = new Letter(content);

        final Envelope envelope = new Envelope("Baeldung", Envelope.Size.C4, letter, true);

        Gson gson = new Gson();

        final Envelope copiedEnvelope = gson.fromJson(gson.toJson(envelope), Envelope.class);

        assertThat(envelope)
                .isNotSameAs(copiedEnvelope);
        assertThat(envelope.getLetter())
                .isNotSameAs(copiedEnvelope.getLetter());

        letter.setContent("No content");

        assertThat(envelope.getLetter())
                .isNotSameAs(copiedEnvelope.getLetter());
    }
}
