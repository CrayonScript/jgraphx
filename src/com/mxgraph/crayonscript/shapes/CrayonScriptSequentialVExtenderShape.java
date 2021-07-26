package com.mxgraph.crayonscript.shapes;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.model.CellPaintMode;
import com.mxgraph.view.mxCellState;

import java.awt.*;
import java.util.ArrayList;

public class CrayonScriptSequentialVExtenderShape extends CrayonScriptBasicShape {

    public CrayonScriptSequentialVExtenderShape(String shapeName) {
        super(ShapeStructureType.SEQUENTIAL_VEXTENDER, shapeName);
    }

    public boolean isExtender() { return true; }

    @Override
    public void paintShape(mxGraphics2DCanvas canvas, mxCellState state) {

        initialize(state);

        CellPaintMode paintMode = state.getPaintMode();

        paintRectangle(canvas, state, 0, getColor(currentColors.get(0)), paintMode,true, true);
        paintRectangle(canvas, state, 1, getColor(currentColors.get(1)), paintMode);
        paintRectangle(canvas, state, 2, getColor(currentColors.get(2)), paintMode);
    }
}
