package CompressStrategy;

import CompressObject.CompressObject;
import net.coobird.thumbnailator.Thumbnails;
import util.ImageHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SizeStrategy implements CompressStrategy {
    String folderPath=null;

    int target_X = 0;
    int target_Y = 0;

    @Override
    public void ResolvePara(BufferedReader bf) throws IOException {
        System.out.println("请输入压缩图片存放的文件夹路径(最好是都要修改的)：");
        folderPath = bf.readLine();
        folderPath = folderPath.replaceAll("\\\\" , "\\\\\\\\");

        System.out.println("请输入图片目标长度（单位px）：");
        target_X = Integer.parseInt(bf.readLine());

        System.out.println("请输入图片目标宽度（单位px）：");
        target_Y = Integer.parseInt(bf.readLine());
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
                    .size(target_X,target_Y)
                    .keepAspectRatio(false)
                    .outputFormat("jpg")
                    .toFile(dir.getAbsolutePath()+"\\"+srcImg.name);
        }
    }
}
