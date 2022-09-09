package pfs2;

import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.regex.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;

import com.opencsv.CSVReader;
import java.util.logging.*;

public class Student {
	private static WebDriver driver;
	static int time = 2000;
	public static Logger log = Logger.getLogger("Student");

	public static void main( String[] args ) throws Exception {
	    FileHandler logFile = new FileHandler("C:\\Users\\Public\\Documents\\student.log");
	    logFile.setFormatter(new SimpleFormatter());
	    log.addHandler(logFile);
	    log.info("A message logged to the file");

	    String CSV_PATH = "C:\\Users\\Public\\Documents\\pfs2.csv";
	    CSVReader csvReader;
	    int count =0;
	    csvReader = new CSVReader(new FileReader(CSV_PATH));

	    String[] csvCell;
	    while ((csvCell = csvReader.readNext()) != null) {
	        if (count == 0){
	         
	            count = count + 1;
	            continue;
	        }
	        String PFSurl = csvCell[0];
	        String Email = csvCell[1];
	        String Role = csvCell[2];
	        System.out.println(PFSurl);
	        System.out.println((Email));
	        System.out.println(Role);
	        initDriver(PFSurl, Role);
	        login(Email);
	        Thread.sleep(10000);
	        if ("student".equals(Role)) {
	            System.out.println("Executing Student portal");
	            testStudent();
	            testStudentEnrollment();
	testStudentAcademic();
	testStudentExamination();
	testStudentAttendance();
	testStudentTimeTable();
	testStudentFees();
	testStudentFeedback();
	testStudentStudentStatus();
	testStudentEvent();
	testStudentsignout();
    
	        }
	        //else if ("faculty".equals(Role)){
	            //  System.out.println("Executing Faculty portal");
	            //testFaculty();
	        }
	        //After all test are over close the browser
	        // quitDriver();
	}

	@BeforeSuite
	public static void initDriver(String url, String Role) throws Exception {
		ChromeOptions p = new ChromeOptions();
		p.addArguments("--disable-notifications");
		System.out.println("You are testing in Chrome");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver(p);

		// logger.info("Succesfuly clicked");
		log.info("Testing portal:" + url);
		log.info("Testing for:" + Role);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// driver.get("https://portal-dev.ken42.com/\\r\\n");
		driver.get(url);
		driver.manage().window().maximize();
	}

	@Test
	public static void login(String Email) throws InterruptedException, SecurityException, IOException {
		log.info("Login test case with emailId: " + Email);
		int time = 2000;
		String regex = "Null";
		log.info("Login test case with emailId: " + Email);
		// Automate.callSendkeys(driver, ActionXpath.email,
		// "anand.rathi@ken42.com",time);
		Automate.callSenkeys(driver, ActionXpath.email, Email, time);
		Automate.CallXpath(driver, ActionXpath.SignIn, time, "SignIn the Student");
		Automate.CallXpath(driver, ActionXpath.mobile, time, "mobile");
		Automate.CallXpath(driver, ActionXpath.mobile2, time, "mobile2");
		Automate.CallXpath(driver, ActionXpath.SignIn, time, "SignIn to log in to the home page of student portal");
		// Thread.sleep(time);
		Alert alert = driver.switchTo().alert(); // switch to alert
		String alertMessage = driver.switchTo().alert().getText(); // capture alert message
		System.out.println(alertMessage); // Print Alert Message
		Pattern pt = Pattern.compile("-?\\d+");
		Matcher m = pt.matcher(alertMessage);
		while (m.find()) {
			regex = m.group();
		}
		Thread.sleep(2000);
		alert.accept();
		Automate.callSenkeys(driver, ActionXpath.OtpInput, regex, time);
		Automate.CallXpath(driver, ActionXpath.submit, time, alertMessage);
	}

	@Test(priority = 1)
	public static void testStudent() throws SecurityException, IOException {
		try {
			log.info("####Starting Home tab test case executation###");
			Automate.CallXpath(driver, ActionXpath.expand, time, "Expand the Enrollment");
			log.info("$$$$ Home tab test case PASSED.. $$$$");
		} catch (SecurityException e) {
			log.warning("****Home tab test case executation FAILED..****");
		}
	}

	@Test(priority = 2)
	public static void testStudentEnrollment() throws InterruptedException, SecurityException, IOException {
		try {
			log.info("###### Starting Student Enrollment test case Executation #######");
		Automate.CallXpath(driver, ActionXpath.clickCompletedEnroll, time, "select the Completes Enrollment");
		Thread.sleep(3000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,2000)");
		Thread.sleep(3000);
		Automate.CallXpath(driver, ActionXpath.ClickOpenEnroll, time, "Go to the open Enrollement");
		Thread.sleep(2000);
		Automate.CallXpath(driver, ActionXpath.SelectOpenSubject, time, "Select subject");
		Thread.sleep(3000);
		Automate.CallXpath(driver, ActionXpath.ClickView, time, "Click view button");
		Thread.sleep(2000);
		Automate.CallXpath(driver, ActionXpath.ClickOk, time, "Click ok ");
		Automate.CallXpath(driver, ActionXpath.CloseExapnd, time, "Close the Exapnd of the Enrollment");
		log.info("$$$$$$$$$$ Enrollment of the Student Test Case PASSED..$$$$$$$$$$$$");
		}catch(InterruptedException e) {
			log.warning("**************Enrollment Tab Test Case Executation FAILED....************");
		}
	}

