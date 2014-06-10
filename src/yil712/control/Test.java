package yil712.control;

public class Test {

	public static void main(String args[]) {
	
		String phone1 = "484-664-0040";
		String phone2 = "484-664-55";
		
		String pattern = "[0-9]{3}\\-[0-9]{3}\\-[0-9]{4}";
		
		System.out.println(phone1.matches(pattern));
		System.out.println(phone2.matches(pattern));
	}
	
}
