import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

public class Application {
    private JButton button1;
    public JPanel panel1;
    private JButton defense2Button;
    private JButton attacks2Button;
    private JProgressBar playerHealth;
    private JProgressBar enemyHealth;
    private JLabel playerHealthText;
    private JLabel enemyHealthText;
    private JLabel enemyIntentionText;
    private JLabel userHelpText;


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

    public void displayIntention(int intention) {
        String toDisplay = "";
        switch (intention) {
            case 0 :
                toDisplay = "damage per attack";
                break;
            case 1 :
                toDisplay = "defense";
                break;
            case 2 :
                toDisplay = "attacks per round";
                break;
        }
        enemyIntentionText.setText("The opponent intends to augment their *" + toDisplay + "* this round");
    }

    public void setUserHelpText(String input) {
        userHelpText.setText(input);
        panel1.repaint();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
