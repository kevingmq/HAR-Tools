package features;

import features.methods.Method;

import java.util.ArrayList;
import java.util.List;

public class FeatureSet {

	private String name;
	private List<Method> methods;

	public FeatureSet(){
		methods = new ArrayList<>();
	}
	public FeatureSet(String name){
		this.name = name;
		methods = new ArrayList<>();
	}

	public String getName() {
		return this.name;
	}

	public void addMethod(Method method){
		this.methods.add(method);
	}

	public Method[] getAllMethods() {
		return methods.toArray(new Method[]{});
	}

	public enum SelectFeatures{
		FS1,
		FS2,
		FS3,
	}
}
