import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class TestUpDown2 implements Runnable {

    private HttpURLConnection connection;//存储连接

    private int downsize = -1;//下载文件大小,初始值为-1

    private int downed = 0;//文加已下载大小,初始值为0

    private RandomAccessFile savefile;//记录下载信息存储文件

    private URL fileurl;//记录要下载文件的地址

    private DataInputStream fileStream;//记录下载的数据流

    public TestUpDown2() {
        try {
   /*开始创建下载的存储文件,并初始化值*/
            File tempfileobject = new File("C:\\200504091iis51.rar");
            if (!tempfileobject.exists()) {
    /*文件不存在则建立*/
                tempfileobject.createNewFile();
            }
            savefile = new RandomAccessFile(tempfileobject, "rw");

   /*建立连接*/
            fileurl = new URL(
                    "\\10.25.20.105\\public\\工具-新员工入职\\dubbo-2.5.4C.zip");
            connection = (HttpURLConnection) fileurl.openConnection();
            connection.setRequestProperty("Range", "byte=" + this.downed + "-");

            this.downsize = connection.getContentLength();
            //System.out.println(connection.getContentLength());

            new Thread(this).start();
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("构建器错误");
            System.exit(0);
        }
    }

    public void run() {
  /*开始下载文件,以下测试非断点续传,下载的文件存在问题*/
        try {
            System.out.println("begin!");
            Date begintime = new Date();
            begintime.setTime(new Date().getTime());
            byte[] filebyte;
            int onecelen;
            //System.out.println(this.connection.getInputStream().getClass().getName());
            this.fileStream = new DataInputStream(new BufferedInputStream(
                    this.connection.getInputStream()));
            System.out.println("size   =   " + this.downsize);
            while (this.downsize != this.downed) {
                if (this.downsize - this.downed > 262144) {//设置为最大256KB的缓存
                    filebyte = new byte[262144];
                    onecelen = 262144;
                } else {
                    filebyte = new byte[this.downsize - this.downed];
                    onecelen = this.downsize - this.downed;
                }
                onecelen = this.fileStream.read(filebyte, 0, onecelen);
                this.savefile.write(filebyte, 0, onecelen);
                this.downed += onecelen;
                System.out.println(this.downed);
            }
            this.savefile.close();
            System.out.println("end!");
            System.out.println(begintime.getTime());
            System.out.println(new Date().getTime());
            System.out.println(begintime.getTime() - new Date().getTime());
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("run()方法有问题!");
        }
    }

    public static void main(String[] src) throws Exception {
        new Thread(new TestUpDown2()).start();
    }
}