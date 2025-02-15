package com.yomahub.liteflow.test.asyncNode.cmp;

import com.yomahub.liteflow.annotation.LiteflowCmpDefine;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.slot.DefaultContext;
import com.yomahub.liteflow.slot.Slot;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import org.springframework.stereotype.Component;


@Component("a")
@LiteflowCmpDefine
public class ACmp{
    @LiteflowMethod(LiteFlowMethodEnum.PROCESS)
    public void process(NodeComponent bindCmp) {
        DefaultContext context = bindCmp.getFirstContextBean();
        synchronized (NodeComponent.class){
            if (context.hasData("check")){
                String str = context.getData("check");
                str += bindCmp.getNodeId();
                context.setData("check", str);
            }else{
                context.setData("check", bindCmp.getNodeId());
            }
        }
        System.out.println("Acomp executed!");
    }
}
