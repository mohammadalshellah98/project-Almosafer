package almosafeer;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import net.bytebuddy.implementation.bytecode.Throw;

public class myTestCases {

	WebDriver driver = new ChromeDriver();
	String URL = "https://www.almosafer.com/en";
	String expectedResultLanguageEN = "en";
	String expectedResultLanguageAR = "ar";
	String expectedResultCurrency = "SAR";
	String expectedContactNumber = "+966554400000";
	boolean expectedResultQitafLogoIsThere = true;
	Random rand = new Random();

	@BeforeTest
	public void mySetUp() {
		driver.get(URL);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		WebElement clickCountryID = driver
				.findElement(By.cssSelector(".sc-jTzLTM.hQpNle.cta__button.cta__saudi.btn.btn-primary"));
		clickCountryID.click();

	}

	@Test(enabled = false)
	public void checkTheLanguage() {
		WebElement htmlTag = driver.findElement(By.tagName("html"));
		String actualResult = htmlTag.getAttribute("lang");
		Assert.assertEquals(actualResult, expectedResultLanguageEN);
	}

	@Test(enabled = false)
	public void testTheCurrencySAR() {
		String actualCurrency = driver.findElement(By.xpath("//button[@data-testid='Header__CurrencySelector']"))
				.getText();

		Assert.assertEquals(actualCurrency, expectedResultCurrency);
	}

	@Test(enabled = false)
	public void Contact_Number() {
		String actualNumber = driver.findElement(By.tagName("strong")).getText();
		Assert.assertEquals(actualNumber, expectedContactNumber);
	}

	@Test(enabled = false)
	public void checkTheLogoIsDisplay() {
		WebElement footerTag = driver.findElement(By.tagName("footer"));
		boolean actualResult = footerTag.findElement(By.cssSelector(".sc-fihHvN.eYrDjb")).findElement(By.tagName("svg"))
				.isDisplayed();
		Assert.assertEquals(actualResult, expectedResultQitafLogoIsThere);
	}

	@Test(enabled = false)
	public void hotelIsNotSelected() {
		String expectedResultTheHotelIsNotSelected = "false";
		String actualResultNotSelected = driver.findElement(By.id("uncontrolled-tab-example-tab-hotels"))
				.getAttribute("aria-selected");
		Assert.assertEquals(actualResultNotSelected, expectedResultTheHotelIsNotSelected);
	}

	@Test(enabled = false)
	public void checkDepatureAndDate() {
		LocalDate today = LocalDate.now();

		int ExpectedDepatureDate = today.plusDays(1).getDayOfMonth();

		int ExpectedReturneDate = today.plusDays(2).getDayOfMonth();

		String ActualDepatureDate = driver
				.findElement(By.cssSelector("div[class='sc-iHhHRJ sc-kqlzXE blwiEW'] span[class='sc-cPuPxo LiroG']"))
				.getText();

		String ActualReturnDate = driver
				.findElement(By.cssSelector("div[class='sc-iHhHRJ sc-OxbzP edzUwL'] span[class='sc-cPuPxo LiroG']"))
				.getText();

		int ActualDepatureDateASInt = Integer.parseInt(ActualDepatureDate);
		int ActualReturnDateASInt = Integer.parseInt(ActualReturnDate);

		org.testng.Assert.assertEquals(ActualDepatureDateASInt, ExpectedDepatureDate);
		org.testng.Assert.assertEquals(ActualReturnDateASInt, ExpectedReturneDate);
	}

	@Test(priority = 1)
	public void ChanageTheLanguageOfTheWebSitePrandomly() {
		String[] website = { "https://www.almosafer.com/en", "https://www.almosafer.com/ar" };
		int randomIndex = rand.nextInt(website.length);
		driver.get(website[randomIndex]);

		if (driver.getCurrentUrl().contains("en")) {
			WebElement htmlTag = driver.findElement(By.tagName("html"));
			String actualResult = htmlTag.getAttribute("lang");
			Assert.assertEquals(actualResult, expectedResultLanguageEN);
		} else if (driver.getCurrentUrl().contains("ar")) {
			WebElement htmlTag = driver.findElement(By.tagName("html"));
			String actualResult = htmlTag.getAttribute("lang");
			Assert.assertEquals(actualResult, expectedResultLanguageAR);
		}

	}

	@Test(priority = 2)
	public void HotelSelection() {
		WebElement hotelTab = driver.findElement(By.id("uncontrolled-tab-example-tab-hotels"));
		hotelTab.click();
		WebElement hotelSerech = driver.findElement(By.cssSelector(".sc-phbroq-2.uQFRS.AutoComplete__Input"));

		if (driver.getCurrentUrl().contains("en")) {
			String[] EnglishCities = { "dubai", "jeddah", "riyadh" };
			int randomIndex = rand.nextInt(EnglishCities.length);
			hotelSerech.sendKeys(EnglishCities[randomIndex]);
		} else if (driver.getCurrentUrl().contains("ar")) {
			String[] AribichCities = { "جدة", "دبي" };
			int randomIndex = rand.nextInt(AribichCities.length);
			hotelSerech.sendKeys(AribichCities[randomIndex]);
		}

	}

	@Test(priority = 3)
	public void selectNumberOfPeople() {
		WebElement myElement = driver
				.findElement(By.xpath("//select[@data-testid = 'HotelSearchBox__ReservationSelect_Select']"));
		Select myselector = new Select(myElement);
		int randomIndex = rand.nextInt(2);
		myselector.selectByIndex(randomIndex);

		WebElement searchclick = driver.findElement(By.xpath("//button[@data-testid='HotelSearchBox__SearchButton']"));
		searchclick.click();
	}

	@Test(priority = 4)
	public void checkThePageFullyLoaded() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(1));
		WebElement results = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//span[@data-testid='HotelSearchResult__resultsFoundCount']")));
		Assert.assertEquals(results.getText().contains("found") || results.getText().contains("وجدنا"), true);
	}

	@Test(priority = 5)
	public void SortItems() throws InterruptedException {
		WebElement LowestpriceButton = driver
				.findElement(By.xpath("//button[@data-testid='HotelSearchResult__sort__LOWEST_PRICE']"));
		LowestpriceButton.click();

		Thread.sleep(4000);
		WebElement priceContainer = driver.findElement(By.cssSelector(".sc-htpNat.KtFsv.col-9"));
		List<WebElement> Prices = priceContainer.findElements(By.className("Price__Value"));

		System.out.println(Prices.size());

		String firstprice = Prices.get(0).getText();
		String lastprice = Prices.get(Prices.size() - 1).getText();

		int firstpriceasINT = Integer.parseInt(firstprice);
		int lastpriceasINT = Integer.parseInt(lastprice);

		Assert.assertEquals(firstpriceasINT < lastpriceasINT, true);
	}
	
	@Test(priority = 6)

}
