/**
 * <p>Title: liteflow</p>
 * <p>Description: 轻量级的组件式流程框架</p>
 * @author Bryan.Zhang
 * @email weenyc31@163.com
 * @Date 2020/4/1
 */
package com.yomahub.liteflow.test.tag.cmp;

import cn.hutool.core.collection.ConcurrentHashSet;
import com.yomahub.liteflow.annotation.LiteflowCmpDefine;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.slot.DefaultContext;
import com.yomahub.liteflow.slot.Slot;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;

@LiteflowComponent("b1")
@LiteflowCmpDefine
public class B1Cmp{

	@LiteflowMethod(LiteFlowMethodEnum.PROCESS)
	public void process(NodeComponent bindCmp) {
		DefaultContext context = bindCmp.getFirstContextBean();
		context.setData("test",new ConcurrentHashSet<String>());
	}
}
