package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.base.exception.XueChengPlusException;
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
   public void saveTeachPlan(SaveTeachPlanDto saveTeachPlanDto) {
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

   @Override
   public void deleteTeachPlan(Long id) {
      // 创建条件构造器
      LambdaQueryWrapper<Teachplan> lambdaQueryWrapper = new LambdaQueryWrapper<>();
      lambdaQueryWrapper.eq(Teachplan::getId, id);
      // 此构造器是查看是否有下属章节
      LambdaQueryWrapper<Teachplan> belongQueryWrapper = new LambdaQueryWrapper<>();
      belongQueryWrapper.eq(Teachplan::getParentid, id);

      Integer count = teachplanMapper.selectCount(belongQueryWrapper);

      if (count > 0) {
//         return new StatusOperateDto("120409", "课程计划信息还有子级信息，无法操作");
         throw new XueChengPlusException("120409", "课程计划信息还有子级信息，无法操作");
      }
      int delete = teachplanMapper.deleteById(id);
      if (delete == 0) {
         throw  new RuntimeException("操作失败！");
      }
//      return new StatusOperateDto(200);
   }

   @Override
   public void movedown(Long id) {
      // 根据Id查询对应的章节

      LambdaQueryWrapper<Teachplan> lambdaQueryWrapper = new LambdaQueryWrapper<>();
      lambdaQueryWrapper.eq(Teachplan::getId, id);
      // 查询当前章节或者小节
      Teachplan teachplanChapter = teachplanMapper.selectOne(lambdaQueryWrapper);
      // 获取当前章节的parentId
      Long parentid = teachplanChapter.getParentid();
      // 获取当前章节所属的课程Id
      Long courseId = teachplanChapter.getCourseId();
      // 获取当前章节的序号
      Integer orderby = teachplanChapter.getOrderby();
      LambdaQueryWrapper<Teachplan> moveQueryWrapper = new LambdaQueryWrapper<>();
      // 由于此处是向下移动，所以查询orderBy+1的下一个子章节
      try {
         moveQueryWrapper.eq(Teachplan::getParentid, parentid)
                 .eq(Teachplan::getOrderby, orderby + 1)
                 .eq(Teachplan::getCourseId, courseId);
         Teachplan changeChapterTwo = teachplanMapper.selectOne(moveQueryWrapper);
         // 获取changeChapterTwo的orderBy
         Integer changeOrderBy = changeChapterTwo.getOrderby();

         // 修改两个章节的orderby
         teachplanChapter.setOrderby(changeOrderBy);
         teachplanMapper.updateById(teachplanChapter);

         // 修改下一个章节的orderby
         changeChapterTwo.setOrderby(orderby);
         teachplanMapper.updateById(changeChapterTwo);
      } catch (Exception e) {
         throw  new XueChengPlusException("当前章节已经是底部！");
      }
   }

   @Override
   public void moveup(Long id) {
      // 根据Id查询对应的章节

      LambdaQueryWrapper<Teachplan> lambdaQueryWrapper = new LambdaQueryWrapper<>();
      lambdaQueryWrapper.eq(Teachplan::getId, id);
      // 查询当前章节或者小节
      Teachplan teachplanChapter = teachplanMapper.selectOne(lambdaQueryWrapper);
      // 获取当前章节的parentId
      Long parentid = teachplanChapter.getParentid();
      // 获取当前章节所属的课程Id
      Long courseId = teachplanChapter.getCourseId();
      // 获取当前章节的序号
      Integer orderby = teachplanChapter.getOrderby();
      LambdaQueryWrapper<Teachplan> moveQueryWrapper = new LambdaQueryWrapper<>();
      // 由于此处是向下移动，所以查询orderBy-1的下一个子章节
      try {
         moveQueryWrapper.eq(Teachplan::getParentid, parentid)
                 .eq(Teachplan::getOrderby, orderby - 1)
                 .eq(Teachplan::getCourseId, courseId);
         Teachplan changeChapterTwo = teachplanMapper.selectOne(moveQueryWrapper);
         // 获取changeChapterTwo的orderBy
         Integer changeOrderBy = changeChapterTwo.getOrderby();

         // 修改两个章节的orderby
         teachplanChapter.setOrderby(changeOrderBy);
         teachplanMapper.updateById(teachplanChapter);

         // 修改下一个章节的orderby
         changeChapterTwo.setOrderby(orderby);
         teachplanMapper.updateById(changeChapterTwo);
      } catch (Exception e) {
         throw  new XueChengPlusException("当前章节已经是顶部！");
      }
   }
}
