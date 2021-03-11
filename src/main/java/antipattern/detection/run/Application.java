package antipattern.detection.run;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import core.helper.FileUtil;

/**
 * assignment for SOEN 6591, find instances of exception handing pattern: destructive wrapping
 * subject application: cloudstack-4.9
 * using JDT
 */
public class Application implements IApplication {
	private List<CompilationUnit> unitList = new ArrayList<CompilationUnit>();
	private List<String> exceptionList = new ArrayList<String>(); 
	private String directoryPath = "E:\\Documents\\lessons\\SOEN 6591\\assignment2+deadline March 10\\workspace\\a2_SOEN6591\\";
	private String fileName = "result_destructive_wrapper.txt"; // output file
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
		long startTime = System.nanoTime();
		//String[] args = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject[] projects = root.getProjects();

		root = ResourcesPlugin.getWorkspace().getRoot();
		projects = root.getProjects();
		for (IProject project : projects) {
			if (project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {
				System.out.println("analyzing " + project.getName());
				analyzeProject(project); 
			}
		}
		
		System.out.println("\r\n--------------------------------------------");
		System.out.println("##BugInstance type: Destructive Wrapping##");
		ClassModelVisitor classModelVisitor = new ClassModelVisitor();
		for (CompilationUnit unit : unitList) {				
			if(classModelVisitor.isPattern(unit)) {
				exceptionList.addAll(classModelVisitor.getExceptionList());
			}
		}
		
		// write data to file
		PrintWriter cfgResultPW = FileUtil.getPrintWriter(directoryPath + fileName);
		for(String msg : exceptionList) {
			cfgResultPW.println(msg);
		}
		cfgResultPW.close();
		
		System.out.println("Done. Total Destructive Wrapping:" + exceptionList.size() + "\n");
		System.out.println("Java files:" + unitList.size() + "\n");
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		System.out.println("It took " + duration / 1000000 / 1000 + " seconds");
		return null;
	}

	@Override
	public void stop() {
	}

	private void setup() {

	}

	/**
	 * Perform static analysis on the Java project
	 * 
	 * @param project
	 * @throws JavaModelException
	 */
	private void analyzeProject(IProject project) throws JavaModelException {
		IPackageFragment[] packages = JavaCore.create(project).getPackageFragments();
		for (IPackageFragment mypackage : packages) {
			if (mypackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
				if (mypackage.getElementName().toLowerCase().contains("test")) {
					continue;
				}
				analyze(mypackage);
			}
		}
	}

	/**
	 * Analyze data usage of each Spring entry function
	 * 
	 * @param mypackage
	 * @throws JavaModelException
	 */
	private void analyze(IPackageFragment mypackage) throws JavaModelException {
		for (ICompilationUnit unit : mypackage.getCompilationUnits()) {

			if (unit.getElementName().contains("test")|| unit.getElementName().contains("IT") && !unit.getElementName().contains("ITenant")
							|| (unit.getElementName().contains("Test")))
				continue;

			CompilationUnit parsedUnit = parse(unit);

			//EmptyCatchVisitor exVisitor = new EmptyCatchVisitor(mypackage, unit, parsedUnit);
			
			unitList.add(parsedUnit);

			//parsedUnit.accept(exVisitor);

		}

	}

	/**
	 * Reads a ICompilationUnit and creates the AST DOM for manipulating the Java
	 * source file
	 * 
	 * @param unit
	 * @return
	 */

	private CompilationUnit parse(ICompilationUnit unit) {
		ASTParser parser = ASTParser.newParser(AST.JLS4);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(unit);
		parser.setResolveBindings(false);
		parser.setBindingsRecovery(false);
		return (CompilationUnit) parser.createAST(null); // parse
	}
}
