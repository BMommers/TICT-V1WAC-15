document.querySelector("#textbox").addEventListener('keyup', function() {
    window.localStorage.setItem('content', document.querySelector("#textbox").value)
});