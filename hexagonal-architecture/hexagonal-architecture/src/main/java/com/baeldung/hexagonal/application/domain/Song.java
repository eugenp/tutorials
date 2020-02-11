package com.baeldung.hexagonal.application.domain;

public class Song {
	
	

	private Integer songId;
    private String name;
    private String description;

    public Song() {
        super();
    }

    public Song(Integer songId, String name, String description) {
        this.songId = songId;
        this.name = name;
        this.description = description;
    }

    public Integer getSongId() {
        return songId;
    }

    public void setSongtId(Integer songId) {
        this.songId = songId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
	public String toString() {
		return "Song [songId=" + songId + ", name=" + name + ", description=" + description + "]";
	}

}
