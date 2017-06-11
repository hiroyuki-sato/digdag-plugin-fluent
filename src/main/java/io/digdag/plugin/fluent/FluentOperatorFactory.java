package io.digdag.plugin.fluent;

import static java.nio.charset.StandardCharsets.UTF_8;
import io.digdag.client.config.Config;
import io.digdag.spi.Operator;
import io.digdag.spi.OperatorContext;
import io.digdag.spi.OperatorFactory;
import io.digdag.spi.TaskResult;
import io.digdag.spi.TemplateEngine;
import io.digdag.util.BaseOperator;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import org.fluentd.logger.FluentLogger;

import com.google.common.base.Throwables;

public class FluentOperatorFactory implements OperatorFactory {
    @SuppressWarnings("unused")
    private final TemplateEngine templateEngine;

    private static FluentLogger logger;

    public FluentOperatorFactory(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String getType() {
        return "fluent";
    }

    @Override
    public Operator newOperator(OperatorContext context) {
        return new FluentOperator(context);
    }

    private class FluentOperator extends BaseOperator {
        public FluentOperator(OperatorContext context) {
            super(context);
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!");
            Config params = request.getConfig().mergeDefault(
                    request.getConfig().getNestedOrGetEmpty("fluent"));
            String host = params.get("host", String.class);
            Integer port = params.get("port",Integer.class);
            String tag = params.get("tag",String.class);
            logger = FluentLogger.getLogger(tag,host,port);
        }

        @Override
        public TaskResult runTask() {
            Config params = request.getConfig().mergeDefault(
                    request.getConfig().getNestedOrGetEmpty("fluent"));
            Map<String, Object> data = new HashMap<String, Object>();
            String message = params.get("message", String.class);
            System.out.println("@@@@@@@@@@@@@");
            data.put("message",message);

            logger.log("test",data);
            //            String message = workspace.templateCommand(templateEngine, params, "message", UTF_8);
/*
            try {
//                Files.write(workspace.getPath(path), message.getBytes(UTF_8));
            } catch (IOException ex) {
                throw Throwables.propagate(ex);
            }
*/

            return TaskResult.empty(request);
        }
    }

}
