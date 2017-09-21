package org.configManager.exception;


public class MyException extends RuntimeException{
	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyException()
	    {
	    }

	    public MyException(RuntimeException cause)
	    {
	        super(cause);
	    }

	    public MyException(String message, RuntimeException cause)
	    {
	        super(message, cause);
	    }

	    public MyException(String message)
	    {
	        super(message);
	    }
}
