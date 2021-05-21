package com.mxgraph.model;

public class mxCellComponent implements mxICellComponent
{
    mxICell owner;

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
}
