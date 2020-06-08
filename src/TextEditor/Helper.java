package TextEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class Helper {
    public static void buttonIns(JButton button,String text, ImageIcon imageIcon){
        button.setIcon(imageIcon);
        button.setHideActionText(true);
        button.setToolTipText(text);

    }
    public static void itemAdd(JMenuItem item,String text, ImageIcon imageIcon,KeyStroke keyStroke){
        item.setText(text);
        item.setIcon(imageIcon);
        item.setToolTipText(text);
        item.setAccelerator(keyStroke);
    }


}
