package com.yomahub.liteflow.test.preAndFinally;

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
 * 非spring环境下pre节点和finally节点的测试
 * @author Bryan.Zhang
 * @since 2.6.4
 */
public class PreAndFinallyTest extends BaseTest {

    private static FlowExecutor flowExecutor;

    @BeforeClass
    public static void init(){
        LiteflowConfig config = new LiteflowConfig();
        config.setRuleSource("preAndFinally/flow.xml");
        flowExecutor = FlowExecutorHolder.loadInstance(config);
    }

    //测试普通的pre和finally节点
    @Test
    public void testPreAndFinally1() throws Exception{
        LiteflowResponse response = flowExecutor.execute2Resp("chain1", "arg");
        Assert.assertTrue(response.isSuccess());
        Assert.assertEquals("p1==>p2==>a==>b==>c==>f1==>f2",response.getExecuteStepStr());
    }

    //测试pre和finally节点不放在开头和结尾的情况
    @Test
    public void testPreAndFinally2() throws Exception{
        LiteflowResponse response = flowExecutor.execute2Resp("chain2", "arg");
        Assert.assertTrue(response.isSuccess());
        Assert.assertEquals("p1==>p2==>a==>b==>c==>f1==>f2",response.getExecuteStepStr());
    }

    //测试有节点报错是否还执行finally节点的情况，其中d节点会报错，但依旧执行f1,f2节点
    @Test
    public void testPreAndFinally3() throws Exception{
        LiteflowResponse response = flowExecutor.execute2Resp("chain3", "arg");
        Assert.assertFalse(response.isSuccess());
        Assert.assertEquals("p1==>p2==>a==>d==>f1==>f2", response.getExecuteStepStr());
    }

    //测试在finally节点里是否能获取exception
    @Test
    public void testPreAndFinally4() throws Exception{
        LiteflowResponse response = flowExecutor.execute2Resp("chain4", "arg");
        DefaultContext context = response.getFirstContextBean();
        Assert.assertFalse(response.isSuccess());
        Assert.assertTrue(context.getData("hasEx"));
    }

    @Test
    public void testPreAndFinally5() throws Exception{
        LiteflowResponse response = flowExecutor.execute2Resp("chain5", "arg");
        Assert.assertTrue(response.isSuccess());
        Assert.assertEquals("p1==>p2==>p1==>p2==>a==>b==>c==>f1==>f2==>f1", response.getExecuteStepStrWithoutTime());
    }
}
