public class Resolve extends Creature {
    private int healing = 2;
    private int healChance = 10;

    public Resolve(String name) {
        super(2,5,2,40, name);
    }
    
    public int[][] fightUnique(int[] player, int[] opponent) {
        if (Math.random()*100 <= healChance) {
            player[3] += healing;
        }
        return (new int[][] {player, opponent});
    }

    protected int[] defendUnique(int[] player, int[] opponent) {
        return player;
    }

}