package com.google.common.css.compiler.gssfunctions;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.css.compiler.ast.CssTree;
import com.google.common.css.compiler.ast.GssFunction;
import com.google.common.css.compiler.ast.GssParserException;
import com.google.common.css.compiler.ast.testing.NewFunctionalTestBase;
import com.google.common.css.compiler.passes.ResolveCustomFunctionNodes;

public class CustomGssFunctionMapProviderTest extends NewFunctionalTestBase {

    @Override
    protected void runPass() {
        Map<String, GssFunction> functionMap = new CustomGssFunctionMapProvider().get();
        final boolean allowUnknownFunctions = false;
        final Set<String> allowedNonStandardFunctions = ImmutableSet.of();
        new ResolveCustomFunctionNodes(tree.getMutatingVisitController(), errorManager,
                functionMap, allowUnknownFunctions, allowedNonStandardFunctions).runPass();
    }

    public void testConcat() throws GssParserException {
        //CssTree cssTree = parseAndRun("A { width: concat(5px, ' \9'); }");
        //test("A { width: concat(5px, ' \\9'); }", " A { width: 5px; \\9}");
    }
}
