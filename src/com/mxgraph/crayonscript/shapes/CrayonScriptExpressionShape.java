package com.mxgraph.crayonscript.shapes;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.model.CellFrameEnum;
import com.mxgraph.model.CellPaintMode;
import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxCellState;

import java.awt.*;
import java.util.ArrayList;

public class CrayonScriptExpressionShape extends CrayonScriptBasicShape {

    public CrayonScriptExpressionShape(String shapeName) {
        super(ShapeStructureType.EXPRESSION, shapeName);
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

        CellFrameEnum snapToParentDropFlag = ((mxCell) state.getCell()).snapToParentDropFlag;
        if (snapToParentDropFlag != null && ((mxCell) state.getCell()).getParent().isShape())
        {
            secondColor = ((mxCell) ((mxCell) state.getCell()).getParent()).referenceShape.getFrameColor(snapToParentDropFlag);
        }

        CellPaintMode paintMode = state.getPaintMode();

        paintRectangle(canvas, scaleRectangle(state, first, first, paintMode), getColor(frameColor), paintMode);
        paintRectangle(canvas, scaleRectangle(state, first, second, paintMode), getColor(secondColor), paintMode);

        drawText(canvas, ((mxCell) state.getCell()).getText(), state);
    }
}
