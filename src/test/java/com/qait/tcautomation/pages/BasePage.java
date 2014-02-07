package com.qait.tcautomation.pages;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Reporter;

import com.qait.tcautomation.ui.ClassRoomObservationFormPageUi;
import com.qait.tcautomation.ui.HomePageUi;
import com.qait.tcautomation.ui.LoginPageUi;
import com.qait.tcautomation.ui.StudentLearningObjectivesUi;
import com.qait.tcautomation.util.PropertyReaderUtil;
import com.qait.tcautomation.util.TcAutomationUtil;
import com.qait.tcautomation.util.WaitUtil;

public class BasePage {

	protected WebDriver driver;
	protected DesiredCapabilities capabilities;
	protected HttpCommandExecutor executor;

	protected String browser;

	protected PropertyReaderUtil propertReaderUtil;

	protected LoginPageUi loginPageUi;
	protected HomePageUi homePageUi;
	protected StudentLearningObjectivesUi studentLearningObjectivesUi;
	protected ClassRoomObservationFormPageUi classRoomObservationFormPageUi;

	private void init() {
		loginPageUi = new LoginPageUi(driver);
		homePageUi = new HomePageUi(driver);
		studentLearningObjectivesUi = new StudentLearningObjectivesUi(driver);
		classRoomObservationFormPageUi = new ClassRoomObservationFormPageUi(
				driver);
	}

	public BasePage(WebDriver driver, DesiredCapabilities capabilities,
			HttpCommandExecutor executor, PropertyReaderUtil propertReaderUtil) {
		this.driver = driver;
		this.capabilities = capabilities;
		this.executor = executor;
		this.propertReaderUtil = propertReaderUtil;
		init();
	}

	public BasePage() {
		// TODO Auto-generated constructor stub
	}

