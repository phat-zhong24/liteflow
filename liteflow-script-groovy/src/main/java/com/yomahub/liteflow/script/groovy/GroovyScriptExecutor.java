package com.yomahub.liteflow.script.groovy;

import cn.hutool.core.util.StrUtil;
import com.yomahub.liteflow.slot.DataBus;
import com.yomahub.liteflow.slot.Slot;
import com.yomahub.liteflow.script.ScriptExecutor;
import com.yomahub.liteflow.script.exception.ScriptExecuteException;
import com.yomahub.liteflow.script.exception.ScriptLoadException;
import com.yomahub.liteflow.util.CopyOnWriteHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.*;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Groovy脚本语言的执行器实现
 * @author Bryan.Zhang
 * @since 2.6.0
 */
public class GroovyScriptExecutor implements ScriptExecutor {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private ScriptEngine scriptEngine;

    private final Map<String, CompiledScript> compiledScriptMap = new CopyOnWriteHashMap<>();

    @Override
    public ScriptExecutor init() {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        scriptEngine = scriptEngineManager.getEngineByName("groovy");
        return this;
    }

    @Override
    public void load(String nodeId, String script) {
        try{
            CompiledScript compiledScript = ((Compilable) scriptEngine).compile(script);
            compiledScriptMap.put(nodeId, compiledScript);
        }catch (Exception e){
            String errorMsg = StrUtil.format("script loading error for node[{}], error msg:{}", nodeId, e.getMessage());
            throw new ScriptLoadException(errorMsg);
        }

    }

    @Override
    public Object execute(String nodeId, int slotIndex) {
        try{
            if (!compiledScriptMap.containsKey(nodeId)){
                String errorMsg = StrUtil.format("script for node[{}] is not loaded", nodeId);
                throw new RuntimeException(errorMsg);
            }

            CompiledScript compiledScript = compiledScriptMap.get(nodeId);
            Bindings bindings = new SimpleBindings();

            //往脚本语言绑定表里循环增加绑定上下文的key
            //key的规则为自定义上下文的simpleName
            //比如你的自定义上下文为AbcContext，那么key就为:abcContext
            //这里不统一放一个map的原因是考虑到有些用户会调用上下文里的方法，而不是参数，所以脚本语言的绑定表里也是放多个上下文
            DataBus.getContextBeanList(slotIndex).forEach(o -> {
                String key = StrUtil.lowerFirst(o.getClass().getSimpleName());
                bindings.put(key, o);
            });
            return compiledScript.eval(bindings);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            String errorMsg = StrUtil.format("script execute error for node[{}]", nodeId);
            throw new ScriptExecuteException(errorMsg);
        }
    }

    @Override
    public void cleanCache() {
        compiledScriptMap.clear();
    }
}
