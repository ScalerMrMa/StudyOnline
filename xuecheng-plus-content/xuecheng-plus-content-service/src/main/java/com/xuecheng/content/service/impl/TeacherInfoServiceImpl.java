package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.content.mapper.CourseTeacherMapper;
import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.TeacherInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author MrMa
 * @version 1.0
 * @description
 * @data 2023-06-08 22:45
 */

@Service
public class TeacherInfoServiceImpl implements TeacherInfoService {

   @Autowired
   CourseTeacherMapper courseTeacherMapper;

   @Override
   public List<CourseTeacher> findTeacherInfo(Long courseId) {
      // 根据课程Id查询教师
      LambdaQueryWrapper<CourseTeacher> lambdaQueryWrapper = new LambdaQueryWrapper<>();
      lambdaQueryWrapper.eq(CourseTeacher::getCourseId, courseId);

      List<CourseTeacher> courseTeachers = courseTeacherMapper.selectList(lambdaQueryWrapper);
      return courseTeachers;
   }


   @Transactional
   @Override
   public void saveCourseTeacherInfo(CourseTeacher courseTeacher) {
      Long id = courseTeacher.getId();

      // 创建条件构造器
      LambdaQueryWrapper<CourseTeacher> wrapper = new LambdaQueryWrapper<>();
      LambdaQueryWrapper<CourseTeacher> eq = wrapper.eq(CourseTeacher::getCourseId, courseTeacher.getCourseId());
      // 数据库中的教师信息
      CourseTeacher dataCourseTeacher = courseTeacherMapper.selectOne(eq);
      // 如果数据库中教师信息为null，则插入信息;否则更新信息
      if (dataCourseTeacher == null) {
         int insert = courseTeacherMapper.insert(courseTeacher);
         if (insert <= 0) {
            throw new RuntimeException("提交失败！");
         }
      }else {
         int update = courseTeacherMapper.updateById(courseTeacher);
         if (update <= 0) {
            throw new RuntimeException("修改失败！");
         }
      }

   }

   @Override
   public Integer deleteCourseTeacherInfo(Long courseId, Long teacherId) {
      // 创建条件构造器
      LambdaQueryWrapper<CourseTeacher> wrapper = new LambdaQueryWrapper<>();
      wrapper.eq(CourseTeacher::getCourseId, courseId)
            .eq(CourseTeacher::getId, teacherId);
      int delete = courseTeacherMapper.delete(wrapper);

      if (delete > 0) {
         return 200;
      }
      return 400;
   }
}
