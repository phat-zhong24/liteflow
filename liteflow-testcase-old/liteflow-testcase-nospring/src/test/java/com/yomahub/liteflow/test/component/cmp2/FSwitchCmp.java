package com.yomahub.liteflow.test.component.cmp2;

import com.yomahub.liteflow.core.NodeSwitchComponent;
import java.util.Objects;

public class FSwitchCmp extends NodeSwitchComponent {
    @Override
    public String processSwitch() {
        Integer requestData = this.getRequestData();
        if (Objects.isNull(requestData)){
            return "d";
        } else if(requestData == 0){
            return "c";
        } else {
            return "b";
        }
    }
}
