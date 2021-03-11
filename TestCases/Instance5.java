package edu.concordia.a2_soen6591;

public class Instance5{   
	public static void test(Exception e) {
		int a =10;
	}
	
	public static void bad(int value) throws MyException{
        try{
            Integer.parseInt("a");
        } catch (Exception e) {
        	String msg = "hello" + e.getMessage();
        	test(msg, e);
            throw new MyException(msg);
        }
    }
}