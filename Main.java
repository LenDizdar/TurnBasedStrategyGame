import javax.swing.*;


public class Main {
    static JFrame frame;
    static Creature playerCreature = new Flurry("chad", 1);
    static Creature[] enemies = generateEnemies();
    static Creature opponent;
    static Application scene = new Application();
    static int nextEncounter = 0;

    public static void setPlayerClass(Creature creature) {
        //playerCreature = creature;
    }

    private static Creature[] generateEnemies() {
        String[] names = {"Rylan", "Len", "Tuong"};
        Creature[] toReturn = new Creature[3];
        for (int i = 0; i < 3; i++) {
            int randomNumber = (int) (Math.random()*4);
            switch (randomNumber) {
                case 0:
                    toReturn[i] = new Destruction(names[i],i*2);
                    break;
                case 1:
                    toReturn[i] = new Flurry(names[i],i*2);
                case 2:
                    toReturn[i] = new Bewitched(names[i],i*2);
                case 3:
                    toReturn[i] = new Resolve(names[i],i*2);
                break;
            }
        }
        return toReturn;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        frame = new JFrame("Application");
        scene.initialize(frame, scene.card1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        nextEncounter();
    }

    public static void nextEncounter() {
        setUpEncounter(playerCreature, enemies[nextEncounter]);
        nextEncounter++;
    }

    private static void setUpEncounter(Creature player, Creature enemy) {
        opponent = enemy;
        scene.updateHealth(true, true, player);
        scene.updateHealth(true, false, enemy);
        enemy.setRandomIntention();
        scene.displayIntention(enemy.getIntention(), enemy.getHealth(), enemy.getName());
    }

    public static void fightRound(Creature a, Creature b, int choiceA, int choiceB) {
        //try to find a better way to do this???
        if (a.stats[4] > b.stats[4]) {
            a.fight(b, choiceA, choiceB, scene);
            b.fight(a, choiceB, choiceA, scene);
        } else {
            b.fight(a, choiceB, choiceA, scene);
            a.fight(b, choiceA, choiceB, scene);
        }

        if (a.getHealth() == 0) {
            scene.lossSetVisible(true);
        }

        scene.updateHealth(false, true, a);
        scene.updateHealth(false, false, b);
        b.setRandomIntention();
        scene.displayIntention(b.getIntention(), b.getHealth(), b.getName());
    }

    public static void confirmInput(int userInput) {
        fightRound(playerCreature, opponent, userInput, opponent.getIntention());
    }

    public static Creature getPlayerCreature() {
        return playerCreature;
    }

}
