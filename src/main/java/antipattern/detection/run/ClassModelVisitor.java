package antipattern.detection.run;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class ClassModelVisitor {
	private List<String> exceptionList = new ArrayList<String>(); 
	public long catchNum = 0;

	public List<String> getExceptionList() {
		return exceptionList;
	}
	/**
	 * get all methods in the class
	 * 
	 * @author wei liu
	 */
	private class ClassVisitor extends ASTVisitor {
		// all methods in this class
		private List<MethodDeclaration> methodList = new ArrayList<MethodDeclaration>();

		public boolean visit(MethodDeclaration node) {
			methodList.add(node);
			return super.visit(node);
		}

		public List<MethodDeclaration> getMethodList() {
			return methodList;
		}
	}

	public boolean isPattern(CompilationUnit cu) {
		boolean flag = false;
		List<MethodDeclaration> methodList = getMethods(cu);
		for (MethodDeclaration method : methodList) {
			Object type = cu.types().get(0);
			if (type instanceof TypeDeclaration) {
				String className = ((TypeDeclaration)type).getName().toString();
				
				MethodModelVisitor methodModelVisitor = new MethodModelVisitor(cu, className);
				method.accept(methodModelVisitor);
				catchNum += methodModelVisitor.catchNum;

				if (methodModelVisitor.isPattern()) {
					flag = true;
					exceptionList.addAll(methodModelVisitor.getExceptionList());
//					System.out.println(bugNumber++ + ". class: " + cu.getPackage().getName() + "." + className 
//					+ ", method:" + method.getName());
				}
			}
		}
		return flag;
	}
	
	/**
	 * get all method in the class
	 * @param cu  the class to analyze
	 * @return methods in a class
	 */
	public List<MethodDeclaration> getMethods(CompilationUnit cu) {
		ClassVisitor classVisitor = new ClassVisitor();
		cu.accept(classVisitor);
		return classVisitor.getMethodList();
	}
}
