package com.yomahub.liteflow.test.customWhenThreadPool;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.core.FlowExecutorHolder;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.property.LiteflowConfig;
import com.yomahub.liteflow.slot.DefaultContext;
import com.yomahub.liteflow.test.BaseTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * nospring环境下异步线程超时日志打印测试
 *
 * @author Bryan.Zhang
 * @since 2.6.4
 */
public class CustomWhenThreadPoolTest extends BaseTest {

    private static FlowExecutor flowExecutor;

    @BeforeClass
    public static void init(){
        LiteflowConfig config = new LiteflowConfig();
        config.setRuleSource("customWhenThreadPool/flow.xml");
        flowExecutor = FlowExecutorHolder.loadInstance(config);
    }

    /**
     * 测试全局线程池配置
     */
    @Test
    public void testGlobalThreadPool() {
        LiteflowResponse response = flowExecutor.execute2Resp("chain", "arg");
        DefaultContext context = response.getFirstContextBean();
        Assert.assertTrue(response.isSuccess());
        Assert.assertTrue(context.getData("threadName").toString().startsWith("lf-when-thead"));
    }

    /**
     * 测试全局和when上自定义线程池-优先以when上为准
     */
    @Test
    public void testGlobalAndCustomWhenThreadPool() {
        LiteflowResponse response1 = flowExecutor.execute2Resp("chain1", "arg");
        DefaultContext context = response1.getFirstContextBean();
        Assert.assertTrue(response1.isSuccess());
        Assert.assertTrue(context.getData("threadName").toString().startsWith("customer-when-1-thead"));
    }


    /**
     * when配置的线程池可以共用
     */
    @Test
    public void testCustomWhenThreadPool() {
        // 使用when - thread1
        testGlobalAndCustomWhenThreadPool();
        // chain配置同一个thead1
        LiteflowResponse response2 = flowExecutor.execute2Resp("chain2", "arg");
        DefaultContext context = response2.getFirstContextBean();
        Assert.assertTrue(response2.isSuccess());
        Assert.assertTrue(context.getData("threadName").toString().startsWith("customer-when-1-thead"));

    }
}
