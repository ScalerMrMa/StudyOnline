package com.xuecheng.media.config;

import com.xuecheng.media.model.dto.UploadFileResultDto;
import io.minio.MinioClient;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author MrMa
 * @version 1.0
 * @description   Minio配置
 * @data 2023-06-23 22:19
 */
@Configuration
public class MinioConfig {

   // 地址
   @Value("${minio.endpoint}")
   private String endpoint;
   // 用户名
   @Value("${minio.accessKey}")
   private String accessKey;
   // 登录密码
   @Value("${minio.secretKey}")
   private String secretKey;

   /**
    * 获取MinioClient
    * @return MinioClient
    */
   @Bean
   public MinioClient minioClient() {
      MinioClient minioClient = MinioClient.builder()
              .endpoint(endpoint)
              .credentials(accessKey, secretKey)
              .build();

      return minioClient;
   }

   @ApiOperation("上传文件")
   @RequestMapping(value = "/upload/coursefile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   public UploadFileResultDto upload(@RequestPart("filedata")MultipartFile upload) {


      return null;
   }
}

