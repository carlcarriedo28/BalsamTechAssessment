package page_objects;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BalsamHillPage {

    private WebDriver driver;

    //Function to Take screenshot
    public static void takeSnapShot(WebDriver webdriver, String Filename) throws Exception{
        Date date = new Date() ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;

        //Convert web driver object to TakeScreenshot
        TakesScreenshot scrShot =((TakesScreenshot)webdriver);

        //Call getScreenshotAs method to create image file
        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);

        //Move image file to new destination
        File DestFile=new File("screenshots\\" + Filename +"_" + dateFormat.format(date) + ".jpg");

        //Copy file at destination
        FileUtils.copyFile(SrcFile, DestFile);
    }
}
