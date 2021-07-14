/**
 * Copyright (c) 2007, Gaudenz Alder
 */
package com.mxgraph.model;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.crayonscript.shapes.CrayonScriptBasicShape;
import com.mxgraph.crayonscript.shapes.CrayonScriptIShape;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Cells are the elements of the graph model. They represent the state
 * of the groups, vertices and edges in a graph.
 *
 * <h4>Edge Labels</h4>
 * 
 * Using the x- and y-coordinates of a cell's geometry it is
 * possible to position the label on edges on a specific location
 * on the actual edge shape as it appears on the screen. The
 * x-coordinate of an edge's geometry is used to describe the
 * distance from the center of the edge from -1 to 1 with 0
 * being the center of the edge and the default value. The
 * y-coordinate of an edge's geometry is used to describe
 * the absolute, orthogonal distance in pixels from that
 * point. In addition, the mxGeometry.offset is used
 * as a absolute offset vector from the resulting point.
 * 
 * The width and height of an edge geometry are ignored.
 * 
 * To add more than one edge label, add a child vertex with
 * a relative geometry. The x- and y-coordinates of that
 * geometry will have the same semantiv as the above for
 * edge labels.
 */
public class mxCell implements mxICell, Cloneable, Serializable
{

	/**
	 *
	 */
	private static final long serialVersionUID = 910211337632342672L;

	/**
	 * Holds the Id. Default is null.
	 */
	protected String id;

	/**
	 * Holds the user object. Default is null.
	 */
	protected Object value;

	/**
	 * Holds the geometry. Default is null.
	 */
	protected mxGeometry geometry;

	/**
	 * Holds the original geometry. Default is null
	 */
	protected mxGeometry originalGeometry;

	/**
	 * Holds the style as a string of the form
	 * stylename[;key=value]. Default is null.
	 */
	protected String style;

	/**
	 * Specifies whether the cell is a vertex or edge and whether it is
	 * connectable, visible and collapsed. Default values are false, false,
	 * true, true and false respectively.
	 */
	protected boolean vertex = false, edge = false, connectable = true,
			visible = true, collapsed = false,
			shape = false, dropSource=false, dropTarget=false, marked = true;

	protected mxCellTextParser cellTextParser;

	/**
	 * Reference to the parent cell and source and target terminals for edges.
	 */
	protected mxICell parent, source, target;

	/**
	 * Holds the child cells, components and connected edges.
	 */
	protected List<Object> children, edges;

	protected int dropSourceBitMask = 0;

	protected int dropTargetBitMask = 0;

	protected double boundingBoxWidth = Double.NaN, boundingBoxHeight = Double.NaN;

	protected CellTypeEnum cellType = CellTypeEnum.NATIVE;

	/**
	 * Reference to the last marked hotspot
	 */
	public transient boolean isHotspot;

	public transient RoundRectangle2D hotspotRect;

	public transient CellFrameEnum hotSpotDropFlag;

	public transient CrayonScriptIShape referenceShape;

	public transient Color markerColor;

	public transient CellFrameEnum snapToParentDropFlag;

	public transient CellFrameEnum[] snapToChildrenDropFlags;

	/**
	 * Reference to the cell that is being added to this cell.
	 */
	public transient Object otherCell;

	/**
	 * template level
	 */
	public transient int templateLevel;

	/**
	 * Constructs a new cell with an empty user object.
	 */
	public mxCell()
	{
		this(null);
	}

	/**
	 * Constructs a new cell for the given user object.
	 *
	 * @param value
	 *   Object that represents the value of the cell.
	 */
	public mxCell(Object value)
	{
		this(value, null, null);
	}

	/**
	 * Constructs a new cell for the given parameters.
	 *
	 * @param value Object that represents the value of the cell.
	 * @param geometry Specifies the geometry of the cell.
	 * @param style Specifies the style as a formatted string.
	 */
	public mxCell(Object value, mxGeometry geometry, String style)
	{
		this(value, geometry, style, Double.NaN, Double.NaN);
	}


