package com.mxgraph.shape;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class mxStencilCell extends mxCell implements Cloneable, Serializable
{
    private static final long serialVersionUID = -519555295553433212L;

    Shape shape;

    public ArrayList<mxStencilCell> innerStencilCells = new ArrayList<mxStencilCell>();

    public boolean isDropTarget;

    mxStencilCell(Shape shape, boolean isDropTarget)
    {
        this.shape = shape;
        this.isDropTarget = isDropTarget;
    }

    /* (non-Javadoc)
     * @see com.mxgraph.model.mxICell#setGeometry(com.mxgraph.model.mxGeometry)
     */
    public void setGeometry(mxGeometry geometry)
    {
        super.setGeometry(geometry);

        if (geometry != null)
        {
            double widthRatio = 1;
            double heightRatio = 1;

            if (this.shape != null)
            {
                widthRatio = geometry.getWidth() / this.shape.getBounds2D().getWidth();
                heightRatio = geometry.getHeight() / this.shape.getBounds2D().getHeight();
            }

            // update the inner stencil cells
            for (mxStencilCell innerCell : innerStencilCells)
            {
                if (innerCell.shape != null)
                {
                    mxGeometry innerCellGeometry = new mxGeometry(
                            geometry.getX() + (innerCell.shape.getBounds2D().getX() - this.shape.getBounds2D().getX()) * widthRatio,
                            geometry.getY() + (-innerCell.shape.getBounds2D().getY() + this.shape.getBounds2D().getY()) * heightRatio,
                            (innerCell.shape.getBounds2D().getWidth()) * widthRatio,
                            (innerCell.shape.getBounds2D().getHeight()) * heightRatio
                    );
                    innerCell.setGeometry(innerCellGeometry);
                }
            }
        }
    }

}
