package com.baeldung.serenity.membership;

/**
 * @author aiet
 */
public enum Commodity {

    MacBookPro(1499), GoProHero5(400);

    public final int price;

    Commodity(int price) {
        this.price = price;
    }

}
