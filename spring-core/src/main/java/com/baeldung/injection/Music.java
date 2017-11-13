package com.baeldung.injection;

public class Music {

    private String title;

    private String singer;

    public Music(String title, String singer) {
        super();
        this.title = title;
        this.singer = singer;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Music [");
        if (title != null) {
            builder.append("title=");
            builder.append(title);
            builder.append(", ");
        }
        if (singer != null) {
            builder.append("singer=");
            builder.append(singer);
        }
        builder.append("]");
        return builder.toString();
    }

}
