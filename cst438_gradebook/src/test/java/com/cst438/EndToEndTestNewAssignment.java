package com.cst438;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentGradeRepository;
import com.cst438.domain.AssignmentRepository;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.EnrollmentRepository;
import static org.junit.jupiter.api.Assertions.*;
/*
 * This example shows how to use selenium testing using the web driver
 * with Chrome browser.
 *
 *  - Buttons, input, and anchor elements are located using XPATH expression.
 *  - onClick( ) method is used with buttons and anchor tags.
 *  - Input fields are located and sendKeys( ) method is used to enter test data.
 *  - Spring Boot JPA is used to initialize, verify and reset the database before
 *      and after testing.
 *
 *  In SpringBootTest environment, the test program may use Spring repositories to
 *  setup the database for the test and to verify the result.
 */

@SpringBootTest
public class EndToEndTestNewAssignment {

    public static final String CHROME_DRIVER_FILE_LOCATION = "C:/chromedriver_win32/chromedriver.exe";

    public static final String URL = "http://localhost:3000";
    public static final int SLEEP_DURATION = 1000; // 1 second.
    public static final String TEST_ASSIGNMENT_NAME = "New Test Assignment";
    public static final String TEST_ASSIGNMENT_DATE = "11-5-2021";
    public static final int TEST_COURSE_ID = 40443;


    @Autowired
    EnrollmentRepository enrollmentRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    AssignmentGradeRepository assignnmentGradeRepository;

    @Autowired
    AssignmentRepository assignmentRepository;

    @Test
    public void createAssignmentTest() throws Exception {





        Assignment x = assignmentRepository.findByCourseIdAndName(TEST_COURSE_ID, TEST_ASSIGNMENT_NAME);
        assertNull(x);


        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
        ChromeOptions ops = new ChromeOptions();
        ops.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(ops);
        // Puts an Implicit wait for 10 seconds before throwing exception
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get(URL);
        Thread.sleep(SLEEP_DURATION);

        try {

            // Locate and click New Assignment button
            driver.findElement(By.xpath("//a[last()]")).click();
            Thread.sleep(SLEEP_DURATION);

            // Locate text fields and enter the data
            WebElement we = driver.findElement(By.xpath("//input[@name='courseName']"));
            we.sendKeys("40443");
            we = driver.findElement(By.xpath("//input[@name='assignmentName']"));
            we.sendKeys(TEST_ASSIGNMENT_NAME);
            we = driver.findElement(By.xpath("//input[@name='dueDate']"));
            we.sendKeys(TEST_ASSIGNMENT_DATE);


            driver.findElement(By.xpath("//input[@name='submit']")).click();
            Thread.sleep(SLEEP_DURATION);


            Assignment a = assignmentRepository.findByCourseIdAndName(TEST_COURSE_ID, TEST_ASSIGNMENT_NAME);
            assertNotNull(a);


        } catch (Exception ex) {
            throw ex;
        } finally {

            //Clean up
            Assignment a = assignmentRepository.findByCourseIdAndName(TEST_COURSE_ID, TEST_ASSIGNMENT_NAME);
            if(a !=null)
                assignmentRepository.delete(a);
            driver.close();
            driver.quit();
        }

    }
}
