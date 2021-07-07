package com.mxgraph.crayonscript.shapes;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxCellState;

import java.awt.*;
import java.util.ArrayList;

public class CrayonScriptMarkerShape extends CrayonScriptBasicShape {

    public CrayonScriptMarkerShape() {
        super(ShapeStructureType.MARKER);
    }

    @Override
    public void paintShape(mxGraphics2DCanvas canvas, mxCellState state) {

        initialize(state);

        Rectangle stateRect = state.getRectangle();
        ArrayList<SvgElement> svgElements = getSvgElements();

        SvgElement first = svgElements.get(0);

        paintRectangle(canvas, scaleRectangle(stateRect, first, first), ((mxCell) state.getCell()).markerColor);
    }
}
