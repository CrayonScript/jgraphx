package com.mxgraph.crayonscript.shapes;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.view.mxCellState;

import java.awt.*;
import java.util.ArrayList;

public class CrayonScriptFunctionShape extends CrayonScriptBasicShape {

    private String name;

    private int argCount;

    public CrayonScriptFunctionShape(String name, int argCount) {
        super(ShapeStructureType.VERTICAL2);
        this.name = name;
        this.argCount = argCount;
    }

    @Override
    public void paintShape(mxGraphics2DCanvas canvas, mxCellState state) {

        initialize(state);

        Rectangle stateRect = state.getRectangle();
        ArrayList<SvgElement> svgElements = getSvgElements();

        SvgElement first = svgElements.get(0);
        SvgElement second = svgElements.get(1);
        SvgElement third = svgElements.get(2);

        paintRectangle(canvas, scaleRectangle(stateRect, first, first), getColor(first.fillColor), true);
        paintRectangle(canvas, scaleRectangle(stateRect, first, second), getColor(second.fillColor));
        paintRectangle(canvas, scaleRectangle(stateRect, first, third), getColor(third.fillColor));
    }
}
