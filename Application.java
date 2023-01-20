import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Application {
    private JButton button1;
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
    private JLabel userHelpText;
    private JLabel combatLog;
    private JButton nextButton;
    private JButton lossButton;
    private JScrollPane scrollBox;
    public JLabel PlayerPic;
    public JLabel OpponentPic;
    private JButton toGloryButton;
    private Title startMenu;
    public PostFight shop = new PostFight();
    private JPanel winScreen = new JPanel(new GridLayout(3,3));

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
        winScreen.add(winText);
        makeNextButton(startMenu.getNextButton());
        makeNextButton(nextButton);
        pane.add(cards, BorderLayout.CENTER);
    }
    public Title getStartMenu() {
        return startMenu;
    }
    public Application() {

        //playerHealth.setMaximum(Main.getPlayerCreature().getHealth());

        button1.addActionListener(e -> battleButton(0));

        defense2Button.addActionListener(e -> battleButton(1));
        attacks2Button.addActionListener(e -> battleButton(2));

        lossButton.addActionListener(e -> System.exit(1));
        toGloryButton.addActionListener(e -> cardsLayout.last(cards));
    }

    public void setCreaturePictures(ImageIcon player, ImageIcon opponent) {
        PlayerPic.setIcon(player);
        OpponentPic.setIcon(opponent);
    }

    private void makeNextButton(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextPanel(Application.cardsLayout, cards);
            }
        });
    }

    public void setFightBackground(Color newColor) {
        card1.setBackground(newColor);
    }


    public void nextPanel(CardLayout layout, Container panel) {
        layout.next(panel);
        nextSetVisible(false);
        combatLog.setText("<html>Combat Log: ");
        shop.playerStatSet();
    }
    public static void goFightPanel() {
        cardsLayout.show(cards, "fight");
    }
    public void battleButton(int userInput) {
        Main.confirmInput(userInput);
        playerHealth.setValue(Main.getPlayerCreature().getHealth());
    }

    public void updateHealth(boolean isMax, boolean isPlayer, Creature creature) {
        if (isPlayer) {
            updateHealthHelper(playerHealth, playerHealthText, isMax, creature.getHealth(), creature.getMaxHealth(), creature.getName());
        } else {
            updateHealthHelper(enemyHealth, enemyHealthText, isMax, creature.getHealth(), creature.getMaxHealth(), creature.getName());
        }
    }

    private void updateHealthHelper(JProgressBar healthBar, JLabel text, boolean isMax, int health, int maxHealth, String name) {
        if (isMax) {
            healthBar.setMaximum(maxHealth);
        }
        healthBar.setValue(health);
        text.setText(name + " health: " + health);
    }

    public void lossSetVisible(boolean isVisible) {
        lossButton.setVisible(isVisible);
    }
    private void nextSetVisible(boolean isVisible) {
        nextButton.setVisible(isVisible);
    }
    private void toGlorySetVisible(boolean isVisible) {
        toGloryButton.setVisible(isVisible);
        nextButton.setVisible(!isVisible);
    }
    public void updateCombatLog(String content) {
        combatLog.setText(combatLog.getText() + "<br>" + content);
        scrollBox.getVerticalScrollBar().setValue(scrollBox.getVerticalScrollBar().getMaximum());
    }

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
                toGlorySetVisible(true);
            }
        }
        enemyIntentionText.setText(toDisplay);
    }

}
