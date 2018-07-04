<%--
  Created by IntelliJ IDEA.
  User: lalalala
  Date: 7/4/2018
  Time: 14:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"
            integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T"
            crossorigin="anonymous"></script>
</head>
<body>
    <div class="container">
        <form action="http://localhost:8080/login" method="post">
            <div class="form-group">
                <label for="nameLogin">Name</label>
                <input type="text" class="form-control" id="nameLogin" aria-describedby="emailHelp"
                       placeholder="Enter name" name="account">
            </div>
            <div class="form-group">
                <label for="passwordLogin">Password</label>
                <input type="password" class="form-control" id="passwordLogin" placeholder="Password" name="password">
            </div>
            <%--<div class="form-group form-check">--%>
                <%--<input type="checkbox" class="form-check-input" id="exampleCheck1">--%>
                <%--<label class="form-check-label" for="exampleCheck1">Check me out</label>--%>
            <%--</div>--%>
            <a href="#" ></a>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </div>

    <div class="container">
        <form action="http://localhost:8080/login" method="post">
            <div class="form-group">
                <label for="nameRegister">Name</label>
                <input type="text" class="form-control" id="nameRegister" aria-describedby="emailHelp"
                       placeholder="Enter name" name="account">
            </div>
            <div class="form-group">
                <label for="passwordRegister">Password</label>
                <input type="password" class="form-control" id="passwordRegister" placeholder="Password" name="password">
            </div>
            <div class="form-group">
                <label for="passwordConfirmRegister">Confirm Password</label>
                <input type="password" class="form-control" id="passwordConfirmRegister" placeholder="Password" name="password">
            </div>
            <div class="form-group">
                <label for="emailRegister">Email address</label>
                <input type="email" class="form-control" id="emailRegister" aria-describedby="emailHelp" placeholder="Enter email">
                <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
            </div>
            <%--<div class="form-group form-check">--%>
            <%--<input type="checkbox" class="form-check-input" id="exampleCheck1">--%>
            <%--<label class="form-check-label" for="exampleCheck1">Check me out</label>--%>
            <%--</div>--%>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
            integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
            crossorigin="anonymous"></script>
</body>
</html>
