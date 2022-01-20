package CompressObject;

import java.awt.image.BufferedImage;


public class CompressObject {
    public BufferedImage bufferedImage;
    public float scale = (float) 1.0;
    public String name=null;

    public CompressObject(){}

    public CompressObject(BufferedImage bufferedImage, float scale , String name){
        this.bufferedImage = bufferedImage;
        this.scale = scale;
        this.name = name;
    }

    public CompressObject(BufferedImage bufferedImage, String name){
        this.bufferedImage = bufferedImage;
        this.name = name;
    }

}
