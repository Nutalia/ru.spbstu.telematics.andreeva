package ru.spbstu.telematics;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;

public class Integral {

	private double a; 		//нижний предел
	private double b;		//верхний предел
	private int n;	 		//количество интервалов:от 1 до 10
	private double h; 		//шаг интервала
	private double sum = 0; //приближенное значение интеграла
	private Object lock = new Object(); 
	private CountDownLatch latch;
	private Func func;
	
	private final int[][] constantsKotes = { { 0 }, { 1, 1 },
											{ 1, 4, 1 },
											{ 1, 3, 3, 1 },
											{ 7, 32, 12, 32, 7 },
											{ 19, 75, 50, 50, 75, 19 },
											{ 41, 216, 27, 272, 27, 216, 41 },
											{ 751, 3577, 1323, 2989, 2989, 1323, 3577, 751 },
											{ 989, 5888, -928, 10496, -4540, 10496, -928, 5888, 989 },
											{ 2857, 15741, 1080, 19344, 5778, 5778, 19344, 1080, 15741, 2857 } };
	
	private final int[] constants = { 0, 2, 6, 8, 90, 288, 840, 17280, 28350, 89600 };
	
	public Integral(double a, double b, int n, Func f) {
		this.a = a;
		this.b = b;
		this.n = n;
		func=f;
		h = (b - a)/(double) n;
		latch = new CountDownLatch(n + 1);
	}
	
	//подынтегральная функция 
	public static interface Func {
		public double f(double x);
	}
	
	public double count() {
		if(n < 1 || n >= 10)
			throw new IllegalArgumentException();
		sum = 0;
		latch = new CountDownLatch(n+1);
		for(int j = 0; j < n + 1; j++)
			new Thread(new NewtonKotes(j)).start();
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return sum;
	}
	
	public double coherentCount() {
		if(n < 1 || n >= 10)
			throw new IllegalArgumentException();
		sum = 0;
		for(int i = 0; i < n + 1; i++)
		{
			new NewtonKotes(i).run();
		}
		return sum;
	}
	
	public class NewtonKotes implements Runnable {
		int i; //номер точки
		
		public NewtonKotes(int i)
		{
			this.i = i;
		}
		
		@Override
		public void run() {
			double x_i = a + i*h;
			double y_i = func.f(x_i)*constantsKotes[n][i];
			try {
				Thread.sleep(0);//для тестирования
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			y_i = ((double)n*h)*y_i/((double)constants[n]);
			synchronized(lock)
			{
				sum += y_i;
			}
			latch.countDown();
		}
	}
	
	public void setN(int n) {
		this.n = n;
		h = (b - a)/(double) n;
		sum = 0;
		latch = new CountDownLatch(n + 1);
	}
}