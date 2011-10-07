package tests;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.OneToOne;

import org.hibernate.classic.Session;
import org.junit.Test;

import tools.AnnotationParameter;
import tools.Generator;
import tools.HibernateTest;
import tools.PropertyParameter;
import tools.Results;
import tools.TestMode;
import tools.TestQuery;


public class BidirectionalOneToOneForwardChainFetchingTest extends HibernateTest {
	
	AnnotationParameter annotationAb = new AnnotationParameter( OneToOne.class, new HashSet<PropertyParameter>(){{  add(fetchTypeEager); }}) ;
	
	AnnotationParameter annotationBa = new AnnotationParameter( OneToOne.class, new HashSet<PropertyParameter>(){{  add(fetchTypeEager); add(mappedByB);}}) ;
	AnnotationParameter annotationBc = new AnnotationParameter( OneToOne.class, new HashSet<PropertyParameter>(){{  add(fetchTypeEager); }}) ;
	
	AnnotationParameter annotationCb = new AnnotationParameter( OneToOne.class, new HashSet<PropertyParameter>(){{  add(fetchTypeEager); add(mappedByC);}}) ;	
	
	@Test
	public void run(){
				
		// Ugly parameter passing :(	
			testQuery = new TestQuery() {
			@Override
			public List<Object> runQuery(Session session) {
				return session.createQuery("FROM A").list()  ;
//				return session.createQuery("FROM A as a JOIN FETCH a.b").list()  ;
			}
		};
			// Ugly parameter passing :(	
		    generator = new Generator(){	
			public void setupData(Results results, Class clazzA, Class clazzB, Class clazzC, Session session) throws Exception {
			
			Object a = clazzA.newInstance() ;
			Object b = clazzB.newInstance() ;
			Object c = clazzC.newInstance() ;
			
			Field feildAb = clazzA.getField("b");
			feildAb.set(a,b);			
			
			Field feildBa = clazzB.getField("a");
			feildBa.set(b,a);			

			Field feildBc = clazzB.getField("c");
			feildBc.set(b,c);			

			Field feildCb = clazzC.getField("b");
			feildCb.set(c,b);			
			

			session.save(b);				
			session.save(a);
			session.save(c);
			
			results.setClassA(a.getClass());
			results.setClassB(b.getClass());
			results.setClassC(c.getClass());
		}
		};
		
		Set<Results> results = runTests(annotationAb, annotationBa, annotationBc, annotationCb, 1, testMode );
		printResults(results);
		
	}


}