	@Test(priority = 3)
	public static void testStudentAcademic() throws InterruptedException, SecurityException, IOException {
		try {
			log.info("###### Starting Student Academic test case Executation #######");
		
		Automate.CallXpath(driver, ActionXpath.ExpandAcademic, time, "Exapand Academic ");
		Thread.sleep(3000);
		Automate.CallXpath(driver, ActionXpath.ClickDashboard, time, "Click on dashboard");
		Automate.CallXpath(driver, ActionXpath.ClickNotCompleted, time, "Select the not complted acadmic");
		Automate.CallXpath(driver, ActionXpath.clickToLearn, time, "navigate to the leran Section");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,2000)");
		Automate.CallXpath(driver, ActionXpath.openAssign, time, "Open Assign ");
		Automate.CallXpath(driver, ActionXpath.openResources, time, "Open Resources");
		Automate.CallXpath(driver, ActionXpath.CloseAcademicExapand, time, "Close Academic Expand");
		log.info("$$$$$$$$$$ Academic of the Student Test Case PASSED..$$$$$$$$$$$$");
		}catch(InterruptedException e) {
			log.warning("**************Academic Tab Test Case Executation FAILED....************");
		}
	}

	@Test(priority = 4)
	public static void testStudentExamination() throws SecurityException, IOException {
		try {
			log.info("###### Starting Student Examination test case Executation #######");
		
		Automate.CallXpath(driver, ActionXpath.ClickExam, time, "Click on Exam");
		Automate.CallXpath(driver, ActionXpath.opDra, time, "open darwer of the exam");
		Automate.CallXpath(driver, ActionXpath.buttonOpneExam, time, "Open Button open exam");
		log.info("$$$$$$$$$$ Examination of the Student Test Case PASSED..$$$$$$$$$$$$");
		}catch(SecurityException e) {
			log.warning("**************Examination Tab Test Case Executation FAILED....************");
		}
	}

	@Test(priority = 5)
	public static void testStudentAttendance() throws SecurityException, IOException {
		try {
			log.info("###### Starting Student Attendance test case Executation #######");
		
		Automate.CallXpath(driver, ActionXpath.ClickAttendance, time, "Select the Attendance");
		Automate.CallXpath(driver, ActionXpath.clickattendanceHistory, time, "Select the Attendance History");
		log.info("$$$$$$$$$$ Attendance of the Attendance Test Case PASSED..$$$$$$$$$$$$");
		}catch(SecurityException e) {
			log.warning("**************Attendance Tab Test Case Executation FAILED....************");
		}
	}

	@Test(priority = 6)
	public static void testStudentTimeTable() throws InterruptedException, SecurityException, IOException {
		try {
			
			log.info("###### Starting Student Timetable test case Executation #######");
		Thread.sleep(2000);
		Automate.CallXpath(driver, ActionXpath.ClickTimetable, time, "Select time table");
		Automate.CallXpath(driver, ActionXpath.stunext, time, "Select slide of the Timetable");
		Automate.CallXpath(driver, ActionXpath.stubetween, time, "Selecte student Between");

		Automate.CallXpath(driver, ActionXpath.stunext, time, "time Table next");
		log.info("$$$$$$$$$$ Timetable of the Timetable Test Case PASSED..$$$$$$$$$$$$");
		}catch(InterruptedException e) {
			log.warning("**************Timetable Tab Test Case Executation FAILED....************");
		}
	}

	@Test(priority = 7)
	public static void testStudentFees() throws SecurityException, IOException {
		try {
			log.info("###### Starting Student FEES test case Executation #######");
		
		Automate.CallXpath(driver, ActionXpath.ExpandFees, time, "Expand the Fees");
		Automate.CallXpath(driver, ActionXpath.clickMyCart, time, " on the My cart");
		Automate.CallXpath(driver, ActionXpath.stufeestype, time, "Select Fee type");
		Automate.CallXpath(driver, ActionXpath.stufeesamount, time, "Select fee amount");
		Automate.CallXpath(driver, ActionXpath.stufeescollect, time, "Select Fee collect");
		Automate.CallXpath(driver, ActionXpath.stufeesschedulecurrency, time, "Select Fee Schedule currency");
		Automate.CallXpath(driver, ActionXpath.stufeesschedulefees, time, "Select fee schedule fees");
		Automate.CallXpath(driver, ActionXpath.stufeesscheduledate, time, "Select the Fees Schedule date");
		Automate.CallXpath(driver, ActionXpath.stufeesschedulepaid, time, "Fee schudule paid");
		Automate.CallXpath(driver, ActionXpath.stufeesscheduledue, time, "Fees Schedule");
		Automate.CallXpath(driver, ActionXpath.stufeesscheduleremaning, time, "Maning");
		Automate.CallXpath(driver, ActionXpath.clickFeePayment, time, " on the Fees payment");
		Automate.CallXpath(driver, ActionXpath.stufeedue, time, "Fees Due");
		Automate.CallXpath(driver, ActionXpath.stulastfee, time, "Last Fees");
		Automate.CallXpath(driver, ActionXpath.clickManualpayment, time, " on the Manual Payment");
		Automate.CallXpath(driver, ActionXpath.ClickMyTranscetion, time, " on the My Transcetion");
		Automate.CallXpath(driver, ActionXpath.stutransno, time, "Transection Number");
		Automate.CallXpath(driver, ActionXpath.stutrantype, time, "Transcetion Type");
		Automate.CallXpath(driver, ActionXpath.stutranparticular, time, "Transection Particular");
		Automate.CallXpath(driver, ActionXpath.stutrandate, time, "Transcetion Date");
		Automate.CallXpath(driver, ActionXpath.stutranpaid, time, "Transcetion paid");
		Automate.CallXpath(driver, ActionXpath.stutranremain, time, "Reamian");
		Automate.CallXpath(driver, ActionXpath.stutranrecipt, time, "TRans-Recepit");
		Automate.CallXpath(driver, ActionXpath.stutrantranscation, time, "Transcetion");
		Automate.CallXpath(driver, ActionXpath.stutranpayment, time, "payment");
		Automate.CallXpath(driver, ActionXpath.closeExpandFees, time, "CloseExpandFess");
		log.info("$$$$$$$$$$ FEES of the FEES Test Case PASSED..$$$$$$$$$$$$");
		}catch(SecurityException e) {
			log.warning("**************FEES Tab Test Case Executation FAILED....************");
		}
	}

	@Test(priority = 8)
	public static void testStudentFeedback() throws SecurityException, IOException {
		try {
			log.info("###### Starting Student FEEDBACK test case Executation #######");
		Automate.CallXpath(driver, ActionXpath.feedBack, time, "FeedBack");
		Automate.CallXpath(driver, ActionXpath.clickPrograme, time, "Programe Feedbcak");
		Automate.CallXpath(driver, ActionXpath.stufeedbackfaculty, time, "Feedback Faculty");
		Automate.CallXpath(driver, ActionXpath.stufeedbackfac, time, "feedBack Fac");
		Automate.CallXpath(driver, ActionXpath.stufeedbacksubject, time, " feedback Subject");
		Automate.CallXpath(driver, ActionXpath.stufeedbackcode, time, "Code");
		Automate.CallXpath(driver, ActionXpath.stufeedbackfeed, time, "Feed");
		log.info("$$$$$$$$$$ FEEDBACK of the FEEDBACK Test Case PASSED..$$$$$$$$$$$$");
		}catch(SecurityException e) {
			log.warning("**************FEEDBACK Tab Test Case Executation FAILED....************");
		}
	}

	@Test(priority = 9)
	public static void testStudentStudentStatus() throws InterruptedException, SecurityException, IOException {
		try {
			log.info("###### Starting  Student Status test case Executation #######");
		Automate.CallXpath(driver, ActionXpath.StudentService, time, "Student Status");
		Automate.CallXpath(driver, ActionXpath.Raisecase, time, "Raise case");
		Automate.CallXpath(driver, ActionXpath.MakeRaise, time, "Make Raise");
		Thread.sleep(3000);
		Automate.CallXpath(driver, ActionXpath.buttonRaisecase, time, "Button Raise");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,2000)");
		Automate.CallXpath(driver, ActionXpath.cancel, time, "Cancel the raise case");
		log.info("$$$$$$$$$$ Student Status of the Student Status Test Case PASSED..$$$$$$$$$$$$");
		}catch(InterruptedException e) {
			log.warning("**************Student Status Tab Test Case Executation FAILED....************");
		}
	}

	@Test(priority = 10)
	public static void testStudentEvent() throws SecurityException, IOException {
		try {
			log.info("###### Starting Student Event test case Executation #######");
		Automate.CallXpath(driver, ActionXpath.Event, time, "Event");
		Automate.CallXpath(driver, ActionXpath.clcikEvent, time, "Open Event");
		Automate.CallXpath(driver, ActionXpath.bcak, time, "Go back");
		log.info("$$$$$$$$$$ Event of the EVENT Test Case PASSED..$$$$$$$$$$$$");
		}catch(SecurityException e){
		log.warning("**************Event Tab Test Case Executation FAILED....************");
        }
	}

	@Test(priority = 11)
	public static void testStudentsignout() throws SecurityException, IOException {
		try {
			log.info("###### Starting Student SIGNOUT test case Executation #######");
		Automate.CallXpath(driver, ActionXpath.SelectPrtoSignout, time, " on the Profile on the student portal");
		Automate.CallXpath(driver, ActionXpath.signOut, time, "Signout the student portal");
		log.info("$$$$$$$$$$ SIGNOUT of the EVENT Test Case PASSED..$$$$$$$$$$$$");
		}catch(SecurityException e) {
			log.warning("**************SIGNOUT Tab Test Case Executation FAILED....************");
		}
	}

	@AfterSuite
	public static void quitDriver() throws Exception {
		Thread.sleep(3000);
		driver.quit();
	}
}
