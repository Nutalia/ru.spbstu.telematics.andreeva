package ru.spbstu.telematics;

import java.time.Duration;
import java.time.Instant;

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
		Integral it = new Integral(1,5,0,(x) -> 1/x);
		final double[] exp = {0, 1.6147286265, 1.6098272749, 1.6095028538, 1.6094541763, 1.6094431490};
		double scale = Math.pow(10,10);
		for(int i = 1; i <= 5; i++)
		{
			it.setN(i);
			double act = Math.round(it.count()*scale)/scale;
			assertEquals(exp[i], act);
		}
	}
	
	public void testCoherent1() {
		Integral it = new Integral(1,5,1,(x) -> 1/x);
		final double[] exp = {0, 1.6147286265, 1.6098272749, 1.6095028538, 1.6094541763, 1.6094431490};
		double scale = Math.pow(10,10);
		for(int i = 1; i <= 5; i++)
		{
			it.setN(i);
			double act = Math.round(it.coherentCount()*scale)/scale;
			assertEquals(exp[i], act);
		}
	}
	
	public void test2() {
		Integral it = new Integral(0,1,1,(x) -> (Math.exp(-Math.pow(x, 2))));
		final double[] exp = {0, 0.7468293572, 0.7468241535, 0.7468241342, 0.7468241330, 0.7468241329};
		double scale = Math.pow(10,10);
		for(int i = 1; i <= 5; i++)
		{
			it.setN(i);
			double act = Math.round(it.count()*scale)/scale;
			assertEquals(exp[i], act);
		}
	}
	
	public void testCoherent2() {
		Integral it = new Integral(0,1,1,(x) -> (Math.exp(-Math.pow(x, 2))));
		final double[] exp = {0, 0.7468293572, 0.7468241535, 0.7468241342, 0.7468241330, 0.7468241329};
		double scale = Math.pow(10,10);
		for(int i = 1; i <= 5; i++)
		{
			it.setN(i);
			double act = Math.round(it.coherentCount()*scale)/scale;
			assertEquals(exp[i], act);
		}
	}
	
	public void test3() {
		Integral it = new Integral(1,5,1,(x) -> (Math.sin(x)/x));
		final double[] exp = {0, 0.6043430354, 0.6038534445, 0.6038486102, 0.6038482505, 0.6038481943};
		double scale = Math.pow(10,10);
		for(int i = 1; i <= 5; i++)
		{
			it.setN(i);
			double act = Math.round(it.count()*scale)/scale;
			assertEquals(exp[i], act);
		}
	}
	
	public void testCoherent3() {
		Integral it = new Integral(1,5,1,(x) -> (Math.sin(x)/x));
		final double[] exp = {0, 0.6043430354, 0.6038534445, 0.6038486102, 0.6038482505, 0.6038481943};
		double scale = Math.pow(10,10);
		for(int i = 1; i <= 5; i++)
		{
			it.setN(i);
			double act = Math.round(it.coherentCount()*scale)/scale;
			assertEquals(exp[i], act);
		}
	}
	
	public void test4() {
		Integral it = new Integral(1,10,1,(x) -> (Math.cos(Math.pow(x, 2))));
		final double[] exp = {0, -2.4411060010, -2.5085032763, -1.3311713224, -1.9213724185, 0.2116439543};
		double scale = Math.pow(10,10);
		for(int i = 1; i <= 5; i++)
		{
			it.setN(i);
			double act = Math.round(it.count()*scale)/scale;
			assertEquals(exp[i], act);
		}
	}
	
	public void testCoherent4() {
		Integral it = new Integral(1,10,1,(x) -> (Math.cos(Math.pow(x, 2))));
		final double[] exp = {0, -2.4411060010, -2.5085032763, -1.3311713224, -1.9213724185, 0.2116439543};
		double scale = Math.pow(10,10);
		for(int i = 1; i <= 5; i++)
		{
			it.setN(i);
			double act = Math.round(it.coherentCount()*scale)/scale;
			assertEquals(exp[i], act);
		}
	}
	
	public void test5() {
		Integral it = new Integral(0,1,1,(x) -> (Math.tan(x)));
		final double[] exp = {0, 0.6158451131, 0.6156347591, 0.6156274454, 0.6156266683, 0.6156265260};
		double scale = Math.pow(10,10);
		for(int i = 1; i <= 5; i++)
		{
			it.setN(i);
			double act = Math.round(it.count()*scale)/scale;
			assertEquals(exp[i], act);
		}
	}
	
	public void testCoherent5() {
		Integral it = new Integral(0,1,1,(x) -> (Math.tan(x)));
		final double[] exp = {0, 0.6158451131, 0.6156347591, 0.6156274454, 0.6156266683, 0.6156265260};
		double scale = Math.pow(10,10);
		for(int i = 1; i <= 5; i++)
		{
			it.setN(i);
			double act = Math.round(it.coherentCount()*scale)/scale;
			assertEquals(exp[i], act);
		}
	}
	
	public void testOptimal() {
		int times = 10;
		Integral it = new Integral(1,10,1,(x) -> (Math.cos(Math.pow(x, 2))));
		for(int i = 1; i <= 20; i++)
		{
			it.setN(i);
			double avr = 0;
			for(int j = 1; j <= times; j++)
			{
				Instant start = Instant.now();
				it.count();
				Instant end = Instant.now();
				avr += Duration.between(start, end).toMillis();
			}
			avr /= times;
			System.out.println("Кол-во потоков:" + i + ", среднее время выполнения:" + avr + " ms");
		}
	}

}