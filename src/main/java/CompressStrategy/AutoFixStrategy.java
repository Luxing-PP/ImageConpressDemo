package CompressStrategy;

import CompressObject.CompressObject;
import net.coobird.thumbnailator.Thumbnails;
import util.ImageHelper;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class AutoFixStrategy implements CompressStrategy {
    String folderPath=null;

    int limit_X = 0;
    int limit_Y = 0;

    @Override
    public void ResolvePara(BufferedReader bf) throws IOException {
        System.out.println("请输入压缩图片存放的文件夹路径(最好是都要修改的)：");
        folderPath = bf.readLine();
        folderPath = folderPath.replaceAll("\\\\" , "\\\\\\\\");

        System.out.println("请输入图片最大长度（单位px）：");
        limit_X = Integer.parseInt(bf.readLine());

        System.out.println("请输入图片最大宽度（单位px）：");
        limit_Y = Integer.parseInt(bf.readLine());
    }

    @Override
    public void Compress() throws IOException {
        List<File> imgList = Arrays.asList(new File(folderPath).listFiles());

        final int limit_height = limit_Y;
        final int limit_width = limit_X;

        List<CompressObject> toConductList = ImageHelper.ImageLoadWithScale(imgList,limit_height,limit_width);

        //创建成品文件夹
        File dir = new File(folderPath+"\\Resize");
        if(!dir.isDirectory()){
            dir.mkdir();
        }

        //压缩
        for (CompressObject srcImg : toConductList){
            System.out.println("开始压缩:"+srcImg.name);
            Thumbnails.of(srcImg.bufferedImage)
                    .scale(srcImg.scale)
                    .outputFormat("jpg")
                    .toFile(dir.getAbsolutePath()+"\\"+srcImg.name);
            System.out.println("压缩完毕，压缩比:"+srcImg.scale);
        }
    }
}
