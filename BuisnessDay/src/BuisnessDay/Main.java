package BuisnessDay;

import java.util.Calendar;
import java.util.Date;

public class Main {
	
	@SuppressWarnings({ "deprecation", "null" })
	public static void main(String [] args)
	{
		//Calendar thisDate = Calendar.getInstance();
		Date testDate = new Date();
		Date testDate2 = new Date();
		Date returnDate = new Date();
		BusinessDay BD = new BusinessDay();
		int daysOut = 7;
		
		testDate.setMonth(3);
		testDate.setDate(15);
		testDate.setYear(116);
		testDate2.setMonth(3);
		testDate2.setDate(25);
		testDate2.setYear(116);
		
		System.out.println("Here is the orignal date: ");
		System.out.println(testDate.toString());
		
		//returnDate = BD.getDateChecked(testDate);
		//returnDate = testDate; 
		
		//System.out.println("Here is the day check: ");
		//System.out.println(returnDate.toString());
		
		returnDate = BD.getBusinessDaysOut((Date)testDate.clone(), daysOut);
		System.out.println("Here is "+daysOut+" days out: ");
		System.out.println(returnDate.toString());
		
		returnDate = BD.getBusinessDaysFrom((Date)testDate.clone(), daysOut);
		System.out.println("Here is "+ daysOut+" days from: ");
		System.out.println(returnDate.toString());
		System.out.println();
		
		//System.out.println("Your case is " + String.valueOf(BD.numBusinessDaysOut((Date)testDate.clone()))+" days out.");
		
		System.out.println(testDate);
		System.out.println(testDate2);
		System.out.println("Your case is " + String.valueOf(BD.numBusinessDaysOut((Date)testDate2.clone(),
				(Date)testDate.clone()))+" days out");
		
	}

}
