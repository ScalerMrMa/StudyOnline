package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;

import java.util.List;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */
public interface CourseCategoryService {

   public List<CourseCategoryTreeDto> queryTreeNodes(String id);
}
