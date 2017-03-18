package net.SeleniumUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Browser extends WebDriverException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String url = null;
	protected ProfilesIni profile = new ProfilesIni();
	protected WebDriver driver;
	protected WebDriverWait wait;
	protected Set<Cookie> cookies = null;
	protected ArrayList<String> urlList = new ArrayList<String>();
	private String OS_NAME = System.getProperty("os.name");
	private String BIT = System.getProperty("os.arch");
	private String FIREFOX = null;
	private String CHROME = null;
	private String IE = null;

	public static void main(String[] args) {
		Browser browser = new Browser();
		browser.startFireFox();
		// browser.startChrome();
		browser.sleep(1000);
		browser.getDriver().get("https://www.google.com/doodles");
		browser.getDriver().navigate().to("https://www.google.com/doodles");
		browser.getDriver().quit();
	}

	public ArrayList<String> getUrlList() {
		return urlList;
	}

	public void scroll(int y) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0," + y + ")", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setUrlList(ArrayList<String> urlList) {
		this.urlList = urlList;
	}

	public void startSafari() {
		// driver = SafariDriver();
	}

	public void setPosition(int x, int y) {
		driver.manage().window().setPosition(new Point(x, y));
	}

	public Browser() {
		BIT = BIT.substring(BIT.length() - 2);
		if (OS_NAME.toLowerCase().indexOf("mac") != -1) {
			CHROME = "./driver/chrome/mac";
			FIREFOX = "./driver/firefox/mac";
		} else if (OS_NAME.toLowerCase().indexOf("win") != -1) {
			CHROME = ".\\driver\\chorme\\win.exe";
			FIREFOX = ".\\driver\\firefox\\" + BIT + ".exe";
			IE = ".\\driver\\ie\\" + BIT + ".exe";
		} else if (OS_NAME.toLowerCase().indexOf("linux") != -1) {
			FIREFOX = ".\\driver\\firefox\\" + BIT + ".exe";
			CHROME = ".\\driver\\chorme\\win.exe";
		}
		System.out.println("OS_NAME:" + OS_NAME);
		System.out.println("BIT:" + BIT);
		System.out.println("FIREFOX:" + FIREFOX);
		System.out.println("CHROME:" + CHROME);
		System.out.println("IE:" + IE);
	}

	public void setPageLoadTimeout(int seconds) {
		driver.manage().timeouts().pageLoadTimeout(seconds, TimeUnit.SECONDS);
	}

	public void setScriptTimeout(int time) {
		driver.manage().timeouts().setScriptTimeout(time, TimeUnit.SECONDS);
	}

	public String getLinkByXpath(String xpath) {
		WebElement webElement = driver.findElement(By.xpath(xpath));
		String link = webElement.getAttribute("href");
		return link;

	}

	public void openUrl(String url) {
		try {
			driver.navigate().to(url);
		} catch (Exception e) {
			try {
				driver.navigate().refresh();
			} catch (Exception e2) {
				openUrl(url);
			}
		}
	}

	public void takeScreenshot(String path) {
		try {
			new File("img").mkdir();
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File("img/" + path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void clearAlert() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.onbeforeunload = function() {};");
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public void startIe() {
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		System.setProperty("webdriver.ie.driver", IE);
		driver = new InternetExplorerDriver(capabilities);
	}

	public void startChrome() {
		System.setProperty("webdriver.chrome.driver", CHROME);
		driver = new ChromeDriver();
	}

	public void startFireFox() {
		System.setProperty("webdriver.gecko.driver", FIREFOX);
		driver = new FirefoxDriver();
	}

	// public void startPhantom() {
	// DesiredCapabilities caps = new DesiredCapabilities();
	// caps.setCapability(
	// PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
	// "/bin/phantomjs");
	// this.driver = new PhantomJSDriver(caps);
	// this.driver = new PhantomJSDriver();
	// }

	public void setTimeOut(int seconds) {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	public void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public WebElement read(ArrayList<String> xpaths) {
		for (String xpath : xpaths) {
			try {
				List<WebElement> els = driver.findElements(By.xpath(xpath));
				if (els.size() > 0) {
					return els.get(0);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return null;

	}

	public void setProfile(ProfilesIni profile) {
		this.profile = profile;
	}

	public void setWait(WebDriverWait wait) {
		this.wait = wait;
	}

	public void openMail(int num) {
		// TODO Auto-generated method stub

	}

}
