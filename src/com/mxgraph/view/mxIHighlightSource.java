package com.mxgraph.view;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;

public interface mxIHighlightSource {

    Path2D getPath();

    RoundRectangle2D getHighlightRect();

    Rectangle getHighlightBounds();
}
