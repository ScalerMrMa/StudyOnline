package com.xuecheng.content;

import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.model.dto.TeachPlanDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author MrMa
 * @version 1.0
 * @description
 * @data 2023-06-07 22:49
 */
@SpringBootTest
public class TeachPlanMapperTests {

   @Autowired
   TeachplanMapper teachplanMapper;

   @Test
   public void test(){
      List<TeachPlanDto> teachPlanDtos = teachplanMapper.selectTreeNodes(117L);

      teachPlanDtos.forEach(System.out::println);
   }
}
