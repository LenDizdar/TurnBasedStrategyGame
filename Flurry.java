/*
Name: Len Dizdar
Date: 1/22/2023
Description: The ninja class! A child of creature and a type of character the user may play.
This class is characterized by many punches and swift movement, dodging attacks.
 */
import javax.swing.*;

public class Flurry extends Creature {

    public Flurry(String name,int modifier) {
        super(2,2,5, 4,20, modifier, name, new ImageIcon("Flurry.png"));
    }

    /**
     * This is the unique fighting capability method - Flurry just has high attacks.
     * @param player the attacking creature.
     * @param opponent the target creature.
     * @param scene the screen on which this takes place.
     * @return a non-adjusted, compounded list of both creatures' stats.
     */
    @Override
    protected int[][] fightUnique(int[] player, int[] opponent, Application scene) {
        return new int[][] {player, opponent};
    }

    /**
     * This method allows Flurry creatures to dodge attacks, fairly often as well.
     * @param player the attacking creature.
     * @param opponent the target creature.
     * @param scene the screen on which this takes place.
     * @return the attacker's adjusted stats, taking dodges into account.
     */
    @Override
    public int[] defendUnique(int[] player, int[] opponent, Application scene) {
        int dodgeChance = 20;
        if (Math.random()*100 <= dodgeChance) {
            player[2] --;
            scene.updateCombatLog(this.getName() + " dodged!");
        }
        return player;
    }
}