package com.mxgraph.crayonscript.shapes;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.view.mxCellState;

import java.awt.*;
import java.util.ArrayList;

public class CrayonScriptParallelShape extends CrayonScriptBasicShape {

    public CrayonScriptParallelShape() {
        super();
    }

    @Override
    public void paintShape(mxGraphics2DCanvas canvas, mxCellState state) {

        initialize();

        Rectangle stateRect = state.getRectangle();
        ArrayList<SvgElement> svgElements = svgElementsMap.get(ShapeStructureType.VERTICAL2);

        SvgElement first = svgElements.get(0);
        SvgElement second = svgElements.get(1);
        SvgElement third = svgElements.get(2);

        paintRectangle(canvas, scaleRectangle(stateRect, first, first), ColorCode.JAZZ_BERRY_JAM.color, true);
        paintRectangle(canvas, scaleRectangle(stateRect, first, second), second.fillColor);
        paintRectangle(canvas, scaleRectangle(stateRect, first, third), third.fillColor);
    }
}
