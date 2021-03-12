package edu.concordia.a2_SOEN6591;

import static org.junit.Assert.assertEquals;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import antipattern.detection.run.ClassModelVisitor;

/**
 * JUnit Test for pattern: Destructive Wrapping
 * @author wei liu
 */
public class DestructiveWrappingPatternTest {
	String instance1 = "";
	String instance2 = "";
	String instance3 = "";
	String instance4 = "";
	String instance5 = "";
	String instance6 = "";
	ClassModelVisitor destructiveWrappingPattern = null;
	
	// read file content into a string
	public String readFileAsString(String filePath) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(filePath)));
		return data;
	}
	
	public CompilationUnit parse(String str) {
		ASTParser parser = ASTParser.newParser(AST.JLS9);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(str.toCharArray());
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		return (CompilationUnit) parser.createAST(null);
	}

	@Before
	public void setUp() throws Exception {
		instance1 = readFileAsString("TestCases/Instance1.java");
		instance2 = readFileAsString("TestCases/Instance2.java");
		instance3 = readFileAsString("TestCases/Instance3.java");
		instance4 = readFileAsString("TestCases/Instance4.java");
		instance5 = readFileAsString("TestCases/Instance5.java");
		instance6 = readFileAsString("TestCases/Instance6.java");
		destructiveWrappingPattern = new ClassModelVisitor();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		CompilationUnit cu1 = parse(instance1);
		CompilationUnit cu2 = parse(instance2);
		CompilationUnit cu3 = parse(instance3);
		CompilationUnit cu4 = parse(instance4);
		CompilationUnit cu5 = parse(instance5);
		CompilationUnit cu6 = parse(instance6);

		assertEquals(destructiveWrappingPattern.isPattern(cu1), false);
		assertEquals(destructiveWrappingPattern.isPattern(cu2), false);
		assertEquals(destructiveWrappingPattern.isPattern(cu3), true);
		assertEquals(destructiveWrappingPattern.isPattern(cu4), true);
		assertEquals(destructiveWrappingPattern.isPattern(cu5), true);
		assertEquals(destructiveWrappingPattern.isPattern(cu6), false);
	}
}
