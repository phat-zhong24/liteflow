/**
 * <p>Title: liteflow</p>
 * <p>Description: 轻量级的组件式流程框架</p>
 * @author Bryan.Zhang
 * @email weenyc31@163.com
 * @Date 2020/4/1
 */
package com.yomahub.liteflow.test.tag.cmp;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.slot.DefaultContext;
import com.yomahub.liteflow.slot.Slot;

@LiteflowComponent("a")
public class ACmp extends NodeComponent {

	@Override
	public void process() {
		String testKey = "test";

		DefaultContext context = this.getFirstContextBean();
		if (context.getData(testKey) == null){
			context.setData(testKey,this.getTag());
		}else{
			String s = context.getData(testKey);
			s += this.getTag();
			context.setData(testKey, s);
		}
	}
}
