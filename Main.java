// Part 3

public class Main {


    public static void main(String[] args) {

        Creature Len = new Flurry(2, 2, 5);
        Len.setName("Len");
        Creature Rylan = new Resolve(2,2,5);
        Rylan.setName("Rylan");
        int rylanChoice = 0;
        int lenChoice = 2;

        while (Rylan.getHealth() > 0 && Len.getHealth() > 0) {

            System.out.println("Len Health : " + Len.getHealth() + " Rylan Health : " + Rylan.getHealth());
            Len.fight(Rylan, lenChoice, rylanChoice);
            System.out.println("Rylan Health : " + Rylan.getHealth());
            Rylan.fight(Len, rylanChoice, lenChoice);

        }

        System.out.println("Len Health : " + Len.getHealth() + " Rylan Health : " + Rylan.getHealth());

    }

}
