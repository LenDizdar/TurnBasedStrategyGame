/*
Name: Len Dizdar
Date: 1/22/2023
Description: This is the superclass of the creatures the user can fight as or with in this game.
Each creature has its own stats, its icon, and states. This class contains the logic to do with
combat in comparing the player's stats to the enemy's stats and allocating damage.
 */

import javax.swing.*;

abstract class Creature {

    protected int[] stats;
    private String name;
    private final int modifier;
    private int intention;
    private final int maxHealth;
    private boolean isStunned;
    public ImageIcon sprite;

    /**
     * Stores all the given information for the creature.
     * Notably, damage, defense, attacks, speed, and health are stored together in an array.
     * @param damage the amount of damage each of the creature's attacks deal.
     * @param defense the integer amount subtracted from each attack taken.
     * @param attacks the amount of attacks the creature makes on a turn.
     * @param speed compared to other creatures, decides who strikes first.
     * @param health the amount of points of damage a creature can take before death.
     * @param modifier the integer amount by which a creature increases its stats on its turn.
     * @param name the string used to identify a creature. And to love it as your own.
     * @param sprite the image associated with the creature.
     */
    public Creature(int damage, int defense, int attacks, int speed, int health, int modifier, String name, ImageIcon sprite) {
        this.stats = new int[] {damage, defense, attacks, health, speed};
        this.name = name;
        this.maxHealth = health;
        this.modifier = modifier;
        this.sprite = sprite;
    }

    /**
     * This takes an attacker and a target, compares their stats, considers special cases
     * and reduces the target's health by a calculated amount.
     * @param enemy the target of the attack.
     * @param userBoost the attacker's choice of augmentation this turn.
     * @param enemyBoost the target's choice of augmentation this turn.
     * @param scene the screen on which this fight takes place. Used to identify health bars.
     * @param battlefieldChanges the array of stat changes that the environment provides.
     */
    public void fight(Creature enemy, int userBoost, int enemyBoost, Application scene, int[] battlefieldChanges) {

        if (this.getHealth() > 0 && enemy.getHealth() > 0) {

            //create temporary sets of stats for only this turn
            int[] playerStats = cloneStats(this, userBoost, battlefieldChanges);
            int[] enemyStats = cloneStats(enemy, enemyBoost, battlefieldChanges);
            if (this.isStunned) {
                playerStats[2] = -50;
                this.stunned(false);
            }
            //resolve (damage-defense)*attacks
            enemy.stats[3] -= resolveFight(this, enemy, playerStats, enemyStats, scene);
            if (enemy.stats[3] < 0) {
                enemy.setHealth(0);
            }
        }
    }

    /**
     * Creates a modifiable clone of a creature's stat list. This is more usable
     * for a single round and does not affect the Creature instance's attributes.
     * @param animal the creature to make a duplicate stat list of.
     * @param boost the choice of augmentation this round.
     * @param battlefieldChanges the array of stat changes the environment provides.
     * @return a clone of the original creature's stats considering changes due to user interaction and environment.
     */
    private int[] cloneStats(Creature animal, int boost, int[] battlefieldChanges) {
        int[] newStats = animal.stats.clone();
        for (int i = 0; i < battlefieldChanges.length; i++) {
            newStats[i] += battlefieldChanges[i];
        }
        newStats[boost] += animal.modifier;
        return newStats;
    }

    /**
     * Calculates, based on two int[]s, how much damage the target should take from the attacker this round.
     * @param creature the attacker.
     * @param enemyCreature the target.
     * @param player the modified temporary stats of the attacker.
     * @param enemy the modified temporary stats of the target
     * @param scene the screen on which this takes place.
     * @return the sum amount of damage dealt by the attacker to the target over the round.
     */
    private int resolveFight(Creature creature, Creature enemyCreature, int[] player, int[] enemy, Application scene) {
        int damageSum = 0;
        for (int i = 0; i < player[2]; i++) {
            damageSum += resolveAttack(creature.fightUnique(enemyCreature.defendUnique(player.clone(), enemy.clone(), scene),enemy.clone(), scene), creature, enemyCreature);
            // lowers the target's defense each attack, makes flurry viable.
            if (enemy[1] > 0) {
                enemy[1] -= 1;
            }
        }
        return damageSum;
    }

    /**
     * Calculates the amount of damage dealt by one attack, after defense, from the attacker to the target.
     * @param toCompare the lists of stats to compare, the first one is the attacker, the second is the target.
     * @param attacker the Creature object of the attacker, used to modify its health (healing or resolve thorns).
     * @param enemy the Creature object of the target, used to take care of stuns.
     * @return the non-negative amount of damage dealt by this attack.
     */
    private int resolveAttack(int[][] toCompare, Creature attacker, Creature enemy) {
        int damage = toCompare[0][0]-toCompare[1][1];
        if (toCompare[1][4] == 0) {
            enemy.stunned(true);
        }
        //just for resolve class's healing
        if (toCompare[1][4] == -1) {
            if (attacker.getHealth() + 2 <= attacker.maxHealth) attacker.setHealth(attacker.getHealth()+2);
        }
        if  (enemy.getHealth() > 0 && toCompare[0][4] == -1) {
            attacker.setHealth(attacker.getHealth()-1);
        }
        return Math.max(damage, 0);
    }

    /**
     * Most subclasses or "types of creature" have unique attacking abilities, this considers them in the fight method.
     * @param player the attacking creature.
     * @param opponent the target creature.
     * @param scene the screen on which this takes place.
     * @return an adjusted, compounded stat list based on the creature's unique abilities.
     */
    protected abstract int[][] fightUnique(int[] player, int[] opponent, Application scene);

    /**
     * Most subclasses or "types of creature" have unique defending abilities, this considers them in the fight method.
     * @param player the attacking creature.
     * @param opponent the target creature.
     * @param scene the screen on which this takes place.
     * @return an adjusted, compounded stat list based on the creature's unique abilities.
     */
    protected abstract int[] defendUnique(int[] player, int[] opponent, Application scene);

    /**
     * Raises the chosen stat of the creature. Used between fights for permanent power boosts.
     * @param stat the attribute to be raised. (0 = damage, 1 = defense, 2 = attacks).
     */
    public void buff(int stat) {
        this.stats[stat] += 2;
    }

    /**
     * Used to raise the creature's health back to maximum, visually and numerically. Used between fights.
     */
    public void fullHeal() {
        if (this.maxHealth > this.getHealth()) {
            this.stats[3] = maxHealth;
            Main.scene.updateHealth(true, true, this);
        }
    }

    /**
     * a setter
     * @param isStunned whether to stun the creature or not.
     */
    private void stunned(boolean isStunned) {
        this.isStunned = isStunned;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public ImageIcon getSprite() {
        return sprite;
    }

    public int getHealth() {
        return stats[3];
    }

    public int getMaxHealth() {
        return maxHealth;
    }
    public int[] getStats() {return stats;}

    public void setHealth(int health) {
        this.stats[3] = health;
    }

    public int getIntention() {
        return intention;
    }

    /**
     * Basically a setter, but setting the intention is always random, so no reason to take an input.
     */
    public void setRandomIntention() {
        intention = (int) (Math.random()*3);
    }

}