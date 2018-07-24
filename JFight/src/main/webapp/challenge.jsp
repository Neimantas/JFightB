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
                <h1>${readyToFightList.get(0).getUserName()}</h1>
                <h2>${1+5}</h2>
                <h3><%=18+22%></h3>
                <java:forEach var="user" items="${readyToFightList}">
                    <tr>
                        <th scope="row">1</th>
                        <td>${user.getUserName()}</td>
                        <td>${user.getUserId()}</td>
                        <td>
                            <div class="form-check">
                                <input type="checkbox" value="${user.getUserId()}" name="player">
                            </div>
                        </td>
                    </tr>
                </java:forEach>
                <!--<tr>-->
                    <!--<th scope="row">1</th>-->
                    <!--<td>USERNAME + USER ID</td>-->
                    <!--<td><span id="fightStatus2"></span></td>-->
                    <!--<td>-->
                        <!--<div class="form-check">-->
                            <!--<input type="checkbox" value="UserID" name="player">-->
                        <!--</div>-->
                    <!--</td>-->
                <!--</tr>-->
                <!--<tr>-->
                    <!--<th scope="row">1</th>-->
                    <!--<td>UberFighter</td>-->
                    <!--<td><span id="fightStatus3"></span></td>-->
                    <!--<td>-->
                    <!--<div class="form-check">-->
                    <!--<input type="checkbox" value="UberFighter" name="player">-->
                    <!--</div>-->
                    <!--</td>-->
                    <!--</tr><tr>-->
                    <!--<th scope="row">1</th>-->
                    <!--<td>MotherLover69</td>-->
                    <!--<td><span id="fightStatus4"></span></td>-->
                    <!--<td>-->
                    <!--<div class="form-check">-->
                    <!--<input type="checkbox" value="MotherLover69" name="player">-->
                    <!--</div>-->
                    <!--</td>-->
                    <!--</tr>-->
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
