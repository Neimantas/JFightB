<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
    <link rel="stylesheet" href="Styles/styles.css">
</head>
<body>
    <div class="container top5">

        <div class="row">
            <h3>${userExtended.get("userName")}</h3>
        </div>

        <div class="row">
            <div class="col-3">
                <img src="/Images/images%20(2).png" class="img-fluid border" alt="Responsive image">
            </div>
            <div class="col-6">
                <ul class="list-group">
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        Wins
                        <span class="badge badge-primary badge-pill"> ${userExtended.get("win")}1</span>
                    </li>
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        Loses
                        <span class="badge badge-primary badge-pill"> ${userExtended.get("lose")}</span>
                    </li>
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        Draws
                        <span class="badge badge-primary badge-pill"> ${userExtended.get("draw")}</span>
                    </li>
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        Total Fights
                        <span class="badge badge-primary badge-pill"> ${userExtended.get("totalFights")}</span>
                    </li>
                </ul>
            </div>
        </div>

        <div class="row">
            <button type="button" class="btn btn-secondary" id="backButton">Back</button>
        </div>
    </div>


    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
    <script src="/Scripts/user.js"></script>
</body>
</html>
