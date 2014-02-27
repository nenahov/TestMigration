package ru.crystals.printlabel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import ru.crystals.config.CdbField;
import ru.crystals.config.IntegerField;
import ru.crystals.config.PanelOther;
import ru.crystals.configuration.SettingsValues;

public class MainPrintLabel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTextField edLabelsPath;

    public MainPrintLabel() {
        setLayout(new MigLayout("", "[][grow][]", "[][][][][][][][grow]"));
        {
            JLabel lblIp = new JLabel("IP службы лицензирования:");
            add(lblIp, "cell 0 0,alignx trailing");
            JTextField edHaspRemoteHost = new JTextField();
            add(edHaspRemoteHost, "cell 1 0,growx");
            JButton btDefault = new JButton("");
            add(btDefault, "cell 2 0");
            SettingsValues.add(edHaspRemoteHost, btDefault, "HaspRemoteHost", "127.0.0.1");
        }

        JSeparator separator = new JSeparator();
        add(separator, "cell 0 1 3 1,grow");
        {
            JLabel label = new JLabel("Доступ к БД:");
            add(label, "cell 0 2,alignx trailing");
            CdbField cdbField = new CdbField();
            add(cdbField, "cell 1 2 2 1,grow");
            SettingsValues.add(cdbField, "CDB");
        }

        JSeparator separator_1 = new JSeparator();
        add(separator_1, "cell 0 3 3 1,grow");
        {
            JLabel label = new JLabel("Путь к файлам шаблонов:");
            add(label, "cell 0 4,alignx trailing");
            edLabelsPath = new JTextField();
            add(edLabelsPath, "flowx,cell 1 4,growx");
            JButton btDefault = new JButton();
            add(btDefault, "cell 2 4");
            SettingsValues.add(edLabelsPath, btDefault, "LabelsPath", "C:\\SET\\Crystal\\TRF");
        }
        {
            JButton btnSelectPath = new JButton("...");
            btnSelectPath.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser dialog = new JFileChooser(edLabelsPath.getText());
                    dialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    dialog.setAcceptAllFileFilterUsed(false);
                    if (dialog.showOpenDialog(MainPrintLabel.this) == JFileChooser.APPROVE_OPTION) {
                        edLabelsPath.setText(dialog.getSelectedFile().getAbsolutePath());
                    }
                }
            });
            add(btnSelectPath, "cell 1 4");
        }
        {
            JTextField textField = new JTextField();
            textField.setBackground(new Color(211, 211, 211));
            textField.setEditable(false);
            textField.setText("Порт веб-сервиса печати этикеток http://127.0.0.1:8080/WS/PrintLabelService?wsdl");
            add(textField, "cell 0 5 3 1,growx");
            textField.setColumns(10);

            JLabel label = new JLabel("Порт:");
            add(label, "cell 0 6,alignx trailing");

            JTextField ui = new IntegerField();
            add(ui, "cell 1 6,growx");

            JButton b = new JButton();
            add(b, "cell 2 6");
            SettingsValues.add(ui, b, "PrintLabelServicePort", "8080");
        }
        JPanel panel_1 = new PanelOther();
        add(panel_1, "cell 0 7 3 1,grow");
    }
}
