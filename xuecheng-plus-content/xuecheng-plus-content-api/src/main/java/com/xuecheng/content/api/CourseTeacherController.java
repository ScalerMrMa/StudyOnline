package com.xuecheng.content.api;

import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.TeacherInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author MrMa
 * @version 1.0
 * @description   课程师资管理
 * @data 2023-06-08 22:36
 */
@Api(value = "课程师资管理接口",tags = "课程师资管理接口")
@RestController
public class CourseTeacherController {

   @Autowired
   TeacherInfoService teacherInfoService;

   @ApiOperation("根据课程Id查询师资信息")
   @GetMapping("/courseTeacher/list/{courseId}")
   @ResponseBody
   public List<CourseTeacher> getCourseTeacherInfo(@PathVariable Long courseId) {

      List<CourseTeacher> teacherInfo = teacherInfoService.findTeacherInfo(courseId);
      return teacherInfo;
   }

   @ApiOperation("添加课程教师西信息")
   @PostMapping("/courseTeacher")
   public void saverCourseTeacher(@RequestBody CourseTeacher courseTeacher) {

      teacherInfoService.saveCourseTeacherInfo(courseTeacher);
   }

   @ApiOperation("删除课程教师西信息")
   @RequestMapping("/courseTeacher/course/{courseId}/{teacherId}")
   public Integer saverCourseTeacher(@PathVariable Long courseId, @PathVariable Long teacherId) {
      return teacherInfoService.deleteCourseTeacherInfo(courseId, teacherId);
   }
}
