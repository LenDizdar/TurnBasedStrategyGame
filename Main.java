import javax.swing.*;

public class Main {
    static JFrame frame;
    static Creature playerCreature = new Flurry("chad", 1);
    static Application scene = new Application();
    static Creature[] enemies = generateEnemies();
    static Creature opponent;
    static int nextEncounter = 0;
    static ImageIcon[] enemyIcons;
    static ImageIcon[] environmentIcons;
    static Environment[] battlefields = generateEnvironments();
    static Environment battlefield = battlefields[0];

    public static void setPlayerClass(Creature creature) {
        playerCreature = creature;
    }

    private static Creature[] generateEnemies() {
        String[] names = {"Rylan", "Len", "Mrs. Uniat", "Tuong"};
        Creature[] toReturn = new Creature[4];
        enemyIcons = new ImageIcon[4];
        for (int i = 0; i < 4; i++) {
            int randomNumber = (int) (Math.random()*4);
            switch (randomNumber) {
                case 0 -> {
                    toReturn[i] = new Destruction(names[i], i * 2);
                    enemyIcons[i] = new ImageIcon("Destruction.png");
                }
                case 1 -> {
                    toReturn[i] = new Flurry(names[i], i * 2);
                    enemyIcons[i] = new ImageIcon("Flurry.png");
                }
                case 2 -> {
                    toReturn[i] = new Bewitched(names[i], i * 2);
                    enemyIcons[i] = new ImageIcon("Bewitched.png");
                }
                case 3 -> {
                    toReturn[i] = new Resolve(names[i], i * 2);
                    enemyIcons[i] = new ImageIcon("Resolve.png");
                }
            }
        }
        return toReturn;
    }

    //I considered making using some generalization to put these generation methods together
    //But that would be so much more inefficient - new class, new null methods in environment - than just repeating myself a little
    private static Environment[] generateEnvironments() {
        Environment[] toReturn = new Environment[4];
        environmentIcons = new ImageIcon[4];
        for (int i = 0; i < 4; i++) {
            int randomNumber = (int) (Math.random()*3);
            switch (randomNumber) {
                case 0 -> {
                    toReturn[i] = environmentSetup(i, Environment.FOREST, "Forest.png");
                }
                case 1 -> {
                    toReturn[i] = environmentSetup(i, Environment.DESERT, "Desert.png");
                }
                case 2 -> {
                    toReturn[i] = environmentSetup(i, Environment.TUNDRA, "Tundra.png");
                }
            }
        }
        return toReturn;
    }

    private static Environment environmentSetup(int i, Environment environment, String imageName) {
        environmentIcons[i] = new ImageIcon(imageName);
        int[] stats = environment.getStatChanges();
        environmentIcons[i].setDescription("<html><strong>" + environment + "</strong><br>Damage:" + showSign(stats[0]) + "<br>Defense:" + showSign(stats[1]) + "<br>Attacks:" + showSign(stats[2]));
        return environment;
    }

    private static String showSign(int n) {
        if (n < 0){
            return "" + n;
        }
        return "+" + n;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        frame = new JFrame("Application");
        scene.initialize(frame, scene.card1);
        scene.getStartMenu().initializePopups();
        scene.getStartMenu().fillEnemyList(enemyIcons, true);
        scene.getStartMenu().fillEnemyList(environmentIcons, false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void nextEncounter() {
        setUpEncounter(playerCreature, enemies[nextEncounter]);
        battlefield = battlefields[nextEncounter];
        scene.shop.playerStatSet();
        if (nextEncounter < 3) {
            nextEncounter ++;
        }
        scene.shop.setEnemyPreview(enemies[nextEncounter].getSprite());
    }

    private static void setUpEncounter(Creature player, Creature enemy) {
        opponent = enemy;
        scene.setFightBackground(battlefield.getColor());
        scene.setCreaturePictures(player.getSprite(), enemy.getSprite());
        scene.updateHealth(true, true, player);
        scene.updateHealth(true, false, enemy);
        enemy.setRandomIntention();
        scene.displayIntention(enemy.getIntention(), enemy.getHealth(), enemy.getName(), false);

    }

    public static void fightRound(Creature a, Creature b, int choiceA, int choiceB) {
        //try to find a better way to do this???
        if (b.stats[4] > a.stats[4]) {
            b.fight(a, choiceB, choiceA, scene, false, battlefield.getStatChanges());
            a.fight(b, choiceA, choiceB, scene, true, battlefield.getStatChanges());
        } else {
            a.fight(b, choiceA, choiceB, scene, true, battlefield.getStatChanges());
            b.fight(a, choiceB, choiceA, scene, false, battlefield.getStatChanges());
        }
        if (a.getHealth() == 0) {
            scene.lossSetVisible(true);
        }

        scene.updateHealth(false, true, a);
        scene.updateHealth(false, false, b);
        b.setRandomIntention();
        scene.displayIntention(b.getIntention(), b.getHealth(), b.getName(), (opponent.getName().equals("Tuong")));
    }

    public static void confirmInput(int userInput) {
        fightRound(playerCreature, opponent, userInput, opponent.getIntention());
    }

    public static Creature getPlayerCreature() {
        return playerCreature;
    }

}