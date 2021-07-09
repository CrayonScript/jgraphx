package com.mxgraph.view;

import com.mxgraph.model.DropFlagEnum;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public interface mxIHighlightSource {

    RoundRectangle2D getHighlightRect();

    Rectangle getHighlightBounds();

    DropFlagEnum getHighlightDropFlag();

    RoundRectangle2D getOtherHighlightRect();

    Rectangle getOtherHighlightBounds();

    DropFlagEnum getOtherHighlightDropFlag();
}
