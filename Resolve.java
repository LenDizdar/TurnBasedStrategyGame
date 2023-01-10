import javax.swing.*;

public class Resolve extends Creature {
    private int healing = 2;
    private int healChance = 10;
    private String name;

    public Resolve(String name, int modifier) {
        super(2,5,2, 1, 40, modifier, name, new ImageIcon("Resolve.png"));
        this.name = name;
    }
    
    public int[][] fightUnique(int[] player, int[] opponent, Application scene) {
        if (Math.random()*100 <= healChance) {
            player[3] += healing;
            scene.updateCombatLog(this.getName() + " heal!");
        }
        return (new int[][] {player, opponent});
    }

    protected int[] defendUnique(int[] player, int[] opponent, Application scene) {
        return player;
    }

}