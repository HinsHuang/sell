<html>
<#include "../common/header.ftl">

<body>

    <div id="wrapper" class="toggled">

        <#--边栏sidebar-->
        <#include "../common/nav.ftl">

        <#--主要内容content-->
        <div id="page-content-wrapper">
            <div class="container">
                <div class="row clearfix">
                    <#--订单-->
                    <div class="col-md-8 column">
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th>订单号</th>
                                <th>下单人</th>
                                <th>订单总金额</th>
                                <th>订单状态</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>${orderDTO.orderId}</td>
                                <td>${orderDTO.buyerName}</td>
                                <td>${orderDTO.orderAmount}</td>
                                <td>${orderDTO.getOrderStatusEnum().message}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>

                    <#--订单详情-->
                    <div class="col-md-12 column">
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th>商品号</th>
                                <th>商品名称</th>
                                <th>价格</th>
                                <th>数量</th>
                                <th>总价</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list orderDTO.orderDetailList as orderDetail>
                                <tr>
                                    <td>${orderDetail.productId}</td>
                                    <td>${orderDetail.productName}</td>
                                    <td>${orderDetail.productPrice}</td>
                                    <td>${orderDetail.productQuantity}</td>
                                    <td>${orderDetail.productQuantity * orderDetail.productPrice}</td>
                                </tr>
                            </#list>
                            </tbody>
                        </table>
                    </div>

                    <#--操作订单-->
                    <div class="col-md-12 column">
                        <#if orderDTO.getOrderStatusEnum().message == "新订单" >
                            <a href="/sell/seller/order/finish?orderId=${orderDTO.orderId}" type="button" class="btn btn-default btn-primary">完结订单</a>
                            <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}" type="button" class="btn btn-default btn-danger">取消订单</a>
                        </#if>
                    </div>

                </div>
            </div>
        </div>
    </div>

</body>
</html>