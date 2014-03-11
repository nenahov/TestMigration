package ru.crystals.cashagent;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import ru.crystals.config.PanelOther;

public class MainCashAgent extends JPanel {

    private static final long serialVersionUID = 1L;

    public MainCashAgent() {

        setLayout(new BorderLayout(0, 0));

        JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
        add(tabbedPane, BorderLayout.CENTER);

        JPanel pnMain = new PanelMain();
        tabbedPane.addTab("Основные", null, pnMain, null);

        {
            JPanel pn = new PanelDB();
            tabbedPane.addTab("БД", null, pn, null);
        }
        {
            JPanel pn = new PanelOther("Bonus.");
            tabbedPane.addTab("Бонусы", null, pn, null);
        }
        {
            JPanel pn = new PanelOther("Voucher.");
            tabbedPane.addTab("ПК", null, pn, null);
        }
        {
            JPanel pn = new PanelOther("ExternalCard.");
            tabbedPane.addTab("Внешние карты", null, pn, null);
        }
        {
            JPanel pn = new PanelOther("SETv10.");
            tabbedPane.addTab("SET 10", null, pn, null);
        }
        {
            JPanel pn = new PanelOther("Vingado.");
            tabbedPane.addTab("Vingado", null, pn, null);
        }
        {
            JPanel pn = new PanelOther("PinPay.");
            tabbedPane.addTab("PinPay", null, pn, null);
        }
        {
            JPanel pn = new PanelOther("GorChel.");
            tabbedPane.addTab("GorChel", null, pn, null);
        }
        {
            JPanel pn = new PanelOther("Netto.");
            tabbedPane.addTab("Netto", null, pn, null);
        }
        {
            JPanel pn = new PanelOther("SoftCase.");
            tabbedPane.addTab("SoftCase", null, pn, null);
        }
        {
            JPanel pn = new PanelOther("Printer.");
            tabbedPane.addTab("Printer", null, pn, null);
        }
        {
            JPanel pn = new PanelOther("Comarch.", "urlclm", "usecomarch", "departmentid", "partnerid");
            tabbedPane.addTab("Comarch", null, pn, null);
        }
        {
            JPanel pnOther = new PanelOther();
            tabbedPane.addTab("Остальные", null, pnOther, null);
        }

    }
}
