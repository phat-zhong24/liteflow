package com.yomahub.liteflow.test.lfCmpAnno;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.slot.DefaultContext;
import com.yomahub.liteflow.test.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * 测试@LiteflowComponent标注
 * @author Bryan.Zhang
 * @since 2.5.10
 */
@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:/lfCmpAnno/application.xml")
public class LiteflowComponentSpringTest extends BaseTest {

    @Autowired
    private FlowExecutor flowExecutor;

    @Test
    public void testConfig() {
        LiteflowResponse response = flowExecutor.execute2Resp("chain1", "arg");
        Assert.assertTrue(response.isSuccess());
        Assert.assertEquals("a[A组件]==>b[B组件]==>c[C组件]==>b[B组件]==>a[A组件]==>d", response.getExecuteStepStr());
    }
}
