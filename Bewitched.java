public class Bewitched extends Creature {
    private int armorPen = 1;
    private int wildMagicChance = 5;
    private String name;

    public Bewitched(String name, int modifier) {
        super(3,3,3, 3,20, modifier, name);
        this.name = name;
    }
    
    public int[][] fightUnique(int[] player, int[] opponent, Application scene) {
        if (opponent[1] - armorPen < 0) {
            opponent[1] = 0;
        } else {
            opponent[1] -= armorPen;
        }
        if (Math.random()*100 <= wildMagicChance) {
            int randomNumber = (int) (Math.random() * 3);
            player[randomNumber] *= 2;
            String output;
            switch (randomNumber) {
                case 0:
                    output = " damage";
                    break;
                case 1:
                    output = " defense";
                    break;
                case 2:
                    output = " attacks";
                    break;
                default:
                    output = " bug";
            }
            scene.updateCombatLog(this.getName() + output + " up!");
        }
        return (new int[][] {player, opponent});
    }

    protected int[] defendUnique(int[] player, int[] opponent, Application scene) {
        return player;
    }
}