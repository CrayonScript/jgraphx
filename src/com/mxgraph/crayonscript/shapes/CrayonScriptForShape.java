package com.mxgraph.crayonscript.shapes;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.view.mxCellState;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class CrayonScriptForShape extends CrayonScriptBasicShape {

    public CrayonScriptForShape() {
        super();
    }

    @Override
    public void paintShape(mxGraphics2DCanvas canvas, mxCellState state) {

        initialize();

        Rectangle stateRect = state.getRectangle();
        ArrayList<SvgElement> svgElements = svgElementsMap.get(ShapeStructureType.VERTICAL3);

        SvgElement first = svgElements.get(0);
        SvgElement second = svgElements.get(1);
        SvgElement third = svgElements.get(2);

        paintRectangle(canvas, scaleRectangle(stateRect, first, first), first.fillColor);
        paintRectangle(canvas, scaleRectangle(stateRect, first, second), second.fillColor);
        paintRectangle(canvas, scaleRectangle(stateRect, first, third), third.fillColor);
    }
}