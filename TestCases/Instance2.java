package edu.concordia.a2_soen6591;

public class Instance2{    
	public static void good() throws MyException{
        try{
            Integer.parseInt("a");
        } catch (Exception e) {
            throw new MyException("hello", e);
        }
    }
}