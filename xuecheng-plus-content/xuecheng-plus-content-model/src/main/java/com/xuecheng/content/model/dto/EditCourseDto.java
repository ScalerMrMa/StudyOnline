package com.xuecheng.content.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author MrMa
 * @version 1.0
 * @description   用于修改课程的模型类
 * @data 2023-06-06 23:02
 */
@Data
public class EditCourseDto extends AddCourseDto{

   @ApiModelProperty(value = "课程id", required = true)
   private Long id;
}
