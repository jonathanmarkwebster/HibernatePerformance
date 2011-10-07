package tools;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.BooleanMemberValue;
import javassist.bytecode.annotation.ClassMemberValue;
import javassist.bytecode.annotation.EnumMemberValue;
import javassist.bytecode.annotation.IntegerMemberValue;
import javassist.bytecode.annotation.MemberValue;
import javassist.bytecode.annotation.StringMemberValue;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.classic.Session;

public  class Generator {

	AnnotationState[] idAnnotationStates = 
		new AnnotationState[]{ new AnnotationState( Id.class , new HashMap<String, Object>()),
							   new AnnotationState( GeneratedValue.class	, new HashMap<String, Object>(){{ put("generator", "genericGenerator"); }}),
							   new AnnotationState( GenericGenerator.class	, new HashMap<String, Object>(){{ put("name", "genericGenerator");
 																				      						  put("strategy", "uuid");	}})};		
	
	static Set<Class> associationTypes = new HashSet<Class>(){{ add( OneToOne.class ); add( OneToMany.class); add( ManyToOne.class); }} ;
	static Set<Class> collectionAssociationTypes = new HashSet<Class>(){{ add( OneToMany.class); }} ;
	

	public String getString(Map<String,Object> map){
		
		String string = "" ;
		
		for( Object object : map.values() ){
			string += object ;
		}
		return string ;
	}
	
	
	public void generateClasses(
			AnnotationState leftAnnotationState,
			AnnotationState rightAnnotationState,
			AnnotationState upAnnotationState,
			AnnotationState downAnnotationState,
			String classNameB,
			String classNameA,
			String classNameC ) throws CannotCompileException, NotFoundException, IOException {

		ClassPool pool = ClassPool.getDefault();	

		CtClass ctClassA = pool.makeClass(classNameA);		

		ClassFile classFileA = ctClassA.getClassFile();
		ConstPool constPoolA = classFileA.getConstPool();

		Annotation[] annotationsA = new Annotation[2];

		HashMap<String, Object> entityPropertiesA = new HashMap<String, Object>();
		entityPropertiesA.put("name", "A");
		annotationsA[0] = addAnnotation( javax.persistence.Entity.class, classFileA, constPoolA, entityPropertiesA) ;

		HashMap<String, Object> tablePropertiesA = new HashMap<String, Object>();
		tablePropertiesA.put("name", classNameA );
		annotationsA[1] = addAnnotation( javax.persistence.Table.class, classFileA, constPoolA, tablePropertiesA ) ;				


		AnnotationsAttribute annotationsAttributeA = new AnnotationsAttribute(constPoolA, AnnotationsAttribute.visibleTag);
		annotationsAttributeA.setAnnotations( annotationsA );
		classFileA.addAttribute( annotationsAttributeA );			


		addProperty( String.class, "id", ctClassA, constPoolA, idAnnotationStates );

		CtClass ctClassB = pool.makeClass(classNameB);			
		ClassFile classFileB = ctClassB.getClassFile();
		ConstPool constPoolB = classFileB.getConstPool();

		Annotation[] annotationsB = new Annotation[2];

		HashMap<String, Object> entityPropertiesB = new HashMap<String, Object>();
		entityPropertiesB.put("name", "B");
		annotationsB[0] = addAnnotation( javax.persistence.Entity.class, classFileB, constPoolB, entityPropertiesB ) ;

		HashMap<String, Object> tablePropertiesB = new HashMap<String, Object>();
		tablePropertiesB.put("name", classNameB );
		annotationsB[1] = addAnnotation( javax.persistence.Table.class, classFileB, constPoolB, tablePropertiesB ) ;			

		AnnotationsAttribute annotationsAttributeB = new AnnotationsAttribute(constPoolB, AnnotationsAttribute.visibleTag);
		annotationsAttributeB.setAnnotations( annotationsB );
		classFileB.addAttribute( annotationsAttributeB );

		addProperty( String.class, "id", ctClassB, constPoolB, idAnnotationStates );
		
		CtClass ctClassC = pool.makeClass(classNameC);			
		ClassFile classFileC = ctClassC.getClassFile();
		ConstPool constPoolC = classFileC.getConstPool();

		Annotation[] annotationsC = new Annotation[2];

		HashMap<String, Object> entityPropertiesC = new HashMap<String, Object>();
		entityPropertiesC.put("name", "C");
		annotationsC[0] = addAnnotation( javax.persistence.Entity.class, classFileC, constPoolC, entityPropertiesC ) ;

		HashMap<String, Object> tablePropertiesC = new HashMap<String, Object>();
		tablePropertiesC.put("name", classNameC );
		annotationsC[1] = addAnnotation( javax.persistence.Table.class, classFileC, constPoolC, tablePropertiesC ) ;			

		AnnotationsAttribute annotationsAttributeC = new AnnotationsAttribute(constPoolC, AnnotationsAttribute.visibleTag);
		annotationsAttributeC.setAnnotations( annotationsC );
		classFileC.addAttribute( annotationsAttributeC );

		addProperty( String.class, "id", ctClassC, constPoolC, idAnnotationStates );		

		Class tempClazzA = ctClassA.toClass();
		Class tempClazzB = ctClassB.toClass();
		Class tempClazzC = ctClassC.toClass();

		ctClassA.defrost() ;
		ctClassB.defrost() ;
		ctClassC.defrost() ;

		addProperty( tempClazzA, "a", ctClassB, constPoolB, rightAnnotationState );
		addProperty( tempClazzB, "b", ctClassA, constPoolA, leftAnnotationState );				

		addProperty( tempClazzC, "c", ctClassB, constPoolB, upAnnotationState );
		addProperty( tempClazzB, "b", ctClassC, constPoolC, downAnnotationState );
		
		ctClassA.writeFile(".//bin//");
		ctClassB.writeFile(".//bin//");		
		ctClassC.writeFile(".//bin//");		
		
	}
	
	
	public void generateClasses(	AnnotationState leftAnnotationState,
								AnnotationState rightAnnotationState, 
								String classNameB,
								String classNameA ) throws CannotCompileException, NotFoundException, IOException {
		
		ClassPool pool = ClassPool.getDefault();	

		CtClass ctClassA = pool.makeClass(classNameA);		

		ClassFile classFileA = ctClassA.getClassFile();
		ConstPool constPoolA = classFileA.getConstPool();

		Annotation[] annotationsA = new Annotation[2];

		HashMap<String, Object> entityPropertiesA = new HashMap<String, Object>();
		entityPropertiesA.put("name", "A");
		annotationsA[0] = addAnnotation( javax.persistence.Entity.class, classFileA, constPoolA, entityPropertiesA) ;

		HashMap<String, Object> tablePropertiesA = new HashMap<String, Object>();
		tablePropertiesA.put("name", classNameA );
		annotationsA[1] = addAnnotation( javax.persistence.Table.class, classFileA, constPoolA, tablePropertiesA ) ;				


		AnnotationsAttribute annotationsAttributeA = new AnnotationsAttribute(constPoolA, AnnotationsAttribute.visibleTag);
		annotationsAttributeA.setAnnotations( annotationsA );
		classFileA.addAttribute( annotationsAttributeA );			


		addProperty( String.class, "id", ctClassA, constPoolA, idAnnotationStates );

		CtClass ctClassB = pool.makeClass(classNameB);			
		ClassFile classFileB = ctClassB.getClassFile();
		ConstPool constPoolB = classFileB.getConstPool();

		Annotation[] annotationsB = new Annotation[2];

		HashMap<String, Object> entityPropertiesB = new HashMap<String, Object>();
		entityPropertiesB.put("name", "B");
		annotationsB[0] = addAnnotation( javax.persistence.Entity.class, classFileB, constPoolB, entityPropertiesB ) ;

		HashMap<String, Object> tablePropertiesB = new HashMap<String, Object>();
		tablePropertiesB.put("name", classNameB );
		annotationsB[1] = addAnnotation( javax.persistence.Table.class, classFileB, constPoolB, tablePropertiesB ) ;			

		AnnotationsAttribute annotationsAttributeB = new AnnotationsAttribute(constPoolB, AnnotationsAttribute.visibleTag);
		annotationsAttributeB.setAnnotations( annotationsB );
		classFileB.addAttribute( annotationsAttributeB );

		addProperty( String.class, "id", ctClassB, constPoolB, idAnnotationStates );

		Class tempClazzA = ctClassA.toClass();
		Class tempClazzB = ctClassB.toClass();

		ctClassA.defrost() ;
		ctClassB.defrost() ;

		addProperty( tempClazzB, "b", ctClassA, constPoolA, leftAnnotationState );				
		addProperty( tempClazzA, "a", ctClassB, constPoolB, rightAnnotationState );

		ctClassA.writeFile(".//bin//");
		ctClassB.writeFile(".//bin//");
	}
	

