package com.xuecheng.content;

import com.xuecheng.content.service.TeachPlanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author MrMa
 * @version 1.0
 * @description
 * @data 2023-06-07 22:49
 */
@SpringBootTest
public class TeachPlanMapperTests {

   @Autowired
   TeachPlanService teachPlanService;

   @Test
   public void test(){
      teachPlanService.deleteTeachPlan(267L);

   }
}
