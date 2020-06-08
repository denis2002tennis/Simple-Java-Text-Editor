package TextEditor;

import java.awt.*;

import java.awt.event.*;

import javax.swing.*;

import javax.swing.text.*;
import javax.swing.text.DefaultEditorKit.CutAction;
import javax.swing.text.DefaultEditorKit.CopyAction;
import javax.swing.text.DefaultEditorKit.PasteAction;
import javax.swing.text.StyledEditorKit.BoldAction;
import javax.swing.text.StyledEditorKit.ItalicAction;
import javax.swing.text.StyledEditorKit.UnderlineAction;
import javax.swing.text.StyledEditorKit.AlignmentAction;
import javax.swing.text.StyledEditorKit.FontSizeAction;
import javax.swing.text.StyledEditorKit.FontFamilyAction;

import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.border.LineBorder;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import javax.swing.undo.UndoManager;
import javax.swing.event.UndoableEditListener;
import javax.swing.event.UndoableEditEvent;

import java.io.*;
import java.util.Vector;
import java.util.Random;


public class TextEditor {


    private JFrame frame;
    private JTextPane textPane;

    private JComboBox<String> fontSizeComboBox;
    private JComboBox<String> textAlignComboBox;
    private JComboBox<String> fontFamilyComboBox;
    private UndoManager undoMgr;
    private String pictureButtonName;
    private File file;
    private File filek;
    CHECKERER saved;
    int counter=0;
    enum CHECKERER {TRUE, FALSE}
    ;

    enum UndoActionType {UNDO, REDO}
    ;

    private static final String MAIN_TITLE = "Text Editor - ";
    private static final String DEFAULT_FONT_FAMILY = "SansSerif";
    private static final int DEFAULT_FONT_SIZE = 14;
    private static final String[] FONT_SIZES = {"Font Size", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};
    private static final String[] TEXT_ALIGNMENTS = {"Text Align", "Left", "Center", "Right", "Original"};

    private static final String ELEM = AbstractDocument.ElementNameAttribute;
    private static final String COMP = StyleConstants.ComponentElementName;


    private final ImageIcon boldIcon = new ImageIcon("icons/bold.png");
    private final ImageIcon italicIcon = new ImageIcon("icons/italic.png");
    private final ImageIcon colorIcon = new ImageIcon("icons/color.png");
    private final ImageIcon undoIcon = new ImageIcon("icons/undo.png");
    private final ImageIcon redoIcon = new ImageIcon("icons/redo.png");
    private final ImageIcon underlineIcon = new ImageIcon("icons/underline.png");


    private final ImageIcon newIcon = new ImageIcon("icons/new.png");
    private final ImageIcon openIcon = new ImageIcon("icons/open.png");
    private final ImageIcon printIcon = new ImageIcon("icons/print.png");
    private final ImageIcon saveIcon = new ImageIcon("icons/save.png");
    private final ImageIcon closeIcon = new ImageIcon("icons/close.png");


    private final ImageIcon clearIcon = new ImageIcon("icons/clear.png");
    private final ImageIcon cutIcon = new ImageIcon("icons/cut.png");
    private final ImageIcon copyIcon = new ImageIcon("icons/copy.png");
    private final ImageIcon pasteIcon = new ImageIcon("icons/paste.png");
    private final ImageIcon selectAllIcon = new ImageIcon("icons/selectall.png");


    JButton cutButton = new JButton(new CutAction());
    JButton copyButton = new JButton(new CopyAction());
    JButton pasteButton = new JButton(new PasteAction());
    JButton boldButton = new JButton(new BoldAction());
    JButton italicButton = new JButton(new ItalicAction());
    JButton underlineButton = new JButton(new UnderlineAction());
    JButton colorButton = new JButton();

