//<start id="stupidKnightMinstrel"/> 
package com.springinaction.knights;

public class BraveKnight implements Knight {
  private Quest quest;
  private Minstrel minstrel;
  
  public BraveKnight(Quest quest, Minstrel minstrel) {
    this.quest = quest;
    this.minstrel = minstrel;
  }
  
  public void embarkOnQuest() throws QuestException {
    minstrel.singBeforeQuest();  //<co id="co_minstrel_too_involved" />
    quest.embark();
    minstrel.singAfterQuest();
  }
}
//<end id="stupidKnightMinstrel"/> 