	public void generateClasses(	AnnotationState leftAnnotationState,
			String classNameB,
			String classNameA ) throws CannotCompileException, NotFoundException, IOException {

ClassPool pool = ClassPool.getDefault();	

CtClass ctClassA = pool.makeClass(classNameA);		

ClassFile classFileA = ctClassA.getClassFile();
ConstPool constPoolA = classFileA.getConstPool();

Annotation[] annotationsA = new Annotation[2];

HashMap<String, Object> entityPropertiesA = new HashMap<String, Object>();
entityPropertiesA.put("name", "A");
annotationsA[0] = addAnnotation( javax.persistence.Entity.class, classFileA, constPoolA, entityPropertiesA) ;

HashMap<String, Object> tablePropertiesA = new HashMap<String, Object>();
tablePropertiesA.put("name", classNameA );
annotationsA[1] = addAnnotation( javax.persistence.Table.class, classFileA, constPoolA, tablePropertiesA ) ;				


AnnotationsAttribute annotationsAttributeA = new AnnotationsAttribute(constPoolA, AnnotationsAttribute.visibleTag);
annotationsAttributeA.setAnnotations( annotationsA );
classFileA.addAttribute( annotationsAttributeA );			


addProperty( String.class, "id", ctClassA, constPoolA, idAnnotationStates );

CtClass ctClassB = pool.makeClass(classNameB);			
ClassFile classFileB = ctClassB.getClassFile();
ConstPool constPoolB = classFileB.getConstPool();

Annotation[] annotationsB = new Annotation[2];

HashMap<String, Object> entityPropertiesB = new HashMap<String, Object>();
entityPropertiesB.put("name", "B");
annotationsB[0] = addAnnotation( javax.persistence.Entity.class, classFileB, constPoolB, entityPropertiesB ) ;

HashMap<String, Object> tablePropertiesB = new HashMap<String, Object>();
tablePropertiesB.put("name", classNameB );
annotationsB[1] = addAnnotation( javax.persistence.Table.class, classFileB, constPoolB, tablePropertiesB ) ;			

AnnotationsAttribute annotationsAttributeB = new AnnotationsAttribute(constPoolB, AnnotationsAttribute.visibleTag);
annotationsAttributeB.setAnnotations( annotationsB );
classFileB.addAttribute( annotationsAttributeB );

addProperty( String.class, "id", ctClassB, constPoolB, idAnnotationStates );

Class tempClazzA = ctClassA.toClass();
Class tempClazzB = ctClassB.toClass();

ctClassA.defrost() ;
ctClassB.defrost() ;

addProperty( tempClazzB, "b", ctClassA, constPoolA, leftAnnotationState );				

ctClassA.writeFile(".//bin//");
ctClassB.writeFile(".//bin//");
}	
	