	public WebDriver initalizeDriver(String browserName) {
		if (browserName.isEmpty())
			browserName = propertReaderUtil.getPropertyValue("browser");
		browser = browserName;
		URL url = null;
		try {
			url = new URL(
					propertReaderUtil
							.getPropertyValue("selenium_remote_server"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if (propertReaderUtil.getPropertyValue("environment").equalsIgnoreCase(
				"remote")) {
			initalizeRemoteDriver(browserName, url);
		} else {
			initalizeLocalDriver(browserName);
		}

		setDriverPropeties();

		init();

		return driver;
	}

	private void setDriverPropeties() {
		driver.manage()
				.timeouts()
				.implicitlyWait(WaitUtil.DEFAULT_WAIT_FOR_ELEMENT,
						TimeUnit.SECONDS);
		driver.manage()
				.timeouts()
				.pageLoadTimeout(WaitUtil.DEFAULT_WAIT_FOR_PAGE,
						TimeUnit.SECONDS);

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		maximizePage();
	}

	private void maximizePage() {
		((JavascriptExecutor) driver)
				.executeScript("if (window.screen){window.moveTo(0, 0);window.resizeTo(window.screen.availWidth, window.screen.availHeight);};");
	}

	private void initalizeLocalDriver(String browserName) {

		capabilities = new DesiredCapabilities();
		capabilities.setJavascriptEnabled(true);

		if (browserName.contains("firefox")) {
			capabilities.setBrowserName("firefox");
			driver = new FirefoxDriver();

			Reporter.log("Started Local firefox...");
		} else if (browserName.contains("iexplore")) {
			if ((System.getProperty("os.arch")).equalsIgnoreCase("x86")) {
				System.out.println(System.getProperty("os.arch"));
				System.setProperty("webdriver.ie.driver",
						"DriverServer/IEDriverServer2.30x32.exe");
				driver = new InternetExplorerDriver();
				Reporter.log("Started Local Internet Explorer (32-bit)");
			} else {
				System.setProperty("webdriver.ie.driver",
						"DriverServer/IEDriverServer2.30x64.exe");
				driver = new InternetExplorerDriver();
				Reporter.log("Started Local Internet Explorer (64-bit)");
			}
		} else if (browserName.toLowerCase().equalsIgnoreCase("chrome")) {
			capabilities.setBrowserName("chrome");
			System.setProperty("webdriver.chrome.driver",
					"DriverServer/chromedriver.exe");
			driver = new ChromeDriver();
			Reporter.log("Started Local Google Chrome");
		} else if (browserName.equalsIgnoreCase("safari")) {
			capabilities = DesiredCapabilities.safari();
			capabilities.setJavascriptEnabled(true);
			driver = new SafariDriver(capabilities);
			Reporter.log("Started Local Safari");
		} else {
			capabilities.setBrowserName("firefox");
			driver = new FirefoxDriver();
			Reporter.log("Started local default Firefox");
		}
	}

	private void initalizeRemoteDriver(String browserName, URL url) {
		/****** Code to access using MOZILLA FIREFOX *********/
		if (browserName.equalsIgnoreCase("firefox")) {
			capabilities = DesiredCapabilities.firefox();
			capabilities.setJavascriptEnabled(true);
			capabilities.setBrowserName("firefox");
			executor = new HttpCommandExecutor(url);
			driver = new RemoteWebDriver(executor, capabilities);
			Reporter.log("Started Remote firefox");
			/******** Code to access using INTERNET EXPLORER ******/
		} else if (browserName.equalsIgnoreCase("iexplore")) {
			if ((System.getProperty("os.arch")).equalsIgnoreCase("x86")) {
				System.setProperty("webdriver.ie.driver",
						"DriverServer/IEDriverServer32.exe");
				capabilities = DesiredCapabilities.internetExplorer();
				driver = new RemoteWebDriver(url, capabilities);
				Reporter.log("Started Remote Internet Explorer (32-bit) driver");
			} else {
				System.setProperty("webdriver.ie.driver",
						"DriverServer/IEDriverServer.exe");
				capabilities = DesiredCapabilities.internetExplorer();
				driver = new RemoteWebDriver(url, capabilities);
				Reporter.log("Started Remote Internet Explorer (64-bit) driver");
			}
		} else if ((browserName.equalsIgnoreCase("chrome"))) {
			/*
			 * Code to access using GOOGLE CHROME version 2******* The advantage
			 * of using this method is that you can execute your test-cases in a
			 * remote machine and this is advantageous when you want to run it
			 * on grid. First start selenium server using: java
			 * -Dwebdriver.chrome.driver=C:\chromedriver.exe -jar
			 * selenium-server-standalone-2.7.0.jar
			 */
			System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
			capabilities = DesiredCapabilities.chrome();
			driver = new RemoteWebDriver(url, capabilities);
			Reporter.log("Started Remote Google Chrome");
		} else if (browserName.equalsIgnoreCase("safari")) {
			capabilities = DesiredCapabilities.safari();
			capabilities.setJavascriptEnabled(true);
			driver = new SafariDriver(capabilities);
			Reporter.log("Started Remote Safari");
		}
	}

	public PropertyReaderUtil getPropertReaderUtil() {
		return propertReaderUtil;
	}

	public void setPropertReaderUtil(PropertyReaderUtil propertReaderUtil) {
		this.propertReaderUtil = propertReaderUtil;
	}

	/**
	 * Method to get property value
	 * 
	 * @param key
	 *            key in the property file
	 * @return value corresponding to the passed key
	 */
	public String getPropertyValue(String key) {
		return propertReaderUtil.getPropertyValue(key);
	}

	public void waitForPageLoad(String pageTitle) {
		System.out.println("Waiting for page with title '" + pageTitle
				+ "' to laod");
		boolean isPageloaded = WaitUtil.waitForPageTitle(driver,
				WaitUtil.DEFAULT_WAIT_FOR_PAGE, pageTitle);
		assertTrue(isPageloaded, "Page with title " + pageTitle
				+ " was not loaded..");
	}

	/**
	 * Method to load a page with URL
	 * 
	 * @param Url
	 *            : URL of the page
	 */
	public void loadPage(String Url, String pageTitle) {
		driver.get(Url);
		waitForPageLoad(pageTitle);
	}

	/**
	 * Method to close the browser window
	 */
	public boolean closeBrowser() {
		driver.close();
		Reporter.log("Closed Browser session...");
		return true;
	}
	
	/**
	 * Method will call sign out url and waits for login page to load
	 */
	public void signOut() {
		driver.get(getPropertyValue("sign.out.url"));
		waitForPageLoad(getPropertyValue("login.title"));
	}
	
	
	
	/**
	 * Method will check if there are notifications and click the anchor link as
	 * specified by @param index
	 * 
	 * @param index index of list
	 * 
	 * @return ClassRoomObservationFormPage
	 */
	public ClassRoomObservationFormPage viewNotificationByIndex(int index) {
		if(areNewNotifactionPresent()) {
			
			clickNotificationBubble();

			List<WebElement> notificationAnchorList = homePageUi
					.getNotificationAnchorList();

			WebElement e = notificationAnchorList.get(index);

			assertNotNull(e,
					"'View it now' anchor not found in notification list..");
			e.click();

			waitForPageLoad(getPropertyValue("observation.form.page.title1"));

			Reporter.log("Notifaction found");
			
			return new ClassRoomObservationFormPage(driver, capabilities,
					executor, propertReaderUtil);
		}
		
		return null;
	}
	
	/**
	 * Method will get the text of notification bubble
	 * 
	 * @return true if bubble text is > 0 otherwise false
	 */
	public boolean areNewNotifactionPresent() {
		return TcAutomationUtil.getIntFormString(homePageUi
				.getNotificationBubbleDivSpan().getText()) > 0;
	}
	
	/**
	 * Method will click on the notification bubble and wait for the
	 * notification list to appear.
	 */
	public void clickNotificationBubble() {

		homePageUi.getNotificationBubbleDiv().click();

		WaitUtil.waitForElementVisiblity(driver,
				By.cssSelector("div[id='mainBackgroundAlertsBubble_callout']"),
				WaitUtil.DEFAULT_WAIT_FOR_ELEMENT);
	}
}
