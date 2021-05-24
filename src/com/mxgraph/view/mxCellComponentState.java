package com.mxgraph.view;

import com.mxgraph.model.mxICellComponent;
import com.mxgraph.util.mxRectangle;

import java.awt.*;

public class mxCellComponentState extends mxRectangle implements mxIHighlightSource {

    /**
     *
     */
    private static final long serialVersionUID = 249943583902057343L;

    /**
     * Reference to the enclosing graph view.
     */
    protected mxGraphView view;

    /**
     * Reference to the cellComponent that is represented by this state.
     */
    protected mxICellComponent cellComponent;

    /**
     * Reference to the last marked hotspot
     */
    protected transient boolean isHotspot;

    /**
     * Constructs a new object that represents the current state of the given
     * cellComponent in the specified view.
     *
     * @param view Graph view that contains the state.
     * @param cellComponent Cell that this state represents.
     */
    public mxCellComponentState(mxGraphView view, mxICellComponent cellComponent)
    {
        setView(view);
        setCellComponent(cellComponent);
    }

    /**
     * Returns the enclosing graph view.
     *
     * @return the view
     */
    public mxGraphView getView()
    {
        return view;
    }

    /**
     * Sets the enclosing graph view.
     *
     * @param view the view to set
     */
    public void setView(mxGraphView view)
    {
        this.view = view;
    }

    /**
     * Returns the cellComponent that is represented by this state.
     *
     * @return the cellComponent
     */
    public mxICellComponent getCellComponent()
    {
        return cellComponent;
    }

    /**
     * Sets the cellComponent that this state represents.
     *
     * @param cellComponent the cellComponent to set
     */
    public void setCellComponent(mxICellComponent cellComponent)
    {
        this.cellComponent = cellComponent;
    }

    public void updateIntersects(int x, int y,
                                 double hotspot, int min, int max)
    {
        this.isHotspot = intersects(x, y, hotspot, min, max);
    }

    /**
     * Returns true if the given coordinate pair intersects the hotspot of the
     * given state.
     *
     * @param x
     * @param y
     * @param hotspot
     * @param min
     * @param max
     * @return
     */
    protected boolean intersects(int x, int y,
                                     double hotspot, int min, int max)
    {
        mxCellComponentState state = this;
        if (hotspot > 0)
        {
            int cx = (int) Math.round(state.getCenterX());
            int cy = (int) Math.round(state.getCenterY());
            int width = (int) Math.round(state.getWidth());
            int height = (int) Math.round(state.getHeight());

            int w = (int) Math.max(min, width * hotspot);
            int h = (int) Math.max(min, height * hotspot);

            if (max > 0)
            {
                w = Math.min(w, max);
                h = Math.min(h, max);
            }

            Rectangle rect = new Rectangle(Math.round(cx - w / 2),
                    Math.round(cy - h / 2), w, h);

            return rect.contains(x, y);
        }
        return true;
    }

}