package xuecheng;

import com.xuecheng.base.util.Mp4VideoUtil;
import org.junit.jupiter.api.Test;

/**
 * @author MrMa
 * @version 1.0
 * @description
 * @data 2023-08-23 22:05
 */
public class ProcessTest {

   @Test
   public void TransformMedia(){
      String ffmpeg_path = "D:\\software\\FFmpeg\\bin\\ffmpeg.exe";//ffmpeg的安装位置
      //源avi视频的路径
      String video_path = "D:\\test\\file\\my.avi";
      //转换后mp4文件的名称
      String mp4_name = "nacos01.mp4";
      //转换后mp4文件的路径
      String mp4_path = "D:\\test\\file\\nacos01.mp4";
      //创建工具类对象
      Mp4VideoUtil videoUtil = new Mp4VideoUtil(ffmpeg_path,video_path,mp4_name,mp4_path);
      //开始视频转换，成功将返回success
      String s = videoUtil.generateMp4();
      System.out.println(s);
   }
}
