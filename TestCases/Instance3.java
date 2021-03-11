package edu.concordia.a2_soen6591;

public class Instance3{    
	public static void bad() throws MyException{
        try{
            Integer.parseInt("a");
        } catch (Exception e) {
        	throw new MyException(e.getMessage());
        }
    }
}
