package core.datastructure.impl;

import java.util.List;
import java.util.Map;

public class Method {
	private String packageName;
	private String className;
	private String methodName;
	private String params;
	private JavaClass javaClass;

	public Method(String packageName, String className, String methodName) {
		this.packageName = packageName;
		this.className = className;
		this.methodName = methodName;
		this.javaClass = new JavaClass(this.packageName, this.className);
	}

	public Method(String packageName, String className, String methodName,
			String params) {
		this.packageName = packageName;
		this.className = className;
		this.methodName = methodName;
		this.params = params;
		this.javaClass = new JavaClass(this.packageName, this.className);
	}

	public String getPackageName() {
		return this.packageName;
	}

	public String getClassName() {
		return this.className;
	}

	public String getMethodName() {
		return this.methodName;
	}
	
	public JavaClass getJavaClass(){
		return this.javaClass;
	}

	public String getPrams() {
		return this.params;
	}

	public String toString() {
		return packageName + "." + className + "." + methodName;
	}

	public int hashCode() {
		return (packageName + "." + className + "." + methodName).hashCode();
	}



}
