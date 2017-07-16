# Automation

What it does : Orders products from amazon with default delivery method. You can tweak the code for your custom needs.

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
