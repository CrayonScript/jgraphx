package com.mxgraph.crayonscript.shapes;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.model.CellFrameEnum;
import com.mxgraph.model.CellPaintMode;
import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxCellState;

import java.awt.*;
import java.util.ArrayList;

public class CrayonScriptEventShape extends CrayonScriptBasicShape {

    public CrayonScriptEventShape(String shapeName) {
        super(ShapeStructureType.EVENT, shapeName);
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
        if (snapToParentDropFlag != null && ((mxCell) ((mxCell) state.getCell()).getParent()).isShape())
        {
            secondColor = ((mxCell) ((mxCell) state.getCell()).getParent()).referenceShape.getFrameColor(snapToParentDropFlag);
        }

        Color paintedFirstColor = getColor(frameColor);
        Color paintedSecondColor = getColor(secondColor);

        paintedFrameColor = paintedFirstColor;

        CellPaintMode paintMode = state.getPaintMode();

        paintRectangle(canvas, scaleRectangle(stateRect, first, first), paintedFirstColor, paintMode);
        paintRectangle(canvas, scaleRectangle(stateRect, first, second), paintedSecondColor, paintMode);

        drawText(canvas, ((mxCell) state.getCell()).getText(), state);
    }
}
