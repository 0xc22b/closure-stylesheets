package com.google.common.css.compiler.gssfunctions;

import static com.google.common.css.compiler.gssfunctions.ColorUtil.formatColor;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.css.compiler.ast.CssHexColorNode;
import com.google.common.css.compiler.ast.CssLiteralNode;
import com.google.common.css.compiler.ast.CssNumericNode;
import com.google.common.css.compiler.ast.CssStringNode;
import com.google.common.css.compiler.ast.CssValueNode;
import com.google.common.css.compiler.ast.ErrorManager;
import com.google.common.css.compiler.ast.GssError;
import com.google.common.css.compiler.ast.GssFunction;
import com.google.common.css.compiler.ast.GssFunctionException;
import com.google.common.css.compiler.gssfunctions.GssFunctions.AddHsbToCssColor;

public class CustomGssFunctions {

    /**
     * @return a map from each GSS function name to the function
     */
    public static Map<String, GssFunction> getFunctionMap() {
        return ImmutableMap.<String, GssFunction> builder()
                .put("saturate", new Saturate())
                .put("desaturate", new Desaturate())
                .put("grayscale", new Grayscale())
                .put("lighten", new Lighten())
                .put("darken", new Darken())
                .put("mix", new Mix())
                .put("spin", new Spin())
                .put("argb", new Argb())
                .put("percentage", new Percentage())
                .put("removeUnit", new RemoveUnit())
                .put("concat", new Concat())
                .build();
    }
    
    public static String addHsbToCssColor(String baseColorString, String hueToAdd,
            String saturationToAdd, String brightnessToAdd) throws GssFunctionException {
        try {
            return getAddHsbToCssColorInstance().addHsbToCssColor(baseColorString,
                    Integer.parseInt(hueToAdd), Integer.parseInt(saturationToAdd),
                    Integer.parseInt(brightnessToAdd));
        } catch (NumberFormatException e) {
            String message =
                    String.format("Could not parse the integer arguments"
                            + " for the function 'addHsbToCssColor'. The list of arguments was:"
                            + " %s, %s, %s, %s. ", baseColorString, hueToAdd, saturationToAdd,
                            brightnessToAdd);
            throw new GssFunctionException(message);
        } catch (IllegalArgumentException e) {
            String message =
                    String.format("Could not parse the color argument"
                            + " for the function 'addHsbToCssColor'. The list of arguments was:"
                            + " %s, %s, %s, %s. ", baseColorString, hueToAdd, saturationToAdd,
                            brightnessToAdd);
            throw new GssFunctionException(message);
        }
    }

    private static AddHsbToCssColor addHsbToCssColorInstance = null;

    private static AddHsbToCssColor getAddHsbToCssColorInstance() {
        if (addHsbToCssColorInstance == null) {
            addHsbToCssColorInstance = new AddHsbToCssColor();
        }
        return addHsbToCssColorInstance;
    }

    /**
     * Saturate the specified color. First argument is the color, second is the
     * amount of saturation (from 0 to 100)
     */
    public static class Saturate implements GssFunction {
        @Override
        public Integer getNumExpectedArguments() {
            return 2;
        }

        @Override
        public List<CssValueNode> getCallResultNodes(List<CssValueNode> args,
                ErrorManager errorManager) throws GssFunctionException {
            CssValueNode arg1 = args.get(0);
            CssValueNode arg2 = args.get(1);

            if (!(arg1 instanceof CssHexColorNode || arg1 instanceof CssLiteralNode)) {
                String message =
                        "The first argument must be a CssHexColorNode or a CssLiteralNode.";
                errorManager.report(new GssError(message, arg1.getSourceCodeLocation()));
                throw new GssFunctionException(message);
            }
            CssNumericNode numeric2;
            if (arg2 instanceof CssNumericNode) {
                numeric2 = (CssNumericNode) arg2;
            } else {
                String message = "Arguments number 2 must be CssNumericNodes";
                errorManager.report(new GssError(message, arg2.getSourceCodeLocation()));
                throw new GssFunctionException(message);
            }

            try {
                String resultString =
                        addHsbToCssColor(args.get(0).getValue(), "0", numeric2.getNumericPart(),
                                "0");

                CssHexColorNode result =
                        new CssHexColorNode(resultString, arg1.getSourceCodeLocation());
                return ImmutableList.of((CssValueNode) result);
            } catch (GssFunctionException e) {
                errorManager.report(new GssError(e.getMessage(), arg2.getSourceCodeLocation()));
                throw e;
            }
        }

