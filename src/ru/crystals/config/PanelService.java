package ru.crystals.config;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

public class PanelService extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField edServiceName;
    private JButton btnStart;
    private JButton btnStop;
    private long nextCheckTime = 0;
    private String logFile = null;
    private JButton btLog;

    public PanelService() {

        JLabel lbService = new JLabel("Служба:");
        add(lbService);

        edServiceName = new JTextField();
        edServiceName.setEditable(false);
        add(edServiceName);
        edServiceName.setColumns(20);

        btnStart = new JButton("");
        btnStart.setIcon(new ImageIcon(PanelService.class.getResource("/res/Start.png")));
        btnStart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                nextCheckTime = System.currentTimeMillis() + 1500;
                btnStart.setEnabled(false);
                btnStop.setEnabled(false);
                String[] scriptStart = {"cmd.exe", "/c", "sc", "start", edServiceName.getText()};// to start service
                try {
                    execScript(scriptStart);
                } catch (IOException e1) {
                    //
                }
            }
        });
        btnStart.setEnabled(false);
        add(btnStart);

        btnStop = new JButton("");
        btnStop.setIcon(new ImageIcon(PanelService.class.getResource("/res/Stop.png")));
        btnStop.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                nextCheckTime = System.currentTimeMillis() + 1500;
                btnStart.setEnabled(false);
                btnStop.setEnabled(false);
                String[] scriptStop = {"cmd.exe", "/c", "sc", "stop", edServiceName.getText()};// to stop service
                try {
                    execScript(scriptStop);
                } catch (IOException e1) {
                    //
                }
            }
        });
        btnStop.setEnabled(false);
        add(btnStop);

        btLog = new JButton("Log");
        btLog.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                showLog();
            }
        });
        btLog.setEnabled(false);
        add(btLog);

        // JButton button = new JButton("Лог");
        // button.addActionListener(new ActionListener() {
        //
        // @Override
        // public void actionPerformed(ActionEvent e) {
        // showLog();
        // }
        // });
        // add(button);
        ServiceWorker sw = new ServiceWorker();
        sw.execute();
    }

    protected void showLog() {
        new DialogViewLog(this, new File(logFile).getAbsoluteFile()).setVisible(true);
    }

    public void setServiceInfo(String serviceName, String logFile) {
        this.logFile = logFile;
        btLog.setEnabled(false);
        btnStart.setEnabled(false);
        btnStop.setEnabled(false);
        edServiceName.setText(serviceName);
    }

    class ServiceWorker extends SwingWorker<Void, String> {

        @Override
        protected Void doInBackground() throws Exception {
            while (!isCancelled()) {
                Thread.sleep(1000);
                String serviceName = edServiceName.getText();
                if (serviceName != null && !serviceName.isEmpty()) {
                    long now = System.currentTimeMillis();
                    if (now > nextCheckTime) {
                        publish(getServiceStatus(serviceName));
                    }
                }
            }
            return null;
        }

        @Override
        protected void process(List<String> chunks) {
            boolean start = false;
            boolean stop = false;
            if (chunks != null && chunks.size() > 0) {
                String status = chunks.get(chunks.size() - 1);
                if (status != null) {
                    start = status.equals("0");
                    stop = status.equals("1");
                }
            }
            btnStart.setEnabled(start);
            btnStop.setEnabled(stop);
            btLog.setEnabled(logFile != null && !logFile.isEmpty() && new File(logFile).getAbsoluteFile().exists());
        }

    }

    public String getServiceStatus(String serviceName) throws IOException {
        String[] script = {"cmd.exe", "/c", "sc", "query", serviceName, "|", "find", "/C", "\"RUNNING\""};
        return execScript(script);
    }

    protected static String execScript(String[] script) throws IOException {
        StringBuilder result = new StringBuilder();
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(script);
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is, "cp866");
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null) {
            result.append(line);
        }
        isr.close();
        return result.toString();
    }
}
