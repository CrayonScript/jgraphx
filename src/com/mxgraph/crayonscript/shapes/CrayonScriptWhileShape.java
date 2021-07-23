package com.mxgraph.crayonscript.shapes;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.model.CellPaintMode;
import com.mxgraph.view.mxCellState;

import java.awt.*;
import java.util.ArrayList;

public class CrayonScriptWhileShape extends CrayonScriptBasicShape {

    public CrayonScriptWhileShape(String shapeName) {
        super(ShapeStructureType.WHILE, shapeName);
    }

    @Override
    public void paintShape(mxGraphics2DCanvas canvas, mxCellState state) {

        initialize(state);

        Rectangle stateRect = state.getRectangle();
        ArrayList<SvgElement> svgElements = getSvgElements();

        SvgElement first = svgElements.get(0);
        SvgElement second = svgElements.get(1);
        SvgElement third = svgElements.get(2);

        CellPaintMode paintMode = state.getPaintMode();

        paintRectangle(canvas, scaleRectangle(stateRect, first, first), getColor(first.fillColor), paintMode, true);
        paintRectangle(canvas, scaleRectangle(stateRect, first, second), getColor(second.fillColor), paintMode);
        paintRectangle(canvas, scaleRectangle(stateRect, first, third), getColor(third.fillColor), paintMode);
    }
}
