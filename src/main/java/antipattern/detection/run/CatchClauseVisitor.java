package antipattern.detection.run;

import java.util.List;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.ThrowStatement;

public class CatchClauseVisitor extends ASTVisitor {
	private SimpleName exceptionName;
	private SingleVariableDeclaration singleVariableDeclaration;
	private boolean isDestructiveWrapping = false;
	
	public CatchClauseVisitor(SimpleName exceptionName) {
		this.exceptionName = exceptionName;
	}
	
	public boolean visit(ExpressionStatement node) {
		Expression expression = node.getExpression();
		if (expression != null && expression instanceof MethodInvocation) {
			MethodInvocation methodInvocation = (MethodInvocation) expression;
			List<Expression> argumentList = methodInvocation.arguments();
			for (Expression expressionName : argumentList) {
				if ((expressionName instanceof SimpleName) && expressionName.getAST().equals(exceptionName.getAST())
						&& expressionName.toString().equals(exceptionName.toString())){
					if (!node.toString().contains("log") && !node.toString().contains("LOG")) {
						System.out.print("--------------------\n");
						System.out.print("ExpressionStatement: \n"+ node.toString());
						System.out.print("Catch clause: \n"+ node.getParent().getParent().toString());
						System.out.print("--------------------\n");
					}
				}
			}
		}
		
		return true;
	}
	
	public boolean visit(ThrowStatement node) {
		isDestructiveWrapping = true;
		Expression expression = node.getExpression();
		if (expression != null && expression instanceof ClassInstanceCreation) {
			ClassInstanceCreation classInstanceCreation = (ClassInstanceCreation)expression;;
			List<Expression> argumentList = classInstanceCreation.arguments();
			for (Expression expressionName : argumentList) {
				if ((expressionName instanceof SimpleName) && expressionName.getAST().equals(exceptionName.getAST())
						&& expressionName.toString().equals(exceptionName.toString())){
					isDestructiveWrapping = false;
				}
			}
		}
		
		return true;
	}
	
	public boolean isPattern() {
		return isDestructiveWrapping;
	}
	
	public boolean visit(CatchClause node) {
		singleVariableDeclaration = node.getException();
//		System.out.print("CatchClause" + node.toString());
		return true;
	}
}
