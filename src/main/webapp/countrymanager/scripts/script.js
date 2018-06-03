var countries = false;

var res;

var currentSortField;
var sortAsc = true;


function initPage() {
    console.log('initpage');
    loadCountries();


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

function loadCountries() {
    emptyCountryTable();
    var filter = document.querySelector("#filterTerm").value;
    if (filter === '' || filter === undefined) {
        var API = '/restservices/countries';
    }
    else {
        var API = '/restservices/countries/filter/' + filter;
    }
    fetch(API)
        .then(function(response) {
            return response.json();
        })
        .then(function(myJson) {
            countries = myJson;
            window.sessionStorage.setItem("Countries", JSON.stringify(countries));

            var even = true;
            for (const country of countries) {
                var viewInfo = document.createElement("tr");


                var land = document.createElement("td");
                land.appendChild(document.createTextNode(country.name));
                var hoofdstad = document.createElement("td");
                hoofdstad.appendChild(document.createTextNode(country.capital));
                var regio = document.createElement("td");
                regio.appendChild(document.createTextNode(country.region));
                var oppervlakte = document.createElement("td");
                oppervlakte.appendChild(document.createTextNode(country.surface));
                var inwoners = document.createElement("td");
                inwoners.appendChild(document.createTextNode(country.population));

                var pasAan = document.createElement("td");
                // pasAan.classList.add('centered', 'clickable');
                var pasAanBtn = document.createElement('a');
                pasAanBtn.classList.add('centered', 'clickable');
                pasAanBtn.innerHTML = "edit";
                pasAanBtn.href = ("/countrymanager/edit?id=" + country.code);
                pasAan.appendChild(pasAanBtn);


                var verwijder = document.createElement("td");
                verwijder.classList.add('centered', 'clickable');
                var verwijderTxt = document.createTextNode('delete');
                verwijder.addEventListener("click", function () {
                    deleteCountry(country.name, country.code)
                });

                verwijder.appendChild(verwijderTxt);
                viewInfo.appendChild(land);
                viewInfo.appendChild(hoofdstad);
                viewInfo.appendChild(regio);
                viewInfo.appendChild(oppervlakte);
                viewInfo.appendChild(inwoners);
                viewInfo.appendChild(pasAan);

                viewInfo.appendChild(verwijder);


                var land = document.createElement("td");
                land.appendChild(document.createTextNode(country.name));
                var hoofdstad = document.createElement("td");
                hoofdstad.appendChild(document.createElement("input"));
                hoofdstad.type = "text";
                var regio = document.createElement("td");
                regio.appendChild(document.createTextNode(country.region));
                var oppervlakte = document.createElement("td");
                oppervlakte.appendChild(document.createElement("input"));
                oppervlakte.type = "text";
                var inwoners = document.createElement("td");
                inwoners.appendChild(document.createElement("input"));
                inwoners.type = "text";


                var empty = document.createElement("td");

                if (even) {
                    viewInfo.classList.add("even");
                    even = false;
                } else {
                    viewInfo.classList.add("odd");
                    even = true;
                }


                document.querySelector("#countries").appendChild(viewInfo);
                // document.querySelector("#countries").appendChild(editInfo);
            }
        })
}

function filterCountries() {
    loadCountries();
}


function emptyCountryTable() {
    var elmtTable = document.getElementById('countries');
    elmtTable.innerHTML = "";
}

function sortCountries (index) {
    var table, rows, switching, i, x, y, shouldSwitch;
    table = document.getElementById("countryinfo");
    switching = true;
    /* Make a loop that will continue until
    no switching has been done: */
    while (switching) {
        // Start by saying: no switching is done:
        switching = false;
        rows = table.getElementsByTagName("TR");
        /* Loop through all table rows (except the
        first, which contains table headers): */
        for (i = 1; i < (rows.length - 1); i++) {
            // Start by saying there should be no switching:
            shouldSwitch = false;
            /* Get the two elements you want to compare,
            one from current row and one from the next: */
            x = rows[i].getElementsByTagName("TD")[index];
            y = rows[i + 1].getElementsByTagName("TD")[index];
            // Check if the two rows should switch place:
            if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                // If so, mark as a switch and break the loop:
                shouldSwitch = true;
                break;
            }
        }
        if (shouldSwitch) {
            /* If a switch has been marked, make the switch
            and mark that a switch has been done: */
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
        }
    }
}

function deleteCountry (name, id) {
    if (confirm("Wilt u " + name + " echt verwijderen?")) {
        fetch(" https://localhost:8443/restservices/countries/" + id, {method: 'DELETE', headers : {'Authorization': 'Bearer ' + window.sessionStorage.getItem("sessionToken") }})
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
}



initPage();
// loadCountries();