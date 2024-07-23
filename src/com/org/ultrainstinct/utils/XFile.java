package com.org.ultrainstinct.utils;

import java.awt.Container;
import java.awt.Image;
import java.awt.MediaTracker;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;

public class XFile {
    
   public static Image getAppIcon() {
    URL url = XFile.class.getResource("/ps/icon");
    if (url != null) {
        return new ImageIcon(url).getImage();
    } else {
        return null; // Hoặc trả về một hình ảnh mặc định
    }
}
    
   
    public static void save(File src){
        File dst = new File("logos", src.getName());
        if(!dst.getParentFile().exists()){
            dst.getParentFile().mkdirs();
        }
        try {
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(dst.getAbsolutePath());
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } 
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
     
    public static ImageIcon read(String fileName) {
    File path = new File("logos", fileName);
    if (path.exists()) {
        try {
            return new ImageIcon(path.toURI().toURL());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    } else {
        return null; // Hoặc trả về một hình ảnh mặc định
    }
}
    
    
    public static ImageIcon createImageIcon(URL location) {
    if (location == null) {
        return null;
    }

    ImageIcon icon = new ImageIcon(location);
    MediaTracker tracker = new MediaTracker(new Container());
    tracker.addImage(icon.getImage(), 0);
    try {
        tracker.waitForID(0);
    } catch (InterruptedException e) {
        return null;
    }
    icon.setDescription(location.toExternalForm());
    return icon;
}
}