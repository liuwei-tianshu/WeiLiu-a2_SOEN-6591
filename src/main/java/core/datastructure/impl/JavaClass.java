package core.datastructure.impl;

public class JavaClass {
	private String packageName = "";
	private String className = "";
	
	public JavaClass(String packageName, String className) {
		this.packageName = packageName;
		this.className = className;
	}

	public String getPackageName() {
		return this.packageName;
	}

	public String getClassName() {
		return this.className;
	}
	
	public boolean equals(Object other) {
		JavaClass otherClass = (JavaClass) other;
		if (this.packageName.equals(otherClass.getPackageName())
				&& this.className.equals(otherClass.getClassName()))
			return true;
		return false;
	}

	public int hashCode() {
		return this.packageName.hashCode() + this.className.hashCode();
	}
	
	public String toString(){
		if (this.packageName.equals(""))
			return this.className;
		return this.packageName + "." + this.className;
	}
}
