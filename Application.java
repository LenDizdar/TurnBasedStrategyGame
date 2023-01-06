import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

public class Application {
    private JButton button1;
    private CardLayout cardsLayout = new CardLayout();
    public JPanel cards;
    public JPanel card1;
    private JButton defense2Button;
    private JButton attacks2Button;
    private JProgressBar playerHealth;
    private JProgressBar enemyHealth;
    private JLabel playerHealthText;
    private JLabel enemyHealthText;
    private JLabel enemyIntentionText;
    private JLabel userHelpText;

    public void initialize(JFrame frame, JPanel fightPanel) {
        Container pane = frame.getContentPane();
        JPanel card = new JPanel();
        card.add(new JButton("Fight!"));
        cards = new JPanel(cardsLayout);
        cards.add(card, "menu");
        cards.add(fightPanel, "fight");
        makeNextButton((JButton) card.getComponent(0), cardsLayout, cards);
        pane.add(cards, BorderLayout.CENTER);

    }
    public Application() {

        playerHealth.setMaximum(Main.getPlayerCreature().getHealth());
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                battleButton(0);
            }
        });

        defense2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                battleButton(1);
            }
        });
        attacks2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                battleButton(2);
            }
        });
    }

    private void makeNextButton(JButton button, CardLayout layout, Container panel) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                layout.next(panel);
            }
        });
    }

    public void battleButton(int userInput) {
        Main.test(userInput);
        playerHealth.setValue(Main.getPlayerCreature().getHealth());
    }

    public void updateHealth(boolean isMax, boolean isPlayer, Creature creature) {
        if (isPlayer) {
            updateHealthHelper(playerHealth, playerHealthText, isMax, creature.getHealth(), creature.getName());
        } else {
            updateHealthHelper(enemyHealth, enemyHealthText, isMax, creature.getHealth(), creature.getName());
        }
    }

    private void updateHealthHelper(JProgressBar healthBar, JLabel text, boolean isMax, int health, String name) {
        if (isMax) {
            healthBar.setMaximum(health);
        }
        healthBar.setValue(health);
        text.setText(name + " health: " + health);
    }

    public void displayIntention(int intention, int health) {
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
            toDisplay = "The opponent intends to augment their *" + intentionString + "* this round.";
        } else {
            toDisplay = "The opponent has been beat!";
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
