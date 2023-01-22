/*
Name: Len Dizdar
Date: 1/22/2023
Description: This is where the magic happens. Screens are made, and when buttons are pressed their
listeners lead here for some comparisons to be made and for UI calls to be sent back to those screen
classes. The enemy and environment lists are also generated.
 */

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

    /**
     * Randomly generates a list of enemies for the player to fight. Also stores their icons.
     * @return a randomly generated list of enemies for the player to fight.
     */
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

    //I considered using some generalization to put these generation methods together
    //But that would be so much more inefficient - new class, new null methods in environment - than just repeating myself a little
    /**
     * Generates a list of random environments, one for each enemy in the list/every fight. Also stores their icons.
     * @return A list of randomly generated environments.
     */
    private static Environment[] generateEnvironments() {
        Environment[] toReturn = new Environment[enemies.length];
        environmentIcons = new ImageIcon[enemies.length];
        for (int i = 0; i < enemies.length; i++) {
            int randomNumber = (int) (Math.random()*3);
            switch (randomNumber) {
                case 0 -> toReturn[i] = environmentSetup(i, Environment.FOREST, "Forest.png");
                case 1 -> toReturn[i] = environmentSetup(i, Environment.DESERT, "Desert.png");
                case 2 -> toReturn[i] = environmentSetup(i, Environment.TUNDRA, "Tundra.png");
                default -> throw new IllegalStateException("Unexpected value: " + randomNumber);
            }
        }
        return toReturn;
    }

    /**
     * Sets up the hover capability and icon on the title screen for an environment.
     * @param i the number of environment, in the order of the battles, being looked at.
     * @param environment the environment type. This isn't adjusted at all, it's used to assign the return value.
     * @param imageName the name of the image file for this environment's icon.
     * @return returns the inputted environment. This helper method is just to assign a value AND do some extra stuff that was necessary in context.
     */
    private static Environment environmentSetup(int i, Environment environment, String imageName) {
        environmentIcons[i] = new ImageIcon(imageName);
        int[] stats = environment.getStatChanges();
        environmentIcons[i].setDescription("<html><strong>" + environment + "</strong><br>Damage:" + showSign(stats[0]) + "<br>Defense:" + showSign(stats[1]) + "<br>Attacks:" + showSign(stats[2]));
        return environment;
    }

    /**
     * Takes an integer and returns a string with that integer and its sign.
     * @param n the integer.
     * @return the integer as a string with a + or -.
     */
    private static String showSign(int n) {
        if (n < 0){
            return "" + n;
        }
        return "+" + n;
    }

    /**
     * Sets up visuals. Fills the enemy and environment visual lists.
     * @param args semantics? The console I guess.
     */
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

    /**
     * Moves to the next encounter, setting up the post-fight screen with updated information
     * and updating the contents of the fight screen.
     */
    public static void nextEncounter() {
        setUpEncounter(playerCreature, enemies[nextEncounter]);
        battlefield = battlefields[nextEncounter];
        scene.shop.playerStatSet();
        if (nextEncounter < enemies.length-1) {
            nextEncounter ++;
        }
        scene.shop.setEnemyPreview(enemies[nextEncounter].getSprite());
    }

    /**
     * Places the correctly updated visuals in the fight screen.
     * @param player the player's creature.
     * @param enemy the opponent's/computer's creature.
     */
    private static void setUpEncounter(Creature player, Creature enemy) {
        opponent = enemy;
        scene.setFightBackground(battlefield.getColor());
        scene.setCreaturePictures(player.getSprite(), enemy.getSprite());
        scene.updateHealth(true, true, player);
        scene.updateHealth(true, false, enemy);
        enemy.setRandomIntention();
        scene.displayIntention(enemy.getIntention(), enemy.getHealth(), enemy.getName(), false);

    }

    /**
     * Makes two creatures fight each other and updates the visuals in accordance with what happened on the backend.
     * Also makes the opponent's next choice.
     * @param a the player's creature.
     * @param b the opponent's creature.
     * @param choiceA the player's input.
     * @param choiceB the opponent's input.
     */
    public static void fightRound(Creature a, Creature b, int choiceA, int choiceB) {
        //try to find a better way to do this???
        if (b.stats[4] > a.stats[4]) {
            b.fight(a, choiceB, choiceA, scene, battlefield.getStatChanges());
            a.fight(b, choiceA, choiceB, scene, battlefield.getStatChanges());
        } else {
            a.fight(b, choiceA, choiceB, scene, battlefield.getStatChanges());
            b.fight(a, choiceB, choiceA, scene, battlefield.getStatChanges());
        }
        if (a.getHealth() == 0) {
            scene.lossSetVisible(true);
        }

        scene.updateHealth(false, true, a);
        scene.updateHealth(false, false, b);
        b.setRandomIntention();
        scene.displayIntention(b.getIntention(), b.getHealth(), b.getName(), (opponent.getName().equals("Tuong")));
    }

    /**
     * Triggers the fightRound() with a given user input.
     * @param userInput the user's input this round, temporary augmentation of a stat.
     */
    public static void confirmInput(int userInput) {
        fightRound(playerCreature, opponent, userInput, opponent.getIntention());
    }

    public static Creature getPlayerCreature() {
        return playerCreature;
    }
    public static void setPlayerClass(Creature creature) {
        playerCreature = creature;
    }

}