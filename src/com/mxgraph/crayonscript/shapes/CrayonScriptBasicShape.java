/**
 * Copyright (c) 2010, Gaudenz Alder, David Benson
 */
package com.mxgraph.crayonscript.shapes;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.model.DropFlagEnum;
import com.mxgraph.model.mxCell;
import com.mxgraph.shape.mxDefaultTextShape;
import com.mxgraph.shape.mxITextShape;
import com.mxgraph.shape.mxStencilShape;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxCellState;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Logger;

public abstract class CrayonScriptBasicShape implements CrayonScriptIShape
{
	public CrayonScriptBasicShape(ShapeStructureType shapeStructureType)
	{
		this.shapeStructureType = shapeStructureType;
	}

	protected ShapeStructureType shapeStructureType;

	protected mxITextShape textShape = new mxDefaultTextShape();

	protected boolean isTemplate = false;

	protected static Map<ShapeStructureType, ArrayList<SvgElement>> svgElementsMap;

	protected static Map<ShapeStructureType, ArrayList<SvgElement>> hotspotSvgElementsMap;

	private static final Logger log = Logger.getLogger(mxStencilShape.class.getName());

	protected static boolean initialized;

	protected static HashMap<String, Object> textStyle = new HashMap<String, Object>();

	static {
		textStyle.put(mxConstants.STYLE_FONTFAMILY, mxConstants.DEFAULT_FONTFAMILY);
		textStyle.put(mxConstants.STYLE_FONTSIZE, 30);
		textStyle.put(mxConstants.STYLE_FONTSTYLE, 0);
		textStyle.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
		textStyle.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
	};

	public ArrayList<SvgElement> getSvgElements()
	{
		ArrayList<SvgElement> svgElements = svgElementsMap.get(shapeStructureType);
		return  svgElements;
	}

	public ArrayList<SvgElement> getHotspotSvgElements()
	{
		ArrayList<SvgElement> svgElements = hotspotSvgElementsMap.get(shapeStructureType);
		return  svgElements;
	}

	public int getSubElements()
	{
		ArrayList<SvgElement> svgElements = getSvgElements();
		return (svgElements == null) ? 0 : (svgElements.size() - 1);
	}

	public Color getParentFrameColor(mxCellState state)
	{
		Color frameColor = getParentFrameColor((mxCell) state.getCell());
        if (frameColor == null)
        {
            frameColor = getFrameColor();
        }
        return frameColor;
	}

	public Color getFrameColor()
	{
		ArrayList<SvgElement> svgElements = getSvgElements();
		SvgElement svgElement = svgElements.get(0);
		return svgElement.fillColor;
	}

	public Color getDropFlagColor(DropFlagEnum dropFlag)
	{
		ArrayList<SvgElement> svgElements = getSvgElements();
		SvgElement svgElement = svgElements.get(dropFlag.bitIndex);
		return svgElement.fillColor;
	}

	public Color getParentFrameColor(mxCell cell)
	{
		mxCell parentCell = (mxCell) cell.getParent();
		if (parentCell == null) return null;
		CrayonScriptIShape referenceShape = parentCell.referenceShape;
		if (parentCell.referenceShape == null) return null;
		Color parentFrameColor = referenceShape.getFrameColor();
		return parentFrameColor;
	}

	public boolean isExtender() { return false; }

	protected void initialize(mxCellState state)
	{
		checkTemplate(state);
		initialize();
	}

