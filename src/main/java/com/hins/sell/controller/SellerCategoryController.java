package com.hins.sell.controller;

import com.hins.sell.dataobject.ProductCategory;
import com.hins.sell.exceptions.SellException;
import com.hins.sell.form.CategoryForm;
import com.hins.sell.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.Binding;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 类目列表
     * @param model
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(Map<String, Object> model) {
        List<ProductCategory> categoryList = categoryService.findAll();
        model.put("categoryList", categoryList);
        return new ModelAndView("category/list", model);
    }

    /**
     * 展示
     * @param categoryId
     * @param model
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                              Map<String, Object> model) {
        //修改
        if (categoryId != null) {
            ProductCategory category = categoryService.findOne(categoryId);
            model.put("category", category);
        }
        return new ModelAndView("category/index", model);
    }

    /**
     * 新增/更新 类目
     * @param categoryForm
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm categoryForm,
                             BindingResult bindingResult,
                             Map<String, Object> model) {
        if (bindingResult.hasErrors()) {
            model.put("message", bindingResult.getFieldError().getDefaultMessage());
            model.put("returnUrl", "/sell/seller/category/list");
            return new ModelAndView("commom/error", model);
        }

        try {
            ProductCategory category = new ProductCategory();
            if (categoryForm.getCategoryId() != null) {
                category = categoryService.findOne(categoryForm.getCategoryId());
            }
            BeanUtils.copyProperties(categoryForm, category);
            categoryService.save(category);
        } catch (SellException ex) {
            model.put("message", ex.getMessage());
            model.put("returnUrl", "/sell/seller/category/list");
            return new ModelAndView("commom/error", model);
        }

        model.put("returnUrl", "/sell/seller/category/list");
        return new ModelAndView("common/success", model);
    }
}
