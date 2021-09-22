import CompressStrategy.*;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

//tip 优化 IMAGEIO 那里其实读取了两遍，但Thumb没有读取像素的功能我也表示很绝望
//有空可以改成ENUM
public class client {
    public final static int MODE_SCALE = 1;
    public final static int MODE_SIZE = 2;
    public final static int MODE_AUTO = 3;

    public static void main(String[] args) {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        CompressStrategy compressStrategy = null;

        int MODE=0;

        //请保证文件夹内都是要修改的
        try{
            System.out.println("请选择模式: \n" +
                    "1 (批量比例缩放)\n" +
                    "2 (强制缩放到指定长X宽，也就是不等比)\n" +
                    "3 (自动化等比缩放（旧版))");
            MODE = Integer.parseInt(bf.readLine());

            //MODE规范性检测
            if(MODE!=1 && MODE!=2 && MODE!=3){
                System.out.println("不存在属于该编号的模式："+ MODE);
                System.out.println("模式错误，按任意键结束程序");
                try { bf.readLine(); }catch (Exception e){ e.printStackTrace(); }
            }

            //Strategy Set
            switch (MODE){
                case MODE_SCALE:
                    compressStrategy = new ScaleStrategy();
                    break;
                case MODE_SIZE:
                    compressStrategy = new SizeStrategy();
                    break;
                case MODE_AUTO:
                    compressStrategy = new AutoStrategy();
                    break;
                default:
                    System.out.println("不存在属于该编号的模式："+ MODE);
                    System.out.println("模式错误，按任意键结束程序");
                    try { bf.readLine(); }catch (Exception e){ e.printStackTrace(); }
                    return;
            }

            //输入
            compressStrategy.Input(bf);

            //压缩
            compressStrategy.Compress();

        }catch (IOException ioe){
            ioe.printStackTrace();
            System.out.println("程序执行错误，IO问题");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("程序执行错误，问题不明");
        }

        //C:\Users\ASUS\Desktop\测试文件夹\007.jpg


        System.out.println("运行完毕，按任意键结束程序");
        //任意读入一个东西
        try { bf.readLine(); }catch (Exception e){ e.printStackTrace(); }
    }
}
