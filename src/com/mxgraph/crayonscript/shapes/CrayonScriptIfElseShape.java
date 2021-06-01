package com.mxgraph.crayonscript.shapes;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.view.mxCellState;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class CrayonScriptIfElseShape extends CrayonScriptBasicShape {

    public CrayonScriptIfElseShape() {
        super();
    }

    @Override
    public void paintShape(mxGraphics2DCanvas canvas, mxCellState state) {

        initialize();

        Rectangle stateRect = state.getRectangle();
        ArrayList<SvgElement> svgElements = svgElementsMap.get(ShapeStructureType.VERTICAL3);

        SvgElement first = svgElements.get(0);
        SvgElement second = svgElements.get(1);
        SvgElement third = svgElements.get(2);
        SvgElement fourth = svgElements.get(3);

        RoundRectangle2D firstRect = scaleRectangle(stateRect, first, first);
        RoundRectangle2D secondRect = scaleRectangle(stateRect, first, second);
        RoundRectangle2D thirdRect = scaleRectangle(stateRect, first, third);
        RoundRectangle2D fourthRect = scaleRectangle(stateRect, first, fourth);

        paintRectangle(canvas, firstRect, first.fillColor);
        paintRectangle(canvas, secondRect, second.fillColor);
        paintRectangle(canvas, thirdRect, third.fillColor);
        paintRectangle(canvas, fourthRect, fourth.fillColor);
    }
}
