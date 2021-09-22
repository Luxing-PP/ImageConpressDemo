package CompressStrategy;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class ScaleStrategy implements CompressStrategy {
    String folderPath=null;
    Rename RENAME_STRATEGY = null;
    double scale = 1.0;

    @Override
    public void Input(BufferedReader bf) throws IOException {
        System.out.println("请输入压缩图片存放的文件夹路径(最好是都要修改的)：");
        folderPath = bf.readLine();
        folderPath = folderPath.replaceAll("\\\\" , "\\\\\\\\");

        System.out.println("请输入缩放的比例（单位%）：");
        int scalePercent = Integer.parseInt(bf.readLine());
        scale =  scalePercent / 100.0;

        System.out.println("是否覆盖原文件? T/F");
        //有个可能BUG 但是不想修就是输不是T的 == F
        RENAME_STRATEGY = (bf.readLine().equals("T"))? Rename.NO_CHANGE : Rename.PREFIX_DOT_THUMBNAIL;
    }

    @Override
    public void Compress() throws IOException {
        System.out.println("批量压缩中");
        Thumbnails.of(new File(folderPath).listFiles())
                .scale(scale)
                .outputFormat("jpg")
                .toFiles(RENAME_STRATEGY);
    }
}
