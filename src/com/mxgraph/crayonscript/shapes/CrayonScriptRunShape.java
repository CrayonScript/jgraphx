package com.mxgraph.crayonscript.shapes;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.model.CellPaintMode;
import com.mxgraph.view.mxCellState;

import java.awt.*;
import java.util.ArrayList;

public class CrayonScriptRunShape extends CrayonScriptBasicShape {

    public CrayonScriptRunShape(String shapeName) {
        super(ShapeStructureType.RUN, shapeName);
    }

    @Override
    public void paintShape(mxGraphics2DCanvas canvas, mxCellState state) {

        initialize(state);

        ArrayList<SvgElement> svgElements = getSvgElements();

        SvgElement first = svgElements.get(0);
        SvgElement second = svgElements.get(1);

        CellPaintMode paintMode = state.getPaintMode();

        currentRoundRectangles = new ArrayList<>();
        currentRoundRectangles.add(scaleRectangle(state, first, first, paintMode));
        currentRoundRectangles.add(scaleRectangle(state, first, second, paintMode));

        paintRectangle(canvas, currentRoundRectangles.get(0), getColor(first.fillColor), paintMode, true);
        paintRectangle(canvas, currentRoundRectangles.get(1), getColor(second.fillColor), paintMode);
    }
}