        @Override
        public String getCallResultString(List<String> args) throws GssFunctionException {
            String baseColorString = args.get(0);
            return addHsbToCssColor(baseColorString, "0", args.get(1), "0");
        }
    }

    /**
     * Desaturate the specified color. First argument is the color, second is
     * the amount of desaturation (from 0 to 100)
     */
    public static class Desaturate implements GssFunction {

        @Override
        public Integer getNumExpectedArguments() {
            return 2;
        }

        @Override
        public List<CssValueNode> getCallResultNodes(List<CssValueNode> args,
                ErrorManager errorManager) throws GssFunctionException {
            CssValueNode arg1 = args.get(0);
            CssValueNode arg2 = args.get(1);

            if (!(arg1 instanceof CssHexColorNode || arg1 instanceof CssLiteralNode)) {
                String message =
                        "The first argument must be a CssHexColorNode or a CssLiteralNode.";
                errorManager.report(new GssError(message, arg1.getSourceCodeLocation()));
                throw new GssFunctionException(message);
            }
            CssNumericNode numeric2;
            if (arg2 instanceof CssNumericNode) {
                numeric2 = (CssNumericNode) arg2;
            } else {
                String message = "Arguments number 2 must be CssNumericNodes";
                errorManager.report(new GssError(message, arg2.getSourceCodeLocation()));
                throw new GssFunctionException(message);
            }

            try {
                String resultString =
                        addHsbToCssColor(args.get(0).getValue(), "0", "-"
                                + numeric2.getNumericPart(), "0");

                CssHexColorNode result =
                        new CssHexColorNode(resultString, arg1.getSourceCodeLocation());
                return ImmutableList.of((CssValueNode) result);
            } catch (GssFunctionException e) {
                errorManager.report(new GssError(e.getMessage(), arg2.getSourceCodeLocation()));
                throw e;
            }
        }

        @Override
        public String getCallResultString(List<String> args) throws GssFunctionException {
            String baseColorString = args.get(0);
            return addHsbToCssColor(baseColorString, "0", "-" + args.get(1), "0");
        }
    }

    /**
     * Convert the color to a grayscale (desaturation with amount of 100).
     */
    public static class Grayscale implements GssFunction {
        @Override
        public Integer getNumExpectedArguments() {
            return 1;
        }

        @Override
        public List<CssValueNode> getCallResultNodes(List<CssValueNode> args,
                ErrorManager errorManager) throws GssFunctionException {
            CssValueNode arg1 = args.get(0);

            if (!(arg1 instanceof CssHexColorNode || arg1 instanceof CssLiteralNode)) {
                String message = "The argument must be a CssHexColorNode or a CssLiteralNode.";
                errorManager.report(new GssError(message, arg1.getSourceCodeLocation()));
                throw new GssFunctionException(message);
            }

            try {
                String resultString = addHsbToCssColor(args.get(0).getValue(), "0", "-100", "0");

                CssHexColorNode result =
                        new CssHexColorNode(resultString, arg1.getSourceCodeLocation());
                return ImmutableList.of((CssValueNode) result);
            } catch (GssFunctionException e) {
                errorManager.report(new GssError(e.getMessage(), arg1.getSourceCodeLocation()));
                throw e;
            }
        }

        @Override
        public String getCallResultString(List<String> args) throws GssFunctionException {
            String baseColorString = args.get(0);
            return addHsbToCssColor(baseColorString, "0", "-100", "0");
        }
    }
    
    public static class Spin implements GssFunction {
        @Override
        public Integer getNumExpectedArguments() {
            return 2;
        }