    public static void main(String[] args)
            throws Exception {

        UIManager.put("TextPane.font",
                new Font(DEFAULT_FONT_FAMILY, Font.PLAIN, DEFAULT_FONT_SIZE));
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TextEditor().createAndShowGUI();
            }
        });
    }

    private void createAndShowGUI() {

        frame = new JFrame();
        setFrameTitleWithExtn("New file");
        textPane = new JTextPane();
        JScrollPane editorScrollPane = new JScrollPane(textPane);

        textPane.setDocument(getNewDocument());
        textPane.setContentType("text/html");

        undoMgr = new UndoManager();
        EditButtonActionListener editButtonActionListener =
                new EditButtonActionListener();

        Helper.buttonIns(cutButton, "Cut", cutIcon);
        cutButton.addActionListener(editButtonActionListener);

        Helper.buttonIns(copyButton, "Copy", copyIcon);
        copyButton.addActionListener(editButtonActionListener);

        Helper.buttonIns(pasteButton, "Paste", pasteIcon);
        pasteButton.addActionListener(editButtonActionListener);

        Helper.buttonIns(boldButton, "Bold", boldIcon);
        boldButton.addActionListener(editButtonActionListener);

        Helper.buttonIns(italicButton, "Italic", italicIcon);
        italicButton.addActionListener(editButtonActionListener);

        Helper.buttonIns(underlineButton, "Underline", underlineIcon);
        underlineButton.addActionListener(editButtonActionListener);

        colorButton.setIcon(colorIcon);
        colorButton.addActionListener(new ColorActionListener());

        fontFamilyComboBox = new JComboBox<String>();

        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        for (int i = 0; i < fonts.length; i++) {
            fontFamilyComboBox.addItem(fonts[i]);
        }
        fontFamilyComboBox.setMaximumSize(new Dimension(170, 30));
        fontFamilyComboBox.setToolTipText("Font Type");



        textAlignComboBox = new JComboBox<String>(TEXT_ALIGNMENTS);
        textAlignComboBox.setEditable(false);
        textAlignComboBox.addItemListener(new TextAlignItemListener());
        final JComboBox<String> fontType;

        fontType = new JComboBox<String>();

        fontType.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                String p = fontType.getSelectedItem().toString();
                int s = textPane.getFont().getSize();
                textPane.setFont(new Font(p, Font.PLAIN, s));
            }
        });

        fontSizeComboBox = new JComboBox<String>(FONT_SIZES);
        fontSizeComboBox.setEditable(false);
        fontSizeComboBox.addItemListener(new FontSizeItemListener());

        Vector<String> editorFonts = getEditorFonts();
        editorFonts.add(0, "Font Family");
        fontFamilyComboBox = new JComboBox<String>(editorFonts);
        fontFamilyComboBox.setEditable(false);
        fontFamilyComboBox.addItemListener(new FontFamilyItemListener());

        JButton insertPictureButton = new JButton("Picture ");
        insertPictureButton.addActionListener(new PictureInsertActionListener());

        JButton undoButton = new JButton();
        Helper.buttonIns(undoButton, "Undo", undoIcon);
        undoButton.addActionListener(new UndoActionListener(UndoActionType.UNDO));
        JButton redoButton = new JButton();
        Helper.buttonIns(redoButton, "Redo", redoIcon);
        redoButton.addActionListener(new UndoActionListener(UndoActionType.REDO));

        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel1.add(fontFamilyComboBox);
        panel1.add(cutButton);
        panel1.add(copyButton);
        panel1.add(pasteButton);
        panel1.add(new JSeparator(SwingConstants.VERTICAL));
        panel1.add(boldButton);
        panel1.add(italicButton);
        panel1.add(underlineButton);
        panel1.add(undoButton);
        panel1.add(redoButton);
        panel1.add(new JSeparator(SwingConstants.VERTICAL));
        panel1.add(colorButton);
        panel1.add(new JSeparator(SwingConstants.VERTICAL));
        panel1.add(textAlignComboBox);
        panel1.add(new JSeparator(SwingConstants.VERTICAL));
        panel1.add(fontSizeComboBox);
        panel1.add(new JSeparator(SwingConstants.VERTICAL));
        panel1.add(fontFamilyComboBox);
        panel1.add(new JSeparator(SwingConstants.VERTICAL));
        panel1.add(insertPictureButton);


        JPanel toolBarPanel = new JPanel();
        toolBarPanel.setLayout(new BoxLayout(toolBarPanel, BoxLayout.PAGE_AXIS));
        toolBarPanel.add(panel1);


        frame.add(toolBarPanel, BorderLayout.NORTH);
        frame.add(editorScrollPane, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        JMenu editMenu = new JMenu("Edit");
        fileMenu.setMnemonic(KeyEvent.VK_E);


        JMenuItem newItem = new JMenuItem("New", newIcon);
        newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        newItem.addActionListener(new NewFileListener());

        JMenuItem openItem = new JMenuItem("Open", openIcon);
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));

        openItem.addActionListener(e -> {
            if(counter==20) {
                int x = JOptionPane.showConfirmDialog(null, "Do you want to save changes for an Untitled?",
                        "Closing window", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (x == JOptionPane.YES_OPTION) {
                    FileChooser();
                }
            }
            filek = chooseFile();
            if (filek == null) {

                return;
            }
            counter=20;
            HTMLEditorKit kit = new HTMLEditorKit();
            StyledDocument doc = (StyledDocument) kit.createDefaultDocument();

            try {
                kit.read(new FileInputStream(filek), doc, 0);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            catch (BadLocationException e1) {
                e1.printStackTrace();}

            textPane.setEditorKit(kit);
            textPane.setDocument(doc);
            setFrameTitleWithExtn(filek.getName());

        });
        JMenuItem saveItem = new JMenuItem("Save",saveIcon);
        saveItem.setActionCommand("Save");
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, (Toolkit.getDefaultToolkit().getMenuShortcutKeyMask())));
        saveItem.addActionListener(e -> {

            if (file == null) {
                if(counter==50 || counter==0){ chooseFilek();}
                if(counter==20){ FileChooser(); }
                if (file == null) { return;
                }}});


        JMenuItem printItem = new JMenuItem("Print", printIcon);
        printItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
        printItem.addActionListener(new OpenFileListener());

        JMenuItem exitItem = new JMenuItem("Exit", closeIcon);
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.exit(0);
            }
        });

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(printItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);


        JMenuItem selectAll = new JMenuItem(new SelectAllAction("Select All", selectAllIcon, "Select all text", new Integer(KeyEvent.VK_A)));
        Helper.itemAdd(selectAll,"Select All",selectAllIcon,KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));

        JMenuItem copy=new JMenuItem(new DefaultEditorKit.CopyAction());
        Helper.itemAdd(copy,"Copy",copyIcon,KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));

        JMenuItem paste=new JMenuItem(new DefaultEditorKit.PasteAction());
        Helper.itemAdd(paste,"Paste",pasteIcon,KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));

        JMenuItem cut=new JMenuItem(new DefaultEditorKit.CutAction());
        Helper.itemAdd(cut,"Cut",cutIcon,KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));

        JMenuItem clear=new JMenuItem(new DefaultEditorKit.CutAction());
        Helper.itemAdd(clear,"Clear",clearIcon,KeyStroke.getKeyStroke(KeyEvent.VK_K, InputEvent.CTRL_MASK));

        editMenu.add(selectAll);
        editMenu.add(copy);
        editMenu.add(paste);
        editMenu.add(cut);
        editMenu.add(clear);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        frame.setJMenuBar(menuBar);

        frame.setSize(1030, 500);
        frame.setLocation(150, 80);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        textPane.requestFocusInWindow();
    }
    void FileChooser() {

        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(false);

        HTMLEditorKit kit=(HTMLEditorKit) textPane.getEditorKit();
        StyledDocument doc1 = (StyledDocument)textPane.getDocument();

        try {
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filek.getAbsoluteFile()));
            kit.write(out, doc1, doc1.getStartPosition().getOffset(), doc1.getLength());
            out.flush();

            saved = CHECKERER.TRUE;
            out.close();}

        catch (FileNotFoundException e){}
        catch (IOException e1){}
        catch (BadLocationException e1){}}

    private File chooseFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        else {
            return null;
        }
    }

    void chooseFilek() {

        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(false);

        int option = chooser.showSaveDialog(frame);

        if (option == JFileChooser.APPROVE_OPTION) {
            HTMLEditorKit kit=(HTMLEditorKit) textPane.getEditorKit();
            StyleSheet style=kit.getStyleSheet();
            style.addRule("A {line-height: 0;}");
            kit.setStyleSheet(style);

            StyledDocument doc1 = (StyledDocument)textPane.getDocument();

            try {
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(chooser.getSelectedFile().getAbsoluteFile()));
                kit.write(out, doc1, doc1.getStartPosition().getOffset(), doc1.getLength());
                saved = CHECKERER.TRUE;}

            catch (FileNotFoundException e){}
            catch (IOException e1){}
            catch (BadLocationException e1){}}}


    private void setFrameTitleWithExtn(String titleExtn) {

        frame.setTitle(MAIN_TITLE + titleExtn);
    }

    private StyledDocument getNewDocument() {

        StyledDocument doc = new DefaultStyledDocument();
        doc.addUndoableEditListener(new UndoEditListener());
        return doc;
    }


    private Vector<String> getEditorFonts() {

        String[] availableFonts =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        Vector<String> returnList = new Vector<>();

        for (String font : availableFonts) {
                returnList.add(font);
        }
        return returnList;
    }

    private class EditButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            textPane.requestFocusInWindow();
        }
    }

    private class ColorActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Color newColor = JColorChooser.showDialog(frame, "Choose a color", Color.BLACK);
            if (newColor == null) {

                textPane.requestFocusInWindow();
                return;
            }

            SimpleAttributeSet attr = new SimpleAttributeSet();
            StyleConstants.setForeground(attr, newColor);
            textPane.setCharacterAttributes(attr, false);
            textPane.requestFocusInWindow();
        }
    }

    class TextAlignItemListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if ((e.getStateChange() != ItemEvent.SELECTED) ||
                    (textAlignComboBox.getSelectedIndex() == 0)) {
                return;
            }
            String alignmentStr = (String) e.getItem();
            int newAlignment = textAlignComboBox.getSelectedIndex() - 1;
            textAlignComboBox.setAction(new AlignmentAction(alignmentStr, newAlignment));
            textAlignComboBox.setSelectedIndex(0);
            textPane.requestFocusInWindow();
        }
    }

    private class FontSizeItemListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if ((e.getStateChange() != ItemEvent.SELECTED) ||
                    (fontSizeComboBox.getSelectedIndex() == 0)) {
                return;
            }
            String fontSizeStr = (String) e.getItem();
            int newFontSize = 0;
            try {
                newFontSize = Integer.parseInt(fontSizeStr);
            } catch (NumberFormatException ex) {
                return;
            }
            fontSizeComboBox.setAction(new FontSizeAction(fontSizeStr, newFontSize));
            fontSizeComboBox.setSelectedIndex(0);
            textPane.requestFocusInWindow();
        }
    }

    private class FontFamilyItemListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if ((e.getStateChange() != ItemEvent.SELECTED) ||
                    (fontFamilyComboBox.getSelectedIndex() == 0)) {
                return;
            }
            String fontFamily = (String) e.getItem();
            fontFamilyComboBox.setAction(new FontFamilyAction(fontFamily, fontFamily));
            fontFamilyComboBox.setSelectedIndex(0);
            textPane.requestFocusInWindow();
        }
    }

    private class UndoEditListener implements UndoableEditListener {
        @Override
        public void undoableEditHappened(UndoableEditEvent e) {
            undoMgr.addEdit(e.getEdit()); // remember the edit
        }
    }

    private class UndoActionListener implements ActionListener {
        private UndoActionType undoActionType;
        public UndoActionListener(UndoActionType type) {
            undoActionType = type;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (undoActionType) {
                case UNDO:
                    if (!undoMgr.canUndo()) {
                        textPane.requestFocusInWindow();
                        return;
                    }
                    undoMgr.undo();
                    break;

                case REDO:
                    if (!undoMgr.canRedo()) {
                        textPane.requestFocusInWindow();
                        return;
                    }
                    undoMgr.redo();
            }
            textPane.requestFocusInWindow();
        }
    }

    class SelectAllAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        public SelectAllAction(String text, ImageIcon icon, String desc, Integer mnemonic) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            textPane.selectAll();
        }
    }

    private class PictureInsertActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            File pictureFile = choosePictureFile();

            if (pictureFile == null) {

                textPane.requestFocusInWindow();
                return;
            }

            ImageIcon icon = new ImageIcon(pictureFile.toString());
            JButton picButton = new JButton(icon);
            picButton.setBorder(new LineBorder(Color.WHITE));
            picButton.setMargin(new Insets(0, 0, 0, 0));
            picButton.setAlignmentY(.9f);
            picButton.setAlignmentX(.9f);
            picButton.addFocusListener(new PictureFocusListener());
            picButton.setName("PICTURE_ID_" + new Random().nextInt());
            textPane.insertComponent(picButton);
            textPane.requestFocusInWindow();
        }

        private File choosePictureFile() {

            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "PNG, JPG & GIF Images", "png", "jpg", "gif");
            chooser.setFileFilter(filter);

            if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {

                return chooser.getSelectedFile();
            } else {
                return null;
            }
        }
    }
    private class PictureFocusListener implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {

            JButton button = (JButton) e.getComponent();
            button.setBorder(new LineBorder(Color.GRAY));
            pictureButtonName = button.getName();
        }

        @Override
        public void focusLost(FocusEvent e) {

            ((JButton) e.getComponent()).setBorder(new LineBorder(Color.WHITE));
        }
    }

    private class NewFileListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            initEditorAttributes();
            textPane.setDocument(getNewDocument());
            file = null;
            setFrameTitleWithExtn("New file");
        }

        private void initEditorAttributes() {

            AttributeSet attrs1 = textPane.getCharacterAttributes();
            SimpleAttributeSet attrs2 = new SimpleAttributeSet(attrs1);
            attrs2.removeAttributes(attrs1);
            textPane.setCharacterAttributes(attrs2, true);
        }
    }

    private class OpenFileListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            file = chooseFile();

            if (file == null) {
                return;
            }

            readFile(file);
            setFrameTitleWithExtn(file.getName());
        }

        private File chooseFile() {

            JFileChooser chooser = new JFileChooser();

            if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {

                return chooser.getSelectedFile();
            } else {
                return null;
            }
        }

        private void readFile(File file) {

            StyledDocument doc = null;

            try (InputStream fis = new FileInputStream(file);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {

                doc = (DefaultStyledDocument) ois.readObject();
            } catch (FileNotFoundException ex) {

                JOptionPane.showMessageDialog(frame, "Input file was not found!");
                return;
            } catch (ClassNotFoundException | IOException ex) {

                throw new RuntimeException(ex);
            }

            textPane.setDocument(doc);
            doc.addUndoableEditListener(new UndoEditListener());
            applyFocusListenerToPictures(doc);
        }

        private void applyFocusListenerToPictures(StyledDocument doc) {

            ElementIterator iterator = new ElementIterator(doc);
            Element element;

            while ((element = iterator.next()) != null) {

                AttributeSet attrs = element.getAttributes();

                if (attrs.containsAttribute(ELEM, COMP)) {

                    JButton picButton = (JButton) StyleConstants.getComponent(attrs);
                    picButton.addFocusListener(new PictureFocusListener());
                }
            }
        }
    }


}