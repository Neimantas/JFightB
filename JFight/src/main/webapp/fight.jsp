<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
    <link rel="stylesheet" href="Styles/styles.css">
</head>
<body>
<%--<img src="Images/cirith_toronath__gondolin__by_dracarysdrekkar7-dc44ctg.jpg">--%>
<div class="container">
    <div class="row">
        <div class="col-3"><h2 id="userName">${userName}</h2></div>
        <div class="col-6">
            <h3 align="center" id="round">Round: ${round}</h3>
            <h2 align="center">Turn ends in: <span id="roundTime">30s</span></h2>
        </div>
        <div class="col-3"><h2 class="float-right" id="oppName">${oppName}</h2></div>
    </div>
    <div class="row">
        <div class="col-4">
            <img src="Images/knight_left.jpg" class="img-fluid border" alt="Responsive image">
        </div>
        <div class="col-2">
            <div class="float-right text-right">
                <br>
                <h3 class="form-check">Attack</h3>
                <div class="form-check">
                    <label class="form-check-label text-right" for="attHEAD">
                        Head
                    </label>
                    <input type="checkbox" value="" id="attHEAD" name="attackBox" onclick="checkMarked(this)">
                </div>
                <div class="form-check">
                    <label class="form-check-label" for="attHANDS">
                        Hands
                    </label>
                    <input type="checkbox" value="" id="attHANDS" name="attackBox" onclick="checkMarked(this)">
                </div>
                <div class="form-check">
                    <label class="form-check-label" for="attBODY">
                        Body
                    </label>
                    <input type="checkbox" value="" id="attBODY" name="attackBox" onclick="checkMarked(this)">
                </div>
                <div class="form-check">
                    <label class="form-check-label" for="attLEGS">
                        Legs
                    </label>
                    <input type="checkbox" value="" id="attLEGS" name="attackBox" onclick="checkMarked(this)">
                </div>
            </div>
        </div>
        <div class="col-2">
            <br>
            <h3 class="form-check">Defence</h3>
            <div class="form-check">
                <input type="checkbox" value="" id="defHEAD" name="defBox" onclick="checkMarked(this)">
                <label class="form-check-label" for="defHEAD">
                    Head
                </label>
            </div>
            <div class="form-check">
                <input type="checkbox" value="" id="defHANDS" name="defBox" onclick="checkMarked(this)">
                <label class="form-check-label" for="defHANDS">
                    Hands
                </label>
            </div>
            <div class="form-check">
                <input type="checkbox" value="" id="defBODY" name="defBox" onclick="checkMarked(this)">
                <label class="form-check-label" for="defBODY">
                    Body
                </label>
            </div>
            <div class="form-check">
                <input type="checkbox" value="" id="defLEGS" name="defBox" onclick="checkMarked(this)">
                <label class="form-check-label" for="defLEGS">
                    Legs
                </label>
            </div>
        </div>
        <div class="col-4">
            <img src="Images/knight_right.jpg" class="img-fluid border float-right" alt="Responsive image">
        </div>
    </div>
    <div class="row">
        <div class="col-3">
            <h4>HP: <span id="userHp">${userHp}</span></h4>

        </div>
        <div class="col-6" align="center">
            <button type="button" class="btn btn-lg btn-danger" id="endTurn">End Turn</button>
        </div>
        <div class="col-3">
            <h4 class="float-right">HP: <span id="oppHp">${oppHp}</span></h4>
        </div>
    </div>
    <div class="row">
        <div class="col" align="center">
            <br>
            <p>${log}</p>
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
