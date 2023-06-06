package com.xuecheng.content.service.impl;

import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */

@Slf4j
@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {

   @Autowired
   private CourseCategoryMapper courseCategoryMapper;

   @Override
   public List<CourseCategoryTreeDto> queryTreeNodes(String id) {
      // 调用mapper递归查询出分类信息
      List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryMapper.selectTreeNodes(id);

      // 找到每个结点的子节点，将其封装成List<CourseCategoryTreeDto>
      /*
       先将list转成map, key就是结点的id, value就是CourseCategoryTreeDto对象，目的就是为了方便从map获取结点
        filter(item->id.equals(item.getId()))把根结点排除
       */
      Map<String, CourseCategoryTreeDto> mapTemp = courseCategoryTreeDtos.stream().filter(item -> !id.equals(item.getId())).collect(
              Collectors.toMap(key -> key.getId(), value -> value, (key1, key2) -> key2));

      // 定义一个list作为最终返回的list
      List<CourseCategoryTreeDto> courseCategories = new ArrayList<>();
      // 从头遍历List<CourseCategory>, 一边遍历一边找到 子节点并放在父节点的childrenTreeNodes
      courseCategoryTreeDtos.stream().filter(item->!id.equals(item.getId())).forEach(item->{
         if (item.getParentid().equals(id)) {
            courseCategories.add(item);
         }

         // 找到父节点
         CourseCategoryTreeDto courseCategoryParent = mapTemp.get(item.getParentid());

         if (courseCategoryParent != null) {
            if (courseCategoryParent.getChildrenTreeNodes() == null) {
               // 如果该父节点的ChildrenTreeNodes属性为空要new一个集合， 因为要像该集合中方它的子节点
               courseCategoryParent.setChildrenTreeNodes(new ArrayList<CourseCategoryTreeDto>());
            }
            // 到每个结点的子节点放在父节点的childrenTreeNodes属性中
            courseCategoryParent.getChildrenTreeNodes().add(item);
         }

      });
      return courseCategories;
   }
}
