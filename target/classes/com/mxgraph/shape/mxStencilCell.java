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

    public boolean isDropTarget;

    mxStencilCell(Shape shape, boolean isDropTarget)
    {
        this.shape = shape;
        this.isDropTarget = isDropTarget;
    }

    @Override
    public void setGeometry(mxGeometry geometry) {
        super.setGeometry(geometry);

        if (children != null)
        {
            // update child stencil geometry
            for (Object cell : children)
            {
                if (cell instanceof mxStencilCell)
                {
                    updateStencilGeometry((mxStencilCell) cell, this, geometry);
                }
            }
        }
    }

    void updateStencilGeometry(mxStencilCell cell, mxStencilCell referenceCell, mxGeometry referenceGeometry)
    {
        // how far was the shape originally from the (0,0) of the referenceShape
        double originalXOffset =  cell.shape.getBounds2D().getX();
        double originalYOffset = cell.shape.getBounds2D().getY();

        // how much has the geometry changed
        double widthRatio = referenceGeometry.getWidth() / referenceCell.shape.getBounds2D().getWidth();
        double heightRatio = referenceGeometry.getHeight() / referenceCell.shape.getBounds2D().getHeight();

        double newX = originalXOffset * widthRatio;
        double newY = originalYOffset * heightRatio;

        double newWidth = cell.shape.getBounds2D().getWidth() * widthRatio;
        double newHeight = cell.shape.getBounds2D().getHeight() * heightRatio;

        mxGeometry newGeometry = new mxGeometry(newX, newY, newWidth, newHeight);
        cell.geometry = newGeometry;
    }
}
