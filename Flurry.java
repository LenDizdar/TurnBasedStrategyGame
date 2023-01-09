public class Flurry extends Creature {
    private int dodgeChance = 30;
    private String name;

    public Flurry(String name,int modifier) {
        super(2,2,6, 4,25, modifier, name);
        this.name = name;
    }

    protected int[][] fightUnique(int[] player, int[] opponent, Application scene) {
        return new int[][] {player, opponent};
    }

    public int[] defendUnique(int[] player, int[] opponent, Application scene) {
        int playerAttacks = player[2];
        for(int i = 0; i < playerAttacks; i++) {

            if (Math.random()*100 <= dodgeChance) {
                player[2] --;
                scene.updateCombatLog(this.getName() + " dodged!");
                System.out.println("dodged!!!!!!!!!!!!");
            }
        }
        return player;
    }
}