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
            Main.getPlayerCreature().buff(0);
            goNext();
        });
        defense1Button.addActionListener(e -> {
            Main.getPlayerCreature().buff(1);
            goNext();
        });
        attacksButton.addActionListener(e -> {
            Main.getPlayerCreature().buff(2);
            goNext();
        });
        healButton.addActionListener(e -> {
            Main.getPlayerCreature().fullHeal();
            goNext();
        });
    }

    public void playerStatSet() {
        playerStatsText.setText(new Title().makeStatString("Your ", Main.getPlayerCreature().stats, ""));
    }

    private void goNext() {
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
