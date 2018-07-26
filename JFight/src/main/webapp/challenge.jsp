<%@ taglib prefix="java" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
    <link rel="stylesheet" href="Styles/styles.css">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col">
            <h3 id="userName">userName</h3>
            <h4 id="userId">${userId}</h4>
        </div>
        <div class="col">
            <h3>Challenge:</h3>
            <table class="table table-dark">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Player</th>
                    <th scope="col">ID</th>
                    <th scope="col">Challenge him</th>
                </tr>
                </thead>
                <tbody>
                <java:forEach begin="1" varStatus="count" var="user" items="${readyToFightList}">
                    <tr>
                        <th scope="row"><java:out value="${count.count}"/></th>
                        <td>${user.getUserName()}</td>
                        <td>${user.getUserId()}</td>
                        <td>
                            <div class="form-check">
                                <input type="checkbox" value="${user.getUserId()}" name="player">
                            </div>
                        </td>
                    </tr>
                </java:forEach>
                </tbody>
            </table>
            <button type="button" class="btn btn-lg btn-danger float-right" id="challengeBtn">Challenge</button>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
        integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"
        integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T"
        crossorigin="anonymous"></script>
<script src="Scripts/challenge.js"></script>
</body>
</html>
