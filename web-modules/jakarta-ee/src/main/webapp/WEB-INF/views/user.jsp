<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>MVC 2.0</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@100;200;300;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">

</head>
<body>
<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
    <div class="container-fluid">
        <span class="navbar-brand" href="#"><span style="font-size: 24px;">Baeldung - Eclipse Krazo</span></span>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse"
                aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

    </div>
</nav>
<div class="container">
    <div class="row align-items-center">
        <div class="col-sm-7 mx-auto">
            <h3>
                <strong>User Details</strong>
            </h3>
            <hr class="hr-dark"/>
            <c:if test="${errors.size() > 0}">
                <div class="alert alert-danger" role="alert">
                    <dl class="row">
                        <c:forEach var="error" items="${errors}">
                            <dt class="col-sm-2">${error.paramName}</dt>
                            <dd class="col-sm-10">${error.message}</dd>
                        </c:forEach>
                    </dl>
                </div>
            </c:if>
            <form action="${mvc.basePath}/users" method="post">
                <div class="form-group row mt-3">
                    <label for="name" class="col-sm-3 col-form-label">Name</label>
                    <div class="col-sm-9">
                        <input name="name" type="text" class="form-control" id="name">
                    </div>
                </div>
                <div class="form-group row mt-3">
                    <label for="age" class="col-sm-3 col-form-label">Age</label>
                    <div class="col-sm-9">
                        <input name="age" type="text" class="form-control" id="age">
                    </div>
                </div>
                <div class="form-group row mt-3">
                    <label for="email" class="col-sm-3 col-form-label">Email</label>
                    <div class="col-sm-9">
                        <input name="email" type="email" class="form-control" id="email">
                    </div>
                </div>
                <div class="form-group row mt-3">
                    <label for="phone" class="col-sm-3 col-form-label">Phone</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" id="phone">
                    </div>
                </div>
                <hr class="hr-dark"/>
                <div class="form-group row mt-3">
                    <div class="col-sm-10">
                        <button type="submit" class="btn btn-primary"><strong>Create</strong></button>
                    </div>
                </div>
                <input type="hidden" name="${mvc.csrf.name}" value="${mvc.csrf.token}"/>

            </form>

        </div>

    </div>
</div>


</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

</html>