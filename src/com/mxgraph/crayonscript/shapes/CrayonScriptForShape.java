package com.mxgraph.crayonscript.shapes;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.model.CellPaintMode;
import com.mxgraph.view.mxCellState;

import java.awt.*;
import java.util.ArrayList;

public class CrayonScriptForShape extends CrayonScriptBasicShape {

    public CrayonScriptForShape(String shapeName) {
        super(ShapeStructureType.FOR, shapeName);
    }

    @Override
    public void paintShape(mxGraphics2DCanvas canvas, mxCellState state) {

        initialize(state);

        CellPaintMode paintMode = state.getPaintMode();

        paintRectangle(canvas, currentRoundRectangles.get(0), getColor(currentColors.get(0)), paintMode, true);
        paintRectangle(canvas, currentRoundRectangles.get(1), getColor(currentColors.get(1)), paintMode);
        paintRectangle(canvas, currentRoundRectangles.get(2), getColor(currentColors.get(2)), paintMode);
    }
}
