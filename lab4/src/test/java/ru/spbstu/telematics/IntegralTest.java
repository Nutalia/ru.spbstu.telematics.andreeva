package ru.spbstu.telematics;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class IntegralTest extends TestCase {

	public IntegralTest(String testName) {
		super(testName);
	}
	
	public static Test suite() {
		return new TestSuite(IntegralTest.class);
	}
	
	public void test1() {
		Integral it = new Integral(1,2,1,(x) -> 1/x);
		final double[] exp = {0, 0.75, 0.694444, 0.69375, 0.693175, 0.693163, 
				              0.693148, 0.693148, 0.693147, 0.693147};
		double scale = Math.pow(10,6);
		for(int i = 1; i < 10; i++)
		{
			double act = Math.round(it.count()*scale)/scale;
			assertEquals(exp[i], act);
			it.setN(i+1);
		}
	}
	
	public void test2() {
		Integral it = new Integral(0,1,1,(x) -> (Math.exp(-Math.pow(x, 2))));
		final double[] exp = {0, 0.683940, 0.747180, 0.746992, 0.746834, 0.746829, 0.746824, 0.746824, 0.746824, 0.746824};
		double scale = Math.pow(10,6);
		for(int i = 1; i < 10; i++)
		{
			double act = Math.round(it.count()*scale)/scale;
			assertEquals(exp[i], act);
			it.setN(i+1);
		}
	}
	
	public void test3() {
		Integral it = new Integral(1,5,1,(x) -> (Math.sin(x)/x));
		final double[] exp = {0, 1.299372, 0.558564, 0.584616, 0.604739, 0.604343, 0.603833, 0.603839, 0.603848, 0.603848};
		double scale = Math.pow(10,6);
		for(int i = 1; i < 10; i++)
		{
			double act = Math.round(it.count()*scale)/scale;
			assertEquals(exp[i], act);
			it.setN(i+1);
		}
	}
	
	public void test4() {
		Integral it = new Integral(1,10,1,(x) -> (Math.cos(Math.pow(x, 2))));
		final double[] exp = {0, 6.311795, 4.467326, -0.639652, -2.869257, -2.441106, 1.572783, 5.478880, 4.593529, -0.234967};
		double scale = Math.pow(10,6);
		for(int i = 1; i < 10; i++)
		{
			double act = Math.round(it.count()*scale)/scale;
			assertEquals(exp[i], act);
			it.setN(i+1);
		}
	}
	
	public void test5() {
		Integral it = new Integral(0,1,1,(x) -> (Math.tan(x)));
		final double[] exp = {0, 0.778704, 0.623770, 0.619587, 0.615995, 0.615845, 0.615651, 0.615642, 0.615628, 0.615628};
		double scale = Math.pow(10,6);
		for(int i = 1; i < 10; i++)
		{
			double act = Math.round(it.count()*scale)/scale;
			assertEquals(exp[i], act);
			it.setN(i+1);
		}
	}

}
