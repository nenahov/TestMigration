package ru.crystals.config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class DBSettings {

    public static final String PARAM_DRIVERNAME = "driverName";
    public static final String PARAM_HOST = "dbHost";
    public static final String PARAM_NAME = "dbName";
    public static final String PARAM_LOGIN = "dbLogin";
    public static final String PARAM_PASSWORD = "dbPassword";

    private String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private String dbHost = "localhost";
    private String dbName = "SES";
    private String dbLogin = null;
    private String dbPassword = null;

    public DBSettings() {
        this("localhost", "SES");
    }

    public DBSettings(String dbHost, String dbName) {
        this(dbHost, dbName, null, null);
    }

    public DBSettings(String dbHost, String dbName, String dbLogin, String dbPassword) {
        super();
        this.dbHost = dbHost;
        this.dbName = dbName;
        this.dbLogin = dbLogin;
        this.dbPassword = dbPassword;
    }

    public static void encryptToFile(DBSettings dBSettings, File file) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
        IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        // @formatter:off
		byte[] input = (((dBSettings.getDbHost() != null) ? PARAM_HOST + "=" + dBSettings.getDbHost() + "\r\n" : "") 
			+ ((dBSettings.getDbName() != null) ? PARAM_NAME + "=" + dBSettings.getDbName() + "\r\n" : "") 
			+ ((dBSettings.getDbLogin() != null) ? PARAM_LOGIN + "=" + dBSettings.getDbLogin() + "\r\n" : "")
			+ ((dBSettings.getDbPassword() != null) ? PARAM_PASSWORD + "=" + dBSettings.getDbPassword() + "\r\n" : "") 
			+ "data=" + sdf.format(new Date()) + "\r\n" 
			+ "END").getBytes();
		// @formatter:on
        byte[] keyBytes = new byte[16];
        for (int i = 0; i < keyBytes.length; i++) {
            keyBytes[i] = (byte) ((i + 37) * 31);
        }

        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        ByteArrayInputStream bIn = new ByteArrayInputStream(input);
        CipherInputStream cIn = new CipherInputStream(bIn, cipher);
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();

        int ch;
        while ((ch = cIn.read()) >= 0) {
            bOut.write(ch);
        }

        byte[] cipherText = bOut.toByteArray();

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(cipherText);
        fos.flush();
        fos.close();
        cIn.close();
    }

    public static DBSettings decryptFromFile(File file) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        DBSettings result = new DBSettings();
        byte[] keyBytes = new byte[16];
        for (int i = 0; i < keyBytes.length; i++) {
            keyBytes[i] = (byte) ((i + 37) * 31);
        }

        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        CipherOutputStream cOut = new CipherOutputStream(bOut, cipher);
        FileInputStream fis = new FileInputStream(file);
        byte[] b = new byte[1];
        while (fis.available() > 0) {
            fis.read(b);
            cOut.write(b);
        }
        fis.close();
        cOut.close();
        String s = new String(bOut.toByteArray());
        // System.out.println(s);

        result.setDbHost(getStringParamValue(s, PARAM_HOST));
        result.setDbName(getStringParamValue(s, PARAM_NAME));
        result.setDbLogin(getStringParamValue(s, PARAM_LOGIN));
        result.setDbPassword(getStringParamValue(s, PARAM_PASSWORD));

        return result;
    }

    private static String getStringParamValue(String params, String paramName) {
        String result = null;
        Pattern p = Pattern.compile(paramName + "=(.[^\r\n]*)\r\n", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(params);
        if (m.find()) {
            result = m.group(1);
        }
        return result;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDbHost() {
        return dbHost;
    }

    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbLogin() {
        return dbLogin;
    }

    public void setDbLogin(String dbLogin) {
        this.dbLogin = dbLogin;
    }

    public String getDbPassword() {
        return dbPassword != null ? dbPassword : "";
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public static void main(String[] args) {
        System.out.println(getStringParamValue("dbHost=localhost\r\ndbName=SES\r\ndbLogin=sa\r\nEND", PARAM_LOGIN));
    }
}
