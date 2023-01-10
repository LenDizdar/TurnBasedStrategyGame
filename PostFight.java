import javax.swing.*;

public class PostFight extends Screen {
    private JButton damage1Button;
    private JPanel panel;
    private JButton healButton;
    private JButton defense1Button;
    private JButton attacksButton;
    Creature playerCreature;

    public PostFight() {

        damage1Button.addActionListener(e -> {
            goNext();
            playerCreature.buff(0);
        });
        defense1Button.addActionListener(e -> {
            goNext();
            playerCreature.buff(1);
        });
        attacksButton.addActionListener(e -> {
            goNext();
            playerCreature.buff(2);
        });
        healButton.addActionListener(e -> {
            goNext();
            playerCreature.fullHeal();
        });
    }

    private void goNext() {
        playerCreature = Main.getPlayerCreature();
        Main.nextEncounter();
        Application.goFightPanel();
    }

    public JPanel getPanel() {
        return panel;
    }
}
