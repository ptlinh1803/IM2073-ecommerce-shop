# IM2073-ecommerce-shop

This is a group project for IM2073 - Introduction to Design and Project, Sem 2 AY22/23 done by 2 NTU students:
- Pham Thuy Linh (IEM/Y2)
- Tran Huu Nghia (EEE/Y2)

## Project description
This is a full-stack e-commerce website project for an art supplies shop that provides users with an easy-to-use shopping experience. The website includes a home page, shop page, contact page, and cart page.

## Technology used
The following technologies were used in the development of this project:
- HTML
- CSS
- JavaScript
- Java Servlet
- Tomcat
- MySQL database
- AJAX

## Project Features
The website has the following key features:
- The home and shop pages display products from a MySQL database, with the MySQL query returning a JSON file that is processed by AJAX and JavaScript on the frontend.
- The shop page allows users to search for products by name and brand, as well as filter products by brand, category, and price range.
- Users can leave messages via a form, which are automatically sent to the website owner's email.
- The cart page automatically updates when a user adds a product, displaying the new products, allowing users to change the quantity, displaying the real-time price, and letting them check out by filling in their personal information, such as name, phone number, email, and address.

## Project preview
https://drive.google.com/file/d/1sNd6bS_UqMHwxsOLFIfRuUUrMxZ1vTY1/view?usp=sharing

## How to run the project locally
- Download and install Tomcat and MySQL.
- Clone the repository to your local machine.
- Create a MySQL database with 4 tables: products (id, name, category, price, brand), cart (id, name, category, price, brand), user_details (email, name, phone, address, create_at), order_details (product_id, quantity, subtotal, user_email, order_date).
- Start Tomcat and deploy the project on the server.
- Open a web browser and go to http://localhost:<port>/im2073-shop.

## References:
- Front end: https://youtu.be/P8YuWEkTeuE
- Back end: https://www3.ntu.edu.sg/home/ehchua/programming/index.html
