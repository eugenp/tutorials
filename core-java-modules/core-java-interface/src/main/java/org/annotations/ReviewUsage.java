package org.annotations;

public class ReviewUsage {

    @Review(reviewer = "Natasha", date = "2024-08-24")
    public String service() {
        return "Some logic here";
    }

}
