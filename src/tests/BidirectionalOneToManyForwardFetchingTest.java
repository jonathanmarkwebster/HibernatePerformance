package tests;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.classic.Session;
import org.hibernate.stat.Statistics;
import org.junit.Test;

import tools.AnnotationParameter;
import tools.Generator;
import tools.HibernateTest;
import tools.PropertyParameter;
import tools.Results;
import tools.TestQuery;


public class BidirectionalOneToManyForwardFetchingTest extends HibernateTest {
		
	AnnotationParameter annotationLeft = new AnnotationParameter( OneToMany.class, new HashSet<PropertyParameter>(){{ add(optional); add(fetchType); }}) ;
	AnnotationParameter annotationRight = new AnnotationParameter( ManyToOne.class, new HashSet<PropertyParameter>(){{ add(fetchType); add(mappedByB
			);}}) ;
	
	@Test
	public void run(){
		
		testQuery = new TestQuery() {
			@Override
			public List<Object> runQuery(Session session) {
				//return session.createQuery("FROM B").list()  ;
				return session.createQuery("FROM B as b JOIN FETCH b.a as a JOIN FETCH a.b").list()  ; // Solution
			}
		};
		
		Generator generator = new Generator(){
		
			@Override
			public void setupData(Results results, Class clazzA, Class clazzB, Session session) throws Exception {

				Object a = clazzA.newInstance() ;
				Object b = clazzB.newInstance() ;
				
				Method setB = clazzA.getMethod("setB", Set.class );
				HashSet hashSetOfB = new HashSet();
				hashSetOfB.add( b );
				setB.invoke(a, hashSetOfB );
				
				Method setA = clazzB.getMethod("setA", clazzA );
				setA.invoke(b, a );				
				
				session.save(b);				
				session.save(a);
				
				results.setClassA(a.getClass());
				results.setClassB(b.getClass());				
				
			}

			
		};
		
		Set<Results> results = runTests( annotationLeft, annotationRight, 2, testMode );

		printResults( results );
		
	}




}
