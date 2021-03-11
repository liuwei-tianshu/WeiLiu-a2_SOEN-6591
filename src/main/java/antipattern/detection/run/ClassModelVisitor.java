package antipattern.detection.run;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import core.datastructure.impl.Method;
import core.helper.HelperClass;
import core.helper.ObjectCreationHelper;

public class ClassModelVisitor {
	private long bugNumber = 1;
	private String fileName = "destructive_wrapper.txt"; // output file
	private String sysName = "cloudstack-4.9"; // Name of the project
	private String className = ""; // Class name to to defined
	private CompilationUnit parsedunit; //
	private List<String> exceptionList = new ArrayList<String>(); 

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

				if (methodModelVisitor.isPattern()) {
					flag = true;
					exceptionList.addAll(methodModelVisitor.getExceptionList());
//					System.out.println(bugNumber++ + ". class: " + cu.getPackage().getName() + "." + className 
//					+ ", method:" + method.getName());
				}
			} else {
//				System.out.println("--------------------------------");
////				System.out.println(cu);
//				System.out.println("--------------------------------\n\n\n");
			}
		}
		return flag;
	}

//	public void find(CompilationUnit cu) throws JavaModelException {
//		List<MethodDeclaration> methodList = getMethods(cu);
//		for (MethodDeclaration method : methodList) {
//			if (isPattern(method)) {
//				System.out.println(bugNumber++ + ". class: " + cu.getPackage().getName() + "." + cu.getTypeRoot().getElementName() 
//				+ ", method:" + method.getName());
//			}
//		}
//	}
	
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
