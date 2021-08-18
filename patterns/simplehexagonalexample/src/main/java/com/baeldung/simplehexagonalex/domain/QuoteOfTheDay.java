package com.baeldung.simplehexagonalex.domain;

import java.util.Objects;

public class QuoteOfTheDay {

    private String userName;
    private String quote;
    private String provider;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    public int hashCode() {
        return Objects.hash(provider, quote, userName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        QuoteOfTheDay other = (QuoteOfTheDay) obj;
        return Objects.equals(provider, other.provider) && Objects.equals(quote, other.quote) && Objects.equals(userName, other.userName);
    }

    @Override
    public String toString() {
        return "QuoteOfTheDay [userName=" + userName + ", quote=" + quote + ", provider=" + provider + "]";
    }
}
