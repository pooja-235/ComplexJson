package TestCase;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginTest {
	WebDriver driver;

	@BeforeSuite
	public void setUP() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@AfterSuite
	public void tearDown() {
		driver.quit();
	}

	@Test(dataProvider = "dp")
	public void login(String data) {
		String user[] = data.split(",");
		driver.get("https://login.wordpress.org/register?locale=en_US");
		driver.findElement(By.xpath("//input[@id='user_login']")).sendKeys(user[0]);
		driver.findElement(By.xpath("//input[@id='user_email']")).sendKeys(user[1]);
		driver.findElement(By.xpath("//input[@id='user_mailinglist']")).click();
		driver.findElement(By.xpath("//input[@id='wp-submit']")).click();

	}

	@DataProvider(name = "dp")
	public String[] readJson() throws IOException, ParseException {

		JSONParser jsonparser = new JSONParser();
		FileReader reader = new FileReader("I:\\workspace\\ComplexJsonRead\\src\\test\\resources\\json\\jsonfile.json");
		Object obj = jsonparser.parse(reader);
		JSONObject jobj = (JSONObject) obj;
		// jobj.get("userlogins")
		JSONArray jarray = (JSONArray) jobj.get("userlogins");

		String arr[] = new String[jarray.size()];

		for (int i = 0; i < jarray.size(); i++) {
			JSONObject userlogin = (JSONObject) jarray.get(i);
			String user = (String) userlogin.get("username");
			String pwd = (String) userlogin.get("password");
			arr[i] = user + "," + pwd;
		}
		return arr;
		//returning the array

	}

}
