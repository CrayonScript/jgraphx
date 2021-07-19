package com.mxgraph.crayonscript.view;

import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxStylesheet;

import java.util.Hashtable;
import java.util.Map;

public class CrayonScriptStylesheet extends mxStylesheet {

    public CrayonScriptStylesheet() {
        super();
        setDefaultCrayonScriptStyle(createDefaultCrayonScriptStyle());
    }

    /**
     * Creates and returns the default crayonscript style.
     *
     * @return Returns the default crayonscript style.
     */
    protected Map<String, Object> createDefaultCrayonScriptStyle()
    {
        Map<String, Object> style = new Hashtable<String, Object>();

        style.put(mxConstants.STYLE_SHAPE, mxConstants.CRAYONSCRIPT_SHAPE_RUN_PARALLEL);
        style.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC);
        style.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
        style.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
        style.put(mxConstants.STYLE_STROKECOLOR, "#6482B9");
        style.put(mxConstants.STYLE_FONTCOLOR, "#446299");

        return style;
    }

    /**
     * Sets the default style for crayonscript blocks.
     *
     * @param value Style to be used for crayonscript blocks.
     */
    public void setDefaultCrayonScriptStyle(Map<String, Object> value)
    {
        putCellStyle("defaultCrayonScript", value);
    }
}
