package com.xuecheng.content.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
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
}
