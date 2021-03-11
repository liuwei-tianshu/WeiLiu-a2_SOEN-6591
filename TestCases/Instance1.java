package edu.concordia.a2_soen6591;

public class Instance1{
	public static void good() throws MyException{
        try{
            Integer.parseInt("a");
        } catch (Exception e) {
            throw new MyException(e); // first possibility to create exception chain
        }
    }
}