package com.wbl.tests.Mobile;

import com.wbl.base.MobBaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.wbl.pages.Mobile.HomePage;
import com.wbl.pages.Mobile.RegistrationPage;

public class RegistrationPageTest extends MobBaseTest {


	private RegistrationPage _hp;

	@BeforeClass
	public void beforeClass() {
		_hp = new RegistrationPage(driver);
	}


	@Test(dataProviderClass=MyDataProvider.class , dataProvider="Fillform")
	public void test2(String UserName,String Email,String Password,String Name, String ProgramLanguage) throws Exception {

		System.out.println("Inside Test");
		HomePage appHomePage=new HomePage(driver);
		appHomePage.clickOnRegistrationButton();
		RegistrationPage formPage=new RegistrationPage(driver);
		formPage.enterUserName(UserName);
		formPage.enterEmail(Email);
		formPage.enterPassword(Password);
		formPage.enterName(Name);
		formPage.selectProgramLanguage(ProgramLanguage);
		formPage.clickOnRegister();


	}
}
