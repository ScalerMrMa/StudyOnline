package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.model.dto.SaveTeachPlanDto;
import com.xuecheng.content.model.dto.TeachPlanDto;
import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.service.TeachPlanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MrMa
 * @version 1.0
 * @description
 * @data 2023-06-07 23:02
 */
@Service
public class TeachPlanServiceImpl implements TeachPlanService {

   @Autowired
   TeachplanMapper teachplanMapper;

   @Override
   public List<TeachPlanDto> findTeachPlanTree(Long courseId) {
      List<TeachPlanDto> teachPlanDtos = teachplanMapper.selectTreeNodes(courseId);
      return teachPlanDtos;
   }

   private int getTeachPlanCount(Long parentId, Long courseId) {
      Teachplan teachplan = new Teachplan();
      LambdaQueryWrapper<Teachplan> queryChainWrapper = new LambdaQueryWrapper<>();
      queryChainWrapper.eq(Teachplan::getCourseId, courseId).eq(Teachplan::getParentid, parentId);
      Integer count = teachplanMapper.selectCount(queryChainWrapper);

      return count + 1;
   }

   @Override
   public void SaveTeachPlan(SaveTeachPlanDto saveTeachPlanDto) {
      // 通过课程计划id判断是新增还是修改
      Long teachPlanId = saveTeachPlanDto.getId();
      if (teachPlanId == null) {
         // 新增
         Teachplan teachplan = new Teachplan();

         BeanUtils.copyProperties(saveTeachPlanDto, teachplan);
         Long courseId = saveTeachPlanDto.getCourseId();
         Long parentId = saveTeachPlanDto.getParentid();

         int count = getTeachPlanCount(parentId, courseId);
         teachplan.setOrderby(count);
         teachplanMapper.insert(teachplan);
      }else {
         // 修改
         Teachplan teachplan = new Teachplan();
         // 将参数复制到teachPlan
         BeanUtils.copyProperties(saveTeachPlanDto, teachplan);
         teachplanMapper.updateById(teachplan);
      }
   }
}
