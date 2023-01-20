import javax.swing.*;

public class PostFight implements Screen {
    private JButton damage1Button;
    private JPanel panel;
    private JButton healButton;
    private JButton defense1Button;
    private JButton attacksButton;
    private JLabel enemyPreview;
    private JLabel playerStatsText;
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

    public void playerStatSet() {
        playerStatsText.setText(new Title().makeStatString("Your ", Main.getPlayerCreature().stats, ""));
    }

    private void goNext() {
        playerCreature = Main.getPlayerCreature();
        Main.nextEncounter();
        Application.goFightPanel();
    }

    public void setEnemyPreview(ImageIcon enemy) {
        enemyPreview.setIcon(enemy);
        enemyPreview.setHorizontalTextPosition(SwingConstants.LEADING);
    }

    public JPanel getPanel() {
        return panel;
    }
}
