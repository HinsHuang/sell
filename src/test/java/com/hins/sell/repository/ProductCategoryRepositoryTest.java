package com.hins.sell.repository;

import com.hins.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void testFindOne() {
        ProductCategory productCategory = repository.findOne(3);
        Assert.assertEquals(new Integer(3), productCategory.getCategoryType());
    }

    @Test
    @Transactional  //添加该注解测试方法的数据不会添加到数据库
    public void saveTest() {
        ProductCategory productCategory = new ProductCategory("测试", 4);
        ProductCategory result = repository.save(productCategory);
        Assert.assertNotNull(result);
    }

    @Test
    @Transactional
    public void updateTest() {
        List<ProductCategory> categoryList = repository.findByCategoryTypeIn(Arrays.asList(2, 3));
        if (categoryList.size() > 0) {
            ProductCategory category = categoryList.get(0);
            category.setCategoryName("更新");
            Assert.assertNotNull(repository.save(category));
        }
    }

    @Test
    public void findByCategoryTypeInTest() {
        List<Integer> categoryTypeList = Arrays.asList(2, 3, 4);
        List<ProductCategory> result = repository.findByCategoryTypeIn(categoryTypeList);
        Assert.assertNotEquals(0, result.size());
    }

}