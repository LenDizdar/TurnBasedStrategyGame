public class Flurry extends Creature {
    private int dodgeChance = 10;

    public Flurry(int damage, int defense, int attacks) {
        super(damage,defense,attacks,200);
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