        @Override
        public List<CssValueNode> getCallResultNodes(List<CssValueNode> args,
                ErrorManager errorManager) throws GssFunctionException {
            CssValueNode arg1 = args.get(0);
            CssValueNode arg2 = args.get(1);

            if (!(arg1 instanceof CssHexColorNode || arg1 instanceof CssLiteralNode)) {
                String message =
                        "The first argument must be a CssHexColorNode or a CssLiteralNode.";
                errorManager.report(new GssError(message, arg1.getSourceCodeLocation()));
                throw new GssFunctionException(message);
            }
            CssNumericNode numeric2;
            if (arg2 instanceof CssNumericNode) {
                numeric2 = (CssNumericNode) arg2;
            } else {
                String message = "Arguments number 2 must be CssNumericNodes";
                errorManager.report(new GssError(message, arg2.getSourceCodeLocation()));
                throw new GssFunctionException(message);
            }

            try {
                String resultString = addHsbToCssColor(arg1.getValue(), numeric2.getNumericPart(),
                        "0", "0");

                CssHexColorNode result = new CssHexColorNode(resultString,
                        arg1.getSourceCodeLocation());
                return ImmutableList.of((CssValueNode) result);
            } catch (GssFunctionException e) {
                errorManager.report(new GssError(e.getMessage(), arg2.getSourceCodeLocation()));
                throw e;
            }
        }

        @Override
        public String getCallResultString(List<String> args) throws GssFunctionException {
            String baseColorString = args.get(0);
            String degree = args.get(1);
            return addHsbToCssColor(baseColorString, degree, "0", "0");
        }
    }

    public static class Mix implements GssFunction {

        @Override
        public Integer getNumExpectedArguments() {
            return 3;
        }

        @Override
        public List<CssValueNode> getCallResultNodes(List<CssValueNode> args,
                ErrorManager errorManager) throws GssFunctionException {

            CssValueNode arg1 = args.get(0);
            CssValueNode arg2 = args.get(1);
            CssValueNode arg3 = args.get(2);

            if (!(arg1 instanceof CssHexColorNode || arg1 instanceof CssLiteralNode)) {
                String message =
                        "The first argument must be a CssHexColorNode or a CssLiteralNode.";
                errorManager.report(new GssError(message, arg1.getSourceCodeLocation()));
                throw new GssFunctionException(message);
            }
            if (!(arg2 instanceof CssHexColorNode || arg2 instanceof CssLiteralNode)) {
                String message =
                        "The first argument must be a CssHexColorNode or a CssLiteralNode.";
                errorManager.report(new GssError(message, arg2.getSourceCodeLocation()));
                throw new GssFunctionException(message);
            }
            CssNumericNode numeric3;
            if (arg3 instanceof CssNumericNode) {
                numeric3 = (CssNumericNode) arg3;
            } else {
                String message = "Arguments number 3 must be CssNumericNodes";
                errorManager.report(new GssError(message, arg3.getSourceCodeLocation()));
                throw new GssFunctionException(message);
            }

            try {
                String startColorStr = arg1.getValue();
                String endColorStr = arg2.getValue();
                double percent = Double.parseDouble(numeric3.getNumericPart());

                String resultString = blend(startColorStr, endColorStr, percent);

                CssHexColorNode result =
                        new CssHexColorNode(resultString, arg1.getSourceCodeLocation());
                return ImmutableList.of((CssValueNode) result);
            } catch (NumberFormatException e) {
                String message = "The argument must be double";
                errorManager.report(new GssError(message, arg1.getSourceCodeLocation()));
                throw new GssFunctionException(message);
            }
        }

        @Override
        public String getCallResultString(List<String> args) throws GssFunctionException {
            try {
                double percent = Double.parseDouble(args.get(2));
                return blend(args.get(0), args.get(1), percent);
            } catch (NumberFormatException e) {
                throw new GssFunctionException("The argument must be double", e);
            } catch (IllegalArgumentException e) {
                throw new GssFunctionException("Colors could not be parsed", e);
            }
        }

        private String blend(String startColorStr, String endColorStr, double percent) {
            Color startColor = ColorParser.parseAny(startColorStr);
            Color endColor = ColorParser.parseAny(endColorStr);

            double perc = percent / 100;

            int red = (int) Math.round((startColor.getRed() * perc)
                    + (endColor.getRed() * (1.00 - perc)));
            int green = (int) Math.round((startColor.getGreen() * perc)
                    + (endColor.getGreen() * (1.00 - perc)));
            int blue = (int) Math.round((startColor.getBlue() * perc)
                    + (endColor.getBlue() * (1.00 - perc)));
            Color midColor = new Color(red, green, blue);

            return formatColor(midColor);
        }
    }

    public static String addHslToCssColor(String baseColorString, String hToAdd,
            String sToAdd, String lToAdd) throws GssFunctionException {
        try {
            return addHslToCssColor(baseColorString, Integer.parseInt(hToAdd),
                    Integer.parseInt(sToAdd), Integer.parseInt(lToAdd));
        } catch (NumberFormatException e) {
            String message =
                    String.format("Could not parse the integer arguments"
                            + " for the function 'addHslToCssColor'. The list of arguments was:"
                            + " %s, %s, %s, %s. ", baseColorString, hToAdd, sToAdd, lToAdd);
            throw new GssFunctionException(message);
        } catch (IllegalArgumentException e) {
            String message =
                    String.format("Could not parse the color argument"
                            + " for the function 'addHslToCssColor'. The list of arguments was:"
                            + " %s, %s, %s, %s. ", baseColorString, hToAdd, sToAdd, lToAdd);
            throw new GssFunctionException(message);
        }
    }
    
    public static String addHslToCssColor(String baseColorString, int hToAdd, int sToAdd,
            int lToAdd) {

        // Skip transformation for the transparent color.
        if ("transparent".equals(baseColorString)) {
            return baseColorString;
        }
        
        Color baseColor = ColorParser.parseAny(baseColorString);
        float[] hslValues = RGBtoHSL(baseColor);

        // In HSL color space, Hue goes from 0 to 360, Saturation and Lightness
        // from 0 to 100. However, in Java all three parameters vary from 0.0 to
        // 1.0, so we need some basic conversion.
        hslValues[0] = (float) (hslValues[0] + hToAdd / 360.0);
        // The hue needs to wrap around, so just keep hue - floor(hue).
        hslValues[0] -= (float) Math.floor(hslValues[0]);

        // For saturation and brightness, no wrapping around, we just make sure
        // we don't go over 1.0 or under 0.0
        hslValues[1] = (float) Math.min(1.0, Math.max(0, hslValues[1] + sToAdd / 100.0));
        hslValues[2] = (float) Math.min(1.0, Math.max(0, hslValues[2] + lToAdd / 100.0));

        Color newColor = HSLtoRGB(hslValues);
        return formatColor(newColor);
    }
    
    /**
     * Converts a color from RBG to HSL color space.
     *
     * @param colorRGB the Color.
     * @return color space in HSL.
     */
    public static float[] RGBtoHSL(Color colorRGB) {
        float r, g, b, h, s, l; //this function works with floats between 0 and 1
        r = colorRGB.getRed() / 256.0f;
        g = colorRGB.getGreen() / 256.0f;
        b = colorRGB.getBlue() / 256.0f;

        // Then, minColor and maxColor are defined. Min color is the value of the color component with
        // the smallest value, while maxColor is the value of the color component with the largest value.
        // These two variables are needed because the Lightness is defined as (minColor + maxColor) / 2.

        float maxColor = Math.max(r, Math.max(g, b));
        float minColor = Math.min(r, Math.min(g, b));

        // If minColor equals maxColor, we know that R=G=B and thus the color is a shade of gray.
        // This is a trivial case, hue can be set to anything, saturation has to be set to 0 because
        // only then it's a shade of gray, and lightness is set to R=G=B, the shade of the gray.

        //R == G == B, so it's a shade of gray
        if (r == g && g == b) {
            h = 0.0f; //it doesn't matter what value it has
            s = 0.0f;
            l = r; //doesn't matter if you pick r, g, or b
        }

        // If minColor is not equal to maxColor, we have a real color instead of a shade of gray, so more calculations are needed:

        // Lightness (l) is now set to it's definition of (minColor + maxColor)/2.
        // Saturation (s) is then calculated with a different formula depending if light is in the first half of the second half. This is because the HSL model can be represented as a double cone, the first cone has a black tip and corresponds to the first half of lightness values, the second cone has a white tip and contains the second half of lightness values.
        // Hue (h) is calculated with a different formula depending on which of the 3 color components is the dominating one, and then normalized to a number between 0 and 1.

        else {
            l = (minColor + maxColor) / 2;

            if (l < 0.5) s = (maxColor - minColor) / (maxColor + minColor);
            else s = (maxColor - minColor) / (2.0f - maxColor - minColor);

            if (r == maxColor) h = (g - b) / (maxColor - minColor);
            else if (g == maxColor) h = 2.0f + (b - r) / (maxColor - minColor);
            else h = 4.0f + (r - g) / (maxColor - minColor);

            h /= 6; //to bring it to a number between 0 and 1
            if (h < 0) h++;
        }

        // Finally, H, S and L are calculated out of h, s and l as integers between 0 and 255 and "returned"
        // as the result. Returned, because H, S and L were passed by reference to the function.

        float[] hsl = new float[3];
        hsl[0] = h;
        hsl[1] = s;
        hsl[2] = l;
        return hsl;
    }

    /**
     * Converts from HSL color space to RGB color.
     *
     * @param hsl the hsl values.
     * @return the RGB color.
     */
    public static Color HSLtoRGB(float[] hsl) {
        float r, g, b, h, s, l; //this function works with floats between 0 and 1
        float temp1, temp2, tempr, tempg, tempb;
        h = hsl[0];
        s = hsl[1];
        l = hsl[2];

        // Then follows a trivial case: if the saturation is 0, the color will be a grayscale color,
        // and the calculation is then very simple: r, g and b are all set to the lightness.

        //If saturation is 0, the color is a shade of gray
        if (s == 0) {
            r = g = b = l;
        }
        // If the saturation is higher than 0, more calculations are needed again.
        // red, green and blue are calculated with the formulas defined in the code.
        // If saturation > 0, more complex calculations are needed
        else {
            //Set the temporary values
            if (l < 0.5) temp2 = l * (1 + s);
            else temp2 = (l + s) - (l * s);
            temp1 = 2 * l - temp2;
            tempr = h + 1.0f / 3.0f;
            if (tempr > 1) tempr--;
            tempg = h;
            tempb = h - 1.0f / 3.0f;
            if (tempb < 0) tempb++;

            //Red
            if (tempr < 1.0 / 6.0) r = temp1 + (temp2 - temp1) * 6.0f * tempr;
            else if (tempr < 0.5) r = temp2;
            else if (tempr < 2.0 / 3.0) r = temp1 + (temp2 - temp1) * ((2.0f / 3.0f) - tempr) * 6.0f;
            else r = temp1;

            //Green
            if (tempg < 1.0 / 6.0) g = temp1 + (temp2 - temp1) * 6.0f * tempg;
            else if (tempg < 0.5) g = temp2;
            else if (tempg < 2.0 / 3.0) g = temp1 + (temp2 - temp1) * ((2.0f / 3.0f) - tempg) * 6.0f;
            else g = temp1;

            //Blue
            if (tempb < 1.0 / 6.0) b = temp1 + (temp2 - temp1) * 6.0f * tempb;
            else if (tempb < 0.5) b = temp2;
            else if (tempb < 2.0 / 3.0) b = temp1 + (temp2 - temp1) * ((2.0f / 3.0f) - tempb) * 6.0f;
            else b = temp1;
        }

        // And finally, the results are returned as integers between 0 and 255.
        /*int result = 0;
        result += ((int) (r * 255) & 0xFF) << 16;
        result += ((int) (g * 255) & 0xFF) << 8;
        result += ((int) (b * 255) & 0xFF);*/

        int rResult = Math.round(r * 255.0f);
        int gResult = Math.round(g * 255.0f);
        int bResult = Math.round(b * 255.0f);
        return new Color(rResult, gResult, bResult);
    }
    
    /**
     * Lighten a color. First argument is the color, second is value between 0
     * and 100
     * 
     */
    public static class Lighten implements GssFunction {

        @Override
        public Integer getNumExpectedArguments() {
            return 2;
        }

        @Override
        public List<CssValueNode> getCallResultNodes(List<CssValueNode> args,
                ErrorManager errorManager) throws GssFunctionException {
            CssValueNode arg1 = args.get(0);
            CssValueNode arg2 = args.get(1);

            if (!(arg1 instanceof CssHexColorNode || arg1 instanceof CssLiteralNode)) {
                String message =
                        "The first argument must be a CssHexColorNode or a CssLiteralNode.";
                errorManager.report(new GssError(message, arg1.getSourceCodeLocation()));
                throw new GssFunctionException(message);
            }
            CssNumericNode numeric2;
            if (arg2 instanceof CssNumericNode) {
                numeric2 = (CssNumericNode) arg2;
            } else {
                String message = "Arguments number 2 must be CssNumericNodes";
                errorManager.report(new GssError(message, arg2.getSourceCodeLocation()));
                throw new GssFunctionException(message);
            }

            try {
                String resultString = addHslToCssColor(args.get(0).getValue(), "0", "0",
                        numeric2.getNumericPart());

                CssHexColorNode result = new CssHexColorNode(resultString,
                        arg1.getSourceCodeLocation());
                return ImmutableList.of((CssValueNode) result);
            } catch (GssFunctionException e) {
                errorManager.report(new GssError(e.getMessage(), arg2.getSourceCodeLocation()));
                throw e;
            }
        }

        @Override
        public String getCallResultString(List<String> args) throws GssFunctionException {
            String baseColorString = args.get(0);
            return addHslToCssColor(baseColorString, "0", "0", args.get(1));
        }
    }

    /**
     * Darken a color. First argument is the color, second is value between 0
     * and 100
     * 
     */
    public static class Darken implements GssFunction {
        @Override
        public Integer getNumExpectedArguments() {
            return 2;
        }

        @Override
        public List<CssValueNode> getCallResultNodes(List<CssValueNode> args,
                ErrorManager errorManager) throws GssFunctionException {
            CssValueNode arg1 = args.get(0);
            CssValueNode arg2 = args.get(1);

            if (!(arg1 instanceof CssHexColorNode || arg1 instanceof CssLiteralNode)) {
                String message =
                        "The first argument must be a CssHexColorNode or a CssLiteralNode.";
                errorManager.report(new GssError(message, arg1.getSourceCodeLocation()));
                throw new GssFunctionException(message);
            }
            CssNumericNode numeric2;
            if (arg2 instanceof CssNumericNode) {
                numeric2 = (CssNumericNode) arg2;
            } else {
                String message = "Arguments number 2 must be CssNumericNodes";
                errorManager.report(new GssError(message, arg2.getSourceCodeLocation()));
                throw new GssFunctionException(message);
            }

            try {
                String resultString = addHslToCssColor(arg1.getValue(), "0", "0",
                        "-" + numeric2.getNumericPart());

                CssHexColorNode result = new CssHexColorNode(resultString,
                        arg1.getSourceCodeLocation());
                return ImmutableList.of((CssValueNode) result);
            } catch (GssFunctionException e) {
                errorManager.report(new GssError(e.getMessage(), arg2.getSourceCodeLocation()));
                throw e;
            }
        }

        @Override
        public String getCallResultString(List<String> args) throws GssFunctionException {
            String baseColorString = args.get(0);
            return addHslToCssColor(baseColorString, "0", "0", "-" + args.get(1));
        }
    }

    public static class Argb implements GssFunction {

        @Override
        public Integer getNumExpectedArguments() {
            return 1;
        }

        @Override
        public List<CssValueNode> getCallResultNodes(List<CssValueNode> args,
                ErrorManager errorManager) throws GssFunctionException {
            CssValueNode arg = args.get(0);
            Color color = ColorParser.parseAny(arg.getValue());
            String formatColor = formatColor(color);
            String alpha = String.format("#%02X", color.getAlpha());
            CssLiteralNode result =
                    new CssLiteralNode(alpha + formatColor.substring(1), arg
                            .getSourceCodeLocation());
            return ImmutableList.of((CssValueNode) result);
        }

