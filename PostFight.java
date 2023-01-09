import javax.swing.*;

public class PostFight extends Screen {
    private JButton damage1Button;
    private JPanel panel;
    private JButton healButton;
    private JButton defense1Button;
    private JButton attacksButton;

    public PostFight() {
        Creature playerCreature = Main.getPlayerCreature();
        damage1Button.addActionListener(e -> {
            playerCreature.buff(0);
            goNext();
        });
        defense1Button.addActionListener(e -> {
            playerCreature.buff(1);
            goNext();
        });
        attacksButton.addActionListener(e -> {
            playerCreature.buff(2);
            goNext();
        });
        healButton.addActionListener(e -> {
            playerCreature.fullHeal();
            goNext();
        });
    }

    private void goNext() {
        Main.nextEncounter();
        Application.goFightPanel();
    }

    public JPanel getPanel() {
        return panel;
    }
}
