package org.noear.solon.serialization.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.noear.solon.core.XContext;
import org.noear.solon.core.XRender;

import static com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping.NON_FINAL;

public class JacksonRender implements XRender {
    ObjectMapper mapper = new ObjectMapper();
    ObjectMapper mapper_serialize = new ObjectMapper();

    public JacksonRender(){
        mapper_serialize.enableDefaultTypingAsProperty(NON_FINAL, "@type");
    }

    @Override
    public void render(Object obj, XContext ctx) throws Throwable {
        boolean is_serialize = "serialize".equals(ctx.attr("solon.reader.mode", null));

        String txt = null;

        if (is_serialize) {
            //序列化处理
            //
            txt = mapper_serialize.writeValueAsString(obj);
        } else {
            //非序列化处理
            //
            if (obj == null) {
                return;
            }

            if (obj instanceof Throwable) {
                throw (Throwable) obj;
            }

            if (obj instanceof String) {
                ctx.output((String) obj); //不能做为json输出
                return;
            }

            txt = mapper.writeValueAsString(obj);
        }

        if(XPluginImp.output_meta) {
            ctx.headerSet("solon.serialization", "JacksonRender");
        }

        ctx.attrSet("output", txt);
        ctx.outputAsJson(txt);
    }
}