	protected static void initialize() {
		if (initialized) return;
		svgElementsMap = new HashMap<>();
		svgElementsMap.put(ShapeStructureType.ASSIGN,
				readSvgElements(CrayonScriptBasicShape.class.getResource("/com/mxgraph/crayonscript/images/Assign.svg")));
		svgElementsMap.put(ShapeStructureType.VERTICAL2,
				readSvgElements(CrayonScriptBasicShape.class.getResource("/com/mxgraph/crayonscript/images/Vertical2.svg")));
		svgElementsMap.put(ShapeStructureType.IF,
				readSvgElements(CrayonScriptBasicShape.class.getResource("/com/mxgraph/crayonscript/images/If.svg")));
		svgElementsMap.put(ShapeStructureType.ELSE_IF,
				readSvgElements(CrayonScriptBasicShape.class.getResource("/com/mxgraph/crayonscript/images/ElseIf.svg")));
		svgElementsMap.put(ShapeStructureType.WHILE,
				readSvgElements(CrayonScriptBasicShape.class.getResource("/com/mxgraph/crayonscript/images/While.svg")));
		svgElementsMap.put(ShapeStructureType.FOR,
				readSvgElements(CrayonScriptBasicShape.class.getResource("/com/mxgraph/crayonscript/images/For.svg")));
		svgElementsMap.put(ShapeStructureType.PARALLEL2,
				readSvgElements(CrayonScriptBasicShape.class.getResource("/com/mxgraph/crayonscript/images/Parallel2.svg")));
		svgElementsMap.put(ShapeStructureType.SEQUENTIAL2,
				readSvgElements(CrayonScriptBasicShape.class.getResource("/com/mxgraph/crayonscript/images/Sequential2.svg")));
		svgElementsMap.put(ShapeStructureType.PARALLEL_VEXTENDER2,
				readSvgElements(CrayonScriptBasicShape.class.getResource("/com/mxgraph/crayonscript/images/ParallelVExtender2.svg")));
		svgElementsMap.put(ShapeStructureType.SEQUENTIAL_VEXTENDER2,
				readSvgElements(CrayonScriptBasicShape.class.getResource("/com/mxgraph/crayonscript/images/SequentialVExtender2.svg")));
		svgElementsMap.put(ShapeStructureType.HEXTENDER2,
				readSvgElements(CrayonScriptBasicShape.class.getResource("/com/mxgraph/crayonscript/images/HExtender2.svg")));
		svgElementsMap.put(ShapeStructureType.MARKER,
				readSvgElements(CrayonScriptBasicShape.class.getResource("/com/mxgraph/crayonscript/images/Marker.svg")));
		svgElementsMap.put(ShapeStructureType.TEMPLATE,
				readSvgElements(CrayonScriptBasicShape.class.getResource("/com/mxgraph/crayonscript/images/Template.svg")));

		hotspotSvgElementsMap = new HashMap<>();
		for (Map.Entry<ShapeStructureType, ArrayList<SvgElement>> entry: svgElementsMap.entrySet()) {
			hotspotSvgElementsMap.put(entry.getKey(), new ArrayList<>(entry.getValue()));
		}

		// special purpose hotspot map for assign (statement) and expressions
		SvgElement assignInner1 = hotspotSvgElementsMap.get(ShapeStructureType.ASSIGN).get(DropFlagEnum.INNER_1.bitIndex);
		assignInner1 = assignInner1.copy();
		assignInner1.rect.setFrame(
				assignInner1.rect.getFrame().getX(),
				assignInner1.rect.getFrame().getY(),
				170,
				assignInner1.rect.getFrame().getHeight());
		hotspotSvgElementsMap.get(ShapeStructureType.ASSIGN).set(DropFlagEnum.INNER_1.bitIndex, assignInner1);

		SvgElement expressionInner1 = hotspotSvgElementsMap.get(ShapeStructureType.HEXTENDER2).get(DropFlagEnum.INNER_1.bitIndex);
		expressionInner1 = expressionInner1.copy();
		expressionInner1.rect.setFrame(
				expressionInner1.rect.getFrame().getX(),
				expressionInner1.rect.getFrame().getY(),
				170,
				expressionInner1.rect.getFrame().getHeight());
		hotspotSvgElementsMap.get(ShapeStructureType.HEXTENDER2).set(DropFlagEnum.INNER_1.bitIndex, expressionInner1);

		initialized = true;
	}

	public static SvgElement getTemplate()
	{
		ArrayList<SvgElement> templates = svgElementsMap.get(ShapeStructureType.TEMPLATE);
		return templates.get(0);
	}

	protected void checkTemplate(mxCellState state)
	{
		isTemplate = ((mxCell) state.getCell()).isAncestorTemplate();
	}

	protected static ArrayList<SvgElement> readSvgElements(URL url) {
		ArrayList<SvgElement> svgElements = new ArrayList<>();
		Document doc = mxUtils.loadDocument(url.toString());
		Node rootElement = doc.getDocumentElement();
		Stack<Node> childNodes = new Stack<>();
		childNodes.push(rootElement);
		while (!childNodes.empty())
		{
			Node childNode = childNodes.pop();
			SvgElement svgElement = new SvgElement(childNode);
			if (svgElement.svgElementType == SvgElementType.RECTANGLE)
			{
				svgElement.parseRectangle(childNode);
				svgElements.add(svgElement);
			}
			NodeList subChildNodeList = childNode.getChildNodes();
			if (subChildNodeList.getLength() > 0)
			{
				childNodes.push(subChildNodeList.item(0));
			}
			Node nextSiblingNode = childNode.getNextSibling();
			if (nextSiblingNode != null)
			{
				childNodes.push(nextSiblingNode);
			}
		}
		return svgElements;
	}

