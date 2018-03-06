package com.springinaction.knights;

public class BraveKnight implements Knight {
  private Quest quest;
  
  public BraveKnight(Quest quest) {
    this.quest = quest;       //<co id="co_injectedQuest"/>
  }
  
  public void embarkOnQuest() throws QuestException {
    quest.embark();
  }
}
