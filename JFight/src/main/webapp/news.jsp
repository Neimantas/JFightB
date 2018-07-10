<%--
  Created by IntelliJ IDEA.
  User: lalalala
  Date: 7/9/2018
  Time: 16:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
    <link rel="stylesheet" href="/Styles/styles.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"
            integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T"
            crossorigin="anonymous"></script>
</head>
<body>
    <div class="container top5">

            <div class="row">
                <h3>UserName</h3>
                <div class="float-right">
                    <button type="button" class="btn btn-primary btn-lg" id="fightButton">Fight</button>
                    <button type="button" class="btn btn-primary btn-lg" id="infoButton">Info</button>
                </div>
            </div>
            <div class="row">
                <a class="float-right" href="/login" id="logout"><small>Logout</small></a>
            </div>
    </div>
    <div class="container">
        <div class="row">
            <div class="col text-justify">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Asperiores consequuntur exercitationem ipsam nam nisi quas sapiente, tenetur voluptatem. Aspernatur, recusandae?</div>
            <div class="col text-justify">Lorem ipsum dolor sit amet, consectetur adipisicing elit. At ea eos fugiat necessitatibus nobis nulla, officia quas quisquam similique sit.</div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
            integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
            crossorigin="anonymous"></script>
    <script src="Scripts/news.js"></script>
</body>
</html>
