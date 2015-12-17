import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class TestUpDown2 implements Runnable {

    private HttpURLConnection connection;//�洢����

    private int downsize = -1;//�����ļ���С,��ʼֵΪ-1

    private int downed = 0;//�ļ������ش�С,��ʼֵΪ0

    private RandomAccessFile savefile;//��¼������Ϣ�洢�ļ�

    private URL fileurl;//��¼Ҫ�����ļ��ĵ�ַ

    private DataInputStream fileStream;//��¼���ص�������

    public TestUpDown2() {
        try {
   /*��ʼ�������صĴ洢�ļ�,����ʼ��ֵ*/
            File tempfileobject = new File("C:\\200504091iis51.rar");
            if (!tempfileobject.exists()) {
    /*�ļ�����������*/
                tempfileobject.createNewFile();
            }
            savefile = new RandomAccessFile(tempfileobject, "rw");

   /*��������*/
            fileurl = new URL(
                    "\\10.25.20.105\\public\\����-��Ա����ְ\\dubbo-2.5.4C.zip");
            connection = (HttpURLConnection) fileurl.openConnection();
            connection.setRequestProperty("Range", "byte=" + this.downed + "-");

            this.downsize = connection.getContentLength();
            //System.out.println(connection.getContentLength());

            new Thread(this).start();
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("����������");
            System.exit(0);
        }
    }

    public void run() {
  /*��ʼ�����ļ�,���²��ԷǶϵ�����,���ص��ļ���������*/
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
                if (this.downsize - this.downed > 262144) {//����Ϊ���256KB�Ļ���
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
            System.out.println("run()����������!");
        }
    }

    public static void main(String[] src) throws Exception {
        new Thread(new TestUpDown2()).start();
    }
}