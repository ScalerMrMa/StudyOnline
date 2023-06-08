package com.xuecheng.content.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;

/**
 * @author MrMa
 * @version 1.0
 * @description     课程分页查询
 */
public interface CourseBaseInfoService {

   /**
    * 课程分页查询
    * @param pageParams 分页查询参数
    * @param courseParamsDto  查询条件
    * @return
    */
   PageResult<CourseBase> queryCourseBaseInfoList(PageParams pageParams, QueryCourseParamsDto courseParamsDto);


   /**
    * 新增课程
    * @param companyId  机构Id
    * @param addCourseDto  前端表单提交的信息
    * @return 课程添加成功的详细信息
    */
   public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);

   /**
    * 根据课程ID查询课程信息
    * @param courseId 课程Id
    * @return
    */
   public CourseBaseInfoDto getCourseBaseInfo(Long courseId);

   /**
    * 修改课程信息
    * @param companyId  机构ID
    * @param editCourseDto 修改的课程信息
    * @return
    */
   public CourseBaseInfoDto modifyCourseBaseInfo(Long companyId, EditCourseDto editCourseDto);
}
