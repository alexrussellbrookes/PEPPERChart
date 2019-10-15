package uk.ac.brookes.alexrussell.chartinglibrary;

/**
 * Custom text.
 */
public class ChartText extends ChartMark2D {

    private int fontSize;
    private String text;
    private String fontFamily = null;

    /**
     * Constructs a new item of text to be added to a chart.
     * @param color the text's colour. The String should conform to a valid CSS colour specification.
     * @param chartPoint the point identifying the top left-hand corner of the text.
     * @param fontSize the font size of the text.
     * @param text the content of the text.
     */
    public ChartText(String color, ChartPoint chartPoint, int fontSize, String text) {
        super(color, chartPoint);
        this.fontSize = fontSize;
        this.text = text;
    }

    @Override
    String getAdditionalSpec() {
        StringBuilder returnString = new StringBuilder();
        if (fontFamily != null) {
            returnString.append(".attr(\"font-family\", \"").append(fontFamily).append("\")\n");
        }
        returnString.append(".attr(\"font-size\", ").append(fontSize).append(")\n")
                .append(".text(\"").append(text).append("\")");
        return returnString.toString();
    }

    @Override
    String getDimensionName() {
        return "";
    }

    @Override
    double getDimensionValue() {
        return 0;
    }

    @Override
    String getObjectName() {
        return "textObject";
    }

    @Override
    String getObjectType() {
        return "text";
    }

    @Override
    String getXAttribute() {
        return "x";
    }

    @Override
    String getYAttribute() {
        return "y";
    }

    /**
     * Sets the text's font family.
     * @param fontFamily String should be one of the CSS font families, e.g. sans-serif.
     *                   See <a href="https://developer.mozilla.org/en-US/docs/Web/CSS/font-family">
     *                       https://developer.mozilla.org/en-US/docs/Web/CSS/font-family</a>
     */
    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }
}
