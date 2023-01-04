public class Bewitched extends Creature {
    private int armorPen = 2;

    public Bewitched(int damage, int defense, int attacks) {
        super(damage,defense,attacks,200);
    }
    
    public int[][] fightUnique(int[] player, int[] opponent) {
        if (opponent[1] - armorPen < 0) {
            opponent[1] = 0;
        } else {
            opponent[1] -= armorPen;
        }
        return (new int[][] {player, opponent});
    }

    protected int[] defendUnique(int[] player, int[] opponent) {
        return player;
    }
}