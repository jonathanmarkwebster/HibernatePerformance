package tools;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ResultsGrid {

	private Set<Results> results;

	public ResultsGrid( Set<Results> results ){
		this.results = results ;
	}

	public String toString(){
		
		String string = "";		
		
		Results firstResult = results.iterator().next();
		string += "Query : " + firstResult.logHibernateQueryInfo() + "\n";
		string += "N = " + firstResult.getN() + "\n";
		
		int columnWidth = 0 ;
		int columnNumber = 1 ;
		
		Map<String,Map<String,Results>> grid = new HashMap<String, Map<String,Results>>();

		// Sort grid
		for( Results result : results ){
			
			Map<String, Results> column ; 
			
			if( grid.containsKey( result.getLeftAnnotationState().toString() )){
				column = grid.get( result.getLeftAnnotationState().toString() );
			}else{
				column = new HashMap<String, Results>() ;				
			}
			
			column.put( result.getRightAnnotationState().toString(), result );
			grid.put(result.getLeftAnnotationState().toString(), column );
		}
		
		// Find dimensions
		String firstRow = grid.keySet().iterator().next() ;
		columnNumber = grid.get( firstRow ).size() + 1;
		
		for( String row : grid.keySet() ){			
			for( String column : grid.get(row).keySet() ){
				columnWidth = Math.max( column.length(), columnWidth );
			}
			columnWidth = Math.max( row.length(), columnWidth );
		}
		
		// Header line
		string += pad("", "-", columnWidth * columnNumber ) + "\n" ;
		string += pad( "", " ", columnWidth ) ;			
		for( String column : grid.get( firstRow ).keySet() ){
				string += pad( "| " + column, " ", columnWidth  );
		}
		
		string += "\n" ;

		
		// to String
		for( String row : grid.keySet() ){			
			
			// Title Column
			string += pad("", "-", columnWidth * columnNumber ) + "\n" ;
			string += pad( row, " ", columnWidth );	
			
			for( String column : grid.get(row).keySet() ){
				Results result = grid.get(row).get(column);
				String statisticsCheckerResults = result.getClassA().getSimpleName() + " " + result.getStatisticsCheckerResults();
				string += pad("| " + statisticsCheckerResults, " ", columnWidth  );
				//string += pad("| " + result.getLeftAnnotationState().toString() + result.getRightAnnotationState().toString(), " ", columnWidth  );			
			}
			
			string += "\n" ;
		}		
		
		string += pad("", "-", columnWidth * columnNumber ) + "\n" ;
		
		return string ;
	}
	
	String pad( String message ,String padString, int width ){
		
		String string = message ;
		
		for( int i = 0 ; i <= width - message.length() ; i++ ) string += padString ;
		
		return string ;
	}
	
}
