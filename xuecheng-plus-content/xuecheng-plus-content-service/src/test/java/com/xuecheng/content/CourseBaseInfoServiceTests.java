package com.xuecheng.content;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.CourseBaseInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */
@SpringBootTest
public class CourseBaseInfoServiceTests {

   @Autowired
   CourseBaseInfoService courseBaseInfoService;

   @Test
   public void testcourseBaseInfoService() {
      QueryCourseParamsDto queryCourseParamsDto = new QueryCourseParamsDto();
      queryCourseParamsDto.setCourseName("java");
      queryCourseParamsDto.setAuditStatus("202004");
      queryCourseParamsDto.setPublishStatus("203001");

      //分页参数
      PageParams pageParams = new PageParams();
      pageParams.setPageNo(1L);//页码
      pageParams.setPageSize(3L);//每页记录数

      PageResult<CourseBase> courseBasePageResult = courseBaseInfoService.queryCourseBaseInfoList(pageParams, queryCourseParamsDto);

   }
}
