var featuredContainer = document.getElementById("product1");
var searchForm = document.querySelector(".search-bar");

searchForm.addEventListener("submit", function(event) {
  event.preventDefault();
  var searchQuery = document.getElementsByName("searchproduct")[0].value;
  loadSearchProducts(searchQuery);
});

function loadSearchProducts(query) {
  var xhr = new XMLHttpRequest();
  xhr.open('GET', 'search-product?searchproduct=' + encodeURIComponent(query));
  xhr.onload = function() {
    if (xhr.status === 200) {
      var data = JSON.parse(xhr.responseText);
      renderHTML(data);
    } else {
      console.log('Request failed. Returned status of ' + xhr.status);
    }
  };
  xhr.send();
}

function renderHTML(data) {
  var htmlString = "";
  htmlString += "<div class=\"pro-container\">";

  for (i = 0; i < data.length; i++) {
    htmlString += "<div class='pro'>";
    htmlString += "<img src=\"img/artproducts/" + data[i].id + ".jpg\" alt=\"\">";
    htmlString += "<div class=\"des\">";
    htmlString += "<span>" + data[i].brand + "</span>";
    htmlString += "<h6><i>" + data[i].category + "</i></h6>";
    htmlString += "<h5>" + data[i].name + "</h5>";
    htmlString += "<div class=\"star\">";
    htmlString += "<i class=\"fas fa-star\"></i>";
    htmlString += "<i class=\"fas fa-star\"></i>";
    htmlString += "<i class=\"fas fa-star\"></i>";
    htmlString += "<i class=\"fas fa-star\"></i>";
    htmlString += "<i class=\"fas fa-star\"></i>";                   
    htmlString += "</div>";
    htmlString += "<h4>" + data[i].price + "</h4>";
    htmlString += "</div>";
    //htmlString += "<a href=\"#\"><i class=\"fal fa-shopping-cart cart\"></i></a>";
    htmlString += `<a href="#" onclick="addToCart('${data[i].id}')"><i class="fal fa-shopping-cart cart"></i></a>`;
    htmlString += "</div>";
  }
  htmlString += "</div>";

  featuredContainer.innerHTML = htmlString;
}
