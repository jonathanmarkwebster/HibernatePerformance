package tools;

import java.util.HashMap;
import java.util.Map;

public class AnnotationState {
		
	private Class annotationClass ;
	private Map<String,Object> properties = new HashMap<String, Object>();

	public AnnotationState(Class annotationClass, Map<String, Object> properties) {
		super();
		this.annotationClass = annotationClass;
		this.properties = properties;
	}

	public Class getAnnotationClass() {
		return annotationClass;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}
	
	@Override
	public String toString(){
		return annotationClass.getSimpleName() + properties.toString()	;
	}
	
}