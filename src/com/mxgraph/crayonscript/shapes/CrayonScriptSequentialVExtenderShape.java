package com.mxgraph.crayonscript.shapes;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.view.mxCellState;

import java.awt.*;
import java.util.ArrayList;

public class CrayonScriptSequentialVExtenderShape extends CrayonScriptBasicShape {

    public CrayonScriptSequentialVExtenderShape() {
        super(ShapeStructureType.SEQUENTIAL_VEXTENDER2);
    }

    @Override
    public void paintShape(mxGraphics2DCanvas canvas, mxCellState state) {

        initialize(state);

        Rectangle stateRect = state.getRectangle();
        ArrayList<SvgElement> svgElements = svgElementsMap.get(ShapeStructureType.SEQUENTIAL_VEXTENDER2);

        SvgElement first = svgElements.get(0);
        SvgElement second = svgElements.get(1);
        SvgElement third = svgElements.get(2);

        paintRectangle(canvas, scaleRectangle(stateRect, first, first), getColor(first.fillColor), true);
        paintRectangle(canvas, scaleRectangle(stateRect, first, second), getColor(second.fillColor));
        paintRectangle(canvas, scaleRectangle(stateRect, first, third), getColor(third.fillColor));
    }
}