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
    public void saveTeachPlan(SaveTeachPlanDto saveTeachPlanDto);

    /**
     * 根据id删除删除章节
     * @param id
     */
    public void deleteTeachPlan(Long id);

    /**
     * 课程计划排序,向下移动
     * @param id
     */
    public void movedown(Long id);

    public void moveup(Long id);
}

