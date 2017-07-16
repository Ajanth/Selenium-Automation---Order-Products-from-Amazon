package com.gmi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gmi.vo.OrderInput;
import com.src.DriverUtils;
import com.src.SeleniumUtils;

public class AmazonOrder extends DriverUtils{
	By USERNAME = By.cssSelector("#ap_email");
	By PASSWORD = By.cssSelector("#ap_password");
    By SIGNIN_BUTTON = By.cssSelector("#signInSubmit");
	By LOGIN_LINK = By.cssSelector("#nav-flyout-ya-signin>a");
	By MYACC_LINK = By.cssSelector("#nav-link-yourAccount");

	By PRICE_TAG = By.cssSelector("span#priceblock_ourprice");

	By ADD_TO_BASKET_BTN = By.cssSelector("#add-to-cart-button");
	By BASKET_ITEMS = By.cssSelector("form#activeCartViewForm div.sc-list-item");
			
	
	
	By PROCEED_CHECKOUT = By.cssSelector("#hlb-ptc-btn-native");
	
	By FULL_NAME = By.cssSelector("#enterAddressFullName");
	By ADDR_LINE2 = By.cssSelector("#enterAddressAddressLine2");
	By ADDR_CITY = By.cssSelector("#enterAddressCity");
	By ADDR_STATE = By.cssSelector("#enterAddressStateOrRegion");
	By ADDR_ZIPCODE = By.cssSelector("#enterAddressPostalCode");
	By ADDR_PHONE = By.cssSelector("#enterAddressPhoneNumber");
	By DELIVER_BTN = By.cssSelector("input[name='shipToThisAddress']");
	By CONTINUE_BTN = By.cssSelector("span.continue-button input[type='submit']");		
	By ADDR_CHANGE_LINK = By.cssSelector("#addressChangeLinkId");
	By NEW_ADDR_LINK = By.cssSelector("#add-new-address-popover-link");
	
	By SEND_TO_ADDR_BTN = By.cssSelector("span[data-action='add-address-popover-submit'] input.a-button-input");
	
	By GIFT_RADIO = By.cssSelector("#pm_gc_radio");
	
	By USE_THIS_PAYMENT = By.cssSelector("input#continue-top");
	
	By SHIPPING_METHOD_RADIO = By.cssSelector("input[type='radio'][name='order0']");
	
	By FINAL_BUY_NOW = By.cssSelector("input[name='placeYourOrder1']");
	
	By ORDER_CONFIRM_URL = By.cssSelector("a[href*='typ_rev_edit?ie=UTF8&orderID=']");
	
	By ORDER_NUMBER = By.cssSelector("#orders-list span.shipment>b");
	By ORDER_NUMBER_ALT = By.cssSelector("span[id*='order-number-']");
	
	By GIFT_POPUP_LINK = By.cssSelector("a.gift-popover-link");
	By GIFT_TEXT_AREA = By.cssSelector("textarea[name='gift-message-text']");
	By SAVE_GIFT_OPTION = By.cssSelector("span.set-gift-options-button input[type='submit']");
	
	By ADDR_POPOVER_FORM = By.cssSelector("#address-popover-suggestions-form");
	By USE_THIS_ADDRESS = By.cssSelector("input[name='useSelectedAddress']");
	By ADDR_POPOVER_FIRST_RADIO = By.cssSelector("input[name='addr'][value='addr_0']");
	
	String cartUrl = "https://www.amazon.de/gp/cart/view.html/ref=nav_cart";
	String giftText = "Viel Spaß mit Ihrem Artikel wünscht Ihnen SalesPier!";
	String baseUrl = "https://www.amazon.de";

	
	/**
     * Code starts executing from below
     * Provide username,password, property file name (relative path) , cookie file and order input file name as arguments
     * @param args
     * 
     */
	
	
    public static void main(String[] args){
    	AmazonOrder orderObj = new AmazonOrder();
    	try{
    		WebDriver _driver = orderObj.getDriver(args);
    		if(_driver!=null){
    			SeleniumUtils utils = new SeleniumUtils(_driver);
    			orderObj.initiateLogin(utils);
    			orderObj.orderProduct(utils,orderObj.getOrderInputFromMap(args[4]));
    		}
    	} catch(Exception ex){
    		System.out.println(ex);
    	}
    }
    
