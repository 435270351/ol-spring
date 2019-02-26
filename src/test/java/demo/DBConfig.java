package demo;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

/**
 * （描述）
 *
 * @author tangzw
 * @date 2019-02-21
 * @since (版本)
 */
public class DBConfig {
    @BeforeSuite()
    public void beforeSuite() {
        System.out.println("@BeforeSuite");
    }

    @AfterSuite()
    public void afterSuite() {
        System.out.println("@AfterSuite");
    }

    @BeforeTest()
    public void beforeTest() {
        System.out.println("@BeforeTest");
    }

    @AfterTest()
    public void afterTest() {
        System.out.println("@AfterTest");
    }

    @BeforeMethod()
    public void BeforeMethod() {
        System.out.println("@BeforeMethod");
    }

    @AfterMethod()
    public void AfterMethod() {
        System.out.println("@AfterMethod");
    }

    @BeforeClass()
    public void BeforeClass() {
        System.out.println("@BeforeClass");
    }

    @AfterClass()
    public void AfterClass() {
        System.out.println("@AfterClass");
    }
}
