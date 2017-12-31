package com.baeldung.javadoc;

/**
 * Hero is the main entity we will be using to . . .
 * @author Captain America
 * 
 */
public class SuperHero extends Person {

    /**
     * The public name of a hero that is common knowledge
     */
    private String heroName;
    private String uniquePower;
    private int health;
    private int defense;
	
    /**
     * <p>This is a simple description of the method. . .
     * <a href="http://www.supermanisthegreatest.com">Superman!</a>
     * </p>
     * @param incomingDamage the amount of incoming damage
     * @return the amount of health hero has after attack
     * @see <a href="http://www.link_to_jira/HERO-402">HERO-402</a>
     * @since 1.0
     */
    public int successfullyAttacked(int incomingDamage, String damageType) {
    	// do things
        return 0;
    }

	public String getHeroName() {
		return heroName;
	}

	public void setHeroName(String heroName) {
		this.heroName = heroName;
	}

	public String getUniquePower() {
		return uniquePower;
	}

	public void setUniquePower(String uniquePower) {
		this.uniquePower = uniquePower;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}
	
}
