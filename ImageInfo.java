package java_game;

import java.io.Serializable;

public class ImageInfo implements Serializable{
    private String filePath;

    public ImageInfo(String filePath) {
        this.filePath = filePath;
    }
    
    public String getFilePath() {
        return filePath;
    }
    
}
