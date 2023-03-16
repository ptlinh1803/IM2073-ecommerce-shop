function deleteFromCart(productId) {
    // Send an HTTP POST request to the server to add the product to the cart
    console.log("deleteFromCart function called with productId: ", productId);
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'deleteFromCart', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onreadystatechange = function() {
      if (xhr.readyState === 4 && xhr.status === 200) {
        // If the request was successful, update the UI to reflect the cart state
        //updateCartCount();
        alert('Product deleted from cart! Press OK to RELOAD and check the QUANTITY of other products again!');
            setTimeout(function() {
                window.location.reload();
            }, 100); // Reload the page after 0.1 seconds (100 milliseconds)
      }
    };
    xhr.send('productId=' + encodeURIComponent(productId));
  }
  
