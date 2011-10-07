package tools;

import org.hibernate.stat.Statistics;

public abstract class StatisticsChecker {

	public abstract String getMessage() ;
	public abstract boolean checkCondition( Statistics statistics );
	
	public String check( Statistics sessionFactoryStatistics ){
	       return   checkCondition( sessionFactoryStatistics ) ? getMessage() : "" ;   
	}

}
