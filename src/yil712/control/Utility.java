package yil712.control;

import java.sql.Timestamp;

public class Utility {
	
	public static String getNewID(String id) {
		id = id.trim();
		char type = id.charAt(0);
		int oldValue = Integer.parseInt(id.substring(1));
		String newStr = String.format("%06d", (oldValue + 1));
		return type + newStr;
	}
	
	public static boolean isLongTerm(Timestamp oldTime, Timestamp newTime) {
		long delta = Math.abs(newTime.getTime() - oldTime.getTime());
		int days = (int) (delta / (1000 * 60 * 60 * 24));
//		System.out.println("Days: " + days);
		if (days >= 365) {
//			System.out.println("long...");
			return true;
		}
//		System.out.println("short...");
		return false;
	}
}
