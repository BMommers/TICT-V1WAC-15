firstNum = undefined;
operator = undefined;
display = "0";
clr = true;



function updateDisplay() {
    document.querySelector("#display").innerHTML = display;
}

function addToDisplay(num) {
    num = num.toString();
    if (clr) {
        display = num;
        clr = false;
    }
    else {
        display += num;
    }
    updateDisplay();
}

function applyOperator(op) {
    firstNum = display;
    clr = true;
    operator = op;
    updateDisplay();
}

function clear() {
    firstNum = undefined;
    operator = undefined;
    clr = true;
    display = "0";
    updateDisplay();
}

function calculate() {
    var n1 = parseInt(firstNum);
    var n2 = parseInt(display);
    if (operator === "+") {
        display = n1 + n2;
    } else if (operator === "-") {
        display = n1 - n2;
    } else if (operator === "*") {
        display = n1 * n2;
    } else if (operator === "/") {
        display = n1 / n2;
    }
    updateDisplay();
    clr = true;
    firstNum = undefined;
    operator = undefined;
}

var btn_1 = document.querySelector("#btn_1");
var btn_2 = document.querySelector("#btn_2");
var btn_3 = document.querySelector("#btn_3");
var btn_4 = document.querySelector("#btn_4");
var btn_5 = document.querySelector("#btn_5");
var btn_6 = document.querySelector("#btn_6");
var btn_7 = document.querySelector("#btn_7");
var btn_8 = document.querySelector("#btn_8");
var btn_9 = document.querySelector("#btn_9");
var btn_0 = document.querySelector("#btn_0");
var btn_plus = document.querySelector("#btn_plus");
var btn_min = document.querySelector("#btn_min");
var btn_prod = document.querySelector("#btn_prod");
var btn_div = document.querySelector("#btn_div");
var btn_eq = document.querySelector("#btn_eq");
var btn_clear = document.querySelector("#btn_clear");


btns_num = [btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9];

btns_num.forEach(function(btn) {
    btn.addEventListener("click", function () {
        addToDisplay(btn.innerHTML)
    });
});

btns_op = [btn_plus, btn_min, btn_prod, btn_div];
btns_op.forEach(function(btn) {
    btn.addEventListener("click", function () {
        applyOperator(btn.innerHTML)
    });
});

btn_clear.addEventListener("click", clear);
btn_eq.addEventListener("click", calculate);