	protected void paintRectangle(mxGraphics2DCanvas canvas, RoundRectangle2D roundedRect, Color fillColor)
	{
		paintRectangle(canvas, roundedRect, fillColor, false);
	}

	protected void paintRectangle(mxGraphics2DCanvas canvas, RoundRectangle2D roundedRect, Color fillColor, boolean isFrame)
	{
		paintRectangle(canvas, roundedRect, fillColor, isFrame, false);
	}

	protected void paintRectangle(mxGraphics2DCanvas canvas, RoundRectangle2D roundedRect, Color fillColor, boolean isFrame, boolean isExtender)
	{
		Color color = fillColor;
		if (color != null)
		{
			canvas.getGraphics().setColor(color);
		}

		if (isFrame)
		{
			Path2D path = isExtender ? getFrameExtenderPath(roundedRect) : getFramePath(roundedRect);
			canvas.getGraphics().fill(path);
			canvas.getGraphics().draw(path);
		}
		else
		{
			canvas.getGraphics().fillRoundRect(
					(int) roundedRect.getX(),
					(int) roundedRect.getY(),
					(int) roundedRect.getWidth(),
					(int) roundedRect.getHeight(),
					(int) roundedRect.getArcWidth(),
					(int) roundedRect.getArcHeight());
		}
	}

	protected void drawText(mxGraphics2DCanvas canvas, String text, mxCellState state)
	{
		Rectangle stateRect = state.getRectangle();
		mxRectangle rect = new mxRectangle(
				stateRect.getX(),
				stateRect.getY(),
				stateRect.getWidth(),
				stateRect.getHeight());
		state.setLabelBounds(rect);
		textShape.paintShape(canvas, text, state, textStyle);
	}

	protected Color getColor(Color color)
	{
		if (color == null) return null;
		int opacity = isTemplate ? 255 : 85;
		Color adjustedColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
		return adjustedColor;
	}

	public static RoundRectangle2D scaleRectangle(
			Rectangle stateRect,
			SvgElement root,
			SvgElement target)
	{
		double widthRatio = stateRect.getWidth() / root.rect.getWidth();
		double heightRatio = stateRect.getHeight() / root.rect.getHeight();

		int x = (int) (stateRect.x + (target.rect.getX() - root.rect.getX()) * widthRatio);
		int y = (int) (stateRect.y + (target.rect.getY() - root.rect.getY()) * heightRatio);
		int w = (int) (target.rect.getWidth() * widthRatio);
		int h = (int) (target.rect.getHeight() * heightRatio);
		int rw = (int) (target.rect.getArcWidth() * widthRatio);
		int rh = (int) (target.rect.getArcHeight() * heightRatio);

		RoundRectangle2D scaledRect = new RoundRectangle2D.Double(x, y, w, h, rw, rh);
		return scaledRect;
	}

	public static Path2D getFramePath(RoundRectangle2D roundedRect)
	{
		double x = roundedRect.getX();
		double y = roundedRect.getY();
		double w = roundedRect.getWidth();
		double h = roundedRect.getHeight();
		double rx = roundedRect.getArcWidth();
		double ry = roundedRect.getArcHeight();

		double normalizedRxSize = 36;
		double normalizedArcSize = 36;
		double arcSize = normalizedArcSize * rx / normalizedRxSize;

		Path2D path = new Path2D.Double();
		path.moveTo(x, y + arcSize);
		path.lineTo(x, y + h);
		path.lineTo(x + w / 2 - arcSize / 2 - 3, y + h);
		path.curveTo(x + w / 2 - arcSize / 2, y + h - arcSize, x + w / 2 + arcSize / 2, y + h - arcSize, x + w / 2 + arcSize / 2 + 3, y + h);
		path.lineTo(x + w, y + h);
		path.lineTo(x + w, y + arcSize);
		path.lineTo(x + w / 2 + arcSize / 2, y + arcSize);
		path.curveTo(x + w / 2 + arcSize / 2, y, x + w / 2 - arcSize / 2, y, x + w / 2 - arcSize / 2, y + arcSize);
		path.lineTo(x, y + arcSize);
		path.closePath();

		return path;
	}

