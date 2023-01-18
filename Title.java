import javax.swing.*;
import java.awt.*;

public class Title extends Screen {
    private JPanel panel;
    private JButton StartButton;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton confirmButton;
    private JLabel enIcon1;
    private JLabel enIcon2;
    private JLabel enIcon3;
    private JLabel enIcon4;
    JPopupMenu menu;
    static JTextField nameInput = new JTextField();
    private static String name;
    private final Creature destruction = new Destruction(null, 1);
    private final Creature bewitched = new Bewitched(null, 1);
    private final Creature flurry = new Flurry(null, 1);
    private final Creature resolve = new Resolve(null, 1);

    public void fillEnemyList(ImageIcon[] pics) {
        JLabel[] list = new JLabel[] {enIcon1, enIcon2, enIcon3, enIcon4};
        for (int i = 0; i < 4; i++) {
            list[i].setIcon(pics[i]);
            list[i].setHorizontalTextPosition(SwingConstants.LEADING);
        }
    }

    public Title() {
        confirmButton.addActionListener(e -> {
            name = nameInput.getText();
            StartButton.setVisible(true);
        });
        StartButton.addActionListener(e -> {
            Main.getPlayerCreature().setName(name);
            Main.nextEncounter();
            Main.scene.updateHealth(true, true, Main.getPlayerCreature());
        });
        button1.addActionListener(e -> {
            Main.setPlayerClass(destruction);
            popUpTextBox(button1, destruction.getStats(), "Deal destructive damage with critical hits!");
        });
        button2.addActionListener(e -> {
            Main.setPlayerClass(resolve);
            popUpTextBox(button2, resolve.getStats(), "Damage enemies for punching you! And heal it all back.");
        });
        button3.addActionListener(e -> {
            Main.setPlayerClass(flurry);
            popUpTextBox(button3, flurry.getStats(), "Dodge and weave while launching dozens of attacks!");
        });
        button4.addActionListener(e -> {
            Main.setPlayerClass(bewitched);
            popUpTextBox(button4, bewitched.getStats(), "Stun your enemies and break their armor with magic!");
        });
    }

    private void popUpTextBox(JButton button, int[] stats, String classDescription) {
        menu = new JPopupMenu();
        menu.add(new JLabel("Name your creature!"));
        menu.add(nameInput);
        menu.add(new JLabel(makeStatString("",stats,classDescription)));
        menu.setPreferredSize(new Dimension(100,200));
        menu.show(panel,(int) button.getLocation().getX()+5,(int) button.getLocation().getY()+30);
    }

    public String makeStatString(String beforeExtra, int[] stats, String afterExtra) {
        return "<html><strong>" + beforeExtra + "Stats:<br> Damage: " + stats[0] + "<br>Defense: " + stats[1] + "<br>Attacks: " + stats[2] + "<br>Health: " + stats[3] + "<br>Speed: " + stats[4] + "<br>" + afterExtra;
    }

    public JPanel getPanel() {
        return panel;
    }

    public JButton getNextButton() {
        return StartButton;
    }


}
