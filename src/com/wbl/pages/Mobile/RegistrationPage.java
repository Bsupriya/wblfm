package com.wbl.pages.Mobile;

import java.util.Collection;
import java.util.List;

import com.wbl.base.MobBase;
import com.wbl.utils.web.HtmlElement;
import com.wbl.utils.web.PageDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.wbl.utils.mobile.WaitClass;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegistrationPage extends MobBase {


	public RegistrationPage(PageDriver driver) {
		this.driver=driver;
		_logger.debug("Open Home Page");
	}

	public void enterUserName(String username) throws InterruptedException {

		HtmlElement userElement=driver.findElement("id=mob.username.id");
		userElement.sendKeys(username);
        Thread.sleep(2000);
		driver.wHidekeypad();
	}
	public void enterEmail(String email) throws InterruptedException {
		driver.findAElement("id=mob.email.id").sendKeys(email);
		driver.wHidekeypad();
	}
	public void enterPassword(String password)
	{
		driver.findAElement("id=mob.password.id").sendKeys(password);
		driver.wHidekeypad();
	}

	public void enterName(String name)
	{
		HtmlElement nameElement=driver.findAElement("id=mob.name.id");
		nameElement.click();
		nameElement.clear();
		//wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		//WaitClass.waitFor(appiumDriver,By.className("android.widget.CheckedTextView"),60);
		nameElement.sendKeys(name);
		driver.wHidekeypad();
	}

	public void selectProgramLanguage(String text)
	{
		HtmlElement element=driver.findAElement("id=mob.spinnerbtn.id");
		driver.wtap(1, element, 1);
		//WaitClass.waitFor(driver,By.className("android.widget.CheckedTextView"),60);
		Collection<HtmlElement> list=driver.findAElements("id=android.widget.CheckedTextView");
		for(HtmlElement item:list)
		{
			if(item.getAttribute("text").contains(text))
			{
				driver.wtap(1,item, 1);
				break;
			}
				
		}
	}
	
	public void clickOnRegister()
	{
		driver.findAElement("id=mob.resisterbtn.id").click();
	}
	
}
