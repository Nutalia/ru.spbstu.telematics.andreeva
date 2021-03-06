package ru.spbstu.telematics;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;

import ru.spbstu.telematics.Integral.Func;
import ru.spbstu.telematics.Integral.NewtonKotes;

public class Integral {

	private double a; //нижний предел
	private double b; //верхний предел
	int n; //количество интервалов
	private double h; //длина интервала
	private double sum; //приблизительное значение интеграла
	private Func func;
	private int threads; //количество потоков
	
	private Object lock = new Object();
	private CountDownLatch latch;
	
	private final int[] constantsKotes = { 19, 75, 50, 50, 75, 19 };
	private final int constant = 288;
	
	//подынтегральная функция 
	public static interface Func {
		public double f(double x);
	}
	
	public Integral(double a, double b, int n, int threads, Func f) {
		this.a = a;
		this.b = b;
		this.n = n;
		this.threads = threads;
		func = f;
		h = (b - a)/(double) n;
		latch = new CountDownLatch(threads);
	}
	
	public double count() {
		if(n <= 0 || threads <= 0)
			throw new IllegalArgumentException();
		sum = 0;
		latch = new CountDownLatch(threads);
		int step = n/threads;
		if(step <= n - threads*step)
			step++;
		for(int i = 0; i < threads - 1; i++)
		{
			new Thread(new NewtonKotes(step, a + i*step*h)).start();
		}
		new Thread(new NewtonKotes( n-(threads - 1)*step, a + (threads - 1)*step*h)).start();
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
		latch = new CountDownLatch(threads);
		int step = n/threads;
		if(step <= n - threads*step)
			step++;
		for(int i = 0; i < threads - 1; i++)
		{
			new Thread(new NewtonKotes(step, a + i*step*h)).run();
		}
		new Thread(new NewtonKotes( n-(threads - 1)*step, a + (threads - 1)*step*h)).run();
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sum;
	}
	
	public class NewtonKotes implements Runnable {
		
		int intervals; //количество интервалов
		double c; //нижний предел
		//double sub_h; //шаг метода
		
		public NewtonKotes(int intervals, double c) {
			this.intervals = intervals;
			this.c = c;
		}
		
		public void run() {
			double sub_sum = 0;
			for(int k = 1; k <= intervals; k++)
			{
				double sub_c = c + (k - 1)*h;
				double sub_d = c + k*h;
				double sub_h = (sub_d - sub_c)/5;
				for(int j = 0; j <= 5; j++)
				{
					sub_sum += ((double)5*sub_h)*func.f(sub_c + j*sub_h)*constantsKotes[j]/((double)constant);
				}
			}
			synchronized(lock)
			{
				sum += sub_sum;
			}
			latch.countDown();
		}
	}
	
	public void setN(int n)
	{
		this.n = n;
		h = (b - a)/n;
	}
	
	public void setThreads(int threads) {
		this.threads = threads;
	}
	
	public static void main(String[] args) {
		
		Integral it = new Integral(1, 5, 1, 8,(x) -> 1/x);
		
		for(int i = 1000; i <= 10000; i+=1000)
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
