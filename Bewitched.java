/*
Name: Len Dizdar
Date: 1/22/2023
Description: The mage class! A child of creature and a type of character the user may play.
This class is characterized by magical armor penetration and stuns.
 */

import javax.swing.*;

public class Bewitched extends Creature {
    private final int modifier;
    /**
     * The constructor. Creates the Bewitched base stat list, stores its modifier.
     * @param name character name.
     * @param modifier the amount by which a stat is augmented based on user input each round.
     */
    public Bewitched(String name, int modifier) {
        super(3,2,3, 3,25, modifier, name, new ImageIcon("Bewitched.png"));
        this.modifier = modifier;
    }

    /**
     * Breaks the target's armor and has a chance of stunning them, rendering them unable to act on their next turn.
     * @param player the attacking creature.
     * @param opponent the target creature.
     * @param scene the screen on which this takes place.
     * @return an adjusted, compounded stat list taking armor breakage and stuns into account.
     */
    @Override
    public int[][] fightUnique(int[] player, int[] opponent, Application scene) {
        int armorPen = modifier + 5;
        if (opponent[1] - armorPen < 0) {
            opponent[1] = 0;
        } else {
            opponent[1] -= armorPen;
        }
        int stunChance = 4;
        if (Math.random()*100 <= stunChance) {
            opponent[4] = 0;
            scene.updateCombatLog(this.getName() + " stun!");
        }
        return (new int[][] {player, opponent});
    }

    /**
     * This is more for resolve and flurry. Does nothing here.
     * @param player the attacking creature.
     * @param opponent the target creature.
     * @param scene the screen on which this takes place.
     * @return the attacker's stats.
     */
    @Override
    protected int[] defendUnique(int[] player, int[] opponent, Application scene) {
        return player;
    }
}