	public void setupData(Results results, Class clazzA, Class clazzB, Session session) throws Exception{};
	public void setupData(Results results, Class clazzA, Class clazzB, Class clazzC, Session session) throws Exception{};

	public void addMethod(CtClass ctClassB, ConstPool constPoolB, String method ) throws CannotCompileException {
		addMethod( ctClassB, constPoolB, method, new AnnotationState(null, new HashMap<String, Object>()) );
	}

	public void addMethod(CtClass ctClassB, ConstPool constPoolB, String method, AnnotationState... annotationStates ) throws CannotCompileException {
		
		CtMethod getIdMethod;
		getIdMethod = CtNewMethod.make( method, ctClassB);
		ctClassB.addMethod(getIdMethod);	
		
		AnnotationsAttribute methodAnnotationsAttributeB = new AnnotationsAttribute(constPoolB, AnnotationsAttribute.visibleTag);
		
		for( AnnotationState annotationState : annotationStates ){
			
			if( annotationState.getAnnotationClass() != null ){
				addAnnotation(constPoolB, annotationState, getIdMethod, methodAnnotationsAttributeB );
			}
		}
		
	}

	public void addAnnotation( ConstPool constPoolB, AnnotationState annotationState, CtMethod getIdMethod,AnnotationsAttribute methodAnnotationsAttribute ) {

		Annotation methodAnnotationB = new Annotation( annotationState.getAnnotationClass().getCanonicalName(), constPoolB);

		for( String property : annotationState.getProperties().keySet() ){			
			methodAnnotationB.addMemberValue( property, getMemberValue( annotationState.getProperties().get( property ) , getIdMethod ));
		}

		methodAnnotationsAttribute.addAnnotation(methodAnnotationB);			
		getIdMethod.getMethodInfo().addAttribute(methodAnnotationsAttribute);
	}


	public Annotation addAnnotation( Class entityAnnotationClass, ClassFile classFileA, ConstPool constPoolA, Map<String, Object> properties ) {
		
		Annotation annotationA = new Annotation(entityAnnotationClass.getCanonicalName(), constPoolA);

		for( String property : properties.keySet() ){		
			annotationA.addMemberValue( property, getMemberValue( properties.get( property ) , constPoolA ));
		}
		
		return annotationA ;
	}
	
