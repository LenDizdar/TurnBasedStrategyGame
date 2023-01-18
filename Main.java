import javax.swing.*;

public class Main {
    static JFrame frame;
    static Creature playerCreature = new Flurry("chad", 1);
    static Application scene = new Application();
    static Creature[] enemies = generateEnemies();
    static Creature opponent;
    static int nextEncounter = 0;
    static ImageIcon[] icons;

    public static void setPlayerClass(Creature creature) {
        playerCreature = creature;
    }

    private static Creature[] generateEnemies() {
        String[] names = {"Rylan", "Len", "Mrs. Uniat", "Tuong"};
        Creature[] toReturn = new Creature[4];
        icons = new ImageIcon[4];
        for (int i = 0; i < 4; i++) {
            int randomNumber = (int) (Math.random()*4); System.out.println(randomNumber);
            switch (randomNumber) {
                case 0:
                    toReturn[i] = new Destruction(names[i],i*2);
                    icons[i] = new ImageIcon("Destruction.png");
                    break;
                case 1:
                    toReturn[i] = new Flurry(names[i],i*2);
                    icons[i] = new ImageIcon("Flurry.png");
                    break;
                case 2:
                    toReturn[i] = new Bewitched(names[i],i*2);
                    icons[i] = new ImageIcon("Bewitched.png");
                    break;
                case 3:
                    toReturn[i] = new Resolve(names[i],i*2);
                    icons[i] = new ImageIcon("Resolve.png");
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
        scene.getStartMenu().fillEnemyList(icons);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void nextEncounter() {
        setUpEncounter(playerCreature, enemies[nextEncounter]);
        nextEncounter++;
    }

    private static void setUpEncounter(Creature player, Creature enemy) {
        opponent = enemy;
        scene.setCreaturePictures(player.getSprite(), enemy.getSprite());
        scene.updateHealth(true, true, player);
        scene.updateHealth(true, false, enemy);
        enemy.setRandomIntention();
        scene.displayIntention(enemy.getIntention(), enemy.getHealth(), enemy.getName());

    }

    public static void fightRound(Creature a, Creature b, int choiceA, int choiceB) {
        //try to find a better way to do this???
        if (b.stats[4] > a.stats[4]) {
            b.fight(a, choiceB, choiceA, scene, false);
            a.fight(b, choiceA, choiceB, scene, true);
        } else {
            a.fight(b, choiceA, choiceB, scene, true);
            b.fight(a, choiceB, choiceA, scene, false);
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