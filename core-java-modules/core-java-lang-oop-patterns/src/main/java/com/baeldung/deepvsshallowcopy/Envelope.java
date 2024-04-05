package com.baeldung.deepvsshallowcopy;


public class Envelope implements Cloneable {
    private String recipient;
    private Size size;
    private Letter letter;
    private boolean sealed;

    public enum Size {
        DL,
        C4,
        C5,
        C6
    }

    public Envelope(final String recipient, final Size size, final Letter letter, boolean sealed) {
        this.recipient = recipient;
        this.size = size;
        this.letter = letter;
        this.sealed = sealed;
    }

    public Envelope(final Envelope envelope) {
        this.recipient = envelope.recipient;
        this.size = envelope.size;
        this.letter = new Letter(envelope.letter.getContent());
        this.sealed = envelope.sealed;
    }

    public static Envelope copyOf(Envelope envelope) {
        return new Envelope(envelope.getRecipient(), envelope.getSize(), new Letter(envelope.getLetter().getContent()), envelope.isSealed());
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Letter getLetter() {
        return letter;
    }

    public void setLetter(Letter letter) {
        this.letter = letter;
    }

    public boolean isSealed() {
        return sealed;
    }

    public void setSealed(boolean sealed) {
        this.sealed = sealed;
    }

    @Override
    public Envelope clone() {
        try {
            return (Envelope) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Envelope{");
        sb.append("recipient='").append(recipient).append('\'');
        sb.append(", size=").append(size);
        sb.append(", letter=").append(letter);
        sb.append(", sealed=").append(sealed);
        sb.append('}');
        return sb.toString();
    }
}
