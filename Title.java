import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Title implements Screen {
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
    private JLabel envIcon1;
    private JLabel envIcon2;
    private JLabel envIcon3;
    private JLabel envIcon4;
    private JLabel[] list;
    JPopupMenu classInfo = new JPopupMenu();
    JLabel classInfoText = new JLabel();
    JPopupMenu environmentInfo = new JPopupMenu();
    JLabel environmentInfoText = new JLabel();
    static JTextField nameInput = new JTextField();
    private static String name;
    private final Creature destruction = new Destruction(null, 1);
    private final Creature bewitched = new Bewitched(null, 1);
    private final Creature flurry = new Flurry(null, 1);
    private final Creature resolve = new Resolve(null, 1);

    public void fillEnemyList(ImageIcon[] pics, boolean isEnemy) {
        if (isEnemy) {
            list = new JLabel[] {enIcon1, enIcon2, enIcon3, enIcon4};
        } else {
            list = new JLabel[] {envIcon1, envIcon2, envIcon3, envIcon4};
        }


        for (int i = 0; i < 4; i++) {
            list[i].setIcon(pics[i]);
            list[i].setToolTipText(pics[i].getDescription());
            list[i].setHorizontalTextPosition(SwingConstants.LEADING);
        }
    }

    public void initializePopups() {
        environmentInfo.add(environmentInfoText);
        classInfo.add(new JLabel("Name your creature!"));
        classInfo.add(nameInput);
        classInfo.add(classInfoText);
        classInfo.setPreferredSize(new Dimension(100,200));
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
            setCharacter(destruction, button1, "Deal destructive damage with critical hits!");
            confirmButton.setVisible(true);
        });
        button2.addActionListener(e -> {
            setCharacter(resolve, button2, "Damage enemies for punching you! And heal it all back.");
            confirmButton.setVisible(true);
        });
        button3.addActionListener(e -> {
            setCharacter(flurry, button3, "Dodge and weave while launching dozens of attacks!");
            confirmButton.setVisible(true);

        });
        button4.addActionListener(e -> {
            setCharacter(bewitched, button4, "Stun your enemies and break their armor with magic!");
            confirmButton.setVisible(true);
        });
        MouseAdapter listener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                JLabel original = (JLabel) e.getSource();
                Point location = MouseInfo.getPointerInfo().getLocation();
                environmentInfoText.setText(original.getToolTipText());
                ToolTipManager.sharedInstance().setEnabled(false);
                environmentInfo.show(panel, location.x-80, location.y-50);
            }

            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                environmentInfo.setVisible(false);
            }

        };
        envIcon1.addMouseListener(listener);
        envIcon2.addMouseListener(listener);
        envIcon3.addMouseListener(listener);
        envIcon4.addMouseListener(listener);
    }
    
    private void setCharacter(Creature creature, JButton button, String classDescription) {
        Main.setPlayerClass(creature);
        popUpTextBox(button, creature.getStats(), classDescription);
    }
    
    private void popUpTextBox(JButton button, int[] stats, String classDescription) {
        classInfoText.setText(makeStatString("",stats,classDescription));
        classInfo.show(panel,(int) button.getLocation().getX(),(int) button.getLocation().getY());
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