	/**
	 * Constructs a new cell for the given parameters.
	 *
	 * @param value Object that represents the value of the cell.
	 * @param geometry Specifies the geometry of the cell.
	 * @param style Specifies the style as a formatted string.
	 * @param boundingBoxWidth Bounding box width
	 * @param boundingBoxHeight Bounding box height
	 */
	public mxCell(Object value, mxGeometry geometry, String style, double boundingBoxWidth, double boundingBoxHeight)
	{
		setValue(value);
		setGeometry(geometry);
		setStyle(style);

		this.boundingBoxWidth = boundingBoxWidth;
		this.boundingBoxHeight = boundingBoxHeight;
		this.cellTextParser = new mxCellTextParser();
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#getId()
	 */
	public String getId()
	{
		return id;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#setId(String)
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#getValue()
	 */
	public Object getValue()
	{
		return value;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#setValue(Object)
	 */
	public void setValue(Object value)
	{
		this.value = value;
	}

	public void setCellType(CellTypeEnum value)
	{
		cellType = value;
	}

	public boolean isAncestorTemplate()
	{
		if (isTemplate()) return true;
		mxCell ancestor = (mxCell) parent;
		while (ancestor != null)
		{
			if (ancestor.isTemplate()) return true;
			ancestor = (mxCell) ancestor.parent;
		}
		return false;
	}

	public mxCell getAncestorTemplate()
	{
		if (isTemplate()) return this;
		mxCell ancestor = (mxCell) parent;
		while (ancestor != null)
		{
			if (ancestor.isTemplate()) return ancestor;
			ancestor = (mxCell) ancestor.parent;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#getGeometry()
	 */
	public mxGeometry getGeometry()
	{
		return geometry;
	}

	public mxGeometry getExtendedGeometry()
	{
		mxGeometry extendedGeometry = (mxGeometry) geometry.clone();

		double minX, minY;
		minX = minY = Double.MAX_VALUE;

		double maxX, maxY;
		maxX = maxY = 0.0d;

		minX = Math.min(minX, extendedGeometry.getX());
		minY = Math.min(minY, extendedGeometry.getY());

		maxX = Math.max(maxX, extendedGeometry.getX() + extendedGeometry.getWidth());
		maxY = Math.max(maxY, extendedGeometry.getY() + extendedGeometry.getHeight());

		for(int childIndex = 0; childIndex < getChildCount(); childIndex++)
		{
			mxCell childCell = (mxCell) getChildAt(childIndex);
			mxGeometry childGeometry = childCell.getExtendedGeometry();

			minX = Math.min(minX, extendedGeometry.getX() + childGeometry.getX());
			minY = Math.min(minY, extendedGeometry.getY() + childGeometry.getY());

			maxX = Math.max(maxX, extendedGeometry.getX() + childGeometry.getX() + childGeometry.getWidth());
			maxY = Math.max(maxY, extendedGeometry.getY() + childGeometry.getY() + childGeometry.getHeight());
		}

		extendedGeometry.setX(minX);
		extendedGeometry.setY(minY);

		double extendedWidth = maxX - minX;
		double extendedHeight = maxY - minY;

		extendedGeometry.setWidth(extendedWidth);
		extendedGeometry.setHeight(extendedHeight);

		return extendedGeometry;
	}

	public void snapToParentGeometry()
	{
		mxCell parentCell = (mxCell) parent;
		if (parentCell == null) return;

		mxCell thisCell = this;

		// otherCell is the same as thisCell, however it contains the drag and drop info
		mxCell otherCell = (mxCell) parentCell.otherCell;
		if (otherCell == null) return;

		// both are shape based
		CellFrameEnum parentDropFlag = parentCell.hotSpotDropFlag;
		if (parentDropFlag == null) return;

		CellFrameEnum thisDropFlag = otherCell.hotSpotDropFlag;
		if (thisDropFlag == null) return;

		mxGeometry parentSubGeometry = parentCell.getSubGeometry(parentDropFlag.bitIndex);
		if (parentSubGeometry == null) return;

		mxGeometry thisSubGeometry = thisCell.getSubGeometry(thisDropFlag.bitIndex);
		if (thisSubGeometry == null) return;

		mxGeometry parentGeometry = parentCell.getGeometry();
		if (parentGeometry == null) return;

		mxGeometry thisGeometry = thisCell.getGeometry();
		if (thisGeometry == null) return;

		// thisGeometry.absoluteX + thisSubGeometry.relativeX = parentGeometry.absoluteX + parentSubGeometry.relativeX
		// => thisGeometry.absoluteX = parentGeometry.absoluteX + parentSubGeometry.relativeX - thisSubGeometry.relativeX
		// => thisGeometry.relativeX = thisGeometry.absoluteX - parentGeometry.absoluteX
		// => thisGeometry.relativeX = parentSubGeometry.relativeX - thisSubGeometry.relativeX

		thisGeometry.setX(parentSubGeometry.getX() - thisSubGeometry.getX());
		thisGeometry.setY(parentSubGeometry.getY() - thisSubGeometry.getY());

		// snap to parent drop flag
		thisCell.snapToParentDropFlag = parentCell.hotSpotDropFlag;

		if (parentCell.snapToChildrenDropFlags == null)
		{
			parentCell.snapToChildrenDropFlags = new CellFrameEnum[3];
		}

		parentCell.snapToChildrenDropFlags[parentCell.hotSpotDropFlag.bitIndex] = thisCell.hotSpotDropFlag;
	}

	public RoundRectangle2D getFrame(int index)
	{
		if (this.shape)
		{
			if (this.referenceShape == null)
			{
				this.referenceShape = (CrayonScriptIShape) mxGraphics2DCanvas.getShape(getStyle());
			}
			CrayonScriptBasicShape.SvgElement rootSvgElement = this.referenceShape.getSvgElements().get(0);
			CrayonScriptBasicShape.SvgElement svgElement = this.referenceShape.getSvgElements().get(index);
			RoundRectangle2D subRect = CrayonScriptBasicShape.scaleRectangle(this.geometry.getRectangle(), rootSvgElement, svgElement);
			return subRect;
		}
		return null;
	}

	public mxGeometry getSubGeometry(int subIndex)
	{
		if (this.shape)
		{
			if (this.referenceShape == null)
			{
				this.referenceShape = (CrayonScriptIShape) mxGraphics2DCanvas.getShape(getStyle());
			}
			CrayonScriptBasicShape.SvgElement rootElement = this.referenceShape.getSvgElements().get(0);
			CrayonScriptBasicShape.SvgElement subElement = this.referenceShape.getSvgElements().get(subIndex);
			RoundRectangle2D rootRect = CrayonScriptBasicShape.scaleRectangle(this.geometry.getRectangle(), rootElement, rootElement);
			RoundRectangle2D subRect = CrayonScriptBasicShape.scaleRectangle(this.geometry.getRectangle(), rootElement, subElement);
			mxGeometry subGeometry = new mxGeometry(
					subRect.getX() - rootRect.getX(),
					subRect.getY() - rootRect.getY(),
					   subRect.getWidth(),
					   subRect.getHeight());
			return subGeometry;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#setGeometry(com.mxgraph.model.mxGeometry)
	 */
	public void setGeometry(mxGeometry geometry)
	{
		this.geometry = geometry;
	}

	public Rectangle getCellEditorBounds()
	{
		if (!isEditable()) return null;
		// editable cell is always Inner_1
		mxGeometry frameGeometry = getSubGeometry(CellFrameEnum.INNER_1.bitIndex);
		return frameGeometry.getRectangle();
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#getStyle()
	 */
	public String getStyle()
	{
		return style;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#setStyle(String)
	 */
	public void setStyle(String style)
	{
		this.style = style;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#isTemplate()
	 */
	public boolean isTemplate()
	{
		return cellType == CellTypeEnum.TEMPLATE;
	}

	public boolean isExpression()
	{
		return cellType == CellTypeEnum.EXPRESSION;
	}

	public boolean isStatement()
	{
		return cellType == CellTypeEnum.STATEMENT;
	}

	public boolean isEditable() { return isStatement() || isExpression(); }

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#isShape()
	 */
	public boolean isShape()
	{
		return shape;
	}

	public void setShape(boolean value)
	{
		shape = value;
	}

	public boolean isMarked()
	{
		return marked;
	}

	public void setMarked(boolean value)
	{
		marked = value;
	}

	public String getText()
	{
		return cellTextParser.getText();
	}

	public void setText(String value)
	{
		cellTextParser.setText(value);
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#isDropTarget()
	 */
	public boolean isDropTarget()
	{
		return dropTargetBitMask > 0;
	}

	@Override
	public CellFrameEnum[] getDropTargetFlags() {
		if (dropTargetBitMask <= 0)
			return new CellFrameEnum[0];
		CellFrameEnum[] dropFlags = new CellFrameEnum[CellFrameEnum.values().length];
		int count = 0;
		for (CellFrameEnum flag : CellFrameEnum.values())
		{
			if ((dropTargetBitMask & flag.bit) > 0)
			{
				dropFlags[count++] = flag;
			}
		}
		CellFrameEnum[] flags = new CellFrameEnum[count];
		System.arraycopy(dropFlags, 0, flags, 0, count);
		return flags;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#isDropTarget()
	 */
	public boolean isDropSource()
	{
		return dropSourceBitMask > 0;
	}

	@Override
	public CellFrameEnum[] getDropSourceFlags() {
		if (dropSourceBitMask <= 0)
			return new CellFrameEnum[0];
		CellFrameEnum[] dropFlags = new CellFrameEnum[CellFrameEnum.values().length];
		int count = 0;
		for (CellFrameEnum flag : CellFrameEnum.values())
		{
			if ((dropSourceBitMask & flag.bit) > 0)
			{
				dropFlags[count++] = flag;
			}
		}
		CellFrameEnum[] flags = new CellFrameEnum[count];
		System.arraycopy(dropFlags, 0, flags, 0, count);
		return flags;
	}

	public void setDropSources(CellFrameEnum... dropFlags) {
		dropSourceBitMask = 0;
		for (CellFrameEnum dropFlag: dropFlags) {
			dropSourceBitMask |= dropFlag.bit;
		}
	}

	public void setDropTargets(CellFrameEnum... dropFlags) {
		dropTargetBitMask = 0;
		for (CellFrameEnum dropFlag: dropFlags) {
			dropTargetBitMask |= dropFlag.bit;
		}
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#isVertex()
	 */
	public boolean isVertex()
	{
		return vertex;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#setVertex(boolean)
	 */
	public void setVertex(boolean vertex)
	{
		this.vertex = vertex;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#isEdge()
	 */
	public boolean isEdge()
	{
		return edge;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#setEdge(boolean)
	 */
	public void setEdge(boolean edge)
	{
		this.edge = edge;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#isConnectable()
	 */
	public boolean isConnectable()
	{
		return connectable;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#setConnectable(boolean)
	 */
	public void setConnectable(boolean connectable)
	{
		this.connectable = connectable;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#isVisible()
	 */
	public boolean isVisible()
	{
		return visible;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#setVisible(boolean)
	 */
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#isCollapsed()
	 */
	public boolean isCollapsed()
	{
		return collapsed;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#setCollapsed(boolean)
	 */
	public void setCollapsed(boolean collapsed)
	{
		this.collapsed = collapsed;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#getParent()
	 */
	public mxICell getParent()
	{
		return parent;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#setParent(com.mxgraph.model.mxICell)
	 */
	public void setParent(mxICell parent)
	{
		this.parent = parent;
	}

	/**
	 * Returns the source terminal.
	 */
	public mxICell getSource()
	{
		return source;
	}

	/**
	 * Sets the source terminal.
	 * 
	 * @param source Cell that represents the new source terminal.
	 */
	public void setSource(mxICell source)
	{
		this.source = source;
	}

	/**
	 * Returns the target terminal.
	 */
	public mxICell getTarget()
	{
		return target;
	}

	/**
	 * Sets the target terminal.
	 * 
	 * @param target Cell that represents the new target terminal.
	 */
	public void setTarget(mxICell target)
	{
		this.target = target;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#getTerminal(boolean)
	 */
	public mxICell getTerminal(boolean source)
	{
		return (source) ? getSource() : getTarget();
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#setTerminal(com.mxgraph.model.mxICell, boolean)
	 */
	public mxICell setTerminal(mxICell terminal, boolean isSource)
	{
		if (isSource)
		{
			setSource(terminal);
		}
		else
		{
			setTarget(terminal);
		}

		return terminal;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#getChildCount()
	 */
	public int getChildCount()
	{
		return (children != null) ? children.size() : 0;
	}

	public boolean isEmpty()
	{
		return getChildCount() == 0;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#getIndex(com.mxgraph.model.mxICell)
	 */
	public int getIndex(mxICell child)
	{
		return (children != null) ? children.indexOf(child) : -1;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#getChildAt(int)
	 */
	public mxICell getChildAt(int index)
	{
		return (children != null) ? (mxICell) children.get(index) : null;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#insert(com.mxgraph.model.mxICell)
	 */
	public mxICell insert(mxICell child)
	{
		int index = getChildCount();
		
		if (child.getParent() == this)
		{
			index--;
		}
		
		return insert(child, index);
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#insert(com.mxgraph.model.mxICell, int)
	 */
	public mxICell insert(mxICell child, int index)
	{
		if (child != null)
		{
			child.removeFromParent();
			child.setParent(this);

			if (children == null)
			{
				children = new ArrayList<Object>();
				children.add(child);
			}
			else
			{
				children.add(index, child);
			}
		}

		return child;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#remove(int)
	 */
	public mxICell remove(int index)
	{
		mxICell child = null;

		if (children != null && index >= 0)
		{
			child = getChildAt(index);
			remove(child);
		}

		return child;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#remove(com.mxgraph.model.mxICell)
	 */
	public mxICell remove(mxICell child)
	{
		if (child != null && children != null)
		{
			children.remove(child);
			child.setParent(null);
		}

		return child;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#removeFromParent()
	 */
	public void removeFromParent()
	{
		if (parent != null)
		{
			parent.remove(this);
		}
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#getEdgeCount()
	 */
	public int getEdgeCount()
	{
		return (edges != null) ? edges.size() : 0;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#getEdgeIndex(com.mxgraph.model.mxICell)
	 */
	public int getEdgeIndex(mxICell edge)
	{
		return (edges != null) ? edges.indexOf(edge) : -1;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#getEdgeAt(int)
	 */
	public mxICell getEdgeAt(int index)
	{
		return (edges != null) ? (mxICell) edges.get(index) : null;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#insertEdge(com.mxgraph.model.mxICell, boolean)
	 */
	public mxICell insertEdge(mxICell edge, boolean isOutgoing)
	{
		if (edge != null)
		{
			edge.removeFromTerminal(isOutgoing);
			edge.setTerminal(this, isOutgoing);

			if (edges == null || edge.getTerminal(!isOutgoing) != this
					|| !edges.contains(edge))
			{
				if (edges == null)
				{
					edges = new ArrayList<Object>();
				}

				edges.add(edge);
			}
		}

		return edge;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#removeEdge(com.mxgraph.model.mxICell, boolean)
	 */
	public mxICell removeEdge(mxICell edge, boolean isOutgoing)
	{
		if (edge != null)
		{
			if (edge.getTerminal(!isOutgoing) != this && edges != null)
			{
				edges.remove(edge);
			}
			
			edge.setTerminal(null, isOutgoing);
		}

		return edge;
	}

	/* (non-Javadoc)
	 * @see com.mxgraph.model.mxICell#removeFromTerminal(boolean)
	 */
	public void removeFromTerminal(boolean isSource)
	{
		mxICell terminal = getTerminal(isSource);

		if (terminal != null)
		{
			terminal.removeEdge(this, isSource);
		}
	}

	/**
	 * Returns the specified attribute from the user object if it is an XML
	 * node.
	 * 
	 * @param name Name of the attribute whose value should be returned.
	 * @return Returns the value of the given attribute or null.
	 */
	public String getAttribute(String name)
	{
		return getAttribute(name, null);
	}

	/**
	 * Returns the specified attribute from the user object if it is an XML
	 * node.
	 * 
	 * @param name Name of the attribute whose value should be returned.
	 * @param defaultValue Default value to use if the attribute has no value.
	 * @return Returns the value of the given attribute or defaultValue.
	 */
	public String getAttribute(String name, String defaultValue)
	{
		Object userObject = getValue();
		String val = null;

		if (userObject instanceof Element)
		{
			Element element = (Element) userObject;
			val = element.getAttribute(name);
		}

		if (val == null)
		{
			val = defaultValue;
		}

		return val;
	}

	/**
	 * Sets the specified attribute on the user object if it is an XML node.
	 * 
	 * @param name Name of the attribute whose value should be set.
	 * @param value New value of the attribute.
	 */
	public void setAttribute(String name, String value)
	{
		Object userObject = getValue();

		if (userObject instanceof Element)
		{
			Element element = (Element) userObject;
			element.setAttribute(name, value);
		}
	}

	/**
	 * Returns a clone of the cell.
	 */
	public Object clone() throws CloneNotSupportedException
	{
		mxCell clone = (mxCell) super.clone();

		clone.setValue(cloneValue());
		clone.setStyle(getStyle());
		clone.setCollapsed(isCollapsed());
		clone.setConnectable(isConnectable());
		clone.setEdge(isEdge());
		clone.setVertex(isVertex());
		clone.setShape(isShape());
		clone.setVisible(isVisible());
		clone.setParent(null);
		clone.setSource(null);
		clone.setTarget(null);
		clone.children = null;
		clone.edges = null;

		mxGeometry geometry = getGeometry();

		if (geometry != null)
		{
			clone.setGeometry((mxGeometry) geometry.clone());
		}

		clone.dropSourceBitMask = dropSourceBitMask;
		clone.dropTargetBitMask = dropTargetBitMask;
		clone.dropSource = dropSource;
		clone.dropTarget = dropTarget;
		clone.referenceShape = referenceShape;
		clone.isHotspot = isHotspot;
		clone.hotSpotDropFlag = hotSpotDropFlag;
		clone.hotspotRect = hotspotRect;

		clone.markerColor = markerColor;
		clone.marked = marked;

		clone.cellType = cellType;

		return clone;
	}

	/**
	 * Returns a clone of the user object. This implementation clones any XML
	 * nodes or otherwise returns the same user object instance.
	 */
	protected Object cloneValue()
	{
		Object value = getValue();

		if (value instanceof Node)
		{
			value = ((Node) value).cloneNode(true);
		}

		return value;
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder(64);
		builder.append(getClass().getSimpleName());
		builder.append(" [");
		builder.append("id=");
		builder.append(id);
		builder.append(", value=");
		builder.append(value);
		builder.append(", geometry=");
		builder.append(geometry);
		builder.append("]");
		
		return builder.toString();
	}

}
