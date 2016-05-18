package BuisnessDay;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * This class is designed to calculate normal business days. 
 */

/*****
 * 
 * Here are a list of typical standard US holidays
 * New Year's Day            (1st of January)
 * Martin Luther King Jr Day (3rd Monday of January)
 * Presidents Day            (3rd Monday of February)
 * Memorial Day              (Last Monday of May)
 * Independence Day          (4th of July)
 * Labor Day                 (1st Monday of September)
 * Veterans Day              (11th of November)
 * Thanksgiving Day          (4th Thursday of November)
 * Christmas Day             (25th of December)
 *
 */

public class BusinessDay {
	
	private static int jan = 0;
	private static int feb = 1;
	private static int mar = 2;
	private static int apr = 3;
	private static int may = 4; 
	private static int jun = 5;
	private static int jul = 6;
	private static int aug = 7;
	private static int sep = 8;
	private static int oct = 9;
	private static int nov = 10;
	private static int dec = 11;
	
	private static int sun = 0;
	private static int mon = 1;
	private static int tue = 2;
	private static int wed = 3;
	private static int thu = 4;
	private static int fri = 5;
	private static int sat = 6;
	
	// empty constructor
	public BusinessDay(){}

	// Takes a starting date and moves it forward by so many days out.
	public Date getBusinessDaysOut(Date startDate, int daysOut){
		Date tempDate = startDate;
		
		if(daysOut <= 0){
			return startDate;
		}
		
		tempDate.setDate(startDate.getDate()+1);
		tempDate = getDateChecked(tempDate, 1);
		
		return getBusinessDaysOut(tempDate,daysOut-1);
	}
	
	// Takes todays date and returns a date some many days from todays date. 
	public Date getBusinessDaysFrom(int daysFrom){
		Date d = new Date();
		if(daysFrom <= 0){
			return d;
		}
		
		d.setDate(d.getDate()-1);
		d = getDateChecked(d, -1);
		return getBusinessDaysFrom(daysFrom-1);
	}
	
	//takes date and checks days previous from 
	public Date getBusinessDaysFrom(Date date, int daysFrom){
		Date d = date;
		if(daysFrom <= 0){
			return d;
		}
		//System.out.println(d);
		d.setDate(d.getDate()-1);
		d = getDateChecked(d, -1);
		return getBusinessDaysFrom(d, daysFrom-1);
	}

	public int numBusinessDaysOut(Date dateTo, Date dateFrom){
		Date tempDate = dateFrom;
		
		tempDate.setDate(dateFrom.getDate()+1);

		if(dateFrom.after(dateTo) || dateFrom.equals(dateTo)){
			return 0;
		}

		//tempDate.setDate(dateFrom.getDate()+1);
		if(isSaturday(tempDate) || isSunday(tempDate) || (isHoliday(tempDate))){
			return numBusinessDaysOut(dateTo, tempDate);
		} else {
			return numBusinessDaysOut(dateTo, tempDate)+1;
		}
	}
	
	public int numBusinessDaysOut(Date dateFrom){
		Date todayDate = new Date();
		Date tempDate = dateFrom;

		tempDate.setDate(dateFrom.getDate()+1);
		if(dateFrom.after(todayDate) || dateFrom.equals(todayDate)){
			return 0;
		}
		
		//tempDate.setDate(dateFrom.getDate()+1);
		if(isSaturday(dateFrom) || isSunday(dateFrom) || (isHoliday(dateFrom))){
			return numBusinessDaysOut(tempDate);
		} else {
			return numBusinessDaysOut(tempDate)+1;
		}
	}
	
	//This date check does not consider observed holidays 
	//for the next business day. 
	public Date getDateChecked(Date d, int adj){
		Date tempDate = d;
		
		//Checks if date is on the weekend
		if(isSaturday(d)){
			tempDate.setDate(d.getDate()+adj);
			return getDateChecked(tempDate, adj);
		}
		
		if(isSunday(d)){
			tempDate.setDate(d.getDate()+adj);
			return getDateChecked(tempDate, adj);
		}
		
		if(isHoliday(d)){
			tempDate.setDate(d.getDate()+adj);
			return getDateChecked(tempDate, adj);
		}
		
		return d;
		
	}
	
