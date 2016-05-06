package com.core.service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NewThread {
	
	public static void main(String[] args) {   
		ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 7, 10, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(2),new ThreadPoolExecutor.DiscardOldestPolicy() );
         
		for(int i=0;i<15;i++){ 
			MyTask myTask = new MyTask(i);               
			executor.execute(myTask);                            
			System.out.println("线程池中线程数目："+executor.getPoolSize()+"队列等待执行的任务数目："+ executor.getQueue().size()+"已经执行完别的任务数目："+executor.getCompletedTaskCount());           
		}            
		executor.shutdown();
    }

}

class MyTask implements Runnable {
    private int taskNum;
     
    public MyTask(int num) {
        this.taskNum = num;
    }
     
    @SuppressWarnings("static-access")
    public void run() {
        System.out.println("正在执行task "+taskNum);
        try {
            Thread.currentThread().sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task "+taskNum+"执行完毕");
    }
}