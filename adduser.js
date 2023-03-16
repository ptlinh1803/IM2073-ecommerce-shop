function submitForm() {
    var form = document.getElementById("cart-form");
    var xhr = new XMLHttpRequest();
    xhr.open(form.method, "add-user", true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            window.alert(xhr.responseText); // display success message in a popup
        }
    };
    xhr.send(new FormData(form));
}
