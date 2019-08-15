package volley.yan.com.volley.http;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager {

    // JAVA 自带的阻塞式的队列，执行时会阻塞线程
    private static ThreadPoolManager instance = new ThreadPoolManager();
    private LinkedBlockingQueue<Future<?>> taskQuene = new LinkedBlockingQueue<Future<?>>();
    private ThreadPoolExecutor threadPoolExecutor;

    public ThreadPoolManager() {
        threadPoolExecutor = new ThreadPoolExecutor(4, 10, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4), handler);
        threadPoolExecutor.execute(runnable);
        if (null == taskQuene) {
            taskQuene = new LinkedBlockingQueue<Future<?>>();
        }
    }

    private RejectedExecutionHandler handler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                taskQuene.put(new FutureTask<Object>(r, null));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public static ThreadPoolManager getInstance() {
        return instance;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                FutureTask futureTask = null;
                try {
                    /**
                     * 阻塞式函数
                     */
                    Log.d("yanzhe", "等待队列数    " + taskQuene.size());
                    futureTask = (FutureTask) taskQuene.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 执行阻塞队列的线程
                if (null != futureTask) {
                    threadPoolExecutor.execute(futureTask);
                }
            }
        }
    };

    public <T> void exute(FutureTask<T> futureTask) throws InterruptedException {
        Log.d("yanzhe", "执行   " + futureTask.toString());
        taskQuene.put(futureTask);
    }

}
