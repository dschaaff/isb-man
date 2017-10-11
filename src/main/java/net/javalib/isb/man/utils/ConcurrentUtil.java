package net.javalib.isb.man.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConcurrentUtil {

    public static <T> void processItems(Collection<T> items, int numThreads, ConcurrentProcessor<T> processor) {
        List<T> itemsCopy = new ArrayList<T>(items);
        List<Thread> threads = new ArrayList<Thread>();
        for (int x = 0; x < numThreads; x++) {
            Thread thread = new Thread(new ProcessRunner<T>(itemsCopy, processor));
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static interface ConcurrentProcessor<T> {
        void process(T item);
    }

    private static class ProcessRunner<T> implements Runnable {
        private List<T> runnerItems;
        private ConcurrentProcessor<T> processor;

        public ProcessRunner(List<T> items, ConcurrentProcessor<T> processor) {
            this.runnerItems = items;
            this.processor = processor;
        }

        @Override
        public void run() {
            while (true) {
                T nextItem = null;
                synchronized (runnerItems) {
                    if (runnerItems.size() == 0) {
                        return;
                    }
                    nextItem = runnerItems.remove(0);
                }
                if (nextItem != null)
                    processor.process(nextItem);
            }
        }
    }

}
