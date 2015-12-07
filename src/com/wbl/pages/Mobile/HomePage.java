package com.wbl.pages.Mobile;

import com.wbl.base.MobBase;
import com.wbl.utils.web.PageDriver;
import com.wbl.utils.web.WBy;
import org.openqa.selenium.By;

import com.wbl.utils.mobile.WaitClass;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

import javax.xml.ws.WebEndpoint;

public class HomePage extends MobBase {

	public HomePage(PageDriver driver) {
		this.driver=driver;
		_logger.debug("Open Home Page");
	}

	public void clickOnRegistrationButton() throws Exception
	{
		driver.visibilityWait(WBy.get("id=mob.registration.id"));

		driver.findAElement("id=mob.registration.id").click();

	}
	public void clickOnChromeButton() throws Exception
	{

		driver.visibilityWait(WBy.get("id=mob.registration.id"));
		driver.findAElement("id=mob.registration.id").click();

		
	}
	
}
