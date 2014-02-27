package ru.crystals.configuration;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

public class Tailog implements Runnable {

    boolean execute = true;
    BufferedInputStream reader;
    BufferedReader in;
    File file;
    RandomAccessFile raf;
    InputStreamReader inputStreamReader;
    BufferedReader bufferedReader;
    PrintWriter pwriter;

    public Tailog() {
    }

    public Tailog(long numLine, String fileName, PrintWriter pw) {
        try {
            file = new File(fileName);

            // start read line by line
            inputStreamReader = new InputStreamReader(new FileInputStream(fileName), "windows-1251");
            bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                System.out.println(line);
                line = bufferedReader.readLine();
            }
            pwriter = pw;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (execute) {
            try {
                String line = bufferedReader.readLine();
                if (line != null) {
                    pwriter.println(line);
                    pwriter.flush();
                } else {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        execute = false;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        PrintWriter pw = new PrintWriter(System.out, true);
        String numLineStr = "";
        String fileName = "";
        try {
            numLineStr = "7"; // args[0];
            fileName = "D:/_Java/workspace/SetLoyalty/logs/Main.log";// args[1];
        } catch (Exception e) {
            e.printStackTrace();
        }

        long numLine = 0;
        try {
            numLine = Long.parseLong(numLineStr);
            File file = new File(fileName);
            if (file.exists()) {
                Tailog tailog = new Tailog(numLine, fileName, pw);
                tailog.run();
            } else {
                pw.println("Please specify correct file name: eg C:/server/log/server.log");
            }
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("Please specify correct command: java -jar Tailog.jar <number of lines> <file name>");
        }
    }
}
