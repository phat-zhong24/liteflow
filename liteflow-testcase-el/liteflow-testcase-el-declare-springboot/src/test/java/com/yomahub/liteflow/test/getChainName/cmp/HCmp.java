/**
 * <p>Title: liteflow</p>
 * <p>Description: 轻量级的组件式流程框架</p>
 * @author Bryan.Zhang
 * @email weenyc31@163.com
 * @Date 2020/4/1
 */
package com.yomahub.liteflow.test.getChainName.cmp;

import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.annotation.LiteflowSwitchCmpDefine;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.core.NodeSwitchComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.slot.DefaultContext;
import org.springframework.stereotype.Component;

@Component("h")
@LiteflowSwitchCmpDefine
public class HCmp{

	@LiteflowMethod(LiteFlowMethodEnum.PROCESS_SWITCH)
	public String processSwitch(NodeComponent bindCmp) throws Exception {
		DefaultContext context = bindCmp.getFirstContextBean();
		context.setData(bindCmp.getNodeId(), bindCmp.getCurrChainName());
		return "j";
	}

}
