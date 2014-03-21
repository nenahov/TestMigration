package ru.crystals.loyalty;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import ru.crystals.configuration.SettingsValues;

class PanelBrandLoyalty extends JPanel {

    private static final long serialVersionUID = 1L;

    public PanelBrandLoyalty() {
        setLayout(new MigLayout("", "[][grow][]", "[]"));

        JLabel label = new JLabel("Адрес веб-сервиса:");
        add(label, "cell 0 0,alignx trailing");

        JTextField edWS = new JTextField();
        add(edWS, "cell 1 0,growx");
        edWS.setColumns(10);

        JButton btDefault = new JButton("");
        add(btDefault, "cell 2 0");

        SettingsValues.add(edWS, btDefault, "brandloyalty.ws_address", "http://127.0.0.1:5302/soa-infra/services/default/bl_bpel_pos/bl_bpel_pos");
    }

}
