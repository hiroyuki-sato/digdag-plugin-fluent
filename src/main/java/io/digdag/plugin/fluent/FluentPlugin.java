package io.digdag.plugin.fluent;

import io.digdag.spi.OperatorFactory;
import io.digdag.spi.OperatorProvider;
import io.digdag.spi.Plugin;
import io.digdag.spi.TemplateEngine;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class FluentPlugin implements Plugin {
    @Override
    public <T> Class<? extends T> getServiceProvider(Class<T> type) {
        System.out.println("*****************");
        System.out.println(type);
        if (type == OperatorProvider.class) {
            return FluentOperatorProvider.class.asSubclass(type);
        } else {
            return null;
        }
    }

    public static class FluentOperatorProvider implements OperatorProvider {
        @Inject
        protected TemplateEngine templateEngine;

        @Override
        public List<OperatorFactory> get() {
            return Arrays.asList(new FluentOperatorFactory(templateEngine));
        }
    }
}
