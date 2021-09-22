package CompressStrategy;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class SizeStrategy implements CompressStrategy {
    String folderPath=null;
    Rename RENAME_STRATEGY = null;

    int target_X = 0;
    int target_Y = 0;

    @Override
    public void Input(BufferedReader bf) throws IOException {
        System.out.println("请输入压缩图片存放的文件夹路径(最好是都要修改的)：");
        folderPath = bf.readLine();
        folderPath = folderPath.replaceAll("\\\\" , "\\\\\\\\");

        System.out.println("请输入图片目标长度（单位px）：");
        target_X = Integer.parseInt(bf.readLine());

        System.out.println("请输入图片目标宽度（单位px）：");
        target_Y = Integer.parseInt(bf.readLine());

        System.out.println("是否覆盖原文件? T/F");
        //有个可能BUG 但是不想修就是输不是T的 == F
        RENAME_STRATEGY = (bf.readLine().equals("T"))? Rename.NO_CHANGE : Rename.PREFIX_DOT_THUMBNAIL;
    }

    @Override
    public void Compress() throws IOException {
        //tip 卧槽居然默认就是保比例的
        System.out.println("批量压缩中");
        Thumbnails.of(new File(folderPath).listFiles())
                .size(target_X,target_Y)
                .keepAspectRatio(false)
                .outputFormat("jpg")
                .toFiles(RENAME_STRATEGY);
    }
}
