import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

//tip 优化 IMAGEIO 那里其实读取了两遍，但Thumb没有读取像素的功能我也表示很绝望
public class client {
    public static void main(String[] args) {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        String folderPath=null;
        Rename RENAME_STRATEGY = null;

        int limit_X = 0;
        int limit_Y = 0;

        //请保证文件夹内都是要修改的
        try{
            System.out.println("请输入压缩图片存放的文件夹(最好是都要修改的)：");
            folderPath = bf.readLine();
            folderPath = folderPath.replaceAll("\\\\" , "\\\\\\\\");

            System.out.println("请输入图片最大长度（单位px）：");
            limit_X = Integer.parseInt(bf.readLine());

            System.out.println("请输入图片最大宽度（单位px）：");
            limit_Y = Integer.parseInt(bf.readLine());

            System.out.println("是否覆盖原文件? T/F");

            //有个可能BUG 但是不想修就是输不是T的 == F
            RENAME_STRATEGY = (bf.readLine().equals("T"))? Rename.NO_CHANGE : Rename.PREFIX_DOT_THUMBNAIL;
        }catch (IOException ioe){
            ioe.printStackTrace();
            System.out.println("程序执行错误，IO问题");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("程序执行错误，问题不明");
        }

        //C:\Users\ASUS\Desktop\测试文件夹\007.jpg

        // 流式压缩
        try {
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
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("出现故障");
        }

        System.out.println("运行完毕，按任意键结束程序");
        //任意读入一个东西
        try { bf.readLine(); }catch (Exception e){ e.printStackTrace(); }
    }
}
