<%--
  Created by IntelliJ IDEA.
  User: lalalala
  Date: 7/11/2018
  Time: 12:27
  To change this template use File | Settings | File Templates.
--%>
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
        <div class="col-3"><h3>Username1</h3></div>
        <div class="col-6"></div>
        <div class="col-3"><h3 class="float-right">Username2</h3></div>
    </div>
    <div class="row">
        <div class="col-3">
            <img src="Images/imageLeft.png" class="img-fluid border" alt="Responsive image">
        </div>
        <div class="col-3">
            <h3 align="center">Attack</h3>
            <br>
            <div class="float-right text-right">
                <div class="form-check">
                    <label class="form-check-label text-right" for="attHead">
                        Head
                    </label>
                    <input type="checkbox" value="" name="attackBox" onclick="checkMarked(this)" id="attHead">
                </div>
                <div class="form-check">
                    <label class="form-check-label" for="attHands">
                        Hands
                    </label>
                    <input type="checkbox" value="" id="attHands" name="attackBox" onclick="checkMarked(this)">
                </div>
                <div class="form-check">
                    <label class="form-check-label" for="attBody">
                        Body
                    </label>
                    <input type="checkbox" value="" id="attBody" name="attackBox" onclick="checkMarked(this)">
                </div>
                <div class="form-check">
                    <label class="form-check-label" for="attLegs">
                        Legs
                    </label>
                    <input type="checkbox" value="" id="attLegs" name="attackBox" onclick="checkMarked(this)">
                </div>
            </div>
        </div>
        <div class="col-3">
            <h3 align="center">Defence</h3>
            <br>
            <div class="form-check">
                <input type="checkbox" value="" id="defHead" name="defBox" onclick="checkMarked(this)">
                <label class="form-check-label" for="defHead">
                    Head
                </label>
            </div>
            <div class="form-check">
                <input type="checkbox" value="" id="defHands" name="defBox" onclick="checkMarked(this)">
                <label class="form-check-label" for="defHands">
                    Hands
                </label>
            </div>
            <div class="form-check">
                <input type="checkbox" value="" id="defBody" name="defBox" onclick="checkMarked(this)">
                <label class="form-check-label" for="defBody">
                    Body
                </label>
            </div>
            <div class="form-check">
                <input type="checkbox" value="" id="defLegs" name="defBox" onclick="checkMarked(this)">
                <label class="form-check-label" for="defLegs">
                    Legs
                </label>
            </div>
        </div>
        <div class="col-3">
            <img src="Images/imageRight.png" class="img-fluid border float-right" alt="Responsive image">
        </div>
    </div>
    <div class="row">
        <div class="col-3">
            <h5>HP: <span id="user1Hp">10</span></h5>

        </div>
        <div class="col-6" align="center">
            <button type="button" class="btn btn-lg btn-danger" id="endTurn">End Turn</button>
        </div>
        <div class="col-3">
            <h5 class="float-right">HP: <span id="user2Hp">10</span></h5>
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
<script src="Scripts/fight.js"></script>
</body>
</html>
