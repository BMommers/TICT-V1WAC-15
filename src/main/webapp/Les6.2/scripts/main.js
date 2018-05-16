window.addEventListener('storage', function() {
    document.querySelector('#textlabel').innerHTML = window.localStorage.getItem('content')
});