public class Bewitched extends Creature {
    private int armorPen = 2;

    public Bewitched(String name) {
        super(3,3,3,20, name);
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