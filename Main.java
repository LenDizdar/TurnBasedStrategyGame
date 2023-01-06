import javax.swing.*;


public class Main {

    static Creature Len = new Destruction("Len");

    static JFrame frame;

    static Creature playerCreature = Len;

    static Creature Rylan = new Resolve("Rylan");

    static Application scene = new Application();

    public static void main(String[] args) {


        frame = new JFrame("Application");
        scene.initialize(frame, scene.card1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        scene.updateHealth(true, true, Len);
        scene.updateHealth(true, false, Rylan);
        Rylan.setRandomIntention();
        scene.displayIntention(Rylan.getIntention(), Rylan.getHealth());
    }
    
    public static void fightRound(Creature a, Creature b, int choiceA, int choiceB) {
        if (a.getHealth() > 0 && b.getHealth() > 0) {

            System.out.println(b.getName() + " Health : " + b.getHealth() + " " + a.getName() + " Health : " + a.getHealth());
            b.fight(a, choiceB, choiceA);
            System.out.println(a.getName() + " Health : " + a.getHealth() + " " + b.getName() + " Health : " + b.getHealth());
            a.fight(b, choiceA, choiceB);
            //scene.setUserHelpText("What stat will you boost this round?");
        }
        scene.updateHealth(false, true, a);
        scene.updateHealth(false, false, b);
        b.setRandomIntention();
        scene.displayIntention(b.getIntention(), b.getHealth());
    }

    public static void test(int userInput) {
        fightRound(Len, Rylan, userInput, Rylan.getIntention());
    }

    public static Creature getPlayerCreature() {
        return playerCreature;
    }

}
