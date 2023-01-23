/*
Name: Len Dizdar
Date: 1/22/2023
Description: Environments are the place in which battles are held. They modify
both creatures' stats by a certain, constant amount. They also change the colour
of the fight screen!
 */
import java.awt.*;

public enum Environment {

    FOREST(new int[] {-1,2,2}, new Color(150, 225, 146), "Forest"),
    DESERT(new int[] {0,-1,1}, new Color(204, 198, 96), "Desert"),
    TUNDRA(new int[] {0,1,-1}, new Color(29, 222, 174), "Tundra");

    Color color;
    String name;
    private int[] statChanges;

    /**
     * Creates an environment. These guys change stats and affect decision-making.
     * @param statChanges The actual modifications made to creatures' stats.
     * @param color The background colour.
     * @param name The identifier string, used for the popup menus.
     */
    Environment(int[] statChanges, Color color, String name)
{       this.color = color;
        this.statChanges = statChanges;
        this.name = name;
}
    public int[] getStatChanges() {
        return this.statChanges;
    }
    public Color getColor() {
        return this.color;
    }
    public String toString() {
        return name;
    }
}