	//This date check does consider observed holidays 
	//for the next business day. (i.e. Holidays that
	//occur on Sunday but the business is closed on
	//Monday)
	public Date getDateChecked2(Date d, int adj){	
		Date tempDate = d;
		
		//Checks if Saturday is a holiday.
		//Sets Friday as a holiday.
		if(isFriday(d)){
			tempDate.setDate(d.getDate()+adj);
			if(isHoliday(tempDate))
				return getDateChecked(tempDate, adj);
			else
				tempDate.setDate(d.getDate()-1);
		}
		
		if(isHoliday(d)){
			if(isSunday(d)){
				tempDate.setDate(d.getDate()+adj+adj);
				return tempDate;
			}
			tempDate.setDate(d.getDate()+adj);
			return getDateChecked(tempDate, adj);
		}
		
		if(isSaturday(d)){
			tempDate.setDate(d.getDate()+adj);
			return getDateChecked(tempDate, adj);
		}
		
		if(isSunday(d)){
			tempDate.setDate(d.getDate()+1);
			return getDateChecked(tempDate, adj);
		}
		
		return d;
	}
	
	// Takes a date and checks if it is after normal business hours
	private boolean isInBusinessHours(Date d){
		Date endDay = (Date) d.clone();
		
		endDay.setHours(17);
		endDay.setMinutes(0);
		endDay.setSeconds(0);
		
		if(d.after(endDay)){
			return false;
		}
		
		return true;
		
	}
	
	private boolean isFriday(Date d) {
		if (d.getDay()==fri)
			return true;
		return false;
	}
	
	private boolean isSunday(Date d) {
		// TODO Auto-generated method stub
		if (d.getDay()==sun)
			return true;
		return false;
	}

	private boolean isSaturday(Date d) {
		// TODO Auto-generated method stub
		if (d.getDay()==sat)
			return true;
		return false;
	}

	private boolean isHoliday(Date d){		
		if(isChristmas(d)||isThanksgiving(d)||isVeterans(d)||isLabor(d)||
				isIndependence(d)||isMemorial(d)||isPresidents(d)||
				isMLK(d)||isNewYears(d)){
			return true;
		}
		return false;
	}
	
	private boolean isChristmas(Date d) {
		// TODO Auto-generated method stub
		//* Christmas Day             (25th of December)
		if(d.getMonth()==dec && d.getDate()==25)
			return true;
		return false;
	}

	private boolean isThanksgiving(Date d) {
		// TODO Auto-generated method stub
		//* Thanksgiving Day          (4th Thursday of November)
		if(d.getMonth()==nov && d.getDay() == thu && (d.getDate()>21 && d.getDate()<29))
			return true;
		return false;
	}

	private boolean isVeterans(Date d) {
		// TODO Auto-generated method stub
		//* Veterans Day              (11th of November)
		if(d.getMonth()==nov && d.getDate()==11)
			return true;
		return false;
	}

	private boolean isLabor(Date d) {
		// TODO Auto-generated method stub
		//* Labor Day                 (1st Monday of September)
		if(d.getMonth()==sep && d.getDay()==mon && d.getDate()<8)
			return true;
		return false;
	}

	private boolean isIndependence(Date d) {
		// TODO Auto-generated method stub
		//* Independence Day          (4th of July)
		if(d.getMonth()==jul && d.getDate()==4)
			return true;
		return false;
	}

	private boolean isMemorial(Date d) {
		// TODO Auto-generated method stub
		//* Memorial Day              (Last Monday of May)
		if(d.getMonth()==may && d.getDay()==mon && d.getDate()>24)
			return true;
		return false;
	}

	private boolean isPresidents(Date d) {
		// TODO Auto-generated method stub
		//* Presidents Day            (3rd Monday of February)
		if(d.getMonth()==feb && d.getDay()==mon && (d.getDate()>14 && d.getDate()<22))
			return true;
		return false;
	}

	private boolean isMLK(Date d) {
		// TODO Auto-generated method stub
		//* Martin Luther King Jr Day (3rd Monday of January)
		if(d.getMonth()==jan && d.getDay()==mon && (d.getDate()>14 && d.getDate()<22))
			return true;
		return false;
	}

	private boolean isNewYears(Date d) {
		// TODO Auto-generated method stub
		//* New Year's Day            (1st of January)
		if(d.getMonth()==jan && d.getDate()==mon)
			return true;
		return false;
	}

}

