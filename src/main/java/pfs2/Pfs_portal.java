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

public class Pfs_portal {
    private static WebDriver driver;
	static int time = 2000;
	public static Logger log = Logger.getLogger("Pfs_portal");
    
    public static void main( String[] args ) throws Exception
    {
        FileHandler logFile = new FileHandler("C:\\Users\\Public\\Documents\\PFS_results.log");
	    logFile.setFormatter(new SimpleFormatter());
	    log.addHandler(logFile);
	    log.info("A message logged to the file");
        String CSV_PATH = "C:\\Users\\Public\\Documents\\pfs.csv";
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
            initDriver(PFSurl);
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
            else if ("faculty".equals(Role)){
                System.out.println("Executing Faculty portal");
                testFaculty();
testFacultyQuestionBank();
testFacultyCourseContent();
testFacultyExamination();
testFacultyMYStudent();
testFacultyAttendance();
testFaculityTimetable();
testFacultyService();
testFacultyEvent();
testFacultyViewPr();
            }
            //After all test are over close the browser
            quitDriver();
        }
    }
    @BeforeSuite
    public static void initDriver(String url) throws Exception {
        ChromeOptions op = new ChromeOptions();
        op.addArguments("--disable-notifications");
        System.out.println("the:"+op);
        System.out.println("\n Welcome \n");
        log.info("=============Automate Testing============ ");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(op);
        log.info("Launching chrome browser");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(url);
       
        driver.manage().window().maximize();
        log.info("Maximize the Browser");	
    }
    @Test
    public static void login(String Email) throws InterruptedException, SecurityException, IOException {
        int time=2000;
        String regex="Null";
        Automate.callSenkeys(driver, ActionXpath.email, Email,time);
		Automate.CallXpath(driver, ActionXpath.SignIn, time, "Sign in");
        Automate.CallXpath(driver, ActionXpath.mobile, time, "Enter mobile Number");
        Automate.CallXpath(driver, ActionXpath.mobile2, time, "Click Mobile ");
        Automate.CallXpath(driver, ActionXpath.SignIn, time, "Sign in for otp");
        //Thread.sleep(time);
        Alert alert = driver.switchTo().alert(); // switch to alert
        String alertMessage= driver.switchTo().alert().getText(); // capture alert message
        System.out.println(alertMessage); // Print Alert Message
        Pattern pt = Pattern.compile("-?\\d+");
        Matcher m = pt.matcher(alertMessage);
        while (m.find()) {
            regex = m.group();
        }
        Thread.sleep(2000);
        alert.accept();
        Automate.callSenkeys(driver, ActionXpath.OtpInput, regex, time);
        Automate.CallXpath(driver, ActionXpath.submit, time, "Submit");
    }
    @Test(priority = 1)
	public static void testStudent() throws SecurityException, IOException {
		try {
			log.info(" Test-1:Starting Home tab  case executation \n");
			Automate.CallXpath(driver, ActionXpath.expand, time, "Expand the Enrollment");
			log.info(" Home tab test case PASSED..  \n");
		} catch (SecurityException e) {
			log.warning("****Home tab test case executation FAILED..****");
		}
	}
    @Test(priority = 2)
	public static void testStudentEnrollment() throws InterruptedException, SecurityException, IOException {
		try {
			log.info(" Test-2: Starting Student Enrollment  case Executation \n");
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
		log.info(" Enrollment of the Student Test Case PASSED.. \n");
		}catch(InterruptedException e) {
			log.warning("**************Enrollment Tab Test Case Executation FAILED....************");
		}
	}
    @Test(priority = 3)
	public static void testStudentAcademic() throws InterruptedException, SecurityException, IOException {
		try {
			log.info("Test-3 Starting Student Academic  case Executation\n ");
		
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
		log.info(" Academic of the Student Test Case PASSED..\n");
		}catch(InterruptedException e) {
			log.warning("**************Academic Tab Test Case Executation FAILED....************");
		}
	}
    @Test(priority = 4)
	public static void testStudentExamination() throws SecurityException, IOException {
		try {
			log.info("Test-4: Starting Student Examination test case Executation \n");
		
		Automate.CallXpath(driver, ActionXpath.ClickExam, time, "Click on Exam");
		Automate.CallXpath(driver, ActionXpath.opDra, time, "open darwer of the exam");
		Automate.CallXpath(driver, ActionXpath.buttonOpneExam, time, "Open Button open exam");
		log.info(" Examination of the Student Test Case PASSED..\n");
		}catch(SecurityException e) {
			log.warning("**************Examination Tab Test Case Executation FAILED....************");
		}
	}
    @Test(priority = 5)
	public static void testStudentAttendance() throws SecurityException, IOException {
		try {
			log.info("Test-5: Starting Student Attendance test case Executation \n");
		
		Automate.CallXpath(driver, ActionXpath.ClickAttendance, time, "Select the Attendance");
		Automate.CallXpath(driver, ActionXpath.clickattendanceHistory, time, "Select the Attendance History");
		log.info(" Attendance of the Attendance Test Case PASSED..\n");
		}catch(SecurityException e) {
			log.warning("**************Attendance Tab Test Case Executation FAILED....************");
		}
	}
    @Test(priority = 6)
	public static void testStudentTimeTable() throws InterruptedException, SecurityException, IOException {
		try {
			
			log.info(" Test-6 :\n Starting Student Timetable test case Executation\n ");
		Thread.sleep(2000);
		Automate.CallXpath(driver, ActionXpath.ClickTimetable, time, "Select time table");
		Automate.CallXpath(driver, ActionXpath.stunext, time, "Select slide of the Timetable");
		Automate.CallXpath(driver, ActionXpath.stubetween, time, "Selecte student Between");

		Automate.CallXpath(driver, ActionXpath.stunext, time, "time Table next");
		log.info(" Timetable of the Timetable Test Case PASSED..\n");
		}catch(InterruptedException e) {
			log.warning("**************Timetable Tab Test Case Executation FAILED....************");
		}
	}
    @Test(priority = 7)
	public static void testStudentFees() throws SecurityException, IOException {
		try {
			log.info(" Test-7:\n Starting Student FEES test case Executation \n");
		
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
		log.info(" FEES of the FEES Test Case PASSED..\n");
		}catch(SecurityException e) {
			log.warning("**************FEES Tab Test Case Executation FAILED....************");
		}
	}
    @Test(priority = 8)
	public static void testStudentFeedback() throws SecurityException, IOException {
		try {
			log.info("Test-8:\n  Starting Student FEEDBACK test case Executation\n");
		Automate.CallXpath(driver, ActionXpath.feedBack, time, "FeedBack");
		Automate.CallXpath(driver, ActionXpath.clickPrograme, time, "Programe Feedbcak");
		Automate.CallXpath(driver, ActionXpath.stufeedbackfaculty, time, "Feedback Faculty");
		Automate.CallXpath(driver, ActionXpath.stufeedbackfac, time, "feedBack Fac");
		Automate.CallXpath(driver, ActionXpath.stufeedbacksubject, time, " feedback Subject");
		Automate.CallXpath(driver, ActionXpath.stufeedbackcode, time, "Code");
		Automate.CallXpath(driver, ActionXpath.stufeedbackfeed, time, "Feed");
		log.info(" FEEDBACK of the FEEDBACK Test Case PASSED..\n");
		}catch(SecurityException e) {
			log.warning("**************FEEDBACK Tab Test Case Executation FAILED....************");
		}
	}
    @Test(priority = 9)
	public static void testStudentStudentStatus() throws InterruptedException, SecurityException, IOException {
		try {
			log.info("Test-9: \n Starting  Student Status test case Executation \n");
		Automate.CallXpath(driver, ActionXpath.StudentService, time, "Student Status");
		Automate.CallXpath(driver, ActionXpath.Raisecase, time, "Raise case");
		Automate.CallXpath(driver, ActionXpath.MakeRaise, time, "Make Raise");
		Thread.sleep(3000);
		Automate.CallXpath(driver, ActionXpath.buttonRaisecase, time, "Button Raise");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,2000)");
		Automate.CallXpath(driver, ActionXpath.cancel, time, "Cancel the raise case");
		log.info(" Student Status of the Student Status Test Case PASSED..\n");
		}catch(InterruptedException e) {
			log.warning("**************Student Status Tab Test Case Executation FAILED....************");
		}
	}
    @Test(priority = 10)
	public static void testStudentEvent() throws SecurityException, IOException {
		try {
			log.info("Test Case-10: \n Starting Student Event case Executation \n ");
		Automate.CallXpath(driver, ActionXpath.Event, time, "Event");
		Automate.CallXpath(driver, ActionXpath.clcikEvent, time, "Open Event");
		Automate.CallXpath(driver, ActionXpath.bcak, time, "Go back");
		log.info(" Event of the EVENT Test Case PASSED..\n");
		}catch(SecurityException e){
		log.warning("**************Event Tab Test Case Executation FAILED....\n");
        }
        log.info("STUDENT PORTAL TEST CASES WAS DONE SUCCESSFULL");
	}

	@Test(priority = 11)
	public static void testStudentsignout() throws SecurityException, IOException {
		try {
			log.info("Test Case-11 :\n Starting Student SIGNOUT  case Executation \n");
		Automate.CallXpath(driver, ActionXpath.SelectPrtoSignout, time, " on the Profile on the student portal");
		Automate.CallXpath(driver, ActionXpath.signOut, time, "Signout the student portal");
		log.info(" SIGNOUT of the EVENT Test Case PASSED...\n");
		}catch(SecurityException e) {
			log.warning("**************SIGNOUT Tab Test Case Executation FAILED....************");
		}
	}

    @Test(priority = 12)
    public static void testFaculty() throws Exception{
        try {
			log.info("Test-12 : \n Starting FACULTY PORTAL Academic tab test case executation \n");
        Automate.CallXpath(driver, ActionXpath.facClickacademics, time,"facClickacademics");
        Automate.CallXpath(driver, ActionXpath.facclickdashboard, time,"facclickdashboard");		      
        Automate.CallXpath(driver, ActionXpath.facdbfilter, time,"facdbfilter");
        Automate.CallXpath(driver, ActionXpath.facdbfilterselect, time,"facdbfilterselect");
        Automate.CallXpath(driver, ActionXpath.facdbresfilter, time,"facdbresfilter");
        Automate.CallXpath(driver, ActionXpath.facdbrestypes, time,"facdbrestypes");
        Automate.CallXpath(driver, ActionXpath.facdbrestypesselect, time,"facdbrestypesselect");
        Automate.CallXpath(driver, ActionXpath.facdbresapply, time,"facdbresapply");
        log.info(" ACADEMIC Test case PASSED \n");
    } catch (SecurityException e) {
        log.warning("*******************ACADEMIC Test case FAILED *******************************");
    }
}
@Test(priority = 13)
public static void testFacultyQuestionBank() {
    try {
        log.info(" Test-13:\n Starting QuestionBank Tab test case Executation \n");
        Automate.CallXpath(driver, ActionXpath.facqb, time,"facqb");
        Automate.CallXpath(driver, ActionXpath.facaddque, time,"facaddque");
        Automate.CallXpath(driver, ActionXpath.facquetype, time,"facquetype");
        Automate.CallXpath(driver, ActionXpath.facquetypeselect, time,"facquetypeselect");
        Automate.CallXpath(driver, ActionXpath.facqueclass, time,"facqueclass");
        Automate.CallXpath(driver, ActionXpath.facqueclassselect, time,"facqueclassselect");
        Automate.CallXpath(driver, ActionXpath.facquesub, time,"facquesub");
        Automate.CallXpath(driver, ActionXpath.facquesubselect, time,"facquesubselect");		      
        Automate.CallXpath(driver, ActionXpath.facquesubmit, time,"facquesubmit");
        Automate.CallXpath(driver, ActionXpath.facqueback, time,"facqueback");
        log.info(" QuestionBank Test Case PASSED \n ");
    } catch (Exception e) {
        log.warning("***************** QuestionBank Test Case FAILED ****************************************");
    }
}

@Test(priority = 14)
public static void testFacultyCourseContent() {
    try {
        log.info("Test-14:\n Course Content Test Execution  Started \n ");
        Automate.CallXpath(driver, ActionXpath.faccc, time,"faccc");
        Automate.CallXpath(driver, ActionXpath.facccactivity, time,"facccactivity");
        Automate.CallXpath(driver, ActionXpath.faccform, time,"faccform");
        Automate.CallXpath(driver, ActionXpath.faccformadd, time,"faccformadd");
        Automate.CallXpath(driver, ActionXpath.facccformcancel, time,"facccformcancel");
        Automate.CallXpath(driver, ActionXpath.facccformyes, time,"facccformyes");
        Automate.CallXpath(driver, ActionXpath.facccres, time,"facccres");
        Automate.CallXpath(driver, ActionXpath.facccrespdf, time,"facccrespdf");
        Automate.CallXpath(driver, ActionXpath.facccresadd, time,"facccresadd");
        Automate.CallXpath(driver, ActionXpath.facccrescancel, time,"facccrescancel");
        Automate.CallXpath(driver, ActionXpath.facccresyes, time,"facccresyes");
        log.info("  Course Content Test PASSED..\n");
    } catch (Exception e) {
        log.warning("********************* Course Contetn Test Failed *********************************");
    }

}
@Test(priority = 15)
public static void testFacultyExamination() {
    try {
        log.info("Test-15:\n Examination Test Executation Statred \n");
        Automate.CallXpath(driver, ActionXpath.facexam, time,"facexam");
        Automate.CallXpath(driver, ActionXpath.facexamarrow, time,"facexamarrow");
        Automate.CallXpath(driver, ActionXpath.facexamdropdown, time,"facexamdropdown");
        Automate.CallXpath(driver, ActionXpath.facexamexam, time,"facexamexam");
        Automate.CallXpath(driver, ActionXpath.facexamdate, time,"facexamdate");
        Automate.CallXpath(driver, ActionXpath.faceexamtime, time,"faceexamtime");
        log.info(" Examanation test cases PASSED... \n ");
    } catch (Exception e) {
        log.warning("************************ Examination test case FAILED..***************************** ");
    }
}
@Test(priority = 16)
public static void testFacultyMYStudent() {
    try {
        log.info(" Test -16: \n My Students Test Executation Started \n");
        Automate.CallXpath(driver, ActionXpath.facstudent, time,"facstudent");
        Automate.CallXpath(driver, ActionXpath.facstudrop, time,"facstudrop");
        Automate.CallXpath(driver, ActionXpath.facstudropselect, time,"facstudropselect");
        Automate.CallXpath(driver, ActionXpath.facstuname, time,"facstuname");
        Automate.CallXpath(driver, ActionXpath.facsturegid, time,"facsturegid");
        Automate.CallXpath(driver, ActionXpath.facstuemail, time,"facstuemail");
        Automate.CallXpath(driver, ActionXpath.facstuatt, time,"facstuatt");
        Automate.CallXpath(driver, ActionXpath.facstusearch, time,"facstusearch");
        log.info("My Students  Test executation PASSED ");
    } catch (Exception e) {
        log.warning("********************** MY Student Test Executation FAILED...********************");
    }
}
@Test(priority = 17)
public static void testFacultyAttendance() {
    try {
        log.info("Test-17 : \n Attendance Test Executation Startred \n ");
        Automate.CallXpath(driver, ActionXpath.facatt, time,"facatt");
        Automate.CallXpath(driver, ActionXpath.facattsub, time,"facattsub");
        Automate.CallXpath(driver, ActionXpath.facattsubselect, time,"facattsubselect");
        Automate.CallXpath(driver, ActionXpath.facattterm, time,"facattterm");
        Automate.CallXpath(driver, ActionXpath.facattdate, time,"facattdate");
        Automate.CallXpath(driver, ActionXpath.facattmark, time,"facattmark");
        Automate.CallXpath(driver, ActionXpath.facattmarkattendence, time,"facattmarkattendence");
        Automate.CallXpath(driver, ActionXpath.facatthistory, time,"facatthistory");
        Automate.CallXpath(driver, ActionXpath.facatthistsub, time,"facatthistsub");
        Automate.CallXpath(driver, ActionXpath.facatthistsubselect, time,"facatthistsubselect");
        Automate.CallXpath(driver, ActionXpath.facatthiststdate, time,"facatthiststdate");
        Automate.CallXpath(driver, ActionXpath.facatthistenddate, time,"facatthistenddate");
        Automate.CallXpath(driver, ActionXpath.facatthistterm, time,"facatthistterm");
        Automate.CallXpath(driver, ActionXpath.facatthistst, time,"facatthistst");
        Automate.CallXpath(driver, ActionXpath.facatthisend, time,"facatthisend");
        Automate.CallXpath(driver, ActionXpath.facatthistatt, time,"facatthistatt");
    	log.info(" Attendance Test Executation PASSED \n ");
		} catch (Exception e) {
			log.warning("************************* Attendance Test executation FAILED...*****************");
		}
	}
    @Test(priority = 18)
	public static void testFaculityTimetable() {
		try {
			log.info("Test -18: Timetable Test Executation Started \n ");
    Automate.CallXpath(driver, ActionXpath.facClickTimetable, time,"facClickTimetable");
    Automate.CallXpath(driver, ActionXpath.facttmonth, time,"facttmonth");
    Automate.CallXpath(driver, ActionXpath.facttweek, time,"facttweek");
    Automate.CallXpath(driver, ActionXpath.facttday, time,"facttday");
    Automate.CallXpath(driver, ActionXpath.facnext, time,"facnext");
    Automate.CallXpath(driver, ActionXpath.facbetween, time,"facbetween");
    log.info(" Timetable test case PASSED..\n ");
} catch (Exception e) {
    log.warning("******************** Timetable test case FAILED...**********************************");
}
}
@Test(priority = 19)
public static void testFacultyService() {
    try {
        log.info( " Test-19 : \n Faculty Service Test case Started \n");
    Automate.CallXpath(driver, ActionXpath.facClickservice, time,"facClickservice");
    Automate.CallXpath(driver, ActionXpath.facraise, time,"facraise");
    Automate.CallXpath(driver, ActionXpath.facrequest, time,"facrequest");
    Automate.CallXpath(driver, ActionXpath.facraisecase, time,"facraisecase");
    Automate.CallXpath(driver, ActionXpath.facraisecaseno, time,"facraisecaseno");
    Automate.CallXpath(driver, ActionXpath.facraisesub, time,"facraisesub");
    Automate.CallXpath(driver, ActionXpath.facraisedesc, time,"facraisedesc");
    Automate.CallXpath(driver, ActionXpath.facraisedate, time,"facraisedate");
    Automate.CallXpath(driver, ActionXpath.facraisestatus, time,"facraisestatus");
    log.info(
        " Faculty Service Test case PASSED..\n ");
} catch (Exception e) {
log.warning("************************* Faculity Services test case Failed ... *****************");
}
}
@Test(priority = 20)
public static void testFacultyEvent() {
    try {
        log.info( " Test-20 : \n Faculty Portal Event Tab Test case Started \n");
    Automate.CallXpath(driver, ActionXpath.facEvent, time,"facEvent");
    Automate.CallXpath(driver, ActionXpath.faceventlocation, time,"faceventlocation");
    Automate.CallXpath(driver, ActionXpath.faceventlocationselect, time,"faceventlocationselect");
    log.info(
        " Event Test case Executation PASSED..");
} catch (Exception e) {
log.warning("**************************** Event Test case Executation FAILED..*******************");
}
}
@Test(priority = 21)
public static void testFacultyViewPr() {
    try {
        log.info(" Test-21 : \n View Profile test Executation Started \n");
    Automate.CallXpath(driver, ActionXpath.facselectpro, time,"facselectpro");
    Automate.CallXpath(driver, ActionXpath.facprofile, time,"facprofile");	      
    log.info("=============View Profile Completed============ ");
    
    Automate.CallXpath(driver, ActionXpath.facSelectPrtoSignout, time,"facSelectPrtoSignout");
    Automate.CallXpath(driver, ActionXpath.facsignOut, time,"facsignOut");
    log.info(
        " View Profile Sign out Test Case PASSED..\n ");
} catch (Exception e) {
log.warning("**************************** View Profile Signout test case FAILED...***************");
}
log.info("=============Completed===============");

}
    @AfterSuite
    public static void quitDriver() throws Exception {
        Thread.sleep(3000);
        driver.quit();
    }
}
