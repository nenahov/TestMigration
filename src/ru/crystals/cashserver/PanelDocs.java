package ru.crystals.cashserver;

import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTextPane;

import net.miginfocom.swing.MigLayout;
import ru.crystals.config.PanelOther;

public class PanelDocs extends JPanel {

    public PanelDocs() {
        setLayout(new MigLayout("", "[grow]", "[][grow]"));

        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setFont(new Font("Tahoma", Font.BOLD, 16));
        textPane
            .setText("<№ принтера> для печати документа <№ док> на кассе <№ кассы>:\r\nDOC<№ док>.<№ кассы>=<№ принтера>\r\nи / или\r\nDOC<№ док>.*=<№ принтера>\r\n\r\nЧисло копий для печати документа <№ док>:\r\nDOC<№ док>.Copies=<Число копий>");
        add(textPane, "cell 0 0,grow");

        JPanel panel = new PanelOther("doc");
        add(panel, "cell 0 1,grow");
    }

    private static final long serialVersionUID = 1L;

}
