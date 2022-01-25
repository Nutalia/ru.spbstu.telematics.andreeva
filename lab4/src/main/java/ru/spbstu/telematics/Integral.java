package ru.spbstu.telematics;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;

import ru.spbstu.telematics.Integral.Func;
import ru.spbstu.telematics.Integral.NewtonKotes;

public class Integral {

	private double a; //������ ������
	private double b; //������� ������
	int n; //���������� ����������
	private double h; //����� ���������
	private double sum; //��������������� �������� ���������
	private Func func;
	
	private Object lock = new Object();
	private CountDownLatch latch;
	
	private final int[] constantsKotes = { 19, 75, 50, 50, 75, 19 };
	private final int constant = 288;
	
	//��������������� ������� 
	public static interface Func {
		public double f(double x);
	}
	
	public Integral(double a, double b, int n, Func f) {
		this.a = a;
		this.b = b;
		this.n = n;
		func = f;
		h = (b - a)/(double) n;
		latch = new CountDownLatch(n);
	}
	
	public double count() {
		if(n <= 0)
			throw new IllegalArgumentException();
		sum = 0;
		latch = new CountDownLatch(n);
		for(int i = 1; i <= n; i++)
		{
			new Thread(new NewtonKotes(i)).start();
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sum;
	}
	
	public double coherentCount() {
		if(n <= 0)
			throw new IllegalArgumentException();
		sum = 0;
		latch = new CountDownLatch(n);
		for(int i = 1; i <= n; i++)
		{
			new Thread(new NewtonKotes(i)).run();
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sum;
	}
	
	public class NewtonKotes implements Runnable {
		
		int i; //����� ���������
		double c; //������ ������
		double d; //������� ������
		double sub_h; //��� ������
		
		public NewtonKotes(int i) {
			this.i = i;
			c = a + (i - 1)*h;
			d = a + i*h;
			sub_h = (d - c)/5;
		}
		
		public void run() {
			double y_j = 0;
			for(int j = 0; j <= 5; j++)
			{
				y_j += ((double)5*sub_h)*func.f(c + j*sub_h)*constantsKotes[j]/((double)constant);
				try {
					Thread.sleep(0);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			synchronized(lock)
			{
				sum += y_j;
			}
			latch.countDown();
		}
	}
	
	void setN(int n)
	{
		this.n = n;
		h = (b - a)/n;
	}
	
	public static void main(String[] args) {
		
		Integral it = new Integral(1, 5, 0, (x) -> 1/x);
		
		for(int i = 1; i <= 100; i++)
		{
			it.setN(i);
			double res = 0;
			Instant start = Instant.now();
			res = it.count();
			Instant end = Instant.now();
			System.out.println("Parallel, time:" + Duration.between(start, end).toMillis() + " ms, res:" + res);
			start = Instant.now();
			res = it.coherentCount();
			end = Instant.now();
			System.out.println("Coherent, time:" + Duration.between(start, end).toMillis() + " ms, res:" + res);
		}
	}
}
