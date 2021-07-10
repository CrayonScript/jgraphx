package com.mxgraph.crayonscript.shapes;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.model.DropFlagEnum;
import com.mxgraph.shape.mxIShape;
import com.mxgraph.view.mxCellState;

import java.awt.*;
import java.util.ArrayList;

public interface CrayonScriptIShape extends mxIShape {
    /**
     *
     */
    void paintShape(mxGraphics2DCanvas canvas, mxCellState state);

    ArrayList<CrayonScriptBasicShape.SvgElement> getSvgElements();

    ArrayList<CrayonScriptBasicShape.SvgElement> getHotspotSvgElements();

    int getSubElements();

    Color getFrameColor();

    Color getDropFlagColor(DropFlagEnum dropFlag);

    boolean isExtender();
}
