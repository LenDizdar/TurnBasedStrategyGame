/*
Name: Len Dizdar
Date: 1/22/2023
Description: The screen between fights, allows the user to boost a stat or heal. Also displays useful information.
 */
import javax.swing.*;

public class PostFight implements Screen {
    private JButton damage1Button;
    private JPanel panel;
    private JButton healButton;
    private JButton defense1Button;
    private JButton attacksButton;
    private JLabel enemyPreview;
    private JLabel playerStatsText;

    /**
     * Event listeners for button clicks that Creature.buff() the player creature's stats or Creature.fullHeal().
     */
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

    /**
     * Updates the player creature's stat display text.
     */
    public void playerStatSet() {
        playerStatsText.setText(Title.makeStatString("Your ", Main.getPlayerCreature().stats, ""));
    }

    /**
     * Takes the user to the next encounter on the fight screen.
     */
    private void goNext() {
        Main.nextEncounter();
        Application.goFightPanel();
    }

    /**
     * Displays the next enemy that the player will face, helps with strategy around stat buffs.
     * @param enemy the enemy creature's icon.
     */
    public void setEnemyPreview(ImageIcon enemy) {
        enemyPreview.setIcon(enemy);
        enemyPreview.setHorizontalTextPosition(SwingConstants.LEADING);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}
