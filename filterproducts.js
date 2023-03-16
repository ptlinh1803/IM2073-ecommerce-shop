function filterProducts() {
    const productTypeFilter = document.getElementById("product-type-filter").value;
    const brandFilter = document.getElementById("brand-filter").value;
    const priceRangeFilter = document.getElementById("price-range-filter").value;
  
    // Get all the products
    const products = document.querySelectorAll(".pro");
  
    // Loop through each product and hide/show based on the selected filters
    products.forEach((product) => {
        const brand = product.querySelector(".des span").textContent;
        const category = product.querySelector(".des h6").textContent;
        const price = parseFloat(product.querySelector(".des h4").textContent.slice(1));
  
        let isVisible = true;
  
        if (productTypeFilter && category !== productTypeFilter) {
            isVisible = false;
        }
  
        if (brandFilter && brand !== brandFilter) {
            isVisible = false;
        }
  
        if (priceRangeFilter) {
            const [minPrice, maxPrice] = priceRangeFilter.split("-").map(parseFloat);
  
            if (price < minPrice || price > maxPrice) {
                isVisible = false;
            }
        }
  
        if (isVisible) {
            product.style.display = "block";
        } else {
            product.style.display = "none";
        }
    });
  }

const productTypeFilter = document.getElementById("product-type-filter");
productTypeFilter.addEventListener("change", filterProducts);

const brandFilter = document.getElementById("brand-filter");
brandFilter.addEventListener("change", filterProducts);

const priceRangeFilter = document.getElementById("price-range-filter");
priceRangeFilter.addEventListener("change", filterProducts);
