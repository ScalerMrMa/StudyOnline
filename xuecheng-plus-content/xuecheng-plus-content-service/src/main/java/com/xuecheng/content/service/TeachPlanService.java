package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.SaveTeachPlanDto;
import com.xuecheng.content.model.dto.TeachPlanDto;

import java.util.List;

/**
 * @author MrMa
 * @version 1.0
 * @description 课程嘉华管理相关接口
 * @data 2023-06-07 22:58
 */
public interface TeachPlanService {

    /**
     * 根据课程id查询课程
     * @param courseId
     * @return
     */
    public List<TeachPlanDto> findTeachPlanTree(Long courseId);

    /**
     * 新增，修改，保存课程计划
     * @param saveTeachPlanDto
     */
    public void SaveTeachPlan(SaveTeachPlanDto saveTeachPlanDto);
}

