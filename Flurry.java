public class Flurry extends Creature {
    private int dodgeChance = 30;

    public Flurry(String name) {
        super(2,2,5,25, name);
    }

    protected int[][] fightUnique(int[] player, int[] opponent) {
        return new int[][] {player, opponent};
    }

    public int[] defendUnique(int[] player, int[] opponent) {
        int playerAttacks = player[2];
        for(int i = 0; i < playerAttacks; i++) {

            if (Math.random()*100 <= dodgeChance) {
                player[2] --;
                System.out.println("dodged!!!!!!!!!!!!");
            }
        }
        return player;
    }
}