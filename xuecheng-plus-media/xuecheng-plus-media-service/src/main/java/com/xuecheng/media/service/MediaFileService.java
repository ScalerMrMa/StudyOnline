package com.xuecheng.media.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.media.model.dto.QueryMediaParamsDto;
import com.xuecheng.media.model.dto.UploadFileParamsDto;
import com.xuecheng.media.model.dto.UploadFileResultDto;
import com.xuecheng.media.model.po.MediaFiles;

/**
 * @description 媒资文件管理业务类
 * @author Mr.M
 * @date 2022/9/10 8:55
 * @version 1.0
 */
public interface MediaFileService {

 /**
  * @param pageParams          分页参数
  * @param queryMediaParamsDto 查询条件
  * @return com.xuecheng.base.model.PageResult<com.xuecheng.media.model.po.MediaFiles>
  * @description 媒资文件查询方法
  * @author Mr.M
  * @date 2022/9/10 8:57
  */
 public PageResult<MediaFiles> queryMediaFiels(Long companyId, PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto);

 /**
  * 上传文件
  *
  * @param companyId           机构ID
  * @param uploadFileParamsDto 文件信息
  * @param localFilePath       文件本地路径
  * @return UploadFileResultDto
  */
 public UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, String localFilePath);

 public MediaFiles addMediaFilesToDb(Long companyId, String fileMd5, UploadFileParamsDto uploadFileParamsDto, String bucket, String objectName);

}

/* @Autowired
 MediaFilesMapper mediaFilesMapper;

 @Autowired
 MinioClient minioClient;

 // 存储普通文件
 @Value("${minio.bucket.files}")
 private String mediaFiles;

 // 存储视频
// @Value("${minio.bucket.video}")
// private String videoFiles;

 *//**
  * 根据扩展名获取mimeType
  * @param companyId
  * @param pageParams 分页参数
  * @param queryMediaParamsDto 查询条件
  * @return
  *//*
 @Override
 public PageResult<MediaFiles> queryMediaFiels(Long companyId, PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto) {

  //构建查询条件对象
  LambdaQueryWrapper<MediaFiles> queryWrapper = new LambdaQueryWrapper<>();

  //分页对象
  Page<MediaFiles> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
  // 查询数据内容获得结果
  Page<MediaFiles> pageResult = mediaFilesMapper.selectPage(page, queryWrapper);
  // 获取数据列表
  List<MediaFiles> list = pageResult.getRecords();
  // 获取数据总数
  long total = pageResult.getTotal();
  // 构建结果集
  PageResult<MediaFiles> mediaListResult = new PageResult<>(list, total, pageParams.getPageNo(), pageParams.getPageSize());
  return mediaListResult;

 }

 private String getMimeType(String extension) {
  if (extension == null) {
   extension = "";
  }
  // 根据扩展名取出mimeType
  ContentInfo contentInfo = ContentInfoUtil.findMimeTypeMatch(extension);
  String mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;

  if (extension != null) {
   mimeType = contentInfo.getMimeType();
  }

  return mimeType;
 }


 *//**
  * 将文件上传到minio
  * @param localFilePath 文件本地路径
  * @param mimeType 媒体类型
  * @param bucket 桶
  * @param objectName 对象名
  *//*
 public boolean addMediaFilesToMinIo(String localFilePath, String mimeType, String bucket, String objectName) {
  try {
   UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
           .bucket(bucket)
           .filename(localFilePath)
           .object(objectName)
           .contentType(mimeType)
           .build();
   // 上传文件
   minioClient.uploadObject(uploadObjectArgs);
   log.debug("上传文件到minio成功， bucket:{}, objectName:{}", bucket,objectName);
   return true;
  } catch (Exception e) {

   e.printStackTrace();
   log.error("上传文件出错， bucket:{}, objectName:{}, 错误信息:{}", bucket,objectName,e.getMessage());
  }
  return false;
 }

 *//**
  * 获取文件默认存储目录路径  年/月/日
  * @return
  *//*
 private String getDefaultFolderPath() {
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  String folder = sdf.format(new Date()).replace("-","/") + "/";
  return folder;
 }

 *//**
  * 获取文件的md5
  * @param file
  * @return
  *//*
 private String getFileMd5(File file) {
  try (FileInputStream fileInputStream = new FileInputStream(file)) {
   String fileMd5 = DigestUtils.md5Hex(fileInputStream);
   return fileMd5;
  } catch (Exception e) {
   e.printStackTrace();
   return null;
  }
 }

 *//**
  * @description 将文件信息添加到文件表
  * @param companyId  机构id
  * @param fileMd5  文件md5值
  * @param uploadFileParamsDto  上传文件的信息
  * @param bucket  桶
  * @param objectName 对象名称
  * @return com.xuecheng.media.model.po.MediaFiles
  * @author MrMa
  * @date 2022/10/12 21:22
  *//*
 @Transactional
 public MediaFiles addMediaFilesToDb(Long companyId,String fileMd5,UploadFileParamsDto uploadFileParamsDto,String bucket,String objectName){
  //从数据库查询文件
  MediaFiles mediaFiles = mediaFilesMapper.selectById(fileMd5);
  if (mediaFiles == null) {
   mediaFiles = new MediaFiles();
   //拷贝基本信息
   BeanUtils.copyProperties(uploadFileParamsDto, mediaFiles);
   mediaFiles.setId(fileMd5);
   mediaFiles.setFileId(fileMd5);
   mediaFiles.setCompanyId(companyId);
   mediaFiles.setUrl("/" + bucket + "/" + objectName);
   mediaFiles.setBucket(bucket);
   mediaFiles.setFilePath(objectName);
   mediaFiles.setCreateDate(LocalDateTime.now());
   mediaFiles.setAuditStatus("002003");
   mediaFiles.setStatus("1");
   //保存文件信息到文件表
   int insert = mediaFilesMapper.insert(mediaFiles);
   if (insert <= 0) {
    log.error("保存文件信息到数据库失败,{}",mediaFiles.toString());
    XueChengPlusException.cast("保存文件信息失败");
    return null;
   }
   log.debug("保存文件信息到数据库成功,{}",mediaFiles.toString());

  }
  return mediaFiles;

 }

 @Override
 public UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, String localFilePath) {
  // 获取文件名
  String filename = uploadFileParamsDto.getFilename();
  // 获取扩展名
  String extension = filename.substring(filename.lastIndexOf("."));


  // 根据扩展名取出mieType
  String mimeType = getMimeType(extension);
  // 子目录
  String defaultFolderPath = getDefaultFolderPath();

  // 文件的md5值
  String fileMd5 = getFileMd5(new File(localFilePath));

  String objectName = defaultFolderPath + fileMd5 + extension;
  // 上传文件的参数信息
  boolean result = addMediaFilesToMinIo(localFilePath, mimeType, mediaFiles, objectName);

  if (!result) {
   XueChengPlusException.cast("上传文件失败！");
  }
  // 入库文件信息
  MediaFiles files = addMediaFilesToDb(companyId, fileMd5, uploadFileParamsDto, mediaFiles, objectName);
  if (files == null) {
   XueChengPlusException.cast("文件上传后保存信息失败！");
  }
  // 准备返回的对象
  UploadFileResultDto uploadFileResultDto = new UploadFileResultDto();
  BeanUtils.copyProperties(files, uploadFileResultDto);

  return uploadFileResultDto;
 }*/