	public static Path2D getFrameExtenderPath(RoundRectangle2D roundedRect)
	{
		double x = roundedRect.getX();
		double y = roundedRect.getY();
		double w = roundedRect.getWidth();
		double h = roundedRect.getHeight();
		double rx = roundedRect.getArcWidth();
		double ry = roundedRect.getArcHeight();

		double normalizedRxSize = 36;
		double normalizedArcSize = 36;
		double arcSize = normalizedArcSize * rx / normalizedRxSize;

		Path2D path = new Path2D.Double();
		path.moveTo(x, y + arcSize);
		path.lineTo(x, y + h);
		path.lineTo(x + w / 2 - arcSize / 2 - 3, y + h);
		path.curveTo(x + w / 2 - arcSize / 2, y + h - arcSize, x + w / 2 + arcSize / 2, y + h - arcSize, x + w / 2 + arcSize / 2 + 3, y + h);
		path.lineTo(x + w, y + h);
		path.lineTo(x + w, y + arcSize);
		path.lineTo(x, y + arcSize);
		path.closePath();

		return path;
	}

	protected enum SvgElementType {
		OTHER,
		RECTANGLE,
		GROUP
	}

	protected enum ShapeStructureType {
		ASSIGN,
		PARALLEL2,
		SEQUENTIAL2,
		HEXTENDER2,
		VERTICAL2,
		IF,
		ELSE_IF,
		FOR,
		WHILE,
		PARALLEL_VEXTENDER2,
		SEQUENTIAL_VEXTENDER2,
		MARKER,
		TEMPLATE,
	}

	public static class SvgElement implements Cloneable
	{
		Color fillColor = null;
		Color strokeColor = null;
		RoundRectangle2D rect = null;
		private SvgElementType svgElementType = SvgElementType.OTHER;

		public SvgElement(Node node)
		{
			String tag = node.getNodeName();
			if (isGroup(tag))
			{
				svgElementType = SvgElementType.GROUP;
			}
			else if (isRectangle(tag))
			{
				svgElementType = SvgElementType.RECTANGLE;
			}
		}

		public SvgElement copy()
		{
			try
			{
				SvgElement copy = (SvgElement) clone();
				return copy;
			}
			catch(CloneNotSupportedException e)
			{
				throw new IllegalStateException(e);
			}
		}

		@Override
		protected Object clone() throws CloneNotSupportedException {

			SvgElement clone = (SvgElement) super.clone();
			clone.fillColor = fillColor == null ? null
					: new Color(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), fillColor.getAlpha());
			clone.strokeColor = strokeColor == null ? null
					: new Color(strokeColor.getRed(), strokeColor.getGreen(), strokeColor.getBlue(), strokeColor.getAlpha());
			clone.rect = rect == null ? null
					: new RoundRectangle2D.Double(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), rect.getArcWidth(), rect.getArcHeight());
			clone.svgElementType = svgElementType;

			return clone;
		}

		private boolean isRectangle(String tag)
		{
			return tag != null && tag.equals("svg:rect") || tag.equals("rect");
		}

		private boolean isGroup(String tag)
		{
			return tag != null && tag.equals("svg:g") || tag.equals("g");
		}

		protected void parseRectangle(Node node)
		{
			NamedNodeMap attributes = node.getAttributes();
			if (attributes != null)
			{
				double x = 0.0, y = 0.0, width = 0.0, height = 0.0, rx = 0.0, ry = 0.0;
				Node xNode = attributes.getNamedItem("x");
				if (xNode != null) x = Double.parseDouble(xNode.getNodeValue());
				Node yNode = attributes.getNamedItem("y");
				if (yNode != null) y = Double.parseDouble(yNode.getNodeValue());
				Node widthNode = attributes.getNamedItem("width");
				if (widthNode != null) width = Double.parseDouble((widthNode.getNodeValue()));
				Node heightNode = attributes.getNamedItem("height");
				if (heightNode != null) height = Double.parseDouble(heightNode.getNodeValue());
				Node rxNode = attributes.getNamedItem("rx");
				if (rxNode != null) rx = Double.parseDouble(rxNode.getNodeValue());
				Node ryNode = attributes.getNamedItem("ry");
				if (ryNode != null) ry = Double.parseDouble(ryNode.getNodeValue());
				rect = new RoundRectangle2D.Double(x, y, width, height, rx, ry);

				String fill = "none", stroke = "none";
				Node fillNode = attributes.getNamedItem("fill");
				if (fillNode != null) fill = fillNode.getNodeValue();
				if (!"none".equals(fill))  fillColor = Color.decode("0x" + fill.replace("#", ""));
				Node strokeNode = attributes.getNamedItem("stroke");
				if (strokeNode != null) stroke = strokeNode.getNodeValue();
				if (!"none".equals(stroke))  strokeColor = Color.decode("0x" + stroke.replace("#", ""));
			}
		}
	}

}
