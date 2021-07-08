package com.mxgraph.crayonscript.shapes;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.model.DropFlag;
import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxCellState;

import java.awt.*;
import java.util.ArrayList;

public class CrayonScriptExpressionShape extends CrayonScriptBasicShape {

    public CrayonScriptExpressionShape() {
        super(ShapeStructureType.HEXTENDER2);
    }

    @Override
    public void paintShape(mxGraphics2DCanvas canvas, mxCellState state) {

        initialize(state);

        Rectangle stateRect = state.getRectangle();
        ArrayList<SvgElement> svgElements = svgElementsMap.get(ShapeStructureType.HEXTENDER2);

        SvgElement first = svgElements.get(0);
        SvgElement second = svgElements.get(1);
        SvgElement third = svgElements.get(2);

        Color frameColor = getParentFrameColor(state);

        Color secondColor = second.fillColor;
        Color thirdColor = third.fillColor;

        DropFlag snapToParentDropFlag = ((mxCell) state.getCell()).snapToParentDropFlag;
        if (snapToParentDropFlag != null && ((mxCell) state.getCell()).getParent().isShape())
        {
            secondColor = ((mxCell) ((mxCell) state.getCell()).getParent()).referenceShape.getDropFlagColor(snapToParentDropFlag);
        }

        paintRectangle(canvas, scaleRectangle(stateRect, first, first), getColor(frameColor));
        paintRectangle(canvas, scaleRectangle(stateRect, first, second), getColor(secondColor));
        paintRectangle(canvas, scaleRectangle(stateRect, first, third), getColor(thirdColor));

        drawText(canvas, ((mxCell) state.getCell()).getOperatorValue(), state);
    }
}
