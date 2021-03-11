package edu.concordia.a2_soen6591;

public class Instance6{    
	public static void good() throws MyException{
        try{
            Integer.parseInt("a");
        } catch (Exception e) {
        	  s_logger.error("copy failed",e);
        	  errMsg=e.toString();
        }
    }
}