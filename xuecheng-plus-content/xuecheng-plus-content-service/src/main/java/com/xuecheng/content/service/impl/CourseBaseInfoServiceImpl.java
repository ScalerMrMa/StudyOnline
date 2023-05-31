package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.CourseBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
