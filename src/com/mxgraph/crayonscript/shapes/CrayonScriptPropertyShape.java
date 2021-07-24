package com.mxgraph.crayonscript.shapes;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.model.CellFrameEnum;
import com.mxgraph.model.CellPaintMode;
import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxCellState;

import java.awt.*;
import java.util.ArrayList;

public class CrayonScriptPropertyShape extends CrayonScriptBasicShape {

    public CrayonScriptPropertyShape(String shapeName) {
        super(ShapeStructureType.PROPERTY, shapeName);
    }

    @Override
    public void paintShape(mxGraphics2DCanvas canvas, mxCellState state) {

        initialize(state);

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

        CellPaintMode paintMode = state.getPaintMode();

        currentRoundRectangles = new ArrayList<>();
        currentRoundRectangles.add(scaleRectangle(state, first, first, paintMode));
        currentRoundRectangles.add(scaleRectangle(state, first, second, paintMode));

        paintRectangle(canvas, currentRoundRectangles.get(0), paintedFirstColor, paintMode);
        paintRectangle(canvas, currentRoundRectangles.get(1), paintedSecondColor, paintMode);

        drawText(canvas, ((mxCell) state.getCell()).getText(), state);
    }
}
