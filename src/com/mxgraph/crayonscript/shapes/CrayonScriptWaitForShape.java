package com.mxgraph.crayonscript.shapes;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.model.CellPaintMode;
import com.mxgraph.view.mxCellState;

import java.awt.*;
import java.util.ArrayList;

public class CrayonScriptWaitForShape extends CrayonScriptBasicShape {

    public CrayonScriptWaitForShape(String shapeName) {
        super(ShapeStructureType.WAIT_FOR, shapeName);
    }

    @Override
    public void paintShape(mxGraphics2DCanvas canvas, mxCellState state) {

        initialize(state);

        ArrayList<SvgElement> svgElements = getSvgElements();

        SvgElement first = svgElements.get(0);
        SvgElement second = svgElements.get(1);

        CellPaintMode paintMode = state.getPaintMode();

        paintRectangle(canvas, scaleRectangle(state, first, first, paintMode), getColor(first.fillColor), paintMode, true);
        paintRectangle(canvas, scaleRectangle(state, first, second, paintMode), getColor(second.fillColor), paintMode);
    }
}
