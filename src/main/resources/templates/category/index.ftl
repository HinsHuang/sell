<html>
<#include "../common/header.ftl">

<body>

<div id="wrapper" class="toggled">
    <#--边栏sidebar-->
    <#include "../common/nav.ftl">

    <#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <#--<div class="container">-->
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <#--提交表单-->
                    <form role="form" method="post" action="/sell/seller/category/save">

                        <div class="form-group">
                            <label>类目名字</label>
                            <input name="categoryName" type="text" class="form-control" value="${(category.categoryName) ! ''}" />
                        </div>

                        <div class="form-group">
                            <label>类目类型</label>
                            <input name="categoryType" type="number" class="form-control" value="${(category.categoryType) ! ''}" />
                        </div>

                        <input type="text" name="categoryId" value="${(category.categoryId) ! ''}" hidden>

                        <button type="submit" class="btn btn-default">提交</button>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>


