rem ***************************************
rem ** setting up the environment
rem ***************************************

#add customer
curl -X POST "http://localhost:8080/customers/add?name=Ericsson"

#add product
curl -X POST "http://localhost:8080/products/add?name=switchboard-00-001&qtty=5&value=1289.25"
curl -X POST "http://localhost:8080/products/add?name=mainController-99-001&qtty=2&value=5986.50"

#create order
curl -X POST "http://localhost:8080/orders/add?customerId=1&orderNb=FFF20240624001"

#add product to order
curl -X POST "http://localhost:8080/orders/FFF20240624001/addProduct?productNumber=switchboard-00-001&quantity=1"
curl -X POST "http://localhost:8080/orders/FFF20240624001/addProduct?productNumber=mainController-99-001&quantity=1"

pause
curl -X POST "http://localhost:8080/orders/FFF20240624001/addProduct?productNumber=mainController-00-001&quantity=1"
pause