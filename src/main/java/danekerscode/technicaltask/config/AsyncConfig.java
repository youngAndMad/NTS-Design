package danekerscode.technicaltask.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfig {

    /**
     * @return Executor for async file uploading
     * @author Daneker
     * <p>
     * <p>
     * executor.setCorePoolSize(2):
     * This line sets the core pool size of the executor to 2.
     * The core pool size is the number of threads that are kept alive in the pool, even when they are idle.
     * <p>
     * <p>
     * executor.setMaxPoolSize(5):
     * This line sets the maximum pool size of the executor to 5.
     * The maximum pool size is the maximum number of threads that can be created in the pool.
     * <p>
     * <p>
     * executor.setQueueCapacity(50):
     * This line sets the queue capacity of the executor to 50.
     * The queue capacity is the maximum number of tasks that can be queued for execution when all threads in the pool are busy.
     */
    @Bean(name = "file-uploading")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(50);
        executor.initialize();
        return executor;
    }
}