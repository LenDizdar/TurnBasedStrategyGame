/*
Name: Len Dizdar
Date: 1/22/2023
Description: The fighter class! A child of creature and a type of character the user may play.
This class is characterized by high damage critical strikes.
 */
import javax.swing.*;

public class Destruction extends Creature {
    private final int modifier;

    /**
     * The constructor. Creates the Destruction base stat list, stores its modifier.
     * @param name creature name.
     * @param modifier the amount by which a stat is augmented based on user input each round.
     */
    public Destruction(String name, int modifier) {
        super(5,2,2, 3, 30, modifier, name, new ImageIcon("Destruction.png"));
        this.modifier = modifier;
    }

    /**
     * Allows the Destruction creature to critically strike on a random chance, dealing double damage.
     * @param player the attacking creature.
     * @param opponent the target creature.
     * @param scene the screen on which this takes place.
     * @return an adjusted, compounded stat list taking critical strikes into account.
     */
    @Override
    public int[][] fightUnique(int[] player, int[] opponent, Application scene) {
        int critChance = 10 + modifier;
        if (Math.random()*100 <= critChance) {
            player[0] *= 2;
            scene.updateCombatLog(this.getName() + " crit!");
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