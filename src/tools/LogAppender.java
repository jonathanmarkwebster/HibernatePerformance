package tools;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.apache.log4j.Layout;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;

public class LogAppender {

	private WriterAppender writerAppender;
	private org.apache.log4j.Logger targetLogger;
	private ByteArrayOutputStream byteArrayOutputStream;
	
	public void open( String loggerName ){

	    byteArrayOutputStream = new ByteArrayOutputStream();
		OutputStream os = byteArrayOutputStream;
		Layout layout = new PatternLayout();
		
		writerAppender = new WriterAppender(layout, os); 
		
		targetLogger = org.apache.log4j.Logger.getLogger( loggerName );		
		targetLogger.addAppender( writerAppender ) ;	
	}
	
	public void close(){
		targetLogger.removeAppender(writerAppender);
	}
	
	public String toString(){
		return new String( byteArrayOutputStream.toByteArray());
	}	
	
}
