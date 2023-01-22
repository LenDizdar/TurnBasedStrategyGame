/*
Name: Len Dizdar
Date: 1/22/2023
Description: There certainly is a relation between the classes that implement this, just not as much as I thought.
Had I started making scenes earlier in the process, and understood how to properly use .form files, this
interface would be more important as it would provide the framework for creating a scene/screen
 */
import javax.swing.*;

public interface Screen {
    JPanel getPanel();
}
