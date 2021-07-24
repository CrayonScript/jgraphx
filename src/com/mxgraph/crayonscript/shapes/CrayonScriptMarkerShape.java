package com.mxgraph.crayonscript.shapes;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.model.CellPaintMode;
import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxCellState;

import java.awt.*;
import java.util.ArrayList;

public class CrayonScriptMarkerShape extends CrayonScriptBasicShape {

    public CrayonScriptMarkerShape(String shapeName) {
        super(ShapeStructureType.MARKER, shapeName);
    }

    @Override
    public void paintShape(mxGraphics2DCanvas canvas, mxCellState state) {

        initialize(state);

        ArrayList<SvgElement> svgElements = getSvgElements();

        SvgElement first = svgElements.get(0);

        CellPaintMode paintMode = state.getPaintMode();

        currentRoundRectangles = new ArrayList<>();
        currentRoundRectangles.add(scaleRectangle(state, first, first, paintMode));

        paintRectangle(canvas, currentRoundRectangles.get(0), ((mxCell) state.getCell()).markerColor, paintMode);
    }
}
