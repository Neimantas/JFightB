var userName = document.getElementById("userName");

// function sendChallenge(opp) {
//     location.href = "/challenge?userName=" + userName.value() + "&oppName=" + opp.value();
// }

var challengedPlayers = document.getElementsByName("player"),
    challengeBtn = document.getElementById("challengeBtn");

// function checkMarked(cb) {
//     var cBoxes = document.getElementsByName(cb.name);
//     var limit = 2;
//     var count = 0;
//     for (var i = 0; i < cBoxes.length; i++) {
//         if (cBoxes.item(i).checked === true) {
//             count++;
//         }
//     }
//     if (count > limit) {
//         cb.checked = false;
//         alert("Please check only two boxes for Attack/Defence");
//     }
// }

challengeBtn.onclick = function() {
    var url = "";

    for (var i = 0; i < challengedPlayers.length; i++) {
        if (challengedPlayers.item(i).checked === true) {
            url += challengedPlayers.item(i).value + "#";
        }
    }
    if (url !== "") {
        location.href = "?challengedPlayers=" + url;
    } else {
        alert("Please select at least one player to challenge!");
    }
};







// window.setInterval(function(){
//     function check() {
//         if (getParam("userName") !== -1) {
//             location.href = "/challenge?refresh=1";
//         }
//     }
// }, 2000);

function getParam(parameter) {
    var urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has(parameter)) {
        return urlParams.get(parameter);
    } else {
        return -1;
    }
}

