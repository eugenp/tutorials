package com.bealdung.dto;

public class GameDTO {

  private Long id;
  private String name;
  private Long creationTime;

  private String creatorId;
  private String creator;

  private int totalPlayers;

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getCreationTime() {
    return this.creationTime;
  }

  public void setCreationTime(Long creationTime) {
    this.creationTime = creationTime;
  }

  public String getCreatorId() {
    return this.creatorId;
  }

  public void setCreatorId(String creatorId) {
    this.creatorId = creatorId;
  }

  public String getCreator() {
    return this.creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  public int getTotalPlayers() {
    return this.totalPlayers;
  }

  public void setTotalPlayers(int totalPlayers) {
    this.totalPlayers = totalPlayers;
  }
}
