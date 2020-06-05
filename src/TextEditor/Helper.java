package TextEditor;

import javax.swing.*;

public class Helper {
    public static void buttonIns(JButton button,String text, ImageIcon imageIcon){
        button.setIcon(imageIcon);
        button.setHideActionText(true);
        button.setToolTipText(text);

    }
}
