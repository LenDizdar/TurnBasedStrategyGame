import javax.swing.*;

public class Title extends Screen {
    private JPanel panel;
    private JButton StartButton;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton confirmButton;
    JPopupMenu menu;
    static JTextField nameInput = new JTextField();
    private static String name;

    public Title() {
        confirmButton.addActionListener(e -> {
            name = nameInput.getText();
            StartButton.setVisible(true);
        });
        StartButton.addActionListener(e -> {
            Main.getPlayerCreature().setName(name);
            Main.scene.updateHealth(true, true, Main.getPlayerCreature());
        });
        button1.addActionListener(e -> {
            popUpTextBox(button1);
            Main.setPlayerClass(new Destruction(null, 1));
        });
        button2.addActionListener(e -> {
            popUpTextBox(button2);
            Main.setPlayerClass(new Resolve(null, 1));
        });
        button3.addActionListener(e -> {
            popUpTextBox(button3);
            Main.setPlayerClass(new Flurry(null, 1));
        });
        button4.addActionListener(e -> {
            popUpTextBox(button4);
            Main.setPlayerClass(new Bewitched(null, 1));
        });
    }

    public static void applyName(Creature toBeNamed) {
        String toName;
        if (!name.equals("")) {
            toName = name;
        } else {
            toName = "How could you not name your creature?";
        }
        toBeNamed.setName(toName);
    }

    private void popUpTextBox(JButton button) {
        menu = new JPopupMenu();
        menu.add(new JLabel("Name your creature!"));
        menu.add(nameInput);
        menu.show(panel,(int) button.getLocation().getX()+5,(int) button.getLocation().getY()+30);
    }

    public JPanel getPanel() {
        return panel;
    }

    public JButton getNextButton() {
        return StartButton;
    }
}
