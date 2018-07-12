var attBoxes = document.getElementsByName("attackBox"),
    defBoxes = document.getElementsByName("defBox"),
    endBtn = document.getElementById("endTurn");

function checkMarked(cb) {
    var cBoxes = document.getElementsByName(cb.name);
    var limit = 2;
    var count = 0;
    for (var i = 0; i < cBoxes.length; i++) {
        if (cBoxes.item(i).checked === true) {
            count++;
        }
    }
    if (count > limit) {
        cb.checked = false;
        alert("Please check only two boxes for Attack/Defence");
    }
}

endBtn.onclick = function() {
    var url = "";
    for (var i = 0; i < attBoxes.length; i++) {
        if (attBoxes.item(i).checked === true) {
            url += "&" + attBoxes.item(i).id + "=1";
        }
    }
    for (var i = 0; i < defBoxes.length; i++) {
        if (defBoxes.item(i).checked === true) {
            url += "&" + defBoxes.item(i).id + "=1";
        }
    }
    location.href = "?" + url;
};