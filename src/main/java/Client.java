import CompressStrategy.*;
import java.io.*;

public class Client {
    public final static int MODE_SCALE = 1;
    public final static int MODE_SIZE = 2;
    public final static int MODE_AUTO = 3;
    public final static int MULTI_THREAD_MODE_SCALE = 4;
    public static String FOLDER_PATH=".\\";

    public static void main(String[] args) {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        CompressStrategy compressStrategy = null;

        int MODE=0;

        if(args.length!=0){
            MODE = Integer.parseInt(args[0]);
            FOLDER_PATH = args[1];
        }

        //请保证文件夹内都是要修改的
        try{
            MODE = (MODE==0)?resolveMode(bf):MODE;

            //Strategy Set
            switch (MODE){
                case MODE_SCALE:
                    compressStrategy = new ScaleStrategy();
                    break;
                case MODE_SIZE:
                    compressStrategy = new SizeStrategy();
                    break;
                case MODE_AUTO:
                    compressStrategy = new AutoFixStrategy();
                    break;
                case MULTI_THREAD_MODE_SCALE:
                    compressStrategy = new MultiThreadScaleStrategy(FOLDER_PATH);
                    break;
                default:
                    System.out.println("不存在属于该编号的模式："+ MODE);
                    System.out.println("模式错误，按任意键结束程序");
                    try { bf.readLine(); }catch (Exception e){ e.printStackTrace(); }
                    return;
            }

            //设定参数
            compressStrategy.ResolvePara(bf);

            //执行压缩
            compressStrategy.Compress();

        }catch (IOException ioe){
            ioe.printStackTrace();
            System.out.println("程序执行错误，IO问题");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("程序执行错误，问题不明");
        }

        System.out.println("运行完毕，按任意键结束程序");
        try { bf.readLine(); }catch (Exception e){ e.printStackTrace(); }
    }

    private static int resolveMode(BufferedReader bf) throws IOException {
        System.out.println("请选择模式: \n" +
                "1 (批量比例缩放)\n" +
                "2 (批量强制缩放)\n" +
                "3 (批量适应缩放（旧版))");
        int MODE = Integer.parseInt(bf.readLine());

        //MODE规范性检测
        if(MODE!=1 && MODE!=2 && MODE!=3){
            System.out.println("不存在属于该编号的模式："+ MODE);
            System.out.println("模式错误，按任意键结束程序");
            try { bf.readLine(); }catch (Exception e){ e.printStackTrace(); }
        }

        return MODE;
    }
}

