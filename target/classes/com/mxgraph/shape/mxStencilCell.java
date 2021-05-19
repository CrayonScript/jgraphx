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
}
