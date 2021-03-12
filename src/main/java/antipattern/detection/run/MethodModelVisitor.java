package antipattern.detection.run;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
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
	
	public long catchNum = 0;
	
	public List<String> getExceptionList() {
		return exceptionList;
	}
	
	public MethodModelVisitor(CompilationUnit parsedunit, String className) {
		this.parsedunit = parsedunit;
		this.className = className;
	}
	
	public boolean visit(MethodDeclaration method) {
		final Method callsite = ObjectCreationHelper.createMethodFromMethodDeclaration(method, className);
		if (method.getBody() != null) {
			method.getBody().accept(new ASTVisitor() {
				@Override
				public boolean visit(CatchClause ca) {
					//System.out.println("Caught exception: " + ca);
					catchNum++;
					
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
						exceptionList.add(str);
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