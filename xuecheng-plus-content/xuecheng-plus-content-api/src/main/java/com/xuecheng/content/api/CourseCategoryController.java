package com.xuecheng.content.api;

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Mr.M
 * @version 1.0
 * @description TODO
 * @date 2023/2/11 15:44
 */
@Api(value = "课程分类管理接口",tags = "课程分类管理接口")
@RestController
public class CourseCategoryController {

    @Autowired
    private CourseCategoryService courseCategoryService;
    @GetMapping("course-category/tree-nodes")
    public List<CourseCategoryTreeDto> getTreeNodes() {
        return courseCategoryService.queryTreeNodes("1");
    }
}
