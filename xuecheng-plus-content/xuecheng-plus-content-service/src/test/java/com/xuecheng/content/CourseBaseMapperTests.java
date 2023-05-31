package com.xuecheng.content;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */
@SpringBootTest
public class CourseBaseMapperTests {

   @Autowired
   CourseBaseMapper courseBaseMapper;

   @Test
   public void testCourseMapper() {
      CourseBase courseBase = courseBaseMapper.selectById(18);
      // 断言不为空
      Assertions.assertNotNull(courseBase);
      // 详细进行分页查询的单元测试
      // 查询条件,此处的Dto课程查询条件模型类
      QueryCourseParamsDto queryCourseParamsDto = new QueryCourseParamsDto();
      // 课程查询条件
      queryCourseParamsDto.setCourseName("java");
      // 拼装查询条件
      LambdaQueryWrapper<CourseBase> courseBaseLambdaQueryWrapper = new LambdaQueryWrapper<>();
      // 根据名称进行模糊查询
      courseBaseLambdaQueryWrapper.like(StringUtils.isNotEmpty(queryCourseParamsDto.getCourseName()),
              CourseBase::getName, queryCourseParamsDto.getCourseName());
      // 根据课程审核状态查询
      courseBaseLambdaQueryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getAuditStatus()),
              CourseBase::getAuditStatus, queryCourseParamsDto.getAuditStatus());
      // 创建page分页参数对象，参数，当前页码，每页记录数
      PageParams pageParams = new PageParams();
      pageParams.setPageNo(1L);
      pageParams.setPageSize(2L);
      Page<CourseBase> basePage = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
      Page<CourseBase> result = courseBaseMapper.selectPage(basePage, courseBaseLambdaQueryWrapper);
      // 数据列表
      List<CourseBase> records = result.getRecords();
      // 总记录数
      long total = result.getTotal();
      // List<T> items, long counts, long page, long pageSize
      PageResult<CourseBase> courseBasePageResult = new PageResult<CourseBase>(records, total, pageParams.getPageNo(), pageParams.getPageSize());
      System.out.println(courseBasePageResult);
   }
}
