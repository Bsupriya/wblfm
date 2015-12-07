package com.wbl.base;

import com.wbl.utils.Configuration;
import com.wbl.utils.web.PageDriver;
import com.wbl.utils.web.WBy;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterTest;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.wbl.utils.mobile.UtilityClass;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class MobBaseTest extends MobBase {

	public static final Configuration _config;

	static {
		_config = new Configuration("mob");
	}

	@BeforeSuite
	public void beforeSuite() {

		driver = new PageDriver(_config);
		WBy.loadJsonMap(String.format("%s/locators.json", System.getProperty("user.dir")));

	}

		@BeforeTest
	public void setUp() throws Exception {

		//driver.get_appiumDriver();
		//driver = UtilityClass.getDeviceDriver(_config.MobileOs, _config.Devicename);
		driver.winstallApp("D:/supriya/softwares/selendroid-test-app-0.16.0.apk");
		((AndroidDriver) driver._appiumDriver).startActivity("io.selendroid.testapp", ".HomeScreenActivity");
	}

	@AfterTest
	public void After1()
	{
		//((AndroidDriver)appiumDriver).removeApp("io.selendroid.testapp");
		driver.quit();
	}

}
