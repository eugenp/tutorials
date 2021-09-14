class Book {
    private String id;
    private String title;

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title= title;
    }

    public String toString(){
        return "Book ID: " + id + " Title: " + title;
    }
}