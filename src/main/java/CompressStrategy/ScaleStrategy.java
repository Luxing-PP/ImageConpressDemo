package CompressStrategy;

import CompressObject.CompressObject;
import net.coobird.thumbnailator.Thumbnails;
import util.ImageHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ScaleStrategy implements CompressStrategy {
    String folderPath=null;
    double scale = 1.0;

    @Override
    public void ResolvePara(BufferedReader bf) throws IOException {
        System.out.println("请输入压缩图片存放的文件夹路径(最好是都要修改的)：");
        folderPath = bf.readLine();
        folderPath = folderPath.replaceAll("\\\\" , "\\\\\\\\");

        System.out.println("请输入缩放的比例（单位%）：");
        int scalePercent = Integer.parseInt(bf.readLine());
        scale =  scalePercent / 100.0;
    }

    @Override
    public void Compress() throws IOException {
        List<CompressObject> toConductList = ImageHelper.ImageLoad(Arrays.asList(new File(folderPath).listFiles()));

        //创建成品文件夹
        File dir = new File(folderPath+"\\Resize");
        if(!dir.isDirectory()){
            dir.mkdir();
        }

        for (CompressObject srcImg : toConductList){
            System.out.println("开始压缩:"+srcImg.name);
            Thumbnails.of(srcImg.bufferedImage)
                    .scale(scale)
                    .outputFormat("jpg")
                    .toFile(dir.getAbsolutePath()+"\\"+srcImg.name);
        }
    }
}
