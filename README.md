## A fork of closure-stylesheets with additional custom functions ##

## Custom functions ##

* saturate
* desaturate
* grayscale
* lighten
* darken
* mix
* spin
* argb
* percentage
* removeUnit
* concat

## Additional files ##

* src: com.google.common.css.compiler.gssfunctions.CustomGssFunctionMapProvider.java
* src: com.google.common.css.compiler.gssfunctions.CustomGssFunctions.java
* test: com.google.common.css.compiler.gssfunctions.CustomGssFunctionMapProviderTest.java
* test: com.google.common.css.compiler.gssfunctions.CustomGssFunctionsTest.java

## Custom Command Line ##

```bash
java -jar closure-stylesheets.jar \
    --gss-function-map-provider com.google.common.css.compiler.gssfunctions.CustomGssFunctionMapProvider \
    --output-file [output.css] \
    [input.gss]
```

## Credits ##

https://code.google.com/p/closure-stylesheets/
