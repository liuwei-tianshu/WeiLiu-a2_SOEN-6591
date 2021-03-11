package core.helper;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import core.datastructure.impl.Method;

public class ObjectCreationHelper {

	public static Method createMethodFromMethodDeclaration(MethodDeclaration method, String className) {
		Method createdMethod = null;

		try {
			if (method.resolveBinding() == null) {

				createdMethod = new Method("", className, method.getName().toString());

			} else if (method.resolveBinding().getDeclaringClass().getPackage() == null
					&& method.resolveBinding().getDeclaringClass() == null) {

				createdMethod = new Method("", className, method.getName().toString());

			} else if (method.resolveBinding().getDeclaringClass().getPackage() == null
					&& method.resolveBinding().getDeclaringClass() != null) {

				createdMethod = new Method("", className, method.getName().toString());

			} else if (method.resolveBinding().getDeclaringClass().getPackage() != null
					&& method.resolveBinding().getDeclaringClass() == null) {

				createdMethod = new Method(method.resolveBinding().getDeclaringClass().getPackage().getName(),
						className, method.getName().toString());

			} else {

				createdMethod = new Method(method.resolveBinding().getDeclaringClass().getPackage().getName(),
						className, method.getName().toString());

			}

		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		return createdMethod;
	}

}