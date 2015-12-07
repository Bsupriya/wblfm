package com.wbl.tests.Mobile;

import java.util.concurrent.TimeUnit;

import com.wbl.base.MobBaseTest;
import com.wbl.pages.Mobile.WebViewPage;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.wbl.pages.Mobile.HomePage;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class WebviewPageTest extends MobBaseTest {

	private WebViewPage _hp;

	@BeforeClass
	public void beforeClass() {
		_hp = new WebViewPage(driver);
	}


	@Test
	public void test1() throws Exception {
		HomePage appHomePage=new HomePage(driver);
		appHomePage.clickOnChromeButton();
		WebViewPage webViewPage=new WebViewPage(driver);
		PageFactory.initElements(new AppiumFieldDecorator((SearchContext) driver,10,TimeUnit.SECONDS),webViewPage);
		webViewPage.selectWebViewHtml("formPage");
		webViewPage.switchToWebView();

		// WebElement txt=appiumDriver.findElement( By.className("android.widget.TextView"));
		//Assert.assertEquals("Web View Interaction", txt.getText());

	}
}