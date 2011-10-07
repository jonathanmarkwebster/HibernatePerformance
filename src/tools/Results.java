package tools;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.hibernate.stat.Statistics;

public class Results {

	int n = 0 ;
	String sqlStatements = "" ;
	Class classA ;
	Class classB ;
	Class classC ;
	
	private AnnotationState leftAnnotationState;
	private AnnotationState rightAnnotationState;
	private AnnotationState upAnnotationState;
	private AnnotationState downAnnotationState;
	
	public AnnotationState getUpAnnotationState() {
		return upAnnotationState;
	}

	public void setUpAnnotationState(AnnotationState upAnnotationState) {
		this.upAnnotationState = upAnnotationState;
	}

	public AnnotationState getDownAnnotationState() {
		return downAnnotationState;
	}

	public Class getClassC() {
		return classC;
	}

	public void setClassC(Class classC) {
		this.classC = classC;
	}

	public void setDownAnnotationState(AnnotationState downAnnotationState) {
		this.downAnnotationState = downAnnotationState;
	}

	private Statistics statistics;
	private StatisticsChecker statisticsChecker;
			
	public Results(StatisticsChecker statisticsChecker) {
		this.statisticsChecker = statisticsChecker ;
	}
	
	public void setClassA(Class classA) {
		this.classA = classA;
	}
	
	public void setClassB(Class classB) {
		this.classB = classB;
	}
	
	public String getStatisticsCheckerResults() {
		return statisticsChecker.check(statistics) ;
	}
	
	public String getSqlStatements() {
		return sqlStatements;
	}
	
	public void setSqlStatements(String sqlStatements) {
		this.sqlStatements = sqlStatements;
	}
	
	public String classToString( Class clazz ){
		
		String string = "" ;
		
		if( clazz != null ){

			string += annotationsToString( clazz.getAnnotations(), "" ) ;
			string += "class " + clazz.getName() + "{\n\n" ;

			string += "\t//Methods\n" ;

			for( Method method : clazz.getDeclaredMethods()){
				string += methodToString(method) + "\n" ;
			}

			string += "}\n" ;
		}
		
		return string ;
	}
	
	public String methodToString( Method method ){
		
		String string = "";
		
		string +=  annotationsToString( method.getAnnotations(), "\t") ;
		string += "\t" + method.toGenericString() + "\n" ;
		
		return string ;
	}
		
	public String annotationsToString( Annotation[] annotations, String prefix  ){
		
		String string = "";
		
		if( annotations != null && annotations.length > 0)
			for( Annotation annotation : annotations )
				string += prefix + annotation.toString() + "\n";
		else
			string += "\n" ;
			
		return string ;
	}
	
	public String logSessionFactoryStatistics( Statistics sessionFactoryStatistics ){
		
        return sessionFactoryStatistics.toString().replace(",", "\n")
        										  .replace("[", " :\n[\n")
        										  .replace("]", "\n]");
        
	}		

	public String logHibernateQueryInfo(){
		
		String queryInfo = "" ;
		
        String[] queries = statistics.getQueries();
        
        for( String query : queries ){
        	queryInfo += query ; // + "\n";
        }		
        
        return queryInfo ;
	}		
	
	public String toString(){
		return 		classToString(classA) + "\n"
				+   classToString(classB ) + "\n"
				+   classToString(classC ) + "\n"
				+   "Query :" + logHibernateQueryInfo() + "\n"
				+   "N = " + n + "\n\n" 
				+	getStatisticsCheckerResults()+ "\n\n"
				+	"SQL :" + "\n"
				+	sqlStatements + "\n" 
				/*+   logSessionFactoryStatistics( statistics )*/ ;
	}

	public void setLeftAnnotationState(AnnotationState leftAnnotationState) {
		this.leftAnnotationState = leftAnnotationState  ;
	}

	public void setRightAnnotationState(AnnotationState rightAnnotationState) {
		this.rightAnnotationState = rightAnnotationState ;
	}

	public void setSessionStatistics(Statistics statistics) {
		this.statistics = statistics ;
	}

	public Statistics getStatistics() {
		return statistics;
	}

	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}

	public Class getClassA() {
		return classA;
	}

	public Class getClassB() {
		return classB;
	}

	public AnnotationState getLeftAnnotationState() {
		return leftAnnotationState;
	}

	public AnnotationState getRightAnnotationState() {
		return rightAnnotationState;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}	
	
}
