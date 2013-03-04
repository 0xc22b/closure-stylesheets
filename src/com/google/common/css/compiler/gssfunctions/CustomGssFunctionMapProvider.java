package com.google.common.css.compiler.gssfunctions;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.css.compiler.ast.GssFunction;
import com.google.common.css.compiler.gssfunctions.DefaultGssFunctionMapProvider;
import com.google.common.css.compiler.gssfunctions.GssFunctions;

public class CustomGssFunctionMapProvider extends DefaultGssFunctionMapProvider {

    @Override
    public Map<String, GssFunction> get() {
        Map<String, GssFunction> gssFunctions = GssFunctions.getFunctionMap();
        Map<String, GssFunction> customGssFunctions = CustomGssFunctions.getFunctionMap();
        Builder<String, GssFunction> builder = ImmutableMap.<String, GssFunction>builder();
        for (Map.Entry<String, GssFunction> entry : gssFunctions.entrySet()) {
            builder.put(entry);
        }
        for (Map.Entry<String, GssFunction> entry : customGssFunctions.entrySet()) {
            builder.put(entry);
        }
        return builder.build();
    }
}
