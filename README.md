# Amazon Order Automation



Program that automatically buys something on Amazon site.

Input parameters:
- Username
- Password
- Article URL
- Max Price
- Delivery Name
- Delivery Street
- Delivery City
- Delivery Zip
- Delivery Phone


The script will check if there are cookies known for the username and if yes, re-use them to prevent re-login. If no cookie or any login is required in between, it will just login with given credentials.

After login, it will first go to page https://www.amazon.de/gp/cart/view.html/ref=nav_cart and verify that NO article is in basket already. If there is anything in basket, script will terminate with error code.

Then the script checks the price of the article. If it is higher "Max Price", it will cancel with error code.



All Error/Success messages will be printed in the console with prefix :  ==REQUEST==

Error Message : Invalid Arguments. Provide username,password, property file name (relative path) and cookie file name, order properties file name(optional) as arguments
Reason : Runtime arguments provided is wrong.

Error Message : INVALID CHROME BINARY/DRIVER LOCATION
Reason. chromedriver/chrome executable location provided in the config.properties is invalid.

Error Message : Payment Method Unavailable
Reason : Gift Voucher option is not present 

Error Message : Price higher than Max Price
Reason : Price higher than what was given in order.properties

Error Message : Article not available
Reason : Either Article is not in stock or article URL did not load properly

Error Message : Basket is not empty
Reason : User's basket is not empty

Error Message : !!!!LOGIN FAILED IN SELENIUM !!!!! 
Reason : Username/password is incorrect


Error Message : Exception [with stack trace or single line message]
Reason: Uncaught error while executing


Final Success Message:

ORDER_URL::https://www.amazon.de/gp/css/summary/edit.html/ref=typ_rev_edit?ie=UTF8&orderID=302-0010732-9069141
ORDER_NUMBER::302-0010732-9069141
ORDERED ITEM SUCCESSFULLY



How to Run :  
Add chrome binary location and chrome driver location in config.properties
like this
CHROME_BINARY_LOCATION=C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe
CHROME_DRIVER_LOCATION=C:\\Selenium\\chromedriver.exe

CHROME_DRIVER_LOCATION is specific to the chrome version you're using.
chromedriver.exe in my RDP will work only for chrome 43.0. For latest chrome version, you can download the chromedriver.exe from this link
https://sites.google.com/a/chromium.org/chromedriver/downloads

Provide order details in config.properties and with below command

java -jar AmazonOrder.jar amazon_email_id amazon_pwd "config.properties" "cookies.txt" "order.properties"



