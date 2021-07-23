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

        ArrayList<SvgElement> svgElements = svgElementsMap.get(ShapeStructureType.TEMPLATE);

        SvgElement first = svgElements.get(0);

        CellPaintMode paintMode = state.getPaintMode();

        paintRectangle(canvas, scaleRectangle(state, first, first), first.fillColor /* always opaque */, paintMode,true);
    }
}
