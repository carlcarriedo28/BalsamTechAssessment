/*
Company: Balsam Hill
Position: Senior Software QA Engineer - Manila, PH

Part 1: Test Automation
Using Java & Selenium WebDriver, write down the code to simulate the following actions.

1.	Go to https://balsamhill.com
2.	Click Artificial Christmas Trees in the top navigation menu.
3.	Refine the results to show only Undecorated under Decoration Options
4.	Sort the results by Price Low to High
5.	Print the name of the 1st, 3rd, and 4th products on the list.
 */

package steps;

import engine.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page_objects.BalsamHillPage;
import page_objects.GetdatafromExcel;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyStepdefs {
    GetdatafromExcel excelReader;
    BalsamHillPage qa = new BalsamHillPage();
    private WebDriver driver;
    private String navMenu;
    private String menu;
    private String subMenu;
    private String filter;
    private String subFilter;

    //Initialization of the driver
    public MyStepdefs (Driver driver){
        this.driver = driver.get();
    }

    //Navigate to the main page which displays Artificial Christmas Trees page and get testdata values from excel sheet
    @Given("Testcase {string} from sheet {string}, Artificial Christmas Trees page is displayed")
    public void get_data_from_datasheet(String testId, String sheetName) {
        driver.get("https://balsamhill.com");
        excelReader = new GetdatafromExcel(testId, sheetName);
        navMenu = excelReader.fieldsAndValues.get("navMenu");
        menu = excelReader.fieldsAndValues.get("menu");
        subMenu = excelReader.fieldsAndValues.get("subMenu");
        filter = excelReader.fieldsAndValues.get("filter");
        subFilter = excelReader.fieldsAndValues.get("subFilter");
    }

    //Search Undecorated Artificial Christmas Trees and filter Price Low to High
    @When("I choose \"menu\" and click \"subMenu\" and filter using \"filter\"")
    public void searchTrees() throws Exception {
        //Declaration for WebDriverWait variable which is used in our explicit wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //Explicit wait - waits for the visibility of the navMenu
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'" + navMenu + "')]")));

        //Click Artificial Christmas Trees in the top navigation menu
        driver.findElement(By.xpath("//a[contains(text(),'" + navMenu + "')]")).click();

        //Assert the Decoration Option menu is visible
        String menuText = driver.findElement(By.xpath("//span[contains(text(),'" + menu + "')]")).getText();
        Assert.assertTrue(menuText.contains(menu));

        //Screenshot the page
        qa.takeSnapShot(driver, "Artificial Christmas Trees");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //Click Artificial Christmas Trees in the top navigation menu
        driver.findElement(By.xpath("//span[contains(text(),'" + menu + "')]")).click();
        driver.findElement(By.xpath("//span[contains(text(),'" + subMenu + "')]")).click();

        //Screenshot the page
        qa.takeSnapShot(driver, "Undecorated Trees");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //Filter Price Low to High
        driver.findElement(By.xpath("//option[contains(text(),'" + subFilter + "')]")).click();
        driver.findElement(By.xpath("//option[contains(text(),'" + filter + "')]")).click();

        //Screenshot the page
        qa.takeSnapShot(driver, "Sorted Undecorated Trees");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @And("I check search results")
    public void searchResults() throws Exception {
        //Declaration for WebDriverWait variable which is used in our explicit wait
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));

        //Explicit wait - waits for the visibility of the products list
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class, 'prod-name-reg')]")));

        //Assert the product list is visible
        Boolean productListIsVisible = driver.findElement(By.xpath("//span[contains(@class, 'prod-name-reg')]")).isDisplayed();
        Assert.assertTrue(productListIsVisible);

        //Scroll down the current page
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,400)", "");

        //Explicit wait - waits for the visibility of the element with class="prod-name-reg"
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class, 'prod-name-reg')]")));

        //Screenshot the page
        qa.takeSnapShot(driver, "Products List");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Then("Print the 1st, 3rd, and 4th products on the list")
    public void printProducts() throws Exception {

        //Locate 1st product on the list
        List<WebElement> myList = driver.findElements(By.xpath("//span[contains(@class, 'prod-name-reg')]"));
/*        String firstProduct= myList.get(0).getText();
        System.out.println("firstProduct: " + firstProduct);
        String thirdProduct= myList.get(2).getText();
        System.out.println("thirdProduct: " + thirdProduct);
        String fourthProduct= myList.get(3).getText();
        System.out.println("thirdProduct: " + fourthProduct);*/

        //Iterate all the items in the List and print all products
        List<String> all_elements_text=new ArrayList<>();
        for(int i=0; i<myList.size(); i++){
            //loading text of each element in to array all_elements_text
            all_elements_text.add(myList.get(i).getText());
            //to print directly
            System.out.println(myList.get(i).getText());
        }

        //Print the 1st, 3rd and 4th products on the list
        String firstProduct= myList.get(0).getText();
        System.out.println("firstProduct: " + firstProduct);
        String thirdProduct= myList.get(2).getText();
        System.out.println("thirdProduct: " + thirdProduct);
        String fourthProduct= myList.get(3).getText();
        System.out.println("thirdProduct: " + fourthProduct);
   }
}