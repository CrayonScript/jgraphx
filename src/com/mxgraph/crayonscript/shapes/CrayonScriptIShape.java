package com.mxgraph.crayonscript.shapes;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.shape.mxIShape;
import com.mxgraph.view.mxCellState;

public interface CrayonScriptIShape extends mxIShape
{
	/**
	 * 
	 */
	void paintShape(mxGraphics2DCanvas canvas, mxCellState state);

}
