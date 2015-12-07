package com.wbl.utils.web;

import com.wbl.utils.Configuration;
import com.wbl.utils.mobile.UtilityClass;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.TouchAction;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import io.appium.java_client.android.AndroidDriver;

/**
 * Created by svelupula on 8/8/2015.
 */
public class PageDriver implements ElementsContainer {

    public final Configuration _configuration;
    private final Browsers _browser;
    private WebDriver _webDriver;


    /*public AppiumDriver get_appiumDriver() {

        return _appiumDriver;
    }*/

    public AppiumDriver _appiumDriver;
    private String _mainWindowHandler;
    private Logger _logger;
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

    public PageDriver(Configuration configuration) {
        _configuration = configuration;
        _browser = _configuration.Browser;
        _logger = Logger.getLogger(PageDriver.class);
         start();
    }

    public WebDriver getDriver() {
        if (_webDriver == null) {
            try {
                start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return _webDriver;
    }

    public Browsers getBrowser() {
        return _browser;
    }

    public String getMainWindowHandler() {
        return _mainWindowHandler;
    }

    public HtmlElement findElement(String locator) {
        try {
            return new HtmlElement(this, _webDriver.findElement(WBy.get(locator)));
        } catch (Exception ex) {
            _logger.error(ex);
            return null;
        }
    }
    public HtmlElement findAElement(String locator) {
        try {
            System.out.println(locator);
            return new HtmlElement(this, _appiumDriver.findElement(WBy.get(locator)));
        } catch (Exception ex) {
            _logger.error(ex);
            return null;
        }
    }


    // Do not throws exceptions, only return null
    public Collection<HtmlElement> findElements(String locator) {
        Collection<HtmlElement> elements = null;
        try {
            Collection<WebElement> webElements = _webDriver.findElements(WBy.get(locator));
            if (webElements.size() > 0) {
                elements = new ArrayList<HtmlElement>();
            }
            for (WebElement element : webElements) {
                HtmlElement el = new HtmlElement(this, element);
                if (elements != null) elements.add(el);
            }
            return elements;
        } catch (Exception ex) {
            _logger.error(ex);
            return null;
        }
    }
    public Collection<HtmlElement> findAElements(String locator) {
        Collection<HtmlElement> elements = null;
        try {
            Collection<WebElement> webElements = _appiumDriver.findElements(WBy.get(locator));
            if (webElements.size() > 0) {
                elements = new ArrayList<HtmlElement>();
            }
            for (WebElement element : webElements) {
                HtmlElement el = new HtmlElement(this, element);
                if (elements != null) elements.add(el);
            }
            return elements;
        } catch (Exception ex) {
            _logger.error(ex);
            return null;
        }
    }

    public void quit() {
        if (_webDriver == null) {
            return;
        }
        _webDriver.quit();
        _webDriver = null;

        // TODO: Kill rest driver process: chromedriver.exe, IEDriverServer.exe (test regarding should it be done on start)
    }

    public void close()
    {
        if(_webDriver != null)
        {
            _webDriver.close();
        }
    }

    public void open(String url) {
        _webDriver.navigate().to(url);
        try {
            implicitWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return _webDriver.getTitle();
    }

    public void implicitWait() throws Exception {
        if (_browser != Browsers.HtmlUnit) {
            _webDriver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
            return;
        }
        Thread.sleep(2000);
    }

    public String getCurrentUrl() {
        return _webDriver.getCurrentUrl();
    }

    public Set<String> getLinks(String locator) {
        Set<String> links = new HashSet<String>();
        for (HtmlElement el : this.findElements(locator)) {
            links.add(el.getLink());
        }
        return links;
    }

    public void saveScreenShot(String path) {
        try {
            FileUtils.copyFile(((TakesScreenshot) _webDriver).getScreenshotAs(OutputType.FILE), new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WebDriver getWebDriver()
    {
    	return _webDriver;
    }
    
    public Object ExecuteJavaScript(String javaScript, Object[] args) {
        JavascriptExecutor javaScriptExecutor = (JavascriptExecutor) _webDriver;

        return javaScriptExecutor.executeScript(javaScript, args);
    }

    public String getDescription() {
        return "Browser";
    }

    public String getCookie(String cookieName)
    {
        String value = null;
        return _webDriver.manage().getCookieNamed(cookieName).getValue();
    }

    public void implicitWait(long timeout)
    {
        _webDriver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
    }


    public void elementClickWait(By locator )
    {
        long timeout = Long.valueOf(_configuration.WaitTimeout).longValue();
        WebDriverWait wait = new WebDriverWait(_webDriver, timeout);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void visibilityWait(By locator)
    {
        long timeout = Long.valueOf(_configuration.WaitTimeout).longValue();
        WebDriverWait wait = new WebDriverWait(_webDriver, timeout);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public void waitForLoad()
    {
        ExpectedCondition<Boolean> pageLoadCondition = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver _webDriver) {
                        return ((JavascriptExecutor)_webDriver).executeScript("return document.readyState").equals("complete");
                    }
                };
        long timeout = Long.valueOf(_configuration.WaitTimeout).longValue();
        WebDriverWait wait = new WebDriverWait(_webDriver, timeout);
        wait.until(pageLoadCondition);
    }

    public Actions initializeAction()
    {
        return new Actions(_webDriver);
    }

    public void switchToWindow()
    {
        String newWindow = _webDriver.getWindowHandle();
        _webDriver.switchTo().window(newWindow);
    }

    public void takeScreenShot()throws IOException
    {
        if(_configuration.TakeScreenShot)
        {
            String date = getFormattedDate();
            String newDir = _configuration.ScreenFolderPath+"/"+date;
            File file = new File(newDir);
            if(!file.exists()) {
                new File(newDir).mkdir();
            }
            String latestFilePath = file.getPath();
            int count =  new File(latestFilePath).listFiles().length+1 ;
            File scrFile = ((TakesScreenshot)_webDriver).getScreenshotAs(OutputType.FILE);
           // File file =  new File(_configuration.ScreenFolderPath+"\\"+date.toString());
            String path = newDir+"/Screen"+count+".png";
            FileUtils.copyFile(scrFile, new File(path));

        }
    }

    public void windowHandles()
    {
        Iterator<String> handles = _webDriver.getWindowHandles().iterator();
        while(handles.hasNext()){
            String child = handles.next();
            _webDriver.switchTo().window(child);
        }
    }

    public String getFormattedDate()
    {
        return format.format(new Date()).toString();
    }
    private void start() {
        try {

            if (_configuration.Device.toLowerCase().equals("mobile")){
                System.out.println("inside start");
                _appiumDriver=startAppiumDriver();
            }
            else {
                switch (_browser) {
                    case InternetExplorer:
                        _webDriver = startInternetExplorer();
                        break;
                    case Firefox:
                        _webDriver = startFirefox();
                        break;
                    case Chrome:
                        _webDriver = startChrome();
                        break;
                    case HtmlUnit:
                        _webDriver = startHtmlUnit();
                        break;
                    default:
                        _webDriver = startHtmlUnit();
                        break;
                }
                _webDriver.get(_configuration.BaseUrl);

                if (_browser != Browsers.HtmlUnit) {
                    _webDriver.manage().window().maximize();
                    _webDriver.manage().deleteAllCookies();
                }
                _mainWindowHandler = _webDriver.getWindowHandle();
            }
        } catch (Exception ex) {
            _logger.error(ex);
        }
    }
private AppiumDriver startAppiumDriver(){

    if(_configuration.Os.equalsIgnoreCase("ANDROID"))
    {
        System.out.println("inside startAppiumdriver");
        DesiredCapabilities options=new DesiredCapabilities();
        //options.setPlatform(Platform.ANDROID);
        options.setCapability(MobileCapabilityType.PLATFORM_VERSION,"4.4");
        options.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        options.setCapability(MobileCapabilityType.DEVICE_NAME,_configuration.Devicename);
        options.setCapability("appPackage", "io.selendroid.testapp");
        options.setCapability("appActivity", "io.selendroid.testapp.HomeScreenActivity");

        try {
            System.out.println(_appiumDriver);
           // _appiumDriver=new AndroidDriver(new URL("http://localhost:4723/wd/hub"), options);
            _appiumDriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), options);
            System.out.println(_appiumDriver);
        }
        catch (MalformedURLException e) {

            e.printStackTrace();
        }

    }

    return _appiumDriver;
}



    private InternetExplorerDriver startInternetExplorer() {
        System.setProperty("webdriver.ie.driver", String.format("%s/IEDriverServer.exe", System.getProperty("user.dir")));
        DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
        caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        caps.setCapability(InternetExplorerDriver.FORCE_CREATE_PROCESS, false);
        caps.setCapability(InternetExplorerDriver.IE_SWITCHES, "-private");
        caps.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, "true");
        caps.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
        return new InternetExplorerDriver(caps);
    }

    private FirefoxDriver startFirefox() {
        // FirefoxProfile firefoxProfile = new FirefoxProfile();
        /*firefoxProfile.setAcceptUntrustedCertificates(true);
        firefoxProfile.setPreference("browser.download.folderList", 2);
        firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
        firefoxProfile.setPreference("browser.download.dir", _configuration.TestResultPath);
        firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/csv");
        firefoxProfile.setPreference("browser.download.manager.alertOnEXEOpen", false);
        firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/vnd.ms-excel;application/msword;application/octet-stream");

        firefoxProfile.setPreference("browser.download.manager.showWhenStarting",
                false);
        firefoxProfile.setPreference("browser.download.manager.focusWhenStarting",
                false);
        firefoxProfile.setPreference("browser.download.useDownloadDir", true);
        firefoxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
        firefoxProfile.setPreference("browser.download.manager.alertOnEXEOpen", false);
        firefoxProfile.setPreference("browser.download.manager.closeWhenDone", true);
        firefoxProfile.setPreference("browser.download.manager.showAlertOnComplete",
                false);
        firefoxProfile.setPreference("browser.download.manager.useWindow", false);
        firefoxProfile.setPreference(
                "services.sync.prefs.sync.browser.download.manager.showWhenStarting",
                false);
        firefoxProfile.setPreference("pdfjs.disabled", true);*/
        return new FirefoxDriver();
    }

    private ChromeDriver startChrome() {
        System.setProperty("webdriver.chrome.driver", String.format("%s/chromedriver.exe", System.getProperty("user.dir")));
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();

        //Mobile Browsers using Chrome Emulation
        if (!_configuration.Device.toLowerCase().equals("desktop")) {

            Map<String, Object> chromeOptions = new HashMap<String, Object>();
            Map<String, String> mobileEmulation = new HashMap<String, String>();
            mobileEmulation.put("deviceName", _configuration.Device);
            chromeOptions.put("mobileEmulation", mobileEmulation);
            capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        }
        else {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("start-maximized"); //To maximize the browser
            options.addArguments("allow-file-access-from-files");
            options.addArguments("disable-rest-security");
            options.addArguments("ignore-certifcate-errors");
            options.addArguments("--always-authorize-plugins=true");
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        }
        return new ChromeDriver(capabilities);
    }

    private HtmlUnitDriver startHtmlUnit() {
        return new HtmlUnitDriver();
    }

    public void wHidekeypad() {

        ((AndroidDriver) _appiumDriver).hideKeyboard();
    }
    public boolean wisAppInstalled(String bundleId){

       return  _appiumDriver.isAppInstalled(bundleId);

    }
    public void winstallApp(String appPath){

         _appiumDriver.installApp(appPath);

    }

    public void wremoveApp(String bundleId){

        _appiumDriver.removeApp(bundleId);
    }
    public void wlaunchApp(){

        _appiumDriver.launchApp();
    }
    public void wcloseApp(){

        _appiumDriver.closeApp();
    }
    public void wrunAppInBackground(int seconds){

        _appiumDriver.runAppInBackground(seconds);
    }
    public void wtap(int fingers, HtmlElement element, int duration){

        _appiumDriver.tap(fingers, (WebElement) element, duration);
    }
    public void wtap(int fingers, int x,int y,int duration){

        _appiumDriver.tap(fingers,x,y,duration);
    }
    public void wswipe(int startx,int starty, int endx, int endy, int duration){

        _appiumDriver.swipe(startx, starty, endx, endy, duration);
    }
    public void wzoom(org.openqa.selenium.WebElement el){

        _appiumDriver.zoom(el);
    }
    public Set<String> wgetContextHandles(){
        return _appiumDriver.getContextHandles();
    }
    public String wgetContext(){
        return _appiumDriver.getContext();
    }
    public org.openqa.selenium.WebDriver wcontext(String name){
        return _appiumDriver.context(name);
    }

}
