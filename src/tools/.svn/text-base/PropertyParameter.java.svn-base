package tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PropertyParameter<T>{

	String name ;
	List<T> elements ;
	
	public PropertyParameter(String name, Set<T> elements) {
		super();
		this.name = name;
		this.elements = new ArrayList<T>();
		this.elements.addAll( elements );
	}

	public String getName() {
		return name;
	}

	public List<Map<String,Object>> getParameters(){
		
		List<Map<String,Object>> newParameters = new ArrayList<Map<String,Object>>();
		Map<String,Object> newParameter ;
		
		for( T element : elements ){	
			newParameter = new HashMap<String, Object>();
			newParameter.put(name, element );
			newParameters.add(newParameter);
		}
				
		return newParameters ;		
	}
	
	public List<Map<String,Object>> addParameters(List<Map<String,Object>> parameters ){
				
		List<Map<String,Object>> newParameters = new ArrayList<Map<String,Object>>();
		Map<String,Object> newParameter ;
		
		for( Map<String,Object> param : parameters ){			
			for( T element : elements ){	
				newParameter = new HashMap<String, Object>();
				newParameter.putAll(param);
				newParameter.put(name, element );
				newParameters.add(newParameter);				
			}
		}
				
		return newParameters ;
	}
		
}
