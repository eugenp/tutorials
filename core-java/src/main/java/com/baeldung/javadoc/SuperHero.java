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
     * <p>The amount of damage prior to damage mitigation.  Whether or not. . .
     * <a href="http://www.supermanisthegreatest.com">Superman!</a>
     * </p>
     * <p>
     * Lorem ipsum dolor sit amet, consectetur. . .
     * </p>
     * @param incomingDamage the amount of incoming damage
     * @param damageType the value of the type of incoming damage
     * @return the amount of health hero has after attack
     * @see <a href="http://www.link_to_jira/HERO-402">HERO-402</a>
     * @since 1.0
     */
    public int successfullyAttacked(int incomingDamage, String damageType) {
        // 2 defense points will block 1 point of damage
        health = Math.max(0, health - (incomingDamage - defense/2)); 
        if ("fire".equals(damageType)) {
            health = Math.max(0, health - 5);
        }
        return health;
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
