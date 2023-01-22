/*
Name: Len Dizdar
Date: 1/22/2023
Description: The tank class! A child of creature and a type of character the user may play.
This class is characterized by low damage, but high defense, staying in the fight for
thorns damage (1 damage each time they're hit) and healing.
import javax.swing.*;
 */

import javax.swing.*;

public class Resolve extends Creature {
    private final int modifier;

    /**
     * The constructor. Creates the Resolve base stat list, stores its modifier.
     * @param name character name.
     * @param modifier the amount by which a stat is augmented based on user input each round.
     */
    public Resolve(String name, int modifier) {
        super(2,5,2, 1, 35, modifier, name, new ImageIcon("Resolve.png"));
        this.modifier = modifier;
    }

    /**
     * On each attack resolve gets a chance to heal by a small, fixed amount.
     * @param player the attacking creature.
     * @param opponent the target creature.
     * @param scene the screen on which this takes place.
     * @return an adjusted, compounded list of the attacker and target's stats.
     */
    @Override
    public int[][] fightUnique(int[] player, int[] opponent, Application scene) {
        int healChance = 5+modifier;
        if (Math.random()*100 <= healChance) {
            opponent[4] = -1;
            scene.updateCombatLog(this.getName() + " heal!");
        }
        return (new int[][] {player, opponent});
    }

    /**
     * Whenever Resolve is attacked the enemy takes one damage.
     * @param player the attacking creature.
     * @param opponent the target creature.
     * @param scene the screen on which this takes place.
     * @return the attacker's stats.
     */
    protected int[] defendUnique(int[] player, int[] opponent, Application scene) {
        player[4] = -1;
        return player;
    }

}