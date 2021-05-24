package com.mxgraph.model;

import java.awt.geom.Rectangle2D;

public interface mxICellComponent {

    void removeFromOwner();

    mxICell getOwner();

    void setOwner(mxICell owner);

    mxGeometry getGeometry();

    Rectangle2D getBoundingBox();
}
