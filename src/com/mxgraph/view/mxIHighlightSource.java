package com.mxgraph.view;

import com.mxgraph.model.DropFlag;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public interface mxIHighlightSource {

    RoundRectangle2D getHighlightRect();

    Rectangle getHighlightBounds();

    DropFlag getHighlightDropFlag();

    RoundRectangle2D getOtherHighlightRect();

    Rectangle getOtherHighlightBounds();

    DropFlag getOtherHighlightDropFlag();
}
