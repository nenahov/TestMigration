package ru.crystals.configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class TestWD {

    public static void main(String[] args) throws Throwable {
        System.setProperty("user.dir", "D:/_Java/workspace/SetCrystalTerminal5/build");
        // File file = new File("D:/_Java/workspace/SetCrystalTerminal5/build/", "Config.conf");
        // File file = new File("D:/_Java/workspace/SetCrystalTerminal5/build/", "D:/_Java/workspace/SetLoyalty/Config.conf");
        File file = new File("Config.conf").getAbsoluteFile();
        // File file = new File("D:/_Java/workspace/SetLoyalty/Config.conf").getAbsoluteFile();
        ExchangeProperties p = new ExchangeProperties(file, "UTF-8");
        System.out.println(p.get("layoutProvider"));
        System.out.println(p.get("CashPort"));

        // restartService();
    }

    protected static void restartService() throws IOException, InterruptedException {
        String APP_SERVICE_NAME = "SETLicensesServer";
        String[] script = {"cmd.exe", "/c", "sc", "query", APP_SERVICE_NAME, "|", "find", "/C", "\"RUNNING\""};// to check whether service is running
                                                                                                               // or not

        String SERVICE_NAME = "SETLicensesServer";
        String[] scriptStart = {"cmd.exe", "/c", "sc", "start", SERVICE_NAME};// to start service

        String[] scriptStop = {"cmd.exe", "/c", "sc", "stop", SERVICE_NAME};// to stop service

        execScript(script);
        execScript(scriptStop);
        Thread.sleep(1500);
        execScript(script);
        execScript(scriptStart);
        Thread.sleep(1500);
        execScript(script);
    }

    protected static void execScript(String[] script) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(script);
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is, "cp866");
        BufferedReader br = new BufferedReader(isr);
        String line;

        System.out.printf("Output of running %s is:", Arrays.toString(script));

        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }

        isr.close();
    }
}
