package tools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AnnotationParameter {

	Class annotationClass ;
	Set<PropertyParameter> propertyParameters = new HashSet<PropertyParameter>();
	
	public AnnotationParameter( Class annotationClass, Set<PropertyParameter> propertyParameters) {
		this.annotationClass = annotationClass;
		this.propertyParameters = propertyParameters;
	}

	public Class getAnnotationClass() {
		return annotationClass;
	}
	
	public List<Map<String,Object>> getParameters(){

		List<Map<String,Object>> parameters = new ArrayList<Map<String,Object>>();

		for( PropertyParameter propertyParameter : propertyParameters ){
			
			if( parameters.isEmpty() ){
				parameters = propertyParameter.getParameters();
			}else{
				parameters = propertyParameter.addParameters(parameters);
			}
			
		}
				
		return parameters ;
	}	
		
}
