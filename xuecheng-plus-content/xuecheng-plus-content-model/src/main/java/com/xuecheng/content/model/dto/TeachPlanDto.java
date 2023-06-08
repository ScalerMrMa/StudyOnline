package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.model.po.TeachplanMedia;
import lombok.Data;

import java.util.List;

/**
 * @author MrMa
 * @version 1.0
 * @description   课程计划信息表
 * @data 2023-06-07 22:01
 */
@Data
public class TeachPlanDto extends Teachplan {
   // 与媒资管理的西悉尼
   private TeachplanMedia teachplanMedia;

   // 小章节List
   private List<TeachPlanDto> teachPlanTreeNodes;
}
