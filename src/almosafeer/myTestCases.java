package almosafeer;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class myTestCases {

	WebDriver driver = new ChromeDriver();
	String URL = "https://www.almosafer.com/en";
	String expectedResultLanguage = "en";
	String expectedResultCurrency = "SAR";
	String expectedContactNumber = "+966554400000";
	boolean QitafLogoIsThere = true;

	@BeforeTest
	public void mySetUp() {
		driver.get(URL);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		WebElement clickCountryID = driver
				.findElement(By.cssSelector(".sc-jTzLTM.hQpNle.cta__button.cta__saudi.btn.btn-primary"));
		clickCountryID.click();

	}

	@Test
	public void checkTheLanguage() {
		WebElement htmlTag = driver.findElement(By.tagName("html"));
		String actualResult = htmlTag.getAttribute("lang");
		Assert.assertEquals(actualResult, expectedResultLanguage);
	}

	@Test
	public void testTheCurrencySAR() {
		String actualCurrency = driver.findElement(By.xpath("//button[@data-testid='Header__CurrencySelector']"))
				.getText();

		Assert.assertEquals(actualCurrency, expectedResultCurrency);
	}

	@Test
	public void Contact_Number() {
		String actualNumber = driver.findElement(By.tagName("strong")).getText();
		Assert.assertEquals(actualNumber, expectedContactNumber);
	}

	@Test
	public void checkTheLogoIsDisplay() {
		boolean acyualQitaflogo = driver.findElement(By.xpath("//svg[@data-testid='Footer__QitafLogo']")).isDisplayed();
		Assert.assertEquals(acyualQitaflogo, acyualQitaflogo);
	}

}
