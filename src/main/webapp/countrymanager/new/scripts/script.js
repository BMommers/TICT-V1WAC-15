function initPage() {
    document.getElementById("confirmPUT").addEventListener("click", function() {
        var formData = new FormData(document.querySelector("#PUTcountryForm"));
        var encData = new URLSearchParams(formData.entries());

        fetch("https://localhost:8443/restservices/countries/new", {method: 'PUT', body: encData })
            .then(function(response) {return response;})
            .then(function(myJson) { console.log(myJson)})

    });
    document.getElementById("login").addEventListener("click", login);
    document.getElementById("logoutBtn").addEventListener("click", logout);
    if (window.sessionStorage.getItem("username") != null) {
        document.getElementById("loginstatus").innerHTML = "Logged in as " + window.sessionStorage.getItem("username");
        document.getElementById("logoutBtn").classList.remove("hidden")
    }
}



function logout() {
    window.sessionStorage.removeItem("sessionToken");
    window.sessionStorage.removeItem("username");
    document.getElementById("username").value = "";
    document.getElementById("password").value = "";
    document.getElementById("loginstatus").innerHTML = "NOT LOGGED IN";
    document.getElementById("logoutBtn").classList.add("hidden")
}


function login() {
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    var messageBox = document.getElementById("loginMessage");
    if (username === "" || password === "") {
        messageBox.innerHTML = "Voer een gebruikersnaam en wachtwoord in!";
        return
    }


    var formData = new FormData(document.querySelector("#loginForm"));
    var encData = new URLSearchParams(formData.entries());

    fetch("/restservices/authentication", {method: 'POST', body: encData})
        .then(function (response) {
            if (response.ok) return response.text();
            else throw "Wrong username/password";
        })
        .then(function (myText) {
            window.sessionStorage.setItem("sessionToken", myText);
            window.sessionStorage.setItem("username", username);
            document.getElementById("username").value = "";
            document.getElementById("password").value = "";
            document.getElementById("loginstatus").innerHTML = "Logged in as " + window.sessionStorage.getItem("username");
            document.getElementById("logoutBtn").classList.remove("hidden")
        })
        .catch(function (error) {
            console.log(error);
        });


}



initPage();