package ru.crystals.loyalty;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTextPane;

class PanelSiebel extends JPanel {

    private static final long serialVersionUID = 1L;

    public PanelSiebel() {
        setLayout(new BorderLayout(0, 0));

        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setFont(new Font("Tahoma", Font.BOLD, 16));
        textPane
            .setText("В папку со службой необходимо положить файл Siebel.WSDL, полученный от внешней системы.\r\nКроме этого, надо настроить параметры доступа к БД SES (вкладка Set 5)");
        add(textPane, BorderLayout.CENTER);

    }

}
