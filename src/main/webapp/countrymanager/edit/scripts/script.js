var countryCode;

function initPage() {
    countryCode = window.location.search.substring(1).split("=")[1];
    document.getElementById("confirmUpdate").addEventListener("click", function() {
        var formData = new FormData(document.querySelector("#PUTcountryForm"));
        console.log(formData);
        var encData = new URLSearchParams(formData.entries());

        fetch("https://localhost:8443/restservices/countries/"+countryCode, {method: 'PUT', body: encData })
            .then(function(response) {return response;})
            .then(function(myJson) { console.log(myJson)})

    });
    initData();
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


function initData() {
    var API = '/restservices/countries/' + countryCode;
    fetch(API)
        .then(function (response) {
            console.log(response);
            return response.json();
        })
        .then(function (country) {
                console.log(country);
                document.getElementById("Title").innerHTML = "Pas aan: " + country.name;
                document.getElementById("countryName").value = country.name;
                document.getElementById("countryCap").value = country.capital;
                document.getElementById("countrySurface").value = country.surface;
                document.getElementById("countryPopulation").value = country.population;

            }
        )
}

function updateCountry() {
    console.log("update")
    fetch(" https://localhost:8443/restservices/countries/" + id, {method: 'DELETE'})
        .then(function (response) {
            console.log(response);
            if (response.ok) // response-status = 200 OK
                console.log("Country deleted!");
            else if (response.status === 404)
                console.log("Country not found!");
            else console.log("Cannot delete country!");
            initPage();
        })
}


initPage();