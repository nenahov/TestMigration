package ru.crystals.scales;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import ru.crystals.config.PanelOther;

public class MainScales extends JPanel {

    private static final long serialVersionUID = 1L;

    public MainScales() {

        setLayout(new BorderLayout(0, 0));

        JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
        add(tabbedPane, BorderLayout.CENTER);

        JPanel pnMain = new PanelMain();
        tabbedPane.addTab("Основные", null, pnMain, null);

        JPanel pnSet5 = new PanelDB();
        tabbedPane.addTab("БД", null, pnSet5, null);
        {
            JPanel pn = new PanelOther("ACLAS_");
            tabbedPane.addTab("ACLAS", null, pn, null);
        }
        {
            JPanel pn = new PanelOther("BIZERBABS_");
            tabbedPane.addTab("BIZERBA BS", null, pn, null);
        }
        {
            JPanel pn = new PanelOther("BIZERBAGXNET_");
            tabbedPane.addTab("BIZERBA GXNET", null, pn, null);
        }
        {
            JPanel pn = new PanelOther("DIBAL_");
            tabbedPane.addTab("DIBAL", null, pn, null);
        }

        {
            JPanel pn = new PanelOther("DIGI_TCP_");
            tabbedPane.addTab("DIGI TCP", null, pn, null);
        }
        {
            JPanel pn = new PanelOther("DIGI_FTP_");
            tabbedPane.addTab("DIGI FTP", null, pn, null);
        }
        {
            JPanel pn = new PanelOther("DIGI_");
            tabbedPane.addTab("DIGI", null, pn, null);
        }

        {
            JPanel pn = new PanelOther("SHTRIH_");
            tabbedPane.addTab("SHTRIH", null, pn, null);
        }
        {
            JPanel pn = new PanelOther("TOLEDO_");
            tabbedPane.addTab("TOLEDO TIGER", null, pn, null);
        }

        JPanel pnOther = new PanelOther();
        tabbedPane.addTab("Остальные", null, pnOther, null);
    }

}
