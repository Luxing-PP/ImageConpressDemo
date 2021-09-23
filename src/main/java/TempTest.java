import util.JpegReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class TempTest {
    public static void main(String[] args) {
        //C:\Users\ASUS\Desktop\测试文件夹\ErrorJPEG.jpg
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String imgPath=null;

        try{
            imgPath = bf.readLine();
            JpegReader jpegReader = new JpegReader();

            BufferedImage bufferedImage =  jpegReader.readImage(new File(imgPath));

            ImageIO.write(bufferedImage,"jpg",new File("OutPutImage"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
