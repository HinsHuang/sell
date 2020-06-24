package com.hins.sell.repository;

import com.hins.sell.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    @Test
    public void testSave() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("HH123");
        orderMaster.setBuyerName("hins");
        orderMaster.setBuyerAddress("guangzhou");
        orderMaster.setBuyerOpenid("1123456ds");
        orderMaster.setBuyerPhone("123456789");
        orderMaster.setOrderAmount(new BigDecimal(1112.2));
        repository.save(orderMaster);
    }

    @Test
    public void testFindByBuyerOpenid() {

        PageRequest pageRequest = new PageRequest(0, 2);
        Page<OrderMaster> orderMasterPage = repository.findByBuyerOpenid("1346467dsfk", pageRequest);
        Assert.assertNotEquals(0, orderMasterPage.getTotalElements());
    }



}