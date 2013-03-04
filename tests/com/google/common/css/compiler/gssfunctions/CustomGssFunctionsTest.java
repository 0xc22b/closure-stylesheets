package com.google.common.css.compiler.gssfunctions;

import junit.framework.TestCase;

import com.google.common.collect.ImmutableList;
import com.google.common.css.compiler.ast.GssFunctionException;

public class CustomGssFunctionsTest extends TestCase {
    
    public void testLighten() throws GssFunctionException {
        CustomGssFunctions.Lighten funct = new CustomGssFunctions.Lighten();
        assertEquals("#595959", 
                funct.getCallResultString(ImmutableList.of("#333333", "15")));
    }
    
    public void testDarken() throws GssFunctionException {
        CustomGssFunctions.Darken funct = new CustomGssFunctions.Darken();
        assertEquals("#A1A1A1", 
                funct.getCallResultString(ImmutableList.of("#bbb", "10")));
        
        assertEquals("#7F7F7F", 
                funct.getCallResultString(ImmutableList.of("#999999", "10")));
        
        assertEquals("#00547F", 
                funct.getCallResultString(ImmutableList.of("#0088cc", "15")));
    }
    
    public void testMix() throws GssFunctionException {
        CustomGssFunctions.Mix funct = new CustomGssFunctions.Mix();
        assertEquals("#800080", 
                funct.getCallResultString(ImmutableList.of("#ff0000", "#0000ff", "50")));
    }
    
    public void testSpin() throws GssFunctionException {
        CustomGssFunctions.Spin funct = new CustomGssFunctions.Spin();
        assertEquals("#0044CC", 
                funct.getCallResultString(ImmutableList.of("#0088cc", "20")));
    }
    
    public void testArgb() throws GssFunctionException {
        CustomGssFunctions.Argb funct = new CustomGssFunctions.Argb();
        assertEquals("#805A1794", 
                funct.getCallResultString(ImmutableList.of("rgba(90, 23, 148, 0.5)")));
    }
    
    public void testPercentage() throws GssFunctionException {
        CustomGssFunctions.Percentage funct = new CustomGssFunctions.Percentage();
        assertEquals("5.0%", 
                funct.getCallResultString(ImmutableList.of("0.05")));
    }
    
    public void testConcat() throws GssFunctionException {
        CustomGssFunctions.Concat funct = new CustomGssFunctions.Concat();
        assertEquals("5px, 2px", 
                funct.getCallResultString(ImmutableList.of("5px", ", ", "2px")));
        
        assertEquals("auto \\9", 
                funct.getCallResultString(ImmutableList.of("auto", " \\9")));
        
    }
}