        @Override
        public String getCallResultString(List<String> args) throws GssFunctionException {
            Color color = ColorParser.parseAny(args.get(0));
            String formatColor = formatColor(color);
            String alpha = String.format("#%02X", color.getAlpha());
            return alpha + formatColor.substring(1);
        }
    }

    public static class Percentage implements GssFunction {

        @Override
        public Integer getNumExpectedArguments() {
            return 1;
        }

        @Override
        public List<CssValueNode> getCallResultNodes(List<CssValueNode> args,
                ErrorManager errorManager) throws GssFunctionException {
            CssValueNode arg1 = args.get(0);
            CssNumericNode numeric1;
            if (arg1 instanceof CssNumericNode) {
                numeric1 = (CssNumericNode) arg1;
            } else {
                String message = "The argument must be a CssNumericNode";
                errorManager.report(new GssError(message, arg1.getSourceCodeLocation()));
                throw new GssFunctionException(message);
            }
            try {
                double double1 = Double.parseDouble(numeric1.getNumericPart());
                CssNumericNode result =
                        new CssNumericNode((double1 * 100) + "", "%", arg1.getSourceCodeLocation());
                return ImmutableList.of((CssValueNode) result);
            } catch (NumberFormatException e) {
                String message = "The argument must be double";
                errorManager.report(new GssError(message, arg1.getSourceCodeLocation()));
                throw new GssFunctionException(message);
            }
        }

        @Override
        public String getCallResultString(List<String> args) throws GssFunctionException {
            try {
                double double1 = Double.parseDouble(args.get(0));
                return (double1 * 100) + "%";
            } catch (NumberFormatException e) {
                String message = "The argument must be double";
                throw new GssFunctionException(message);
            }
        }
    }

    public static class RemoveUnit implements GssFunction {

        @Override
        public Integer getNumExpectedArguments() {
            return 1;
        }

        @Override
        public List<CssValueNode> getCallResultNodes(List<CssValueNode> args,
                ErrorManager errorManager) throws GssFunctionException {

            CssValueNode arg1 = args.get(0);

            CssNumericNode numeric1;
            if (arg1 instanceof CssNumericNode) {
                numeric1 = (CssNumericNode) arg1;
            } else {
                String message = "The argument must be a CssNumericNode";
                errorManager.report(new GssError(message, arg1.getSourceCodeLocation()));
                throw new GssFunctionException(message);
            }

            CssNumericNode result =
                    new CssNumericNode(numeric1.getNumericPart(), CssNumericNode.NO_UNITS, arg1
                            .getSourceCodeLocation());
            return ImmutableList.of((CssValueNode) result);
        }

        @Override
        public String getCallResultString(List<String> args) throws GssFunctionException {
            String value = args.get(0);
            int length = value.length();

            String unit = value.substring(length - 2, length);
            if (unit.equals("px") || unit.equals("em")) {
                return value.substring(0, length - 2);
            }

            if (value.substring(length - 1, length).equals("%")) {
                return value.substring(0, length - 1);
            }

            return value;
        }
    }

    public static class Concat implements GssFunction {

        @Override
        public Integer getNumExpectedArguments() {
            // Takes a variable number of arguments.
            return null;
        }

        @Override
        public List<CssValueNode> getCallResultNodes(List<CssValueNode> args,
                ErrorManager errorManager) throws GssFunctionException {
            String resultString = "";
            for (CssValueNode arg : args) {
                if (arg instanceof CssNumericNode) {
                    CssNumericNode numericNode = (CssNumericNode) arg;
                    resultString += numericNode.getNumericPart() + numericNode.getUnit();
                } else if (arg instanceof CssStringNode) {
                    resultString += ((CssStringNode) arg).getConcreteValue();
                } else {
                    resultString += arg.getValue();
                }
            }
            CssLiteralNode result =
                    new CssLiteralNode(resultString, args.get(0).getSourceCodeLocation());
            return ImmutableList.of((CssValueNode) result);
        }

        @Override
        public String getCallResultString(List<String> args) throws GssFunctionException {
            String resultString = "";
            for (String arg : args) {
                resultString += arg;
            }
            return resultString;
        }

    }
}
