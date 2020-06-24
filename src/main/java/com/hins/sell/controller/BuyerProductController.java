package com.hins.sell.controller;


import com.hins.sell.dataobject.ProductCategory;
import com.hins.sell.dataobject.ProductInfo;
import com.hins.sell.service.impl.CategoryServiceImpl;
import com.hins.sell.service.impl.ProductServiceImpl;
import com.hins.sell.utils.ResultVOUtil;
import com.hins.sell.viewobject.ProductInfoVO;
import com.hins.sell.viewobject.ProductVO;
import com.hins.sell.viewobject.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private ProductServiceImpl productService;

    @GetMapping("/list")
    public ResultVO list() {
        //获取所有上架商品
        List<ProductInfo> productInfoList = productService.findAllUp();
        //获取商品对应的类目
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());

        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        //组合vo对象
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory category : productCategoryList) {
            //不同的类目
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(category.getCategoryType());
            productVO.setCategoryName(category.getCategoryName());
            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            //获取对应类目下的商品
            for (ProductInfo productInfo : productInfoList) {
                if (category.getCategoryType().equals(productInfo.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }
        return ResultVOUtil.success(productVOList);
    }
}
