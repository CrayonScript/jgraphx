package com.mxgraph.crayonscript.shapes;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.model.CellPaintMode;
import com.mxgraph.view.mxCellState;

import java.awt.*;
import java.util.ArrayList;

public class CrayonScriptForShape extends CrayonScriptBasicShape {

    public CrayonScriptForShape(String shapeName) {
        super(ShapeStructureType.FOR, shapeName);
    }

    @Override
    public void paintShape(mxGraphics2DCanvas canvas, mxCellState state) {

        initialize(state);

        ArrayList<SvgElement> svgElements = getSvgElements();

        SvgElement first = svgElements.get(0);
        SvgElement second = svgElements.get(1);
        SvgElement third = svgElements.get(2);

        CellPaintMode paintMode = state.getPaintMode();

        paintRectangle(canvas, scaleRectangle(state, first, first), getColor(first.fillColor), paintMode, true);
        paintRectangle(canvas, scaleRectangle(state, first, second), getColor(second.fillColor), paintMode);
        paintRectangle(canvas, scaleRectangle(state, first, third), getColor(third.fillColor), paintMode);
    }
}
