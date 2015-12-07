package com.wbl.pages.Mobile;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.wbl.base.MobBase;
import com.wbl.utils.web.HtmlElement;
import com.wbl.utils.web.PageDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.wbl.utils.mobile.WaitClass;

import io.appium.java_client.AppiumDriver;
//import io.appium.java_client.MobileElement;
//import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class WebViewPage extends MobBase {



	@FindBy(id="io.selendroid.testapp:id/spinner_webdriver_test_data")
	public HtmlElement spinnerButton;
	

	@FindBy(id="io.selendroid.testapp:id/goBack")
	public HtmlElement goBackButton;


	public WebViewPage(PageDriver driver) {
		this.driver=driver;
		_logger.debug("Open WebViewPage Page");
	}
	
	public void selectWebViewHtml(String text)
	{

		driver.wtap(1, spinnerButton, 1);

		//WaitClass.waitFor(driver,By.id("android:id/select_dialog_listview"),60);
		//WebElement ddbutton = appiumDriver.findElement(By.className("android.widget.EditText"));
		//ddbutton.click();
		Collection<HtmlElement> list=driver.findAElements("android.widget.TextView");
		for(HtmlElement item:list)
		{
			if(item.getAttribute("text").toLowerCase().contains(text.toLowerCase()))
			{
				driver.wtap(1, item, 1);;
				break;
			}
				
		}
	}
	public void switchToWebView()
	{
		//WaitClass.waitFor(driver, By.className("android.view.View"),10);
		Set<String> webViewContextList=driver.wgetContextHandles();
		for(String text:webViewContextList)
		{
			driver.wcontext(text);
		}
	}
	
}
