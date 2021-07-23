package com.mxgraph.crayonscript.shapes;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.model.CellPaintMode;
import com.mxgraph.view.mxCellState;

import java.awt.*;
import java.util.ArrayList;

public class CrayonScriptGraphTemplateShape extends CrayonScriptBasicShape {

    public CrayonScriptGraphTemplateShape(String shapeName) {
        super(ShapeStructureType.TEMPLATE, shapeName);
    }

    @Override
    public void paintShape(mxGraphics2DCanvas canvas, mxCellState state) {

        initialize(state);

        Rectangle stateRect = state.getRectangle();
        ArrayList<SvgElement> svgElements = svgElementsMap.get(ShapeStructureType.TEMPLATE).get(CellPaintMode.DEFAULT);

        SvgElement first = svgElements.get(0);

        CellPaintMode paintMode = state.getPaintMode();

        paintRectangle(canvas, scaleRectangle(stateRect, first, first), first.fillColor /* always opaque */, paintMode,true);
    }
}
