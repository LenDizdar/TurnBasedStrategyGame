import java.awt.*;

public enum Environment {

    FOREST(new int[] {-1,2,2}, new Color(150, 225, 146)),
    DESERT(new int[] {0,-1,1}, new Color(204, 198, 96)),
    TUNDRA(new int[] {0,1,-1}, new Color(29, 222, 174));

    Color color;
    private int[] statChanges;
    Environment(int[] statChanges, Color color)
{       this.color = color;
        this.statChanges = statChanges;
    }
    public int[] getStatChanges() {
        return this.statChanges;
    }
    public Color getColor() {
        return this.color;
    }
}
