package edu.concordia.a2_soen6591;

public class Instance4{    
	public static void bad() throws MyException{
        try{
            Integer.parseInt("a");
        } catch (Exception e) {
            throw new MyException("hello", e.getMessage());
        }
    }
}