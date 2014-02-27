package ru.crystals.config;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ru.crystals.configuration.SettingsValues;

public class PanelOther extends JPanel {

    private static final long serialVersionUID = 1L;

    public PanelOther(String... prefix) {
        setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane = new JScrollPane();
        add(scrollPane, BorderLayout.CENTER);

        JTextArea textArea = new JTextArea();
        textArea.setToolTipText("Чтобы удалить настройку, удалите только значение после символа \"=\".");
        textArea.setFont(new Font("Tahoma", Font.PLAIN, 16));
        scrollPane.setViewportView(textArea);
        SettingsValues.add(textArea, prefix);
    }

}
