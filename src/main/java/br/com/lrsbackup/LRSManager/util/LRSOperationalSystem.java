package br.com.lrsbackup.LRSManager.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class LRSOperationalSystem {
		 
    private static String OS = System.getProperty("os.name").toLowerCase();
 
    public LRSOperationalSystem() {

    }
 
    public static boolean isWindows() {
        return OS.contains("win");
    }
 
    public static boolean isMac() {
        return OS.contains("mac");
    }
 
    public static boolean isUnix() {
        return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
    }
 
    public static boolean isSolaris() {
        return OS.contains("sunos");
    }

    public static String getOS(){
        if (isWindows()) {
            return "win";
        } else if (isMac()) {
            return "osx";
        } else if (isUnix()) {
            return "uni";
        } else if (isSolaris()) {
            return "sol";
        } else {
            return "err";
        }
    }
    
	private String getOnlyFileName(String originalFileName) {
		String delimiter = this.getFilePathSeparator();
		String onlyFileName = new String();
		LRSOperationalSystem mySO = new LRSOperationalSystem();
		
		
		int initialPoint = originalFileName.lastIndexOf(delimiter);
		int finalPoint = originalFileName.length();
		
		onlyFileName = originalFileName.substring(initialPoint,finalPoint);	
		onlyFileName = onlyFileName.replace(delimiter,"");
		
		return onlyFileName;
	}
	
	public byte[] getObjectFile(String filePath) {

        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;

        try {
            File file = new File(filePath);
            bytesArray = new byte[(int) file.length()];
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytesArray;
    }
	
	public String getFilePathSeparator() {
		String delimiter = new String();
		
		if (this.isWindows()) {
			delimiter = "\\\\";
		} else {
			delimiter = "/";
		}
		
		return delimiter;
	}
	
}
