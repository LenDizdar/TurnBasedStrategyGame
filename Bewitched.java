import javax.swing.*;

public class Bewitched extends Creature {
    private int armorPen = 1;
    private int stunChance = 4;
    private String name;

    public Bewitched(String name, int modifier) {
        super(3,2,3, 3,25, modifier, name, new ImageIcon("Bewitched.png"));
        this.name = name;
    }
    
    public int[][] fightUnique(int[] player, int[] opponent, Application scene) {
        if (opponent[1] - armorPen < 0) {
            opponent[1] = 0;
        } else {
            opponent[1] -= armorPen;
        }
        if (Math.random()*100 <= stunChance) {
            opponent[4] = 0;
            scene.updateCombatLog(this.getName() + " stun!");
        }
        return (new int[][] {player, opponent});
    }

    protected int[] defendUnique(int[] player, int[] opponent, Application scene) {
        return player;
    }
}