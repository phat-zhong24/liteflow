package com.yomahub.liteflow.test.builder;

import com.yomahub.liteflow.builder.LiteFlowChainBuilder;
import com.yomahub.liteflow.builder.LiteFlowConditionBuilder;
import com.yomahub.liteflow.builder.LiteFlowNodeBuilder;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.core.FlowExecutorHolder;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.builder.entity.ExecutableEntity;
import com.yomahub.liteflow.enums.NodeTypeEnum;
import com.yomahub.liteflow.property.LiteflowConfig;
import com.yomahub.liteflow.slot.DefaultContext;
import com.yomahub.liteflow.test.BaseTest;
import com.yomahub.liteflow.test.builder.cmp.ACmp;
import com.yomahub.liteflow.test.builder.cmp.BCmp;
import com.yomahub.liteflow.test.builder.cmp.CCmp;
import com.yomahub.liteflow.test.builder.cmp.DCmp;
import com.yomahub.liteflow.test.builder.cmp.ECmp;
import com.yomahub.liteflow.test.builder.cmp.FCmp;
import com.yomahub.liteflow.test.builder.cmp.GCmp;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BuilderTest extends BaseTest {

    private static FlowExecutor flowExecutor;

    @BeforeClass
    public static void init(){
        LiteflowConfig config = new LiteflowConfig();
        flowExecutor = FlowExecutorHolder.loadInstance(config);
    }

    //基于普通组件的builder模式测试
    @Test
    public void testBuilder() throws Exception {
        LiteFlowNodeBuilder.createNode().setId("a")
                .setName("组件A")
                .setType(NodeTypeEnum.COMMON)
                .setClazz("com.yomahub.liteflow.test.builder.cmp.ACmp")
                .build();
        LiteFlowNodeBuilder.createNode().setId("b")
                .setName("组件B")
                .setType(NodeTypeEnum.COMMON)
                .setClazz("com.yomahub.liteflow.test.builder.cmp.BCmp")
                .build();
        LiteFlowNodeBuilder.createNode().setId("c")
                .setName("组件C")
                .setType(NodeTypeEnum.COMMON)
                .setClazz("com.yomahub.liteflow.test.builder.cmp.CCmp")
                .build();
        LiteFlowNodeBuilder.createNode().setId("d")
                .setName("组件D")
                .setType(NodeTypeEnum.COMMON)
                .setClazz("com.yomahub.liteflow.test.builder.cmp.DCmp")
                .build();
        LiteFlowNodeBuilder.createNode().setId("e")
                .setName("组件E")
                .setType(NodeTypeEnum.SWITCH)
                .setClazz("com.yomahub.liteflow.test.builder.cmp.ECmp")
                .build();
        LiteFlowNodeBuilder.createNode().setId("f")
                .setName("组件F")
                .setType(NodeTypeEnum.COMMON)
                .setClazz("com.yomahub.liteflow.test.builder.cmp.FCmp")
                .build();
        LiteFlowNodeBuilder.createNode().setId("g")
                .setName("组件G")
                .setType(NodeTypeEnum.COMMON)
                .setClazz("com.yomahub.liteflow.test.builder.cmp.GCmp")
                .build();


        LiteFlowChainBuilder.createChain().setChainName("chain2").setCondition(
                LiteFlowConditionBuilder.createThenCondition().setValue("c,d").build()
        ).build();

        LiteFlowChainBuilder.createChain().setChainName("chain1").setCondition(
                LiteFlowConditionBuilder
                        .createThenCondition()
                        .setValue("a,b").build()
        ).setCondition(
                LiteFlowConditionBuilder.createWhenCondition()
                        .setValue("e(f|g|chain2)").build()
        ).build();

        LiteflowResponse response = flowExecutor.execute2Resp("chain1");
        Assert.assertTrue(response.isSuccess());
        Assert.assertEquals("a[组件A]==>b[组件B]==>e[组件E]==>c[组件C]==>d[组件D]", response.getExecuteStepStr());
    }

    //基于普通组件的builder模式测试
    @Test
    public void testBuilderForClassAndCode() throws Exception {
        LiteFlowNodeBuilder.createNode().setId("a")
                .setName("组件A")
                .setType(NodeTypeEnum.COMMON)
                .setClazz(ACmp.class)
                .build();
        LiteFlowNodeBuilder.createNode().setId("b")
                .setName("组件B")
                .setType(NodeTypeEnum.COMMON)
                .setClazz(BCmp.class)
                .build();
        LiteFlowNodeBuilder.createNode().setId("c")
                .setName("组件C")
                .setType(NodeTypeEnum.COMMON)
                .setClazz(CCmp.class)
                .build();
        LiteFlowNodeBuilder.createNode().setId("d")
                .setName("组件D")
                .setType(NodeTypeEnum.COMMON)
                .setClazz(DCmp.class)
                .build();
        LiteFlowNodeBuilder.createNode().setId("e")
                .setName("组件E")
                .setType(NodeTypeEnum.SWITCH)
                .setClazz(ECmp.class)
                .build();
        LiteFlowNodeBuilder.createNode().setId("f")
                .setName("组件F")
                .setType(NodeTypeEnum.COMMON)
                .setClazz(FCmp.class)
                .build();
        LiteFlowNodeBuilder.createNode().setId("g")
                .setName("组件G")
                .setType(NodeTypeEnum.COMMON)
                .setClazz(GCmp.class)
                .build();

        LiteFlowChainBuilder.createChain().setChainName("chain2").setCondition(
                LiteFlowConditionBuilder.createThenCondition().setValue("c,d").build()
        ).build();

        LiteFlowChainBuilder.createChain().setChainName("chain1").setCondition(
                LiteFlowConditionBuilder
                        .createThenCondition()
                        .setValue("a,b").build()
        ).setCondition(
                LiteFlowConditionBuilder.createWhenCondition()
                        .setValue("e(f|g|chain2)").build()
        ).build();

        LiteflowResponse response = flowExecutor.execute2Resp("chain1");
        Assert.assertTrue(response.isSuccess());
        Assert.assertEquals("a[组件A]==>b[组件B]==>e[组件E]==>c[组件C]==>d[组件D]", response.getExecuteStepStr());
    }

    //基于普通组件的builder模式测试
    @Test
    public void testBuilderForConditionNode() throws Exception {
        LiteFlowNodeBuilder.createNode().setId("a")
                .setName("组件A")
                .setType(NodeTypeEnum.COMMON)
                .setClazz(ACmp.class)
                .build();
        LiteFlowNodeBuilder.createNode().setId("b")
                .setName("组件B")
                .setType(NodeTypeEnum.COMMON)
                .setClazz(BCmp.class)
                .build();
        LiteFlowNodeBuilder.createNode().setId("c")
                .setName("组件C")
                .setType(NodeTypeEnum.COMMON)
                .setClazz(CCmp.class)
                .build();
        LiteFlowNodeBuilder.createNode().setId("d")
                .setName("组件D")
                .setType(NodeTypeEnum.COMMON)
                .setClazz(DCmp.class)
                .build();
        LiteFlowNodeBuilder.createNode().setId("e")
                .setName("组件E")
                .setType(NodeTypeEnum.SWITCH)
                .setClazz(ECmp.class)
                .build();
        LiteFlowNodeBuilder.createNode().setId("f")
                .setName("组件F")
                .setType(NodeTypeEnum.COMMON)
                .setClazz(FCmp.class)
                .build();
        LiteFlowNodeBuilder.createNode().setId("g")
                .setName("组件G")
                .setType(NodeTypeEnum.COMMON)
                .setClazz(GCmp.class)
                .build();


        LiteFlowChainBuilder.createChain().setChainName("chain2").setCondition(
                LiteFlowConditionBuilder.createThenCondition()
                        .setExecutable(new ExecutableEntity().setId("c"))
                        .setExecutable(new ExecutableEntity().setId("d"))
                        .build()
        ).build();

        LiteFlowChainBuilder.createChain().setChainName("chain1").setCondition(
                LiteFlowConditionBuilder
                        .createThenCondition()
                        .setExecutable(new ExecutableEntity().setId("a").setTag("hello"))
                        .setExecutable(new ExecutableEntity().setId("b"))
                        .build()
        ).setCondition(
                LiteFlowConditionBuilder.createWhenCondition()
                        .setExecutable(
                                new ExecutableEntity().setId("e")
                                        .addNodeCondComponent(new ExecutableEntity().setId("f").setTag("FHello"))
                                        .addNodeCondComponent(new ExecutableEntity().setId("g"))
                                        .addNodeCondComponent(new ExecutableEntity().setId("chain2")
                                        )).build()
        ).build();

        LiteflowResponse response = flowExecutor.execute2Resp("chain1");
        Assert.assertTrue(response.isSuccess());
        Assert.assertEquals("a[组件A]==>b[组件B]==>e[组件E]==>c[组件C]==>d[组件D]", response.getExecuteStepStr());
    }
}
