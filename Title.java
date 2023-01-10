import javax.swing.*;

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
    private Creature destruction = new Destruction(null, 1);
    private Creature bewitched = new Bewitched(null, 1);
    private Creature flurry = new Flurry(null, 1);
    private Creature resolve = new Resolve(null, 1);

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
            popUpTextBox(button1, destruction.getStats(), "Deal destructive damage with<br>critical hits!");
        });
        button2.addActionListener(e -> {
            Main.setPlayerClass(resolve);
            popUpTextBox(button2, resolve.getStats(), "Take a bunch of hits and <br>heal it all back.");
        });
        button3.addActionListener(e -> {
            Main.setPlayerClass(flurry);
            popUpTextBox(button3, flurry.getStats(), "Dodge and weave while launching<br>dozens of attacks!");
        });
        button4.addActionListener(e -> {
            Main.setPlayerClass(bewitched);
            popUpTextBox(button4, bewitched.getStats(), "Stun your enemies with magic!");
        });
    }

    private void popUpTextBox(JButton button, int[] stats, String classDescription) {
        menu = new JPopupMenu();

        menu.add(new JLabel("Name your creature!"));
        menu.add(nameInput);
        menu.add(new JLabel("<html><strong>Stats:<br> Damage: " + stats[0] + "<br>Defense: " + stats[1] + "<br>Attacks: " + stats[2] + "<br>Health: " + stats[3] + "<br>Speed: " + stats[4] + "<br>" + classDescription ));
        //menu.add(new JLabel(toDisplay));
        menu.show(panel,(int) button.getLocation().getX()+5,(int) button.getLocation().getY()+30);
    }

    public JPanel getPanel() {
        return panel;
    }

    public JButton getNextButton() {
        return StartButton;
    }


}
