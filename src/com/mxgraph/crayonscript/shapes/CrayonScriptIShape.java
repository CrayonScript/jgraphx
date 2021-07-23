package com.mxgraph.crayonscript.shapes;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.model.CellFrameEnum;
import com.mxgraph.model.CellPaintMode;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.shape.mxIShape;
import com.mxgraph.view.mxCellState;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public interface CrayonScriptIShape extends mxIShape {
    /**
     *
     */
    void paintShape(mxGraphics2DCanvas canvas, mxCellState state);

    ArrayList<CrayonScriptBasicShape.SvgElement> getSvgElements();

    ArrayList<CrayonScriptBasicShape.SvgElement> getHotspotSvgElements();

    int getSubElements();

    int getOpacity();

    Color getPaintedFrameColor();

    Color getFrameColor();

    Color getFrameColor(CellFrameEnum frameEnum);

    RoundRectangle2D getFrame(CellFrameEnum frameEnum);

    boolean isExtender();

    void setPaintMode(CellPaintMode paintMode);

    CellPaintMode getPaintMode();
}