	/**
     *  User defined login script starts below
     *  @Param : Selenium Utils object created in main method
     */
	@Override
	public void initiateLogin(SeleniumUtils utils) throws Exception{
		
		
		
		
		_driver.get(baseUrl);
		
		if(cookieFileLocation!=null && !cookieFileLocation.isEmpty()){
			System.out.println("Try to load cookies from file");
			loadCookiesFromFile(cookieFileLocation);
		}
		_driver.get(baseUrl);
		Thread.sleep(10000);
		boolean loggedInAlready = false;
		try{
			new WebDriverWait(_driver, 30).until(ExpectedConditions.presenceOfElementLocated(MYACC_LINK));
        	new WebDriverWait(_driver, 30).until(ExpectedConditions.presenceOfElementLocated(LOGIN_LINK));
        	loggedInAlready = false;
        } catch(org.openqa.selenium.TimeoutException ex){
        	System.out.println("Logged in already");
        	loggedInAlready = true;
        	
        }
		
		if(!loggedInAlready){
			utils.scrollToLoginAndClick(MYACC_LINK,LOGIN_LINK);
	        
			utils.waitForElementToBeVisible(USERNAME);
			
			System.out.println("SIGNIN_PAGE: Entering username: " + username);
			utils.waitForElementToBeVisible(USERNAME);
	        utils.sendKeys(USERNAME, username);
	        
	        System.out.println("SIGNIN_PAGE: Entering password.");
	        utils.waitForElementToBeVisible(PASSWORD);
	        utils.sendKeys(PASSWORD, password);
	        

	        
	        System.out.println("SIGNIN_PAGE: Clicking the [SIGN_IN] button.\n");
	        utils.scrollToThenClick(SIGNIN_BUTTON);
	        
	        
	        try{
	        	new WebDriverWait(_driver, 30).until(ExpectedConditions.invisibilityOfElementLocated(SIGNIN_BUTTON));
	        	
	        } catch(org.openqa.selenium.TimeoutException ex){
	        	completeOperation(-1);
	        }
		}
		
	}
	
	
	private OrderInput getOrderInputFromMap(String fileName){
		OrderInput orderInput = new OrderInput();
		
		System.out.println("Begin load OrderInputs from "+fileName);
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream(fileName);
			prop.load(input);
			Set<Object> keys = prop.keySet();
			for(Object obj : keys){
				String str = (String) obj;
	        	System.out.println("Key "+str +" - "+prop.getProperty(str));
	        }
			orderInput.setArticleUrl(prop.getProperty("ArticleURL"));
			orderInput.setDeliveryName(prop.getProperty("DeliveryName"));
			orderInput.setDeliveryPhone(prop.getProperty("DeliveryPhone"));
			orderInput.setDeliveryStreet(prop.getProperty("DeliveryStreet"));
			orderInput.setDeliveryCity(prop.getProperty("DeliveryCity"));
			orderInput.setDeliveryZip(prop.getProperty("DeliveryZip"));
			orderInput.setMaxPrice(prop.getProperty("MaxPrice"));		
			orderInput.setDeliveryState(prop.getProperty("DeliveryCounty"));
			// load a properties file
			


		} catch (IOException ex) {
			logMessage("Exception "+ex);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
		return orderInput;
	}
	
	private boolean isCartEmpty(SeleniumUtils utils){
		boolean isCartEmpty = false;
		try{
			_driver.get(cartUrl);
			utils.waitForElementToBeVisible(BASKET_ITEMS);
        	List<WebElement> elements = utils.getElements(BASKET_ITEMS);
        	if(elements != null && elements.size() > 0){
        		isCartEmpty = false;
        	} else{
        		isCartEmpty = true;
        	}
        	
        } catch(Exception ex2){
        	System.out.println("Timed out waiting for proceedToCheckout button");
        	isCartEmpty = true;
        }
		
		return isCartEmpty;
	}
	
