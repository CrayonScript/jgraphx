package com.mxgraph.crayonscript.shapes;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.view.mxCellState;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class CrayonScriptParallelShape extends CrayonScriptBasicShape {

    @Override
    public Shape createShape(mxGraphics2DCanvas canvas, mxCellState state) {
        Shape shape = new RoundRectangle2D.Double();
        return shape;
    }
}
