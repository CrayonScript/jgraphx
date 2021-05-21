package com.mxgraph.model;

public interface mxICellComponent {

    void removeFromOwner();

    mxICell getOwner();

    void setOwner(mxICell owner);
}