	private void orderProduct(SeleniumUtils utils,OrderInput orderInputs) throws Exception{
		try{
			Thread.sleep(5000);
			boolean isEmptyCart = isCartEmpty(utils);
			
			if(!isEmptyCart){
				getscreenshot("BASKET_NOTEMPTY");
				logMessage("Basket is not empty");
				throw new Exception("Basket is not empty");
			}
			
			System.out.println("Basket is Empty - Proceed");
			
			_driver.get(orderInputs.getArticleUrl());
			
			Thread.sleep(10000);
			
			try{
				utils.waitForElementToBeVisible(PRICE_TAG);
			} catch(Exception ex){
				getscreenshot("ARTICLE_UNAVAILABLE");
				logMessage("Article not available");
				throw new Exception("Article not available");
			}
			
			
			String amzPrice = utils.getElement(PRICE_TAG).getText();
			System.out.println("Price is "+amzPrice);
			
			amzPrice = amzPrice.replaceAll("[^\\d\\,\\.]","");
			
			
			
			if(!checkPrice(amzPrice, orderInputs.getMaxPrice())){
				getscreenshot("PRICE_HIGHER");
				logMessage("Price higher than Max Price");
				throw new Exception("Price higher than Max Price");
			}
			
			System.out.println("Price After trimming "+amzPrice);
			
			
			
			utils.waitForElementToBeVisible(ADD_TO_BASKET_BTN);
			
			utils.click(ADD_TO_BASKET_BTN);
			
			utils.waitForElementToBeVisible(PROCEED_CHECKOUT);
			
			utils.click(PROCEED_CHECKOUT);
			
			
			
			try{
	        	new WebDriverWait(_driver, 20).until(ExpectedConditions.visibilityOfElementLocated(PASSWORD));
	        	utils.sendKeys(PASSWORD, password);
	        	utils.scrollToThenClick(SIGNIN_BUTTON);
	        } catch(org.openqa.selenium.TimeoutException ex2){
	        	System.out.println("Timed out waiting for relogin");
	        }
			
			try{
	        	new WebDriverWait(_driver, 20).until(ExpectedConditions.visibilityOfElementLocated(ADDR_CHANGE_LINK));
	        	utils.click(ADDR_CHANGE_LINK);
	        	utils.waitForElementToBeVisible(NEW_ADDR_LINK);
	        	utils.click(NEW_ADDR_LINK);
	        } catch(org.openqa.selenium.TimeoutException ex2){
	        	System.out.println("Timed out waiting for ADDR_CHANGE_LINK");
	        }
			
			
			
			
			utils.waitForElementToBeVisible(FULL_NAME);
			utils.sendKeys(FULL_NAME, orderInputs.getDeliveryName());
			utils.sendKeys(ADDR_LINE2, orderInputs.getDeliveryStreet());
			utils.sendKeys(ADDR_CITY, orderInputs.getDeliveryCity());
			utils.sendKeys(ADDR_STATE, orderInputs.getDeliveryState());
			utils.sendKeys(ADDR_ZIPCODE, orderInputs.getDeliveryZip());
			utils.sendKeys(ADDR_PHONE, orderInputs.getDeliveryPhone());
			
			if(utils.getElement(DELIVER_BTN)!=null){
				utils.click(DELIVER_BTN);
			} else{
				utils.click(SEND_TO_ADDR_BTN);
			}
			
			
			try{
				utils.waitForElementToBeVisible(ADDR_POPOVER_FORM);
				utils.waitForElementToBeClickable(ADDR_POPOVER_FIRST_RADIO);
				utils.click(ADDR_POPOVER_FIRST_RADIO);
				utils.click(USE_THIS_ADDRESS);
			} catch(Exception ex){
				System.out.println("Timed out waiting for optinal address popup");
			}
			
			try{
				utils.waitForElementToBeVisible(GIFT_RADIO);
			} catch(Exception ex){
				System.out.println("Timed out waiting for gift card payment option");
				getscreenshot("PAYMENT_METHOD_UNAVAILABLE");
				logMessage("Payment Method Unavailable");
				throw new Exception("Payment Method Unavailable");
			}
			
			
			
			
			
			
			
			utils.waitForElementToBeVisible(USE_THIS_PAYMENT);
			utils.click(USE_THIS_PAYMENT);
			
			Thread.sleep(5000);
			
			try{
				utils.waitForElementToBeVisible(SHIPPING_METHOD_RADIO);
				utils.click(SHIPPING_METHOD_RADIO);
				Thread.sleep(5000);
			} catch(Exception ex){
				System.out.println("Shipping method radio not found");
			}
			
			
			try{
				utils.waitForElementToBeVisible(GIFT_POPUP_LINK);
				utils.click(GIFT_POPUP_LINK);
				utils.waitForElementToBeVisible(SAVE_GIFT_OPTION);
				utils.sendKeys(GIFT_TEXT_AREA,giftText);
				utils.click(SAVE_GIFT_OPTION);
			} catch(Exception ex){
				System.out.println("Timed out waiting for gift pack link");
			}
			System.out.println("After gift popup close");
			Thread.sleep(3000);
			
			utils.waitForElementToBeClickable(FINAL_BUY_NOW);
			utils.scrollToThenClick(FINAL_BUY_NOW);
			System.out.println("After FINAL_BUY_NOW click");
			
			utils.waitForElementToBeVisible(ORDER_CONFIRM_URL);
			String url = utils.getElement(ORDER_CONFIRM_URL).getAttribute("href");
			
			
			
			logMessage("ORDERED ITEM SUCCESSFULLY");
			
			logMessage("ORDER_URL::"+url);
			
			try{
				logMessage("ORDER_NUMBER::"+utils.getElementText(ORDER_NUMBER));
			} catch(Exception ex){
				logMessage("ORDER_NUMBER::"+utils.getElementText(ORDER_NUMBER_ALT));
			}
		} catch(Exception ex){
			System.out.println("Exception "+ex);
			getscreenshot("Exception");
			logMessage("Exception"+ex);
			throw new Exception(ex);
		} finally{
			completeOperation(0);
		}
	}
	
	 public void getscreenshot(String fileName) throws Exception {
		 File scrFile = ((TakesScreenshot)_driver).getScreenshotAs(OutputType.FILE);
         FileUtils.copyFile(scrFile, new File(outputLocation+fileName+".png"));
     }
	 
	private boolean checkPrice(String amzPrice,String expPrice){
		System.out.println("EXP : "+expPrice +" AMZ : "+amzPrice);
		float expPriceF = Float.valueOf(expPrice.replaceAll(",","."));
		float amzPriceF = Float.valueOf(amzPrice.replaceAll(",","."));
		return amzPriceF<=expPriceF;
	}
}
