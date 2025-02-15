package com.yomahub.liteflow.test.whenTimeOut;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.core.FlowExecutorHolder;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.exception.WhenTimeoutException;
import com.yomahub.liteflow.property.LiteflowConfig;
import com.yomahub.liteflow.slot.DefaultContext;
import com.yomahub.liteflow.test.BaseTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 非spring环境下异步线程超时日志打印测试
 * @author Bryan.Zhang
 * @since 2.6.4
 */
public class WhenTimeOutTest1 extends BaseTest {

    private static FlowExecutor flowExecutor;

    @BeforeClass
    public static void init(){
        LiteflowConfig config = new LiteflowConfig();
        config.setRuleSource("whenTimeOut/flow1.xml");
        config.setWhenMaxWaitSeconds(3);
        flowExecutor = FlowExecutorHolder.loadInstance(config);
    }

    //其中b和c在when情况下超时，所以抛出了WhenTimeoutException这个错
    @Test
    public void testWhenTimeOut() throws Exception{
        LiteflowResponse response = flowExecutor.execute2Resp("chain1", "arg");
        Assert.assertFalse(response.isSuccess());
        Assert.assertEquals(WhenTimeoutException.class, response.getCause().getClass());
    }
}
