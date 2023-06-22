package com.xuecheng.content.service;

import com.xuecheng.content.model.po.CourseTeacher;

import java.util.List;

/**
 * @author MrMa
 * @version 1.0
 * @description   教师信息
 * @data 2023-06-08 22:43
 */
public interface TeacherInfoService {

   /**
    * 根据课程id查询教师
    * @param courseId
    * @return
    */
   public List<CourseTeacher> findTeacherInfo(Long courseId);

   /**
    * 保存,修改课程教师信息
    * @param courseTeacher
    */
   public void saveCourseTeacherInfo(CourseTeacher courseTeacher);

   public Integer deleteCourseTeacherInfo(Long courseId, Long teacherId);
}
