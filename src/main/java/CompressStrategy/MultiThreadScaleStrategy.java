package CompressStrategy;

import CompressObject.CompressObject;
import net.coobird.thumbnailator.Thumbnails;
import util.ImageHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class MultiThreadScaleStrategy implements CompressStrategy{
    private int THREAD_COUNT = 8;
    private String folderPath=null;
    private double scale = 1.0;
    private ThreadGroup compressTG;

    public MultiThreadScaleStrategy(String folderPath){
        this.compressTG = new ThreadGroup("compressTG");
        this.folderPath=folderPath;
    }

    public MultiThreadScaleStrategy(String folderPath, int thread_count){
        this.compressTG = new ThreadGroup("compressTG");
        this.folderPath=folderPath;
        this.THREAD_COUNT = thread_count;
    }

    @Override
    public void ResolvePara(BufferedReader bf) throws IOException {
        if(folderPath==null){
            //todo
            System.out.println("FolderPath In MultiThread shouldn't not be Null");
        }else {
            //do nothing
            System.out.println("请输入缩放的比例（单位%）：");
            int scalePercent = Integer.parseInt(bf.readLine());
            scale =  scalePercent / 100.0;
        }
    }

    @Override
    public void Compress() throws IOException {
        //1. 创建成品文件夹
        File dir = new File(folderPath+"\\Resize");
        if(!dir.isDirectory()){
            dir.mkdir();
        }


        List<File> imgFileList = Arrays.asList(new File(folderPath).listFiles());

        for (File imgFile : imgFileList){
            if (imgFile.getName().contains(".bat")){
                return;
            }
            //1. 限制线程数
            while (compressTG.activeCount()> THREAD_COUNT);
            new Thread(compressTG,new CompressJob(imgFile,dir,scale)).start();
        }

        //2. 等待进程执行完毕
        while (compressTG.activeCount()!=0);
    }
}

class CompressJob implements Runnable{
    private File imgFile;
    private File outputDir;
    private double scale=1.0;
    public CompressJob(File imgFile, File outputDir , double scale){
        this.imgFile = imgFile;
        this.outputDir = outputDir;
        this.scale=scale;
    }

    @Override
    public void run() {
        CompressObject srcImg = ImageHelper.ImageLoad(imgFile);

        System.out.println(String.format("线程 %s 开始压缩:%s",Thread.currentThread().getName() , srcImg.name));
        try {
            Thumbnails.of(srcImg.bufferedImage)
                    .scale(scale)
                    .outputFormat("jpg")
                    .toFile(outputDir.getAbsolutePath()+"\\"+srcImg.name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(String.format("线程 %s 压缩完毕:%s",Thread.currentThread().getName() , srcImg.name));
    }
}