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

        Color frameColor = getParentFrameColor(state);

        Color secondColor = currentColors.get(1);

        CellFrameEnum snapToParentDropFlag = ((mxCell) state.getCell()).snapToParentDropFlag;
        if (snapToParentDropFlag != null && ((mxCell) state.getCell()).getParent().isShape())
        {
            secondColor = ((mxCell) ((mxCell) state.getCell()).getParent()).referenceShape.getFrameColor(snapToParentDropFlag);
        }

        CellPaintMode paintMode = state.getPaintMode();

        paintRectangle(canvas, state, 0, getColor(frameColor), paintMode);
        paintRectangle(canvas, state, 1, getColor(secondColor), paintMode);

        drawText(canvas, ((mxCell) state.getCell()).getText(), state);
    }
}
