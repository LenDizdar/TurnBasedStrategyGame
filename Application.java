import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Application {
    private JButton button1;
    public JPanel panel1;
    private JButton defense2Button;
    private JButton attacks2Button;

    public Application() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(null, "Hello world");
                Main.test(0);
            }
        });
        defense2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.test(1);
            }
        });
        attacks2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.test(2);
            }
        });
    }
}
