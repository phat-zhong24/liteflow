package com.yomahub.liteflow.test.subflow.cmp2;

import com.yomahub.liteflow.annotation.LiteflowCmpDefine;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import org.springframework.stereotype.Component;

import static com.yomahub.liteflow.test.subflow.ImplicitSubFlowELDeclSpringbootTest.RUN_TIME_SLOT;


@Component("m")
@LiteflowCmpDefine
public class MCmp{
    @LiteflowMethod(LiteFlowMethodEnum.PROCESS)
    public void process(NodeComponent bindCmp) throws Exception {

        RUN_TIME_SLOT.add(bindCmp.getSlot().getRequestId());

        System.out.println("Mcomp executed!");
    }
}
