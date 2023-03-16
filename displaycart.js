var tableContainer = document.getElementById("table-body");

function displayCart() {
  var xhr = new XMLHttpRequest();
  xhr.open('GET', 'displayCart');
  xhr.onload = function() {
    if (xhr.status === 200) {
      var data = JSON.parse(xhr.responseText);
      if (data) {
        renderHTML(data);
      }
    } else {
      console.log('Request failed. Returned status of ' + xhr.status);
    }
    /*if (xhr.status === 200) {
        console.log(xhr.responseText);
        var data = JSON.parse(xhr.responseText);
        renderHTML(data);
      } else {
        console.log('Request failed. Returned status of ' + xhr.status);
      }*/
  };
  xhr.send();
}

function renderHTML(data) {
    var htmlString = "";
  
    for (i = 0; i < data.length; i++) {
        var productName = data[i].name;
        var productNameWords = productName.split(" ");
        var truncatedProductNameWords = productNameWords.slice(0, 5);
        var truncatedProductName =
        truncatedProductNameWords.join(" ") + (productNameWords.length > 5 ? "..." : "");
        
        htmlString += "<tr>";
        htmlString += `<td><a href="#" onclick="deleteFromCart('${data[i].id}')"><i class="far fa-times-circle"></i></a></td>`;
        htmlString += `<td><img src="img/artproducts/${data[i].id}.jpg" alt=""></td>`;
        htmlString += `<td>${truncatedProductName}</td>`;
        htmlString += "<td>" + data[i].price + "</td>";
        htmlString += `<td><input type="number" value="1" name="" id="input-${data[i].id}"></td>`;
        htmlString += "<td></td>";
        htmlString += "</tr>";
    }
    tableContainer.innerHTML = htmlString;

    for (i = 0; i < data.length; i++) {
        const product = data[i];
        const inputElement = document.getElementById(`input-${product.id}`);

        inputElement.addEventListener("input", function (event) {
            inputElement.value = event.target.value;
            const priceString = product.price;
            const priceWithoutDollarSign = priceString.slice(1);
            const priceFloat = parseFloat(priceWithoutDollarSign);
            const subtotal = priceFloat * inputElement.value;
            const subtotalElement = inputElement.parentElement.nextElementSibling;
            subtotalElement.textContent = subtotal.toFixed(2);

            // Get the Cart Subtotal and Total elements
            const finalsubtotalElement = document.querySelector("#subtotal table tr:first-child td:last-child");
            const totalElement = document.querySelector("#subtotal table tr:last-child td:last-child");

            // Calculate the new subtotal and update its cell
            let finalsubtotal = 0;
            document.querySelectorAll("#product-table tbody tr").forEach((row) => {
                finalsubtotal += parseFloat(row.children[5].textContent);
            });
            finalsubtotalElement.textContent = "$ " + finalsubtotal.toFixed(2);

            // Calculate the new total and update its cell
            const shippingCost = 0; // Assume shipping is free
            const total = finalsubtotal + shippingCost;
            totalElement.textContent = "$ " + total.toFixed(2);
        });

        const priceString = product.price;
        const priceWithoutDollarSign = priceString.slice(1);
        const priceFloat = parseFloat(priceWithoutDollarSign);
        const subtotal = priceFloat;
        const subtotalElement = inputElement.parentElement.nextElementSibling;
        subtotalElement.textContent = subtotal.toFixed(2);
    }

    // Get the Cart Subtotal and Total elements
    const finalsubtotalElement = document.querySelector("#subtotal table tr:first-child td:last-child");
    const totalElement = document.querySelector("#subtotal table tr:last-child td:last-child");

    // Calculate the new subtotal and update its cell
    let finalsubtotal = 0;
    document.querySelectorAll("#product-table tbody tr").forEach((row) => {
        finalsubtotal += parseFloat(row.children[5].textContent);
    });
    finalsubtotalElement.textContent = "$ " + finalsubtotal.toFixed(2);

    // Calculate the new total and update its cell
    const shippingCost = 0; // Assume shipping is free
    const total = finalsubtotal + shippingCost;
    totalElement.textContent = "$ " + total.toFixed(2);
}  

displayCart();

window.onload = displayCart();


