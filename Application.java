/*
Name: Len Dizdar
Date: 1/22/2023
Description: This is effectively the fight screen, but also contains the links between
all the screens shown. Deals with UX and traversing the screens, as well as adding
functionality to the components in the fight screen, like the creature pictures,
health bars, intention text, and combat log.
 */

import javax.swing.*;
import java.awt.*;

public class Application {
    private JButton damage2Button;
    private static final CardLayout cardsLayout = new CardLayout();
    public static JPanel cards;
    public JPanel card1;
    private JButton defense2Button;
    private JButton attacks2Button;
    private JProgressBar playerHealth;
    private JProgressBar enemyHealth;
    private JLabel playerHealthText;
    private JLabel enemyHealthText;
    private JLabel enemyIntentionText;
    private JLabel combatLog;
    private JLabel userHelpText;
    private JButton nextButton;
    private JButton lossButton;
    private JScrollPane scrollBox;
    public JLabel PlayerPic;
    public JLabel OpponentPic;
    private JButton toGloryButton;
    private Title startMenu;
    public PostFight shop = new PostFight();
    private final JPanel winScreen = new JPanel(new GridLayout(3,3));

    /**
     * builds the contents of the window for ui, makes a card stack to flip through
     * panels/screens, adds buttons for flipping through cards. Makes the win screen.
     * @param frame The window in which this card stack resides.
     * @param fightPanel The fight screen/panel/card.
     */
    public void initialize(JFrame frame, JPanel fightPanel) {
        Container pane = frame.getContentPane();
        startMenu = new Title();
        cards = new JPanel(cardsLayout);
        cards.setPreferredSize(new Dimension(900,500));
        cards.add(startMenu.getPanel(), "startMenu");
        cards.add(fightPanel, "fight");
        cards.add(shop.getPanel(), "shop");
        cards.add(winScreen, "winScreen");
        JLabel winText = new JLabel("You won!!", SwingConstants.CENTER);
        winText.setFont(new Font("Serif", Font.PLAIN, 100));
        userHelpText.setText("What stat will you boost this round?");
        winScreen.add(winText);
        makeNextButton(startMenu.getNextButton());
        makeNextButton(nextButton);
        pane.add(cards, BorderLayout.CENTER);
    }
    public Title getStartMenu() {
        return startMenu;
    }

    /**
     * Sets up event listeners (clicked) for the buttons on the fight screen
     */
    public Application() {

        damage2Button.addActionListener(e -> battleButton(0));

        defense2Button.addActionListener(e -> battleButton(1));
        attacks2Button.addActionListener(e -> battleButton(2));

        lossButton.addActionListener(e -> System.exit(1));
        toGloryButton.addActionListener(e -> cardsLayout.last(cards));
    }

    /**
     * Sets up the pictures of the player and opponent in their places on the fight screen.
     * @param player the png imageIcon of the player's creature.
     * @param opponent the png imageIcon of the opponent's creature.
     */
    public void setCreaturePictures(ImageIcon player, ImageIcon opponent) {
        PlayerPic.setIcon(player);
        OpponentPic.setIcon(opponent);
    }

    /**
     * gives a button a clicked eventListener to go to the next screen in the card stack.
     * @param button the button to be given this function.
     */
    private void makeNextButton(JButton button) {
        button.addActionListener(e -> nextPanel(Application.cardsLayout, cards));
    }

    /**
     * Not exactly a setter, but sets the background colour of the fight screen.
     * @param newColor a color obtained from the environment of the battle.
     */
    public void setFightBackground(Color newColor) {
        card1.setBackground(newColor);
    }

    /**
     * Flips to the next screen in the card stack.
     * @param layout the card stack in question.
     * @param panel the panel containing the card stack.
     */
    public void nextPanel(CardLayout layout, Container panel) {
        layout.next(panel);
        nextSetInvisible();
        combatLog.setText("<html>Combat Log: ");
        shop.playerStatSet();
    }

    /**
     * Sends the user to the fight panel. Used to move backwards in the stack from post-fight to fight.
     */
    public static void goFightPanel() {
        cardsLayout.show(cards, "fight");
    }

    /**
     * This is the function of the buttons on the fight screen, they augment the player creature's stats temporarily.
     * @param userInput which stat to modify.
     */
    public void battleButton(int userInput) {
        Main.confirmInput(userInput);
        playerHealth.setValue(Main.getPlayerCreature().getHealth());
    }

    /**
     * Used to update health bars, decides which one to update.
     * @param isMax whether this is the creature's max health or not.
     * @param isPlayer whether this is the player or not, indicates which health bar to update.
     * @param creature the creature whose health is being displayed.
     */
    public void updateHealth(boolean isMax, boolean isPlayer, Creature creature) {
        if (isPlayer) {
            updateHealthHelper(playerHealth, playerHealthText, isMax, creature.getHealth(), creature.getMaxHealth(), creature.getName());
        } else {
            updateHealthHelper(enemyHealth, enemyHealthText, isMax, creature.getHealth(), creature.getMaxHealth(), creature.getName());
        }
    }

    /**
     * Visually updates the health bars to be accurate to the numbers.
     * @param healthBar the health bar being updated.
     * @param text the text (component not string) above the health bar, indicating that this is in fact health.
     * @param isMax whether this is the creature's max health or not.
     * @param health the creature's current health.
     * @param maxHealth the creature's max health.
     * @param name the creature's name.
     */
    private void updateHealthHelper(JProgressBar healthBar, JLabel text, boolean isMax, int health, int maxHealth, String name) {
        if (isMax) {
            healthBar.setMaximum(maxHealth);
        }
        healthBar.setValue(health);
        text.setText(name + " health: " + health);
    }

    //these three methods set some buttons' visibility
    public void lossSetVisible(boolean isVisible) {
        lossButton.setVisible(isVisible);
        nextSetInvisible();
    }
        private void nextSetInvisible() {
        //would take a boolean but it's always false.
        nextButton.setVisible(false);
    }
    private void toGlorySetVisible() {
        //would take a boolean, but it's always true.
        toGloryButton.setVisible(true);
        nextButton.setVisible(false);
    }

    /**
     * Adds another line of text to the box on the side of the fight screen.
     * @param content the text (string) to add.
     */
    public void updateCombatLog(String content) {
        combatLog.setText(combatLog.getText() + "<br>" + content);
        scrollBox.getVerticalScrollBar().setValue(scrollBox.getVerticalScrollBar().getMaximum());
    }

    /**
     * Used to display what stat augmentation choice the opponent is going to make this turn.
     * Also used to display that the enemy has been beat, and handles sending the player to the win screen.
     * @param intention the stat the opponent intends to augment (0 = damage, 1 = defense, 2 = attacks).
     * @param health the creature's health
     * @param name the name of the creature.
     * @param isTuong whether the opponent is the boss or not, indicating if the win screen should be accessible.
     */
    public void displayIntention(int intention, int health, String name, boolean isTuong) {
        var intentionString = switch (intention) {
            case 0 -> "damage per attack";
            case 1 -> "defense";
            case 2 -> "attacks per round";
            default -> "";
        };
        String toDisplay;
        if (health > 0) {
            toDisplay = name + " intends to augment their *" + intentionString + "* this round.";
        } else {
            toDisplay = name + " has been beat!";
            nextButton.setVisible(true);
            if (isTuong) {
                toGlorySetVisible();
            }
        }
        enemyIntentionText.setText(toDisplay);
    }

}
