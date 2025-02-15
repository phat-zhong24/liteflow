/**
 * <p>Title: liteflow</p>
 * <p>Description: 轻量级的组件式流程框架</p>
 * @author Bryan.Zhang
 * @email weenyc31@163.com
 * @Date 2020/4/1
 */
package com.yomahub.liteflow.test.event.cmp;

import com.yomahub.liteflow.annotation.LiteflowCmpDefine;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.slot.DefaultContext;
import org.springframework.stereotype.Component;

@Component("b")
@LiteflowCmpDefine
public class BCmp{

	@LiteflowMethod(LiteFlowMethodEnum.PROCESS)
	public void process(NodeComponent bindCmp) {
		System.out.println("BCmp executed!");
	}

	@LiteflowMethod(LiteFlowMethodEnum.ON_SUCCESS)
	public void onSuccess(NodeComponent bindCmp) throws Exception {
		DefaultContext context = bindCmp.getFirstContextBean();
		String str = context.getData("test");
		str += bindCmp.getNodeId();
		context.setData("test", str);
	}
}
