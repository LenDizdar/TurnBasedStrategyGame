import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

public class Application {
    private JButton button1;
    private static CardLayout cardsLayout = new CardLayout();
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

    public void initialize(JFrame frame, JPanel fightPanel) {
        Container pane = frame.getContentPane();
        Title startMenu = new Title();
        cards = new JPanel(cardsLayout);
        cards.add(startMenu.getPanel(), "startMenu");
        cards.add(fightPanel, "fight");
        cards.add(new PostFight().getPanel(), "shop");
        makeNextButton(startMenu.getNextButton(), cardsLayout, cards);
        makeNextButton(nextButton, cardsLayout, cards);
        pane.add(cards, BorderLayout.CENTER);
    }
    public Application() {

        //playerHealth.setMaximum(Main.getPlayerCreature().getHealth());

        button1.addActionListener(e -> battleButton(0));

        defense2Button.addActionListener(e -> battleButton(1));
        attacks2Button.addActionListener(e -> battleButton(2));

        lossButton.addActionListener(e -> System.exit(1));
    }

    private void makeNextButton(JButton button, CardLayout layout, Container panel) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextPanel(layout, panel);
            }
        });
    }

    public void nextPanel(CardLayout layout, Container panel) {
        layout.next(panel);
        nextSetVisible(false);
        combatLog.setText("<html>Combat Log: ");
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
    public void updateCombatLog(String content) {
        combatLog.setText(combatLog.getText() + "<br>" + content);
        scrollBox.getVerticalScrollBar().setValue(scrollBox.getVerticalScrollBar().getMaximum());
    }

    public void displayIntention(int intention, int health, String name) {
        String intentionString = "";
        switch (intention) {
            case 0 :
                intentionString = "damage per attack";
                break;
            case 1 :
                intentionString = "defense";
                break;
            case 2 :
                intentionString = "attacks per round";
                break;
        }
        String toDisplay;
        if (health > 0) {
            toDisplay = name + " intends to augment their *" + intentionString + "* this round.";
        } else {
            toDisplay = name + " has been beat!";
            nextButton.setVisible(true);
        }
        enemyIntentionText.setText(toDisplay);
    }

    public void setUserHelpText(String input) {
        userHelpText.setText(input);
        card1.repaint();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
