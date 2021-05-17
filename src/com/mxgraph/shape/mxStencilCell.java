package com.mxgraph.shape;

import com.mxgraph.model.mxCell;

import java.awt.*;
import java.io.Serializable;

public class mxStencilCell extends mxCell implements Cloneable, Serializable
{
    private static final long serialVersionUID = -519555295553433212L;

    Shape shape;

    mxStencilCell(Shape shape)
    {
        this.shape = shape;
    }
}
