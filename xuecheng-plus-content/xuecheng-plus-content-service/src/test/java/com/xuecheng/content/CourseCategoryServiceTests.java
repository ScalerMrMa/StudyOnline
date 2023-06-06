package com.xuecheng.content;

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */
@SpringBootTest
public class CourseCategoryServiceTests {

   @Autowired
   CourseCategoryService courseCategoryService;

   @Test
   public void testCourseCategoryService() {
      List<CourseCategoryTreeDto> courseCategories = courseCategoryService.queryTreeNodes("1");
      System.out.println(courseCategories);
   }
}
