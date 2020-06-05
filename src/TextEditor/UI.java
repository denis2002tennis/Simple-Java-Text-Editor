//package TextEditor;
//
//import javax.swing.*;
//import javax.swing.undo.UndoManager;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyEvent;
//import java.util.Vector;
//
//public class UI {
//    private void createAndShowGUI() {
//
//        frame__ = new JFrame();
//        setFrameTitleWithExtn("New file");
//        textPane = new JTextPane();
//        JScrollPane editorScrollPane = new JScrollPane(textPane);
//
//        textPane.setDocument(getNewDocument());
//        textPane.addKeyListener(new MyEditor.BulletParaKeyListener());
//        textPane.addKeyListener(new MyEditor.NumbersParaKeyListener());
//        textPane.addCaretListener(new MyEditor.EditorCaretListener());
//
//        undoMgr__ = new UndoManager();
//        MyEditor.EditButtonActionListener editButtonActionListener =
//                new MyEditor.EditButtonActionListener();
//
//
//        cutButton.setHideActionText(true);
//
//
//        cutButton.setIcon(cutIcon);
//        cutButton.setToolTipText("Cut");
//        cutButton.addActionListener(editButtonActionListener);
//
//
//        copyButton.setIcon(copyIcon);
//        copyButton.setHideActionText(true);
//        copyButton.setToolTipText("Copy");
//        copyButton.addActionListener(editButtonActionListener);
//
//        pasteButton.setIcon(pasteIcon);
//        pasteButton.setHideActionText(true);
//        pasteButton.setToolTipText("Paste");
//        pasteButton.addActionListener(editButtonActionListener);
//
//
//        boldButton.setIcon(boldIcon);
//        boldButton.setHideActionText(true);
//        boldButton.setToolTipText("Bold");
//        boldButton.addActionListener(editButtonActionListener);
//
//        italicButton.setIcon(italicIcon);
//        italicButton.setHideActionText(true);
//        italicButton.setToolTipText("Italic");
//        italicButton.addActionListener(editButtonActionListener);
//
//        underlineButton.setHideActionText(true);
//        underlineButton.setIcon(underlineIcon);
//        underlineButton.setToolTipText("Underline");
//        underlineButton.addActionListener(editButtonActionListener);
//
//        colorButton.setIcon(colorIcon);
//        colorButton.addActionListener(new MyEditor.ColorActionListener());
//
//        textAlignComboBox__ = new JComboBox<String>(TEXT_ALIGNMENTS);
//        textAlignComboBox__.setEditable(false);
//        textAlignComboBox__.addItemListener(new MyEditor.TextAlignItemListener());
//        final JComboBox<String> fontType;
//        final JComboBox<Integer> fontSize;
//
//        fontType = new JComboBox<String>();
//
//        fontType.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent ev) {
//                //Getting the selected fontType value from ComboBox
//                String p = fontType.getSelectedItem().toString();
//                //Getting size of the current font or text
//                int s = textPane.getFont().getSize();
//                textPane.setFont(new Font(p, Font.PLAIN, s));
//            }
//        });
//
//        fontSizeComboBox__ = new JComboBox<String>(FONT_SIZES);
//        fontSizeComboBox__.setEditable(false);
//        fontSizeComboBox__.addItemListener(new MyEditor.FontSizeItemListener());
//
//        Vector<String> editorFonts = getEditorFonts();
//        editorFonts.add(0, "Font Family");
//        fontFamilyComboBox__ = new JComboBox<String>(editorFonts);
//        fontFamilyComboBox__.setEditable(false);
//        fontFamilyComboBox__.addItemListener(new MyEditor.FontFamilyItemListener());
//
//        JButton insertPictureButton = new JButton("Picture Insert");
//        insertPictureButton.addActionListener(new MyEditor.PictureInsertActionListener());
//        JButton deletePictureButton = new JButton("Picture Delete");
//        deletePictureButton.addActionListener(new MyEditor.PictureDeleteActionListener());
//
//        JButton undoButton = new JButton("Undo");
//        undoButton.addActionListener(new MyEditor.UndoActionListener(MyEditor.UndoActionType.UNDO));
//        JButton redoButton = new JButton("Redo");
//        redoButton.addActionListener(new MyEditor.UndoActionListener(MyEditor.UndoActionType.REDO));
//
//        JButton bulletInsertButton = new JButton("Bullets Insert");
//        bulletInsertButton.addActionListener(
//                new MyEditor.BulletActionListener(MyEditor.BulletActionType.INSERT));
//        JButton bulletRemoveButton = new JButton("Bullets Remove");
//        bulletRemoveButton.addActionListener(
//                new MyEditor.BulletActionListener(MyEditor.BulletActionType.REMOVE));
//
//        JButton numbersInsertButton = new JButton("Numbers Insert");
//        numbersInsertButton.addActionListener(
//                new MyEditor.NumbersActionListener(MyEditor.NumbersActionType.INSERT));
//        JButton numbersRemoveButton = new JButton("Numbers Remove");
//        numbersRemoveButton.addActionListener(
//                new MyEditor.NumbersActionListener(MyEditor.NumbersActionType.REMOVE));
//
//        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        panel1.add(cutButton);
//        panel1.add(copyButton);
//        panel1.add(pasteButton);
//        panel1.add(new JSeparator(SwingConstants.VERTICAL));
//        panel1.add(boldButton);
//        panel1.add(italicButton);
//        panel1.add(underlineButton);
//        panel1.add(new JSeparator(SwingConstants.VERTICAL));
//        panel1.add(colorButton);
//        panel1.add(new JSeparator(SwingConstants.VERTICAL));
//        panel1.add(textAlignComboBox__);
//        panel1.add(new JSeparator(SwingConstants.VERTICAL));
//        panel1.add(fontSizeComboBox__);
//        panel1.add(new JSeparator(SwingConstants.VERTICAL));
//        panel1.add(fontFamilyComboBox__);
//
//        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        panel2.add(insertPictureButton);
//        panel2.add(deletePictureButton);
//        panel2.add(new JSeparator(SwingConstants.VERTICAL));
//        panel2.add(bulletInsertButton);
//        panel2.add(bulletRemoveButton);
//        panel2.add(new JSeparator(SwingConstants.VERTICAL));
//        panel2.add(numbersInsertButton);
//        panel2.add(numbersRemoveButton);
//        panel2.add(new JSeparator(SwingConstants.VERTICAL));
//        panel2.add(undoButton);
//        panel2.add(redoButton);
//
//        JPanel toolBarPanel = new JPanel();
//        toolBarPanel.setLayout(new BoxLayout(toolBarPanel, BoxLayout.PAGE_AXIS));
//        toolBarPanel.add(panel1);
//        toolBarPanel.add(panel2);
//
//        frame__.add(toolBarPanel, BorderLayout.NORTH);
//        frame__.add(editorScrollPane, BorderLayout.CENTER);
//
//        JMenuBar menuBar = new JMenuBar();
//        JMenu fileMenu = new JMenu("File");
//        fileMenu.setMnemonic(KeyEvent.VK_F);
//
//        JMenuItem newItem	= new JMenuItem("New");
//        newItem.setMnemonic(KeyEvent.VK_N);
//        newItem.addActionListener(new MyEditor.NewFileListener());
//        JMenuItem openItem	= new JMenuItem("Open");
//        openItem.setMnemonic(KeyEvent.VK_O);
//        openItem.addActionListener(new MyEditor.OpenFileListener());
//        JMenuItem saveItem	= new JMenuItem("Save");
//        saveItem.setMnemonic(KeyEvent.VK_S);
//        saveItem.addActionListener(new MyEditor.SaveFileListener());
//        JMenuItem printItem	= new JMenuItem("Print");
//        printItem.setMnemonic(KeyEvent.VK_P);
//        printItem.addActionListener(new MyEditor.OpenFileListener());
//        JMenuItem exitItem = new JMenuItem("Exit");
//        exitItem.setMnemonic(KeyEvent.VK_X);
//        exitItem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                System.exit(0);
//            }
//        });
//
//        fileMenu.add(newItem);
//        fileMenu.addSeparator();
//        fileMenu.add(openItem);
//        fileMenu.add(saveItem);
//
//        fileMenu.addSeparator();
//        fileMenu.add(exitItem);
//        menuBar.add(fileMenu);
//        frame__.setJMenuBar(menuBar);
//
//        frame__.setSize(900, 500);
//        frame__.setLocation(150, 80);
//        frame__.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame__.setVisible(true);
//
//        textPane.requestFocusInWindow();
//    }
//}
