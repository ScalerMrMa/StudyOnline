package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.mapper.CourseMarketMapper;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseCategory;
import com.xuecheng.content.model.po.CourseMarket;
import com.xuecheng.content.service.CourseBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */
@Slf4j
@Service
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {

   @Autowired
   private CourseBaseMapper courseBaseMapper;

   @Autowired
   private CourseMarketMapper courseMarketMapper;

   @Autowired
   CourseCategoryMapper courseCategoryMapper;

   @Transactional
   @Override
   public PageResult<CourseBase> queryCourseBaseInfoList(PageParams pageParams, QueryCourseParamsDto courseParamsDto) {
      //构建查询条件对象
      LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
      //构建查询条件，根据课程名称查询
      queryWrapper.like(StringUtils.isNotEmpty(courseParamsDto.getCourseName()),CourseBase::getName,courseParamsDto.getCourseName());
      //构建查询条件，根据课程审核状态查询
      queryWrapper.eq(StringUtils.isNotEmpty(courseParamsDto.getAuditStatus()),CourseBase::getAuditStatus,courseParamsDto.getAuditStatus());
      //构建查询条件，根据课程发布状态查询
      //todo:根据课程发布状态查询

      //分页对象
      Page<CourseBase> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
      // 查询数据内容获得结果
      Page<CourseBase> pageResult = courseBaseMapper.selectPage(page, queryWrapper);
      // 获取数据列表
      List<CourseBase> list = pageResult.getRecords();
      // 获取数据总数
      long total = pageResult.getTotal();
      // 构建结果集
      PageResult<CourseBase> courseBasePageResult = new PageResult<>(list, total, pageParams.getPageNo(), pageParams.getPageSize());
      return courseBasePageResult;
   }

   // 凡是增删改都需要进行事务管理
   @Transactional
   @Override
   public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto dto) {

      // 参数的合法性校验
//      if (StringUtils.isBlank(dto.getName())) {
////         throw new RuntimeException("课程名称为空");
//         XueChengPlusException.cast("课程名称为空");
//      }
//
//      if (StringUtils.isBlank(dto.getMt())) {
//         throw new RuntimeException("课程分类为空");
//      }
//
//      if (StringUtils.isBlank(dto.getSt())) {
//         throw new RuntimeException("课程分类为空");
//      }
//
//      if (StringUtils.isBlank(dto.getGrade())) {
//         throw new RuntimeException("课程等级为空");
//      }
//
//      if (StringUtils.isBlank(dto.getTeachmode())) {
//         throw new RuntimeException("教育模式为空");
//      }
//
//      if (StringUtils.isBlank(dto.getUsers())) {
//         throw new RuntimeException("适应人群为空");
//      }
//
//      if (StringUtils.isBlank(dto.getCharge())) {
//         throw new RuntimeException("收费规则为空");
//      }
      // 向课程信息表course_base写入数据
      CourseBase courseBase = new CourseBase();
      // 将传入页面的参数放到courseBaseNew对象
      // 将旧对象中的数据放入到新对象中,只要属性名称一致
      BeanUtils.copyProperties(dto, courseBase);
      courseBase.setCompanyId(companyId);
      courseBase.setCreateDate(LocalDateTime.now());
      // 审核状态默认为未提交
      courseBase.setStatus("202002");
      // 发布状态为未发布
      courseBase.setAuditStatus("203001");
      // 插入数据库
      int insert = courseBaseMapper.insert(courseBase);
      if (insert <= 0) {
         throw new RuntimeException("添加课程失败！");
      }
      // 向课程营销表course_market写入数据
      CourseMarket courseMarket = new CourseMarket();
      BeanUtils.copyProperties(dto, courseMarket);
      // 课程id
      Long courseId = courseBase.getId();
      courseMarket.setId(courseId);
      // 保存营销信息
      saveCourseMarket(courseMarket);
      // 从数据库中查询完整的课程信息
      CourseBaseInfoDto courseBaseInfo = getCourseBaseInfo(courseId);
      return courseBaseInfo;
   }

   // 查询课程信息
   public CourseBaseInfoDto getCourseBaseInfo(Long courseId) {
      // 从课程基本信息表查询
      CourseBase courseBase = courseBaseMapper.selectById(courseId);
      if (courseBase == null) {
         return null;
      }
      // 从课程营销表查询
      CourseMarket courseMarket = courseMarketMapper.selectById(courseId);

      // 将数据组装
      CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
      BeanUtils.copyProperties(courseBase, courseBaseInfoDto);
      if (courseMarket != null) {
         BeanUtils.copyProperties(courseMarket, courseBaseInfoDto);
      }
      // todo:课程分类的名称设置到courseBaseInfoDto中
      // 获取课程基本信息表中的st信息,mt信息
      String st = courseBase.getSt();
      String mt = courseBase.getMt();
      // 根据st和mt获取对应的分类名称
      // 条件构造器
      LambdaQueryWrapper<CourseCategory> stQueryWrapper = new LambdaQueryWrapper<>();
      LambdaQueryWrapper<CourseCategory> mtQueryWrapper = new LambdaQueryWrapper<>();

      stQueryWrapper.eq(CourseCategory::getId, st);
      mtQueryWrapper.eq(CourseCategory::getId, mt);
      // 获取对象
      CourseCategory stCourse = courseCategoryMapper.selectOne(stQueryWrapper);
      CourseCategory mtCourse = courseCategoryMapper.selectOne(mtQueryWrapper);

      // 将分类名称注入到信息中
      courseBaseInfoDto.setStName(stCourse.getName());
      courseBaseInfoDto.setMtName(mtCourse.getName());
      return courseBaseInfoDto;
   }

   /**
    * 用于保存营销信息，逻辑：存在则更新，不存在则添加
    * @param courseMarket
    * @return
    */
   private int saveCourseMarket(CourseMarket courseMarket) {
      // 参数的合法性校验
      String charge = courseMarket.getCharge();
      if (StringUtils.isEmpty(charge)) {
         throw new RuntimeException("收费规则为空！");
      }
      // 如果课程收费,价格没有填写也需要抛出异常
      if (charge.equals("201001")) {
         if (courseMarket.getPrice() == null || courseMarket.getPrice().floatValue() <= 0) {
//            throw new RuntimeException("课程价格不能为空且必须大于0!");
            XueChengPlusException.cast("课程价格不能为空且必须大于0");
         }
      }
      // 从数据库查询营销信息,存在则更新，不存在则添加
      Long id = courseMarket.getId();
      CourseMarket oldCourseMarket = courseMarketMapper.selectById(id);
      if(oldCourseMarket == null) {
         // 插入数据库
         int insert = courseMarketMapper.insert(courseMarket);
         return insert;
      }else {
         // 将courseMarket拷贝到oldCourseMarket
         BeanUtils.copyProperties(oldCourseMarket, courseMarket);
         courseMarket.setId(id);
         // 更新
         int update = courseMarketMapper.updateById(oldCourseMarket);
         return update;
      }
   }
}
