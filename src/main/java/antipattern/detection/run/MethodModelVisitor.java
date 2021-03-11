package antipattern.detection.run;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import core.datastructure.impl.Method;
import core.helper.ObjectCreationHelper;

public class MethodModelVisitor extends ASTVisitor {
	private String className = ""; 
	private CompilationUnit parsedunit; 
	private boolean pattern = false;

	private String sysName = "cloudstack-4.9"; // Name of the project
	private List<String> exceptionList = new ArrayList<String>();
	
	public List<String> getExceptionList() {
		return exceptionList;
	}
	
	public MethodModelVisitor(CompilationUnit parsedunit, String className) {
		this.parsedunit = parsedunit;
		this.className = className;
//		className = parsedunit.getTypeRoot().getElementName();
//		className = parsedunit.getJavaElement().getElementName().replace(".java", "");
//		TypeDeclaration type = (TypeDeclaration)(parsedunit.types().get(0));
//		className = type.getName().toString();
	}
	
	public boolean visit(MethodDeclaration method) {
		final Method callsite = ObjectCreationHelper.createMethodFromMethodDeclaration(method, className);
		if (method.getBody() != null) {
			method.getBody().accept(new ASTVisitor() {
				@Override
				public boolean visit(CatchClause ca) {
					//System.out.println("Caught exception: " + ca);
					
					// get name of exception
					SingleVariableDeclaration singleVariableDeclaration = ca.getException();
					SimpleName exceptionName = singleVariableDeclaration.getName();
					
					CatchClauseVisitor catchClauseVisitor = new CatchClauseVisitor(exceptionName);
					ca.accept(catchClauseVisitor);
					pattern = catchClauseVisitor.isPattern();
					if (pattern) {
						int lineNumber = parsedunit.getLineNumber(ca.getStartPosition());
						String str = "<system>" + sysName + "</system>" + "<callsite>" + callsite
								+ "</callsite>" + "<line>" + lineNumber + "</line>";
						System.out.println("DestructiveWrapping: " + str);
						exceptionList.add(str);
						System.out.println(ca);
//						System.out.println("\n");
//						try {
//							HelperClass.fileAppendMethod(fileName, str);
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
					}
					return pattern;
				}
			});
		}
		return true;
	}
	
	public boolean isPattern() {
		return pattern;
	}
}