package utils;

import java.io.File;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import test.BaseTest;

import static utils.Utilities.takeScreenShot;

public class Listeners extends BaseTest implements ITestListener  {

	public void onStart (ITestContext execution){
		System.out.println("----------StartingExecution-----------");
	}
	public void onFinish (ITestContext execution){
		System.out.println("----------EndingExecution-------------");
	}
	public void onTestFailedButWithSuccessPercentage (ITestResult test){
		///To Do implement this method
	}
	public void onTestSkipped (ITestResult test){
		System.out.println("----------Skipping Test: " + test.getName() + "---------------") ;
	}
	public void onTestStart (ITestResult test){
		System.out.println("----------Start Test: " + test.getName() + "---------------") ;
	}

	public void onTestSuccess (ITestResult test) {
		System.out.println("----------Test: " + test.getName() + " passed:-----------------");
		takeScreenShot(driver);
	}
	public void onTestFailure (ITestResult test){
		System.out.println("----------Test: " + test.getName() + " failed:-----------------");
		takeScreenShot(driver);
	}




}

