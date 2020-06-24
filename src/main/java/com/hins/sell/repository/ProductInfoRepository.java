package com.hins.sell.repository;

import com.hins.sell.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

    List<ProductInfo> findByCategoryTypeIn(List<Integer> categoryTypeList);

    List<ProductInfo> findByProductStatus(Integer productStatus);

}
