package CompressStrategy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EggCompressObject extends CompressObject {
    private static int count = 0;

    public EggCompressObject() {
        super();
        this.name = "怎么有人在图片文件夹里放文件夹啊~_"+count;
        try {
            this.bufferedImage = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("egg.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        count++;
    }

    public EggCompressObject(BufferedImage bufferedImage, float scale, String name) {
        super(bufferedImage, scale, name);
    }

    public EggCompressObject(BufferedImage bufferedImage, String name) {
        super(bufferedImage, name);
    }
}
