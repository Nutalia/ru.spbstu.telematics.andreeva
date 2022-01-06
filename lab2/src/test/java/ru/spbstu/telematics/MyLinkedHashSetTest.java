package ru.spbstu.telematics;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.util.LinkedHashSet;
import java.util.Iterator;

public class MyLinkedHashSetTest extends TestCase {
	
	LinkedHashSet<String> exp = new LinkedHashSet();
	MyLinkedHashSet<String> act = new MyLinkedHashSet();

	public MyLinkedHashSetTest(String testName) {
		super(testName);
	}
	
	public static Test suite() {
		return new TestSuite(MyLinkedHashSetTest.class);
	}
	
	public void testInit() {
		assertEquals(exp.isEmpty(),act.isEmpty());
		assertEquals(exp.size(),act.size());
	}
	
	public void testAdd() {
		assertEquals(exp.add("Hello"),act.add("Hello"));
		assertEquals(exp.size(),act.size());
		assertEquals(exp.add("World"),act.add("World"));
		assertEquals(exp.size(),act.size());
		assertEquals(exp.add("Hello"),act.add("Hello"));
		assertEquals(exp.size(),act.size());
	}
	
	public void testContains() {
		assertEquals(exp.contains("Hello"), act.contains("Hello"));
		assertEquals(exp.contains("World"),act.contains("World"));
		assertEquals(exp.contains("Good afternoon"),act.contains("Good afternoon"));
	}
	
	public void testRemove() {
		assertEquals(exp.remove("Hello"), act.remove("Hello"));
		assertEquals(exp.size(),act.size());
		assertEquals(exp.remove("World"),act.remove("World"));
		assertEquals(exp.size(),act.size());
		assertEquals(exp.remove("Good afternoon"),act.remove("Good afternoon"));
		assertEquals(exp.size(),act.size());
		assertEquals(exp.isEmpty(),act.isEmpty());
	}
	
	public void testClear() {
		exp.add("Hello");			act.add("Hello");
		exp.add("World");			act.add("World");
		exp.add("My name");			act.add("My name");
		exp.add("is");				act.add("is");
		exp.add("...");				act.add("...");
		exp.add("Hello");			act.add("Hello");
		exp.add("world");			act.add("world");
		assertEquals(exp.size(),act.size());
		exp.clear();
		act.clear();
		assertEquals(exp.size(),act.size());
		assertEquals(exp.isEmpty(),act.isEmpty());
	}
	
	public void testIterator() {
		exp.add("Hello");			act.add("Hello");
		exp.add("World");			act.add("World");
		exp.add("My name");			act.add("My name");
		exp.add("is");				act.add("is");
		exp.add("...");				act.add("...");
		exp.add("Hello");			act.add("Hello");
		exp.add("world");			act.add("world");
		
		Iterator<String> expIt = exp.iterator();
		Iterator<String> actIt = act.iterator();
		
		while(expIt.hasNext() && actIt.hasNext())
		{
			assertEquals(expIt.next(),actIt.next());
			expIt.remove();
			actIt.remove();
		}
		assertEquals(exp.size(),act.size());
		assertEquals(exp.isEmpty(),act.isEmpty());
	}

}
