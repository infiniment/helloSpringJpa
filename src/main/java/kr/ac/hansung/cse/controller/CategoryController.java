package kr.ac.hansung.cse.controller;

import org.springframework.ui.Model;
import kr.ac.hansung.cse.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // 카테고리 목록 조회
    @GetMapping
    public String showCreateForm(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categoryList";
    }

}
