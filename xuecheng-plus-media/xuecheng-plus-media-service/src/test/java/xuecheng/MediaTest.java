package xuecheng;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author MrMa
 * @version 1.0
 * @description 测试大文件的上传方法
 * @data 2023-08-15 22:46
 */
public class MediaTest {

   // 分块测试
   @Test
   public void testChunk() throws IOException {
      // 源文件
      File file = new File("D:\\test.mp4");
      // 分块文件存储路径
      String chunkFilePath = "D:\\test\\";
      // 分块文件大小
      int chunkSize = 1024 * 1024 * 1; // 1M大小
      // 分块文件个数
      int chunkNum = (int) Math.ceil(file.length() / chunkSize);
      // 使用流从文件读数据，向分块文件中写数据
      RandomAccessFile readFile = new RandomAccessFile(file, "r");
      // 缓存区
      byte[] bytes = new byte[1024];
      for (int i = 0; i < chunkNum; i++) {
         File chunkFile = new File(chunkFilePath + i);
         // 分块文件写入流
         RandomAccessFile readWrite_file = new RandomAccessFile(chunkFile, "rw");
         int len = -1;
         while ((len = readFile.read(bytes)) != -1) {
            readWrite_file.write(bytes, 0,len);
            if (readWrite_file.length() >= chunkSize) {
               break;
            }
         }
         readWrite_file.close();
      }
      readFile.close();
   }

   // 将分块进行合并
   @Test
   public void testMerge() throws IOException {
      //块文件目录
      File chunkFolder = new File("D:\\test\\");
      //原始文件
      File originalFile = new File("D:\\test.mp4");
      //合并文件
      File mergeFile = new File("D:\\test01.mp4");
      if (mergeFile.exists()) {
         mergeFile.delete();
      }
      //创建新的合并文件
      mergeFile.createNewFile();
      //用于写文件
      RandomAccessFile raf_write = new RandomAccessFile(mergeFile, "rw");
      //指针指向文件顶端
      raf_write.seek(0);
      //缓冲区
      byte[] b = new byte[1024];
      //分块列表
      File[] fileArray = chunkFolder.listFiles();
      // 转成集合，便于排序
      List<File> fileList = Arrays.asList(fileArray);
      // 从小到大排序
      Collections.sort(fileList, new Comparator<File>() {
         @Override
         public int compare(File o1, File o2) {
            return Integer.parseInt(o1.getName()) - Integer.parseInt(o2.getName());
         }
      });
      //合并文件
      for (File chunkFile : fileList) {
         RandomAccessFile raf_read = new RandomAccessFile(chunkFile, "rw");
         int len = -1;
         while ((len = raf_read.read(b)) != -1) {
            raf_write.write(b, 0, len);

         }
         raf_read.close();
      }
      raf_write.close();

      //校验文件
      try (

              FileInputStream fileInputStream = new FileInputStream(originalFile);
              FileInputStream mergeFileStream = new FileInputStream(mergeFile);

      ) {
         //取出原始文件的md5
         String originalMd5 = DigestUtils.md5Hex(fileInputStream);
         //取出合并文件的md5进行比较
         String mergeFileMd5 = DigestUtils.md5Hex(mergeFileStream);
         if (originalMd5.equals(mergeFileMd5)) {
            System.out.println("合并文件成功");
         } else {
            System.out.println("合并文件失败");
         }

      }


   }
}
