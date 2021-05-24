package com.mxgraph.model;

import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class mxCellComponent implements mxICellComponent, Cloneable, Serializable
{
    private static final long serialVersionUID = 1046000944721945439L;

    mxICell owner;

    Rectangle2D boundingBox;

    /**
     * Holds the geometry. Default is null.
     */
    protected mxGeometry geometry;

    public mxCellComponent(mxGeometry geometry, Rectangle2D boundingBox)
    {
        this.geometry = geometry;
        this.boundingBox= boundingBox;
    }

    @Override
    public void removeFromOwner()
    {
        if (this.owner != null)
        {
            this.owner.removeComponent(this);
        }
    }

    public mxICell getOwner()
    {
        return this.owner;
    }

    public void setOwner(mxICell owner)
    {
        this.owner = owner;
    }

    public mxGeometry getGeometry()
    {
        return geometry;
    }

    void setGeometry(mxGeometry geometry) { this.geometry = geometry; }

    public Rectangle2D getBoundingBox() { return boundingBox; };
}
