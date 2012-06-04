package net.davinogueira.prevbolsa.infra;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	
	public static Date formatDate(String format, String date){
		DateFormat df = new SimpleDateFormat(format);
		
		Date result = null;
		
		try {
			result = df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static Date formatDate(String format, Long date){
		DateFormat df = new SimpleDateFormat(format);
		
		Date result = null;
		
		try {
			result = df.parse(date.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String formatDate(String format, Date date){
		DateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}
	
	public static Double roundingDouble(Double value){
		BigDecimal bd = new BigDecimal(value).setScale(2, RoundingMode.HALF_EVEN);
		return bd.doubleValue();
	}
}
