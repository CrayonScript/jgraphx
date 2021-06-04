package com.mxgraph.view;

import java.awt.*;
import java.awt.geom.Path2D;

public interface mxIHighlightSource {

    Path2D getPath();

    Rectangle getRectangle();
}
