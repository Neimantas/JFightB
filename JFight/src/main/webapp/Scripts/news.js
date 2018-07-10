var fightButton = document.getElementById("fightButton"),
    infoButton = document.getElementById("infoButton");

fightButton.onclick = function () {
    location.href = "/news?FIGHT";
};

infoButton.onclick = function () {
    location.href = "/user";
};