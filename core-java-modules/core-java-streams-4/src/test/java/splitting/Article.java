package splitting;

class Article {
    private final String target;
    private final boolean featured;

    public Article(String target, boolean featured) {
        this.target = target;
        this.featured = featured;
    }

    public String getTarget() {
        return target;
    }

    public boolean isFeatured() {
        return featured;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;

        if (featured != article.featured) return false;
        return target.equals(article.target);
    }

    @Override
    public int hashCode() {
        int result = target.hashCode();
        result = 31 * result + (featured ? 1 : 0);
        return result;
    }
}
