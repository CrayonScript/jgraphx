package com.mxgraph.crayonscript.shapes;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.shape.mxIShape;
import com.mxgraph.view.mxCellState;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public interface CrayonScriptIShape extends mxIShape {
    /**
     *
     */
    void paintShape(mxGraphics2DCanvas canvas, mxCellState state);

    ArrayList<CrayonScriptBasicShape.SvgElement> getSvgElements();

    int getSubElements();

    Color getFrameColor();

    boolean isExtender();
}
