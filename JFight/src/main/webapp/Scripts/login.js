// window.addEventListener("load", function () {
//     // Access the form element...
//     var form = document.getElementById("myForm");
//     // ...and take over its submit event.
//     form.addEventListener("submit", function (event) {
//         event.preventDefault();
//
//     });
// });

var loginCon = document.getElementById("loginContainer"),
    registerCon = document.getElementById("registerContainer"),
    goToReg = document.getElementById("goToReg"),
    goToLog = document.getElementById("goToLog");

goToReg.onclick = function() {
    loginCon.classList.add('fadeout');
    setTimeout(function() {
        loginCon.classList.add('hide');
        loginCon.classList.remove('fadeout');
        registerCon.classList.remove('hide');
    }, 300);
};

goToLog.onclick = function() {
    registerCon.classList.add('fadeout');
    setTimeout(function() {
        registerCon.classList.add('hide');
        registerCon.classList.remove('fadeout');
        loginCon.classList.remove('hide');
    }, 300);
};