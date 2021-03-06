<html>
<head>
    <meta charset="utf-8">
    <title>错误提示</title>
    <!-- Bootstrap -->
    <link href="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <div class="alert alert-dismissable alert-warning">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                    <h4>
                        错误!
                    </h4> <strong>${message}</strong> <a href="${returnUrl}" class="alert-link">3s后自动跳转</a>
                </div>
            </div>
        </div>
    </div>
</body>

<script>
    setTimeout('location.href="${returnUrl}"', 3000);
</script>

</html>