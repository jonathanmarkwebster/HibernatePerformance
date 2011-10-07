package tools;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import static org.mockito.Mockito.mock; 
import static org.mockito.Mockito.when;

public class GridTest {
	
	@Test
	public void testGrid(){
		
		
		Set<Results> results = new HashSet<Results>() ;
	
		
		for( String left : new String[]{"A","B","C","D"} ){
			for( String rigth : new String[]{"1","2","3","4"}){
				
				AnnotationState annotationStateL = mock( AnnotationState.class );		
				when( annotationStateL.toString() ).thenReturn( left );
				
				AnnotationState annotationStateR = mock( AnnotationState.class );		
				when( annotationStateR.toString() ).thenReturn( rigth );
				
				Results result = new Results(null);
				result.setLeftAnnotationState(annotationStateL);
				result.setRightAnnotationState(annotationStateR);
				
				results.add(result);
			}
		}
		
		
		
		ResultsGrid resultsGrid = new ResultsGrid(results);
		
		System.out.println( resultsGrid.toString() );
		
	}
	
/*
 * ------
  | 3| 2| 1| 4
------
D | D3| D2| D1| D4
------
A | A3| A2| A1| A4
------
B | B3| B2| B1| B4
------
C | C3| C2| C1| C4
------	
 */
	
	
}
