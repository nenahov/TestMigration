package ru.crystals.config;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.crypto.NoSuchPaddingException;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

public class CdbEditorDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    private final JPanel contentPanel = new JPanel();
    private JTextField edHost;
    private JTextField edLogin;
    private JPasswordField edPWD;
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private JRadioButton rbLogin;
    private JRadioButton rbWindowsNt;
    private JComboBox cbDBName;

    public CdbEditorDialog(Component parent, final File file) {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/res/ico.png")));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setSize(new Dimension(329, 342));
        setTitle(file.getAbsolutePath());
        setLocationRelativeTo(parent);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new MigLayout("", "[20][20][][grow][]", "[][][][][][][][][][grow]"));
        {
            JLabel label = new JLabel("Имя сервера:");
            contentPanel.add(label, "cell 0 0 5 1");
        }
        {
            edHost = new JTextField();
            edHost.setText("localhost");
            edHost.setColumns(10);
            contentPanel.add(edHost, "cell 1 1 4 1,growx");
        }
        {
            JLabel label = new JLabel("Авторизация на сервере:");
            contentPanel.add(label, "cell 0 2 5 1");
        }
        ActionListener rbAction = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                edLogin.setEnabled(rbLogin.isSelected());
                edPWD.setEnabled(rbLogin.isSelected());
            }
        };
        {
            rbWindowsNt = new JRadioButton("Использовать Windows NT авторизацию");

            rbWindowsNt.addActionListener(rbAction);
            buttonGroup.add(rbWindowsNt);
            contentPanel.add(rbWindowsNt, "cell 1 3 4 1");
        }
        {
            rbLogin = new JRadioButton("Использовать логин и пароль:");
            rbLogin.addActionListener(rbAction);
            buttonGroup.add(rbLogin);
            rbLogin.setSelected(true);
            contentPanel.add(rbLogin, "cell 1 4 4 1");
        }
        {
            JLabel label = new JLabel("Логин:");
            contentPanel.add(label, "flowx,cell 2 5,alignx trailing");
        }
        {
            edLogin = new JTextField();
            edLogin.setText("sa");
            edLogin.setColumns(10);
            contentPanel.add(edLogin, "cell 3 5 2 1,growx");
        }
        {
            JLabel label = new JLabel("Пароль:");
            contentPanel.add(label, "flowx,cell 2 6,alignx trailing");
        }
        {
            edPWD = new JPasswordField();
            contentPanel.add(edPWD, "cell 3 6 2 1,growx");
        }
        {
            JLabel label = new JLabel("Имя базы данных:");
            contentPanel.add(label, "cell 0 7 5 1");
        }
        {
            cbDBName = new JComboBox();
            cbDBName.setMaximumRowCount(7);
            cbDBName.setEditable(true);
            contentPanel.add(cbDBName, "cell 1 8 3 1,growx");
        }
        {
            JButton button = new JButton("<");
            button.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    getDBNames();
                }
            });
            button.setToolTipText("Загрузить список баз с сервера");
            contentPanel.add(button, "cell 4 8");
        }
        {
            JButton btnNewButton = new JButton("Тест соединения");
            btnNewButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    testConnection();
                }
            });
            contentPanel.add(btnNewButton, "cell 0 9 5 1,grow");
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("OK");
                okButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        save(file);
                    }
                });
                okButton.setActionCommand("OK");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        CdbEditorDialog.this.setVisible(false);
                    }
                });
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
            }
        }
        try {
            decryptFromFile(file);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Ошибка чтения файла", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void getDBNames() {
        try {
            if ("".equals(edHost.getText().trim())) {
                JOptionPane.showMessageDialog(null, "Заполните 'Имя сервера'", "", JOptionPane.ERROR_MESSAGE);
                edHost.requestFocus();
                return;
            }
            List<String> result = new ArrayList<String>();

            String SETRetailURL = "jdbc:sqlserver://" + edHost.getText() + ";databasename=master";

            if (edHost.getText().isEmpty())
                throw new Exception("Не верный IP адрес сервера");

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            Properties properties = new java.util.Properties();
            if (!rbWindowsNt.isSelected() && !edLogin.getText().trim().equals("")) {
                properties.setProperty("user", edLogin.getText().trim());
                properties.setProperty("password", new String(edPWD.getPassword()));
                properties.setProperty("integratedSecurity", "false");
            } else {
                properties.setProperty("integratedSecurity", "true");
            }
            properties.setProperty("autoReconnect", "true");
            properties.setProperty("loginTimeout", "5");

            Connection conn = DriverManager.getConnection(SETRetailURL, properties);
            PreparedStatement stmt = conn.prepareStatement("select * from sysdatabases");
            ResultSet rs = stmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    result.add(rs.getString("name"));
                }

                if (!rs.isClosed())
                    rs.close();
            }
            stmt.close();
            if (!conn.isClosed()) {
                conn.close();
            }

            Object db = cbDBName.getSelectedItem();
            cbDBName.setModel(new DefaultComboBoxModel(result.toArray()));
            cbDBName.setSelectedItem(db);
            cbDBName.revalidate();
            cbDBName.repaint();
            cbDBName.showPopup();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected boolean testConnection() {
        if ("".equals(edHost.getText().trim())) {
            JOptionPane.showMessageDialog(null, "Заполните 'Имя сервера'", "Тест", JOptionPane.ERROR_MESSAGE);
            edHost.requestFocus();
            return false;
        }
        String dbName = cbDBName.getSelectedItem() != null ? cbDBName.getSelectedItem().toString().trim() : "";
        String SETRetailURL = "jdbc:sqlserver://" + edHost.getText().trim() + ";databasename=" + dbName;

        try {

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            Properties properties = new java.util.Properties();

            if (!rbWindowsNt.isSelected() && !edLogin.getText().trim().equals("")) {
                properties.setProperty("user", edLogin.getText().trim());
                properties.setProperty("password", new String(edPWD.getPassword()));
                properties.setProperty("integratedSecurity", "false");
            } else {
                properties.setProperty("integratedSecurity", "true");
            }
            Connection conn = DriverManager.getConnection(SETRetailURL, properties);
            // DatabaseMetaData dbmd = conn.getMetaData();
            // dbmd.get
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка! " + e.getMessage(), "Тест соединения", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        JOptionPane.showMessageDialog(this, "Соединение установлено", "Тест соединения", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }

    private void fillFieldsFromDBSettings(DBSettings s) {
        edHost.setText(s.getDbHost() != null ? s.getDbHost() : "");
        cbDBName.setSelectedItem(s.getDbName() != null ? s.getDbName() : "");
        edLogin.setText(s.getDbLogin() != null ? s.getDbLogin() : "");
        edPWD.setText(s.getDbPassword() != null ? s.getDbPassword() : "");
        if (s.getDbLogin() != null && !s.getDbLogin().trim().equals("")) {
            rbLogin.setSelected(true);
        } else {
            rbWindowsNt.setSelected(true);
        }
        edLogin.setEnabled(rbLogin.isSelected());
        edPWD.setEnabled(rbLogin.isSelected());
    }

    private void decryptFromFile(File file) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        if (file != null && file.exists()) {
            DBSettings s = DBSettings.decryptFromFile(file);
            fillFieldsFromDBSettings(s);
        }
    }

    private DBSettings getCurrentDBSettings() {
        DBSettings s = null;
        if (rbWindowsNt.isSelected()) {
            s = new DBSettings(edHost.getText().trim(), cbDBName.getSelectedItem() != null ? cbDBName.getSelectedItem().toString().trim() : "");
        } else {
            s =
                    new DBSettings(edHost.getText().trim(), cbDBName.getSelectedItem() != null ? cbDBName.getSelectedItem().toString().trim() : "",
                        edLogin.getText().trim(), new String(edPWD.getPassword()));
        }
        return s;
    }

    private void encryptToFile(File file) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        DBSettings.encryptToFile(getCurrentDBSettings(), file);
    }

    protected void save(File file) {
        try {
            encryptToFile(file);
            setVisible(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Ошибка сохранения файла", JOptionPane.ERROR_MESSAGE);
        }

    }

}
