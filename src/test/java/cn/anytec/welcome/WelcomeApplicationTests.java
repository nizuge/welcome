package cn.anytec.welcome;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Semaphore;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WelcomeApplicationTests {

	@Test
	public void contextLoads() {
		Semaphore semaphore = new Semaphore(5,true);
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					if(semaphore.tryAcquire()){
						System.out.println(Thread.currentThread().getName()+"获取成功");
						int s = semaphore.availablePermits();
						System.out.println(":"+s);
						Thread.sleep(1);
						System.out.println(Thread.currentThread().getName()+"返回");
						semaphore.release();
					}else {
						System.out.println(Thread.currentThread().getName()+"获取失败");
					}


				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread(runnable);
			thread.setName("线程："+i);
			thread.setDaemon(true);
			thread.start();

		}
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public  void mainee() {
		Deque<Integer> deque = new ArrayDeque<Integer>(5);

		// use add() method to add elements in the deque
		deque.add(25);
		deque.add(30);
		deque.add(35);

		// use addFirst() method to add element at the front of the deque
		deque.addFirst(10);
		deque.addFirst(15);
		deque.addFirst(20);//now, element 20 will be at the front

		// these elments will be added in continuation with deque.add(35)
		deque.add(45);
		deque.add(40);

		for (int i = 0; i < deque.size(); i++) {
			System.out.println(deque.poll());
		}
	}

}
