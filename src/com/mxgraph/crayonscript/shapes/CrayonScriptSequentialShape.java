package com.mxgraph.crayonscript.shapes;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.model.CellPaintMode;
import com.mxgraph.view.mxCellState;

import java.awt.*;
import java.util.ArrayList;

public class CrayonScriptSequentialShape extends CrayonScriptBasicShape {

    public CrayonScriptSequentialShape(String shapeName) {
        super(ShapeStructureType.SEQUENTIAL, shapeName);
    }

    @Override
    public void paintShape(mxGraphics2DCanvas canvas, mxCellState state) {

        initialize(state);

        ArrayList<SvgElement> svgElements = getSvgElements();

        SvgElement first = svgElements.get(0);
        SvgElement second = svgElements.get(1);
        SvgElement third = svgElements.get(2);

        CellPaintMode paintMode = state.getPaintMode();

        currentRoundRectangles = new ArrayList<>();
        currentRoundRectangles.add(scaleRectangle(state, first, first, paintMode));
        currentRoundRectangles.add(scaleRectangle(state, first, second, paintMode));
        currentRoundRectangles.add(scaleRectangle(state, first, third, paintMode));

        paintRectangle(canvas, currentRoundRectangles.get(0), getColor(first.fillColor), paintMode, true);
        paintRectangle(canvas, currentRoundRectangles.get(1), getColor(second.fillColor), paintMode);
        paintRectangle(canvas, currentRoundRectangles.get(2), getColor(third.fillColor), paintMode);
    }
}
