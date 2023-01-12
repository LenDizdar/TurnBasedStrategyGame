import javax.swing.*;

public class Resolve extends Creature {
    private int healing = 1;
    private int healChance = 10;

    public Resolve(String name, int modifier) {
        super(2,4,2, 1, 40, modifier, name, new ImageIcon("Resolve.png"));
    }
    
    public int[][] fightUnique(int[] player, int[] opponent, Application scene) {
        if (Math.random()*100 <= healChance) {
            opponent[4] = -1;
            scene.updateCombatLog(this.getName() + " heal!");
        }
        return (new int[][] {player, opponent});
    }

    protected int[] defendUnique(int[] player, int[] opponent, Application scene) {
        player[4] = -1;
        return player;
    }

}