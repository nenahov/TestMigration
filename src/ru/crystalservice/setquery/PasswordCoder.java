package ru.crystalservice.setquery;

public class PasswordCoder {
    public static String encodePassword(String value) {
        StringBuilder sb = new StringBuilder();

        try {
	        for (int i = 0; i < value.length(); i++) {
	        	String t = new String("" + value.charAt(i));
	        	byte[] ta = t.getBytes("Windows-1251");
	            byte b = ta[0];
	            byte nb = (byte)((b ^ 0x05) - i - 1);
	            ta = new byte[1];
	            ta[0] = nb;
	            char nc = new String(ta, "Windows-1251").charAt(0);
	            sb.append(nc);
	        }
        }
        catch (Exception e) {
        	
        }

        return sb.toString();
    }

    public static String decodePassword(String value) {
        StringBuilder sb = new StringBuilder();

        try {
	        for (int i = 0; i < value.length(); i++) {
	        	String t = new String("" + value.charAt(i));
	        	byte[] ta = t.getBytes("Windows-1251");
	            byte b = ta[0];
	            byte nb = (byte)((b + i + 1) ^ 0x05);
	            ta = new byte[1];
	            ta[0] = nb;
	            char nc = new String(ta, "Windows-1251").charAt(0);
	            sb.append(nc);
	        }
        }
        catch (Exception e) {
        	
        }

        return sb.toString();
    }
    
    public static void main(String[] args) throws Exception {
    	System.out.println(encodePassword("mssql"));
    	System.out.println(decodePassword(encodePassword("Привет")));
    }
}
