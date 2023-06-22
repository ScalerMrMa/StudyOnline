package xuecheng;

import io.minio.*;
import io.minio.errors.*;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.util.DigestUtils;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author MrMa
 * @version 1.0
 * @description
 * @data 2023-06-22 22:41
 */


public class MinioTest {

   static MinioClient minioClient =
           MinioClient.builder()
                   .endpoint("http://192.168.101.65:9000")
                   .credentials("minioadmin", "minioadmin")
                   .build();

   @Test
   public void test_upload() throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
      // 上传文件的参数信息
      UploadObjectArgs testbuket = UploadObjectArgs.builder()
              .bucket("testbuket")
              .filename("C:\\Users\\MrMa\\Videos\\test.mp4") // 指定本地文件
              .object("test.map4") // 对象名
              .build();

      // 上传文件
      minioClient.uploadObject(testbuket);
   }

   @Test
   public void test_delete() throws Exception {

      // RemoveObjectArgs
      RemoveObjectArgs testbuket = RemoveObjectArgs.builder()
              .bucket("testbuket")
              .object("test.map4")
              .build();

      // 上传文件
      minioClient.removeObject(testbuket);
   }

   @Test
   public void test_getFile() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

      GetObjectArgs testbuket = GetObjectArgs.builder()
              .bucket("testbuket")
              .object("test.map4")
              .build();

      FilterInputStream fileInputStream = minioClient.getObject(testbuket);

      FileOutputStream fileOutputStream = new FileOutputStream(new File("D:\\test.mp4"));

      IOUtils.copy(fileInputStream, fileOutputStream);

      String s = DigestUtils.md5DigestAsHex(fileInputStream);
      String s1 = DigestUtils.md5DigestAsHex(new FileInputStream(new File("D:\\test.mp4")));

      if (s.equals(s1)) {
         System.out.println("true");
      }else {
         System.out.println("false");
      }
   }
}
