package ru.crystals.config;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CdbField extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField textField;
    private JButton btnEdit;

    public CdbField() {

        textField = new JTextField();
        textField.addCaretListener(new CaretListener() {

            @Override
            public void caretUpdate(CaretEvent e) {
                updateEditUI();
            }

        });
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(textField);
        textField.setColumns(10);

        JButton button = new JButton("...");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser(getCDB().isEmpty() ? "." : getCDB());
                fc.addChoosableFileFilter(new FileNameExtensionFilter("Файлы CDB", "cdb"));
                if (fc.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION) {
                    String fileName = fc.getSelectedFile().getAbsolutePath();
                    if (!fileName.toLowerCase().endsWith(".cdb")) {
                        fileName = fileName + ".cdb";
                    }
                    setCDB(fileName);
                    if (!getCdbFile().exists()) {
                        edit();
                    }
                }
            }
        });
        add(button);

        btnEdit = new JButton("Edit");
        btnEdit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                edit();
            }
        });
        add(btnEdit);
        updateEditUI();
    }

    protected void edit() {
        File file = getCdbFile();
        try {
            if (file.exists()) {
                if (!file.isFile()) {
                    throw new IOException("Неправильное имя файла");
                }
                if (!file.canRead()) {
                    throw new IOException("Нет доступа к файлу");
                }
            }
            CdbEditorDialog dialog = new CdbEditorDialog(this, file);
            dialog.setVisible(true);
            updateEditUI();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage() + " (" + file.getAbsolutePath() + ")", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void updateEditUI() {
        btnEdit.setEnabled(!getCDB().isEmpty());
        File file = getCdbFile();
        if (file.exists() && file.isFile()) {
            btnEdit.setForeground(Color.GREEN.darker());
        } else {
            btnEdit.setForeground(Color.RED.darker());
        }
    }

    public String getCDB() {
        return textField.getText().trim();
    }

    public void setCDB(String cdb) {
        cdb = cdb != null ? cdb.trim() : "";
        textField.setText(cdb);
    }

    private File getCdbFile() {
        return new File(getCDB()).getAbsoluteFile();
    }

}
