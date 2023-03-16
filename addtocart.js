function addToCart(productId) {
    // Send an HTTP POST request to the server to add the product to the cart
    console.log("addToCart function called with productId: ", productId);
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'addToCart', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onreadystatechange = function() {
      if (xhr.readyState === 4 && xhr.status === 200) {
        // If the request was successful, update the UI to reflect the cart state
        //updateCartCount();
        alert('Product added to cart!');
      }
    };
    xhr.send('productId=' + encodeURIComponent(productId));
  }
  
