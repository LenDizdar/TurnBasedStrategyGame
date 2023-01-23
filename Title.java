/*
Name: Len Dizdar
Date: 1/22/2023
Description: This is the title screen. It contains some buttons and icons and text boxes.
 */

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

    /**
     * This fills the enemy and environment lines with icons on the right side of the screen.
     * @param pics the array of icons to fill in.
     * @param isEnemy this indicates which list to fill, the left, enemy list, or the right, environment list.
     */
    public void fillEnemyList(ImageIcon[] pics, boolean isEnemy) {
        JLabel[] list;
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

    /**
     * This creates the popup screens so that instead of making new ones when necessary,
     * the same one can be passed around with adjusted information.
     */
    public void initializePopups() {
        environmentInfo.add(environmentInfoText);
        classInfo.add(new JLabel("Name your creature!"));
        classInfo.add(nameInput);
        classInfo.add(classInfoText);
        classInfo.setPreferredSize(new Dimension(120,200));
    }

    /**
     * All the action listeners.
     */
    public Title() {
        //confirms the player character's name.
        confirmButton.addActionListener(e -> {
            name = nameInput.getText();
            StartButton.setVisible(true);
        });
        //starts the game, moving to the next screen and confirming the player creature.
        StartButton.addActionListener(e -> {
            Main.getPlayerCreature().setName(name);
            Main.nextEncounter();
            Main.scene.updateHealth(true, true, Main.getPlayerCreature());
        });
        //sets the player creature to Destruction
        button1.addActionListener(e -> {
            setCharacter(destruction, button1, "Deal destructive damage with critical hits!");
            confirmButton.setVisible(true);
        });
        //sets the player creature to Resolve
        button2.addActionListener(e -> {
            setCharacter(resolve, button2, "Damage enemies for punching you! And heal it all back.");
            confirmButton.setVisible(true);
        });
        //sets the player creature to Flurry
        button3.addActionListener(e -> {
            setCharacter(flurry, button3, "Dodge and weave while launching dozens of attacks!");
            confirmButton.setVisible(true);

        });
        //sets the player creature to Bewitched
        button4.addActionListener(e -> {
            setCharacter(bewitched, button4, "Stun your enemies and break their armor with magic!");
            confirmButton.setVisible(true);
        });
        //These lines of code are the mouse listeners for the popup box explaining the environments.
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

    /**
     * Sets the player's creature to the type selected and displays a menu with its stats, features, and a name text field.
     * @param creature the player's creature.
     * @param button the button that does this.
     * @param classDescription the unique features of this creature subclass.
     */
    private void setCharacter(Creature creature, JButton button, String classDescription) {
        Main.setPlayerClass(creature);
        popUpTextBox(button, creature.getStats(), classDescription);
    }

    /**
     * Helper for the method above, moves the info menu to the button pressed.
     * @param button the button that activated this.
     * @param stats the stats of the creature that is being displayed.
     * @param classDescription the unique features of this creature subclass.
     */
    private void popUpTextBox(JButton button, int[] stats, String classDescription) {
        classInfoText.setText(makeStatString("",stats,classDescription));
        classInfo.show(panel,(int) button.getLocation().getX(),(int) button.getLocation().getY());
    }

    /**
     * Takes some fluff on either side and a stat list, returns a String of stats being displayed wrapped in fluff.
     * @param beforeExtra the fluff before the stats.
     * @param stats the stats to display.
     * @param afterExtra the fluff after the stats.
     * @return A String displaying, in a user-friendly way, the creature's stats with some extra unspecific information.
     */
    public static String makeStatString(String beforeExtra, int[] stats, String afterExtra) {
        return "<html><strong>" + beforeExtra + "Stats:<br> Damage: " + stats[0] + "<br>Defense: " + stats[1] + "<br>Attacks: " + stats[2] + "<br>Health: " + stats[3] + "<br>Speed: " + stats[4] + "<br>" + afterExtra;
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    public JButton getNextButton() {
        return StartButton;
    }


}