	private MemberValue getMemberValue( Object object, CtMethod getIdMethod ) {
		return getMemberValue(object, getIdMethod.getMethodInfo().getConstPool());
	}
	
	// This is shitty use better pattern :(
	private MemberValue getMemberValue( Object object, ConstPool constPool) {

		MemberValue memberValue = null ;
		
		if( object instanceof Enum ){
			memberValue = getAnnotationMember((Enum)object, constPool );
		}else if( object instanceof String ){
			memberValue = getAnnotationMember((String)object, constPool );			
		}else if( object instanceof Boolean ){
			memberValue = getAnnotationMember((Boolean)object, constPool );			
		}else if( object instanceof Integer ){
			memberValue = getAnnotationMember((Integer)object, constPool );			
		}else if( object instanceof Class ){
			memberValue = getAnnotationMember((Class)object, constPool );			
		}

		return memberValue ;
	}	
	
	private ClassMemberValue getAnnotationMember(Class object, ConstPool constPool) {

		ClassMemberValue classMemberValue = new ClassMemberValue(constPool);
		
		classMemberValue.setValue(((Class)object).getCanonicalName());
		
		return classMemberValue ;
	}

	private IntegerMemberValue getAnnotationMember(Integer eager, ConstPool constPool) {
		IntegerMemberValue fetchTypeB = new IntegerMemberValue( constPool );
		fetchTypeB.setValue( eager );			
		return fetchTypeB ;
	}
	
	private EnumMemberValue getAnnotationMember( Enum eager, ConstPool constPool) {
		
		EnumMemberValue fetchTypeB = new EnumMemberValue( constPool );
		
		fetchTypeB.setType( eager.getClass().getCanonicalName());
		fetchTypeB.setValue( eager.toString() );			
		return fetchTypeB ;
	}

	private BooleanMemberValue getAnnotationMember( Boolean b, ConstPool constPool) {
		return new BooleanMemberValue( b, constPool ) ;
	}

	private StringMemberValue getAnnotationMember( String memberValue, ConstPool constPool) {
		StringMemberValue stringMemberValue = new StringMemberValue( constPool );
		stringMemberValue.setValue(memberValue);
		return stringMemberValue ;
	}

	public void addProperty( Class type, String name, CtClass ctClass, ConstPool constPool, AnnotationState... getterAnnotationStates ) throws CannotCompileException {
		
		boolean isACollectionProperty = false ;
		for( AnnotationState annotationState : getterAnnotationStates ){
			if( collectionAssociationTypes.contains( annotationState.getAnnotationClass() )) isACollectionProperty = true ;
		}
		
		// Hackty hack hack :(
		if ( isACollectionProperty ){
			addSetProperty(type, name, ctClass, constPool, getterAnnotationStates );			
		}else {
			addSingularProperty(type, name, ctClass, constPool, getterAnnotationStates );			
		}
	
	}

	public void addSingularProperty(Class type, String name, CtClass ctClass, ConstPool constPool, AnnotationState[] getterAnnotationStates ) throws CannotCompileException {
		CtField ctField = CtField.make( MessageFormat.format("public {0} {1} ;", type.getName(), name ) , ctClass) ;
		ctClass.addField(ctField);	
		addMethod(ctClass, constPool, MessageFormat.format("public {0} get{2}() '{' return {1} ; '}'", type.getName(), name, uppercaseFirstCharacter(name) ), getterAnnotationStates );
		addMethod(ctClass, constPool, MessageFormat.format("public void set{2}( {0} {1} ) '{' this.{1} = {1} ; '}'", type.getName(),  name, uppercaseFirstCharacter(name)) );
	}

	public void addSetProperty(Class type, String name, CtClass ctClass, ConstPool constPool, AnnotationState[] getterAnnotationStates) throws CannotCompileException {
		
		CtField ctField = CtField.make( MessageFormat.format("public java.util.Set {1} ;", type.getName(), name ) , ctClass) ;
		ctClass.addField(ctField);
		
		for( AnnotationState annotationState : getterAnnotationStates )
			if( collectionAssociationTypes.contains( annotationState.getAnnotationClass()) )
				annotationState.getProperties().put("targetEntity", type );
		
		addMethod(ctClass, constPool, MessageFormat.format("public java.util.Set get{2}() '{' return {1} ; '}'", type.getName(), name, uppercaseFirstCharacter(name) ), getterAnnotationStates );
		addMethod(ctClass, constPool, MessageFormat.format("public void set{2}( java.util.Set {1} ) '{' this.{1} = {1} ; '}'", type,  name, uppercaseFirstCharacter(name)) );
	}

	public String uppercaseFirstCharacter(String name) {
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}	
	

}
