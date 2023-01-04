public class Destruction extends Creature {
    private int critChance = 10;

    public Destruction(int damage, int defense, int attacks) {
        super(damage,defense,attacks,300);
    }

    public int[][] fightUnique(int[] player, int[] opponent) {
        if (Math.random()*100 <= critChance) {
            player[0] *= 2;
            System.out.println("crit!");
        }
        return (new int[][] {player, opponent});
    }

    protected int[] defendUnique(int[] player, int[] opponent) {
        return player;
    }

}