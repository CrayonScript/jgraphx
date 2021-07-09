package com.mxgraph.crayonscript.shapes;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.model.DropFlag;
import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxCellState;

import java.awt.*;
import java.util.ArrayList;

public class CrayonScriptAssignShape extends CrayonScriptBasicShape {

    public CrayonScriptAssignShape() {
        super(ShapeStructureType.ASSIGN);
    }

    @Override
    public void paintShape(mxGraphics2DCanvas canvas, mxCellState state) {

        initialize(state);

        Rectangle stateRect = state.getRectangle();
        ArrayList<SvgElement> svgElements = getSvgElements();

        SvgElement first = svgElements.get(0);
        SvgElement second = svgElements.get(1);

        Color frameColor = getParentFrameColor(state);

        Color secondColor = second.fillColor;

        DropFlag snapToParentDropFlag = ((mxCell) state.getCell()).snapToParentDropFlag;
        if (snapToParentDropFlag != null && ((mxCell) ((mxCell) state.getCell()).getParent()).isShape())
        {
            secondColor = ((mxCell) ((mxCell) state.getCell()).getParent()).referenceShape.getDropFlagColor(snapToParentDropFlag);
        }

        paintRectangle(canvas, scaleRectangle(stateRect, first, first), getColor(frameColor));
        paintRectangle(canvas, scaleRectangle(stateRect, first, second), getColor(secondColor));
    }
}
