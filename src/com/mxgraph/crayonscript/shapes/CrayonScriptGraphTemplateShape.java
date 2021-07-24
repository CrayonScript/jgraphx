package com.mxgraph.crayonscript.shapes;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.model.CellPaintMode;
import com.mxgraph.view.mxCellState;

import java.util.ArrayList;

public class CrayonScriptGraphTemplateShape extends CrayonScriptBasicShape {

    public CrayonScriptGraphTemplateShape(String shapeName) {
        super(ShapeStructureType.TEMPLATE, shapeName);
    }

    @Override
    public void paintShape(mxGraphics2DCanvas canvas, mxCellState state) {

        initialize(state);

        CellPaintMode paintMode = state.getPaintMode();

        paintRectangle(canvas, currentRoundRectangles.get(0), currentColors.get(0) /* always opaque */, paintMode,true);
    }
}
