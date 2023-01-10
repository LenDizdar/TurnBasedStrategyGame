import javax.swing.*;

public class Destruction extends Creature {
    private int critChance = 10;
    private String name;

    public Destruction(String name, int modifier) {
        super(5,2,2, 2, 30, modifier, name, new ImageIcon("Destruction.png"));
        this.name = name;
    }

    public int[][] fightUnique(int[] player, int[] opponent, Application scene) {
        if (Math.random()*100 <= critChance) {
            player[0] *= 2;
            scene.updateCombatLog(this.getName() + " crit!");
            System.out.println("crit!");
        }
        return (new int[][] {player, opponent});
    }

    protected int[] defendUnique(int[] player, int[] opponent, Application scene) {
        return player;
    }

}