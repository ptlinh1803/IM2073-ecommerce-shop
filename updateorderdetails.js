document.getElementById("checkout-btn").addEventListener("click", function() {
    // Retrieve the values from the table
    var rows = document.querySelectorAll("#product-table tbody tr");
    var quantities = [];
    var subtotals = [];
    for (var i = 0; i < rows.length; i++) {
      var cells = rows[i].querySelectorAll("td");
      quantities.push(cells[4].querySelector("input").value);
      subtotals.push(cells[5].textContent);
    }
  
    // Create a new XMLHttpRequest to update the order_details table
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "update-order-details", true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function() {
      if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
        console.log(xhr.responseText); // Display success message in console
      }
    };
    xhr.send("quantities[]=" + encodeURIComponent(JSON.stringify(quantities)) + "&subtotals[]=" + encodeURIComponent(JSON.stringify(subtotals)));
  });
  