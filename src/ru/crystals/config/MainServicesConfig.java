package ru.crystals.config;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ru.crystals.bridge.MainBridge;
import ru.crystals.cashserver.MainCashServer;
import ru.crystals.configuration.ExchangeProperties;
import ru.crystals.configuration.Settings;
import ru.crystals.configuration.SettingsValues;
import ru.crystals.loyalty.MainLoyalty;
import ru.crystals.printlabel.MainPrintLabel;
import ru.crystals.scales.MainScales;
import ru.crystals.scheduler.MainScheduler;
import ru.crystals.transport.MainExchanger;

public class MainServicesConfig {

    private static final String LAF = "Windows";

    private JFrame frame;
    private JList list;
    private JPanel pnParent = null;
    private JButton btnSave;
    private File serviceDir;
    private PanelService pnService;
    private JSeparator separator;

    public static void main(String[] args) {
        for (LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
            if (LAF.equals(laf.getName())) {
                try {
                    UIManager.setLookAndFeel(laf.getClassName());
                } catch (Exception e) {
                    //
                }
            }
        }
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    MainServicesConfig window = new MainServicesConfig();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MainServicesConfig() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/res/ico.png")));
        frame.setSize(new Dimension(800, 500));
        frame.setTitle("Конфигурация служб (Build id: 28.02.2014)");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JSplitPane splitPane = new JSplitPane();
        frame.getContentPane().add(splitPane, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane();
        splitPane.setLeftComponent(scrollPane);

        list = new JList();
        list.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                selectService();
            }
        });

        list.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    selectService();
                }
            }
        });
        list.setPreferredSize(new Dimension(120, 0));
        list.setFont(new Font("Tahoma", Font.PLAIN, 16));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane.setViewportView(list);

        JPanel pnMain = new JPanel();
        splitPane.setRightComponent(pnMain);
        pnMain.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        FlowLayout flowLayout_1 = (FlowLayout) panel.getLayout();
        flowLayout_1.setAlignment(FlowLayout.LEFT);
        pnMain.add(panel, BorderLayout.SOUTH);

        btnSave = new JButton("Save config");
        btnSave.setIcon(new ImageIcon(MainServicesConfig.class.getResource("/res/Save.png")));
        btnSave.setEnabled(false);
        btnSave.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                SettingsValues.saveAll();
            }
        });

        pnService = new PanelService();
        panel.add(pnService);

        separator = new JSeparator();
        separator.setPreferredSize(new Dimension(3, 20));
        separator.setOrientation(SwingConstants.VERTICAL);
        panel.add(separator);
        panel.add(btnSave);

        pnParent = new JPanel();
        pnMain.add(pnParent, BorderLayout.CENTER);
        pnParent.setLayout(new BorderLayout(0, 0));

        fillServices();
    }

    private void fillServices() {
        File dir = new File(".").getAbsoluteFile().getParentFile().getParentFile().getParentFile();
        serviceDir = new File(dir, "Service");
        if (serviceDir.exists()) {
            File[] temp = serviceDir.listFiles();
            final List<String> result = new ArrayList<String>();
            String excluded =
                    "CashMove CashServ FileTracking KassServer LicensesServer MetiExchange SesBank SETSuperMag SMConverter CentrumExchange CentrumManager CentrumManagerImport CentrumManagerExport CentrumAuth CentrumScheduler"
                        .toLowerCase();
            for (File file : temp) {
                if (file.isDirectory()) {
                    String name = file.getName();
                    if (!excluded.contains(name.toLowerCase())) {
                        result.add(name);
                    }
                }
            }

            list.setModel(new AbstractListModel() {

                private static final long serialVersionUID = 1L;

                List<String> values = result;

                @Override
                public int getSize() {
                    return values.size();
                }

                @Override
                public Object getElementAt(int index) {
                    return values.get(index);
                }
            });
        } else {
            JOptionPane.showMessageDialog(frame, "Поместите каталог с программой в папку Utils", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void selectService() {
        if (pnParent != null) {
            pnParent.removeAll();
            pnService.setServiceInfo(null, null);
            if (list.getSelectedValue() != null) {
                String dirName = list.getSelectedValue().toString().toLowerCase();

                String serviceName = null;
                File wrapper = new File(serviceDir, dirName + "/wrapper.conf");
                if (wrapper.exists()) {
                    ExchangeProperties wr = new ExchangeProperties(wrapper, "UTF-8");
                    serviceName = wr.get("wrapper.ntservice.name");
                }
                String confName = "config.conf";
                String logFile = "logs/main.log";
                String encoding = "UTF-8";
                if (dirName.contains("cashserver")) {
                    confName = "CashServer.conf";
                    encoding = "cp1251";
                } else if (dirName.contains("bank")) {
                    confName = "BankServer.conf";
                    encoding = "cp1251";
                } else if (dirName.contains("sesgpls")) {
                    confName = "GPLSServer.ini";
                    encoding = "cp1251";
                } else if (dirName.contains("dbservice")) {
                    encoding = "cp1251";
                } else if (dirName.contains("printlabel")) {
                    confName = "SETLabel.conf";
                    encoding = "cp1251";
                    serviceName = "SETPrintLabel";
                } else if (dirName.contains("scales")) {
                    confName = "SetScales.conf";
                }

                pnService.setServiceInfo(serviceName, logFile);

                File conf = new File(serviceDir, dirName + "/" + confName);

                if (!conf.exists()) {
                    JOptionPane.showMessageDialog(null, "Не найден файл " + conf.getAbsolutePath(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                } else {
                    Settings.Instance.loadProperties(conf, encoding);
                    if (dirName.contains("loyalty")) {
                        pnParent.add(new MainLoyalty());
                    } else if (dirName.contains("cashserver")) {
                        pnParent.add(new MainCashServer());
                    } else if (dirName.contains("setbridge")) {
                        pnParent.add(new MainBridge());
                    } else if (dirName.contains("transport")) {
                        pnParent.add(new MainExchanger());
                    } else if (dirName.contains("setreport")) {
                        pnParent.add(new MainExchanger("-1"));
                    } else if (dirName.contains("scale")) {
                        pnParent.add(new MainScales());
                    } else if (dirName.contains("printlabel")) {
                        pnParent.add(new MainPrintLabel());
                    } else if (dirName.contains("scheduler") || dirName.contains("dbservice")) {
                        pnParent.add(new MainScheduler());
                    } else {
                        pnParent.add(new PanelOther());
                    }
                }
                btnSave.setEnabled(true);
            } else {
                btnSave.setEnabled(false);
            }
            pnParent.revalidate();
            pnParent.repaint();
        }
    }
}
