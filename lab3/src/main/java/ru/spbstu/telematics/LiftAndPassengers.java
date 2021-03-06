package ru.spbstu.telematics;

import java.util.Random;

public class LiftAndPassengers {
		
	static final int MaxPassengers = 10;
	int NumberOfPassengers;
	Thread[] t;
	
		public class Lift implements Runnable
		{
			static final int RandConst = 2000;
			static int number = 0;
			volatile boolean moving = true;
			Random r = new Random();
			
			public static int getNumber() {
				return number;
			}
			
			void move() {
				moving = true;
				System.out.println("Лифт начал движение");
				try {
					Thread.sleep(r.nextInt(RandConst));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Лифт закончил движение");
				moving = false;
			}
			
			public void enter() {
				synchronized(this)
				{
					while(!(number < MaxPassengers) || moving)
						try {
							System.out.print("Попытка войти, " + Thread.currentThread().getName() + ", но");
							if(!(number < MaxPassengers) && moving)
								System.out.println(" слишком много людей и лифт движется");
							else if(!(number < MaxPassengers))
								System.out.println(" слишком много людей");
							else System.out.println(" лифт еще движется");
							wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					number++;
					System.out.println(Thread.currentThread().getName() + " вошёл в лифт");
				}
			}
			
			public void exit() {
				synchronized(this)
				{
					while(moving)
						try {
							System.out.println("Попытка выйти, " + Thread.currentThread().getName() + ", но лифт еще движется");
							wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					number--;
					notify();
					System.out.println(Thread.currentThread().getName() + " вышел из лифта");
				}
			}
			
			@Override
			public void run() {
				while(!Thread.currentThread().isInterrupted())
				{
					move(); //лифт движется
					synchronized(this)
					{
						notifyAll(); //пассажирам можно войти
					}
					try {
						Thread.sleep(5000); //двери открыты 5 секунд
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
		}
		public static class Passenger implements Runnable 
		{
			Lift lift;
			static final int RandConst = 10000;
			Random r = new Random();
			
			Passenger(Lift lift)
			{
				this.lift = lift;
			}
			
			void waitFloor() {
				try {
					Thread.sleep(r.nextInt(RandConst));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void run() {
				while(!Thread.currentThread().isInterrupted())
				{
					lift.enter(); //пассажир пытается войти в лифт
					waitFloor(); //пассажир ждет нужный этаж
					lift.exit(); //пассажир пытается выйти из лифта
					try {
						Thread.sleep(10000); //пассажир "занимается своими делами" 10 секунд
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}

	public LiftAndPassengers(int number) {
		NumberOfPassengers = number;
		Lift lift = new Lift();
		t = new Thread[NumberOfPassengers + 1];
		t[0] = new Thread(lift, "lift");
		t[0].start();
		for (int i = 0; i < NumberOfPassengers; i++)
		{
			t[i + 1] = new Thread(new Passenger(lift), "passenger " + (i + 1));
			t[i + 1].start();
		}
	}
	
	public void stopThreads() {
		for(int i = 0; i < NumberOfPassengers + 1; i++)
			t[i].stop();
	}
	
	public static void main(String[] args) {
		LiftAndPassengers obj = new LiftAndPassengers(15);
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		obj.stopThreads();
	}

}