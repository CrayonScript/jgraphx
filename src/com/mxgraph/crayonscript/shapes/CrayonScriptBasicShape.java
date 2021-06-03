/**
 * Copyright (c) 2010, Gaudenz Alder, David Benson
 */
package com.mxgraph.crayonscript.shapes;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.canvas.mxGraphicsCanvas2D;
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
import java.awt.geom.GeneralPath;
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
	protected enum ColorCode {

		// https://pin.it/3ThtK6U
		FUCHSIA("#C364C5"),
		MAROON("#C8385A"),
		NEON_CARROT("#FFA343"),
		JUNGLE_GREEN("#3BB08F"),
		SHOCKING_PINK("#FB7EFD"),
		RADICAL_RED("#FF496C"),
		SUN_GLOW("#FFCF48"),
		TROPICAL_RAIN_FOREST("#17806D"),
		PURPLE_PIZAZZ("#FF1DCE"),
		WILD_WATERMELON("#FC6C85"),
		LASER_LEMON("#FDFC74"),
		PINE_GREEN("#158078"),
		HOT_MAGENTA("#FF1DCE"),
		VIVID_TANGERINE("#FFA089"),
		UN_MELLOW_YELLOW("#FDFC74"),
		NAVY_BLUE("#1974D2"),
		RAZZLE_DAZZLE_ROSE("#FF48D0"),
		COPPER("#DD9475"),
		INCH_WORM("#B2EC5D"),
		DENIM("#2B6CC4"),
		CERISE("#FF43A4"),
		ATOMIC_TANGERINE("#FFA474"),
		SCREAMIN_GREEN("#76FF7A"),
		MIDNIGHT_BLUE("#1A4876"),
		JAZZ_BERRY_JAM("#CA3767"),
		OUTRAGEOUS_ORANGE("#FF6E4A"),
		ELECTRIC_LIME("#1DF914"),
		WILD_BLUE_WONDER("#A2ADD0"),
		RAZZMATAZZ("#E3256B"),
		MANGO_TANGO("#FF8243"),
		SHAMROCK("#45CEA2"),
		ROYAL_PURPLE("#7851A9");

		protected String hexCode;

		protected Color color;

		ColorCode(String hexCode)
		{
			this.hexCode = hexCode;
			this.color = Color.decode("0x" + hexCode.replace("#", ""));
		}
	}

	protected Map<ShapeStructureType, ArrayList<SvgElement>> svgElementsMap;

	private static final Logger log = Logger.getLogger(mxStencilShape.class.getName());

	protected boolean initialized;

	protected void initialize() {
		if (initialized) return;
		svgElementsMap = new HashMap<>();
		svgElementsMap.put(ShapeStructureType.VERTICAL2,
				readSvgElements(CrayonScriptBasicShape.class.getResource("/com/mxgraph/crayonscript/images/Vertical2.svg")));
		svgElementsMap.put(ShapeStructureType.VERTICAL3,
				readSvgElements(CrayonScriptBasicShape.class.getResource("/com/mxgraph/crayonscript/images/Vertical3.svg")));
		svgElementsMap.put(ShapeStructureType.VEXTENDER2,
				readSvgElements(CrayonScriptBasicShape.class.getResource("/com/mxgraph/crayonscript/images/VExtender2.svg")));
		svgElementsMap.put(ShapeStructureType.HEXTENDER2,
				readSvgElements(CrayonScriptBasicShape.class.getResource("/com/mxgraph/crayonscript/images/HExtender2.svg")));
		svgElementsMap.put(ShapeStructureType.TEMPLATE,
				readSvgElements(CrayonScriptBasicShape.class.getResource("/com/mxgraph/crayonscript/images/Template.svg")));
		initialized = true;
	}

	protected ArrayList<SvgElement> readSvgElements(URL url) {
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

	protected RoundRectangle2D scaleRectangle(
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

	protected void paintRectangle(mxGraphics2DCanvas canvas, RoundRectangle2D roundedRect, Color fillColor)
	{
		paintRectangle(canvas, roundedRect, fillColor, false);
	}

	protected void paintRectangle(mxGraphics2DCanvas canvas, RoundRectangle2D roundedRect, Color fillColor, boolean isFrame)
	{
		double x = roundedRect.getX();
		double y = roundedRect.getY();
		double w = roundedRect.getWidth();
		double h = roundedRect.getHeight();
		double rx = roundedRect.getArcWidth();
		double ry = roundedRect.getArcHeight();

		Color color = fillColor;
		if (color != null)
		{
			canvas.getGraphics().setColor(color);
		}

		double normalizedRxSize = 18;
		double normalizedArcSize = 18;
		double arcSize = normalizedArcSize * rx / normalizedRxSize;

		if (isFrame)
		{
			Path2D path = new Path2D.Double();
			path.moveTo(x, y);
			path.lineTo(x, y+h);
			path.lineTo(x+w/2-arcSize/2-3, y+h);
			path.curveTo(x+w/2-arcSize/2, y+h-arcSize, x+w/2+arcSize/2,y+h-arcSize, x+w/2+arcSize/2+3, y+h);
			path.lineTo(x+w, y+h);
			path.lineTo(x+w, y);
			path.lineTo(x+w/2+arcSize/2, y);
			path.curveTo(x+w/2+arcSize/2, y-arcSize, x+w/2-arcSize/2,y-arcSize, x+w/2-arcSize/2, y);
			path.lineTo(x, y);
			path.closePath();
			canvas.getGraphics().fill(path);
			canvas.getGraphics().draw(path);
		}
		else
		{
			canvas.getGraphics().fillRoundRect((int) x, (int) y, (int) w, (int) h, (int) rx, (int) ry);
		}

	}

	protected enum SvgElementType {
		OTHER,
		RECTANGLE,
		GROUP
	}

	protected enum ShapeStructureType {
		HEXTENDER2,
		VERTICAL0,
		VERTICAL1,
		VERTICAL2,
		VERTICAL3,
		VEXTENDER2,
		TEMPLATE,
	}

	protected static class SvgElement
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
