package util;

import CompressObject.*;
import org.apache.commons.imaging.ImageReadException;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ImageHelper {
    public static List<CompressObject> ImageLoad(List<File> imgList){
        return imgList.stream().map(r->{
            if(r.isDirectory()){
                //文件夹特判
                return new EggCompressObject();
            }
            BufferedImage sourceImg = readImageFromFile(r);
            return new CompressObject(sourceImg,r.getName());
        }).collect(Collectors.toList());
    }
    public static List<CompressObject> ImageLoadWithScale(List<File> imgList,final int limit_height,final int limit_width){
        List<CompressObject> toConductList = imgList.stream().map(r->{
            if(r.isDirectory()){
                //文件夹特判
                return new EggCompressObject();
            }
            float scale=1;
            BufferedImage sourceImg = readImageFromFile(r);

            //计算能够过限制的最大scale
            int imgH = sourceImg.getHeight();
            int imgW = sourceImg.getWidth();

            if(imgH>limit_height && imgW>limit_width){
                float scaleA = limit_height / (float)imgH;
                float scaleB = limit_width  / (float)imgW;

                scale = Math.min(scaleA,scaleB);
            }else if(imgH>limit_height){
                scale = limit_height / (float)imgH;
            }else if(imgW>limit_width){
                scale = limit_width  / (float)imgW;
            }else {
                scale = (float) 1.0;
            }

            return new CompressObject(sourceImg,scale,r.getName());
        }).collect(Collectors.toList());

        return toConductList;
    }
    private static BufferedImage readImageFromFile(File r){
        BufferedImage sourceImg = null;
        try {
            System.out.println("正在读取图片"+r.getName());
            sourceImg = ImageIO.read(new FileInputStream(r));
        }catch (IIOException ccMKException){
            System.out.println("该文件为CCMK架构，转换后可能有色差："+r.getName());

            try {
                sourceImg = new JpegReader().readImage(r);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ImageReadException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return sourceImg;
    }
}
