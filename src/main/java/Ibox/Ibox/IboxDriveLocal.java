package Ibox.Ibox;

import java.io.File;
import java.util.HashMap;

public class IboxDriveLocal {
    HashMap<String,String> map = new HashMap<>();
    //fileName,filePath
    public HashMap<String,String> listFilesForFolder(File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                map.put(fileEntry.getName(),fileEntry.getPath());
            }
        }
//        System.out.println("Local file size: "+map.size());
        return map;
    }
}
