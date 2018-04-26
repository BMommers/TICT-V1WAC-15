function inputLogger() {
    var newValue = document.querySelector("#myID").value;
    if (oldValue !== newValue) {
        console.log(newValue);
        oldValue = newValue
    }
}
var oldValue = "";
setInterval(inputLogger, 5000);