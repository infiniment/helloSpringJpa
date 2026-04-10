package kr.ac.hansung.cse.controller;

import jakarta.validation.Valid;
import kr.ac.hansung.cse.exception.DuplicateCategoryException;
import kr.ac.hansung.cse.model.CategoryForm;
import org.springframework.ui.Model;
import kr.ac.hansung.cse.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // 카테고리 목록 조회
    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categoryList";
    }

    // 등록 폼 표시
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("categoryForm", new CategoryForm());
        return "categoryForm";
    }

    // 등록 처리
    @PostMapping("/create")
    public String createCategory(@Valid @ModelAttribute CategoryForm categoryForm,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) return "categoryForm"; // 검증 실패
        try {
            categoryService.createCategory(categoryForm.getName());
            redirectAttributes.addFlashAttribute("successMessage", "등록 완료");
        } catch (DuplicateCategoryException e) {
            // 중복 예외 → BindingResult에 필드 오류 등록 후 폼 재표시
            bindingResult.rejectValue("name", "duplicate", e.getMessage());
            return "categoryForm"; }
        return "redirect:/categories"; }


}
