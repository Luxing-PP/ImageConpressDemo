package CompressStrategy;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AutoStrategy implements CompressStrategy {
    String folderPath=null;
    Rename RENAME_STRATEGY = null;

    int limit_X = 0;
    int limit_Y = 0;

    @Override
    public void Input(BufferedReader bf) throws IOException {
        System.out.println("请输入压缩图片存放的文件夹路径(最好是都要修改的)：");
        folderPath = bf.readLine();
        folderPath = folderPath.replaceAll("\\\\" , "\\\\\\\\");

        System.out.println("请输入图片最大长度（单位px）：");
        limit_X = Integer.parseInt(bf.readLine());

        System.out.println("请输入图片最大宽度（单位px）：");
        limit_Y = Integer.parseInt(bf.readLine());

        System.out.println("是否覆盖原文件? T/F");

        //有个可能BUG 但是不想修就是输不是T的 == F
        RENAME_STRATEGY = (bf.readLine().equals("T"))? Rename.NO_CHANGE : Rename.PREFIX_DOT_THUMBNAIL;
    }

    @Override
    public void Compress() throws IOException {
        //tip asList
        List<File> imgList = Arrays.asList(new File(folderPath).listFiles());

        final int limit_height = limit_Y;
        final int limit_width = limit_X;

        List<CompressObject> toConductList = imgList.stream().map(r->{
            float scale=1;

            //计算能够过限制的最大scale
            try {
                BufferedImage sourceImg = ImageIO.read(new FileInputStream(r));
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
            } catch (IOException e) {
                e.printStackTrace();
            }

            return new CompressObject(r,scale);
        }).collect(Collectors.toList());


        //压缩
        for (CompressObject srcImg : toConductList){
            System.out.println("开始压缩:"+srcImg.imgFile.getName());
            Thumbnails.of(srcImg.imgFile)
                    .scale(srcImg.scale)
                    .outputFormat("jpg")
                    .toFiles(RENAME_STRATEGY);
            System.out.println("压缩完毕，压缩比:"+srcImg.scale);
        }
    }
}
