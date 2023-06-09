package com.xuecheng.content.api;

import com.xuecheng.content.model.dto.SaveTeachPlanDto;
import com.xuecheng.content.model.dto.TeachPlanDto;
import com.xuecheng.content.service.TeachPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author MrMa
 * @version 1.0
 * @description   课程计划管理相关的接口
 * @data 2023-06-07 22:04
 */
@Api(value = "课程计划编辑接口", tags = "课程计划编辑接口")
@RestController
public class TeachPlanController {

   @Autowired
   TeachPlanService teachPlanService;


   @ApiOperation("查询课程计划树形结构")
   @GetMapping("/teachplan/{courseId}/tree-nodes")
   public List<TeachPlanDto> getTreeNodes(@PathVariable Long courseId) {
      List<TeachPlanDto> teachPlanTree = teachPlanService.findTeachPlanTree(courseId);
      return teachPlanTree;
   }

   @ApiOperation("课程计划创建或修改")
   @PostMapping("/teachplan")
   public void saveTeachPlan(@RequestBody SaveTeachPlanDto saveTeachPlanDto) {
      teachPlanService.saveTeachPlan(saveTeachPlanDto);
   }

   @ApiOperation("根据课程Id删除课程章节")
   @RequestMapping("/teachplan/{id}")
   @ResponseBody
   public void deleteTeachPlan(@PathVariable Long id) {
      teachPlanService.deleteTeachPlan(id);
   }

   @ApiOperation("课程计划排序-上移")
   @RequestMapping("/teachplan/moveup/{id}")
   @ResponseBody
   public void moveup(@PathVariable Long id) {
      teachPlanService.moveup(id);
   }
   @ApiOperation("课程计划排序-下移")
   @RequestMapping("/teachplan/movedown/{id}")
   @ResponseBody
   public void movedown(@PathVariable Long id) {
      teachPlanService.movedown(id);
   }
}
