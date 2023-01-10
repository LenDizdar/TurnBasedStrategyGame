import javax.swing.*;

public class Flurry extends Creature {
    private int dodgeChance = 20;
    private String name;

    public Flurry(String name,int modifier) {
        super(2,2,5, 4,20, modifier, name, new ImageIcon("Flurry.png"));
        this.name = name;
    }

    protected int[][] fightUnique(int[] player, int[] opponent, Application scene) {
        return new int[][] {player, opponent};
    }

    public int[] defendUnique(int[] player, int[] opponent, Application scene) {
        if (Math.random()*100 <= dodgeChance) {
            player[2] --;
            scene.updateCombatLog(this.getName() + " dodged!");
            System.out.println("dodged!!!!!!!!!!!!");
        }
        return player;
    }
}