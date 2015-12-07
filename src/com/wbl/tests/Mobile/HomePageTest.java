package com.wbl.tests.Mobile;


import com.wbl.base.MobBaseTest;
import com.wbl.pages.Mobile.HomePage;
import com.wbl.utils.web.HtmlElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class HomePageTest extends MobBaseTest {

	private HomePage _hp;

	@BeforeClass
	public void beforeClass() {
		_hp = new HomePage(driver);
	}

	@Test
	public void test() throws InterruptedException
	{
		System.out.println("Inside Test");
		HtmlElement inputField = driver.findAElement("class=mob.txtboxonhomepage.class");
		System.out.println(inputField);
		inputField.click();
		Assert.assertEquals("true", inputField.getAttribute("enabled"));
		inputField.sendKeys("Appium");
		Assert.assertEquals("Appium", inputField.getText());
		//Thread.sleep(4000);
		driver.findAElement("xpath=mob.enbthn").click();

		
		Thread.sleep(2000);
		driver.findAElement("xpath=mob.nonobtn").click();

	} 
	
	}


