import javax.swing.*;


public class Main {

    static Creature Len = new Flurry(2, 2, 5);

    static Creature Rylan = new Resolve(1,5,2);

    public static void main(String[] args) {

        JFrame frame = new JFrame("Application");
        frame.setContentPane(new Application().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        Len.setName("Len");
        Rylan.setName("Rylan");

    }
    
    public static void fightRound(Creature a, Creature b, int choiceA, int choiceB) {
        if (a.getHealth() > 0 && b.getHealth() > 0) {

            System.out.println(b.getName() + " Health : " + b.getHealth() + " " + a.getName() + " Health : " + a.getHealth());
            b.fight(a, choiceB, choiceA);
            System.out.println(a.getName() + " Health : " + a.getHealth() + " " + b.getName() + " Health : " + b.getHealth());
            a.fight(b, choiceA, choiceB);

        }
    }

    public static void test(int userInput) {
        fightRound(Len, Rylan, userInput, (int)(Math.random()*3));
    }

}
