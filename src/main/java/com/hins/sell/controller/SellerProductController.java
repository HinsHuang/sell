package com.hins.sell.controller;

import com.hins.sell.dataobject.ProductCategory;
import com.hins.sell.dataobject.ProductInfo;
import com.hins.sell.exceptions.SellException;
import com.hins.sell.form.ProductForm;
import com.hins.sell.service.CategoryService;
import com.hins.sell.service.ProductService;
import com.hins.sell.utils.KeyUtil;
import freemarker.template.utility.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;


    /**
     * 全部商品列表
     * @param page 从第1页开始
     * @param size
     * @param model
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> model) {
        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<ProductInfo> productInfoPage = productService.findAll(pageRequest);
        model.put("productInfoPage", productInfoPage);
        model.put("currentPage", page);
        model.put("size", size);
        return new ModelAndView("product/list", model);
    }

    /**
     * 商品上架
     * @param productId
     * @return
     */
    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String, Object> model) {
        try {
            productService.onSale(productId);
        } catch (SellException ex) {
            model.put("message", ex.getMessage());
            model.put("returnUrl", "/sell/seller/product/list");
            return new ModelAndView("common/error");
        }
        model.put("returnUrl", "/sell/seller/product/list");
        return new ModelAndView("common/success", model);
    }

    /**
     * 商品下架
     * @param productId
     * @param model
     * @return
     */
    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                                Map<String, Object> model) {
        try {
            productService.offSale(productId);
        } catch (SellException ex) {
            model.put("message", ex.getMessage());
            model.put("returnUrl", "/sell/seller/product/list");
            return new ModelAndView("common/error");
        }
        model.put("returnUrl", "/sell/seller/product/list");
        return new ModelAndView("common/success", model);
    }

    /**
     * 商品修改
     * @param productId
     * @param model
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
                      Map<String, Object> model) {
        if (!StringUtils.isEmpty(productId)) {
            ProductInfo productInfo = productService.findOne(productId);
            model.put("productInfo", productInfo);
        }

        //查询所有的类目
        List<ProductCategory> categoryList = categoryService.findAll();
        model.put("categoryList", categoryList);
        return new ModelAndView("product/index", model);
    }

    /**
     * 保存/更新 商品
     * @param productForm
     * @param bindingResult
     * @param model
     * @return
     */
    @PostMapping("/save")
//    @CacheEvict(cacheNames = "product", key = "123")
    public ModelAndView save(@Valid ProductForm productForm,
                             BindingResult bindingResult,
                             Map<String, Object> model) {
        if (bindingResult.hasErrors()) {
            model.put("message", bindingResult.getFieldError().getDefaultMessage());
            model.put("returnUrl", "/sell/seller/product/index");
            return new ModelAndView("common/error", model);
        }

        ProductInfo productInfo = new ProductInfo();
        try {
            //新增商品
            if (StringUtils.isEmpty(productForm.getProductId())) {
                //设置商品id
                String id = KeyUtil.generateUniqueKey();
                productForm.setProductId(id);
            } else {
                productInfo = productService.findOne(productForm.getProductId());
            }
            BeanUtils.copyProperties(productForm, productInfo);
            productService.save(productInfo);
        } catch (SellException ex) {
            model.put("message", ex.getMessage());
            model.put("returnUrl", "/sell/seller/product/index");
            return new ModelAndView("common/error", model);
        }

        model.put("returnUrl", "/sell/seller/product/list");
        return new ModelAndView("common/success", model);
    }


}
