/**
 * Copyright (c) 2007-2012, JGraph Ltd
 */
package com.mxgraph.canvas;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.CellRendererPane;

import com.mxgraph.crayonscript.shapes.*;
import com.mxgraph.shape.*;
import com.mxgraph.swing.util.mxSwingConstants;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxCellState;

/**
 * An implementation of a canvas that uses Graphics2D for painting.
 */
public class mxGraphics2DCanvas extends mxBasicCanvas
{

	private static final Logger log = Logger.getLogger(mxGraphics2DCanvas.class.getName());

	/**
	 * 
	 */
	public static final String TEXT_SHAPE_DEFAULT = "default";

	/**
	 * 
	 */
	public static final String TEXT_SHAPE_HTML = "html";

	/**
	 * Specifies the image scaling quality. Default is Image.SCALE_SMOOTH.
	 */
	public static int IMAGE_SCALING = Image.SCALE_SMOOTH;

	/**
	 * Maps from names to mxIVertexShape instances.
	 */
	protected static Map<String, mxIShape> shapes = new HashMap<String, mxIShape>();

	/**
	 * Maps from names to mxITextShape instances. There are currently three different
	 * hardcoded text shapes available here: default, html and wrapped.
	 */
	protected static Map<String, mxITextShape> textShapes = new HashMap<String, mxITextShape>();

	/**
	 * Static initializer.
	 */
	static
	{
		String shapeName = mxConstants.CRAYONSCRIPT_SHAPE_PARALLEL_EXTENSION;
		putShape(shapeName, new CrayonScriptParallelVExtenderShape(shapeName));

		shapeName = mxConstants.CRAYONSCRIPT_SHAPE_SEQUENTIAL_EXTENSION;
		putShape(shapeName, new CrayonScriptSequentialVExtenderShape(shapeName));

		shapeName = mxConstants.CRAYONSCRIPT_SHAPE_EVENT;
		putShape(shapeName, new CrayonScriptEventShape(shapeName));

		shapeName = mxConstants.CRAYONSCRIPT_SHAPE_ASSIGN;
		putShape(shapeName, new CrayonScriptAssignShape(shapeName));

		shapeName = mxConstants.CRAYONSCRIPT_SHAPE_PROPERTY;
		putShape(shapeName, new CrayonScriptPropertyShape(shapeName));

		shapeName = mxConstants.CRAYONSCRIPT_SHAPE_FUNCTION;
		putShape(shapeName, new CrayonScriptFunctionShape(shapeName));

		shapeName = mxConstants.CRAYONSCRIPT_SHAPE_FOR_EACH_EXPR;
		putShape(shapeName, new CrayonScriptExpressionShape(shapeName));

		shapeName = mxConstants.CRAYONSCRIPT_SHAPE_FOR_IN_RANGE;
		putShape(shapeName, new CrayonScriptExpressionShape(shapeName));

		shapeName = mxConstants.CRAYONSCRIPT_SHAPE_WHILE_BOOLEAN_EXPR;
		putShape(shapeName, new CrayonScriptExpressionShape(shapeName));

		shapeName = mxConstants.CRAYONSCRIPT_SHAPE_EQUALS;
		putShape(shapeName, new CrayonScriptExpressionShape(shapeName));

		shapeName = mxConstants.CRAYONSCRIPT_SHAPE_NOTEQUALS;
		putShape(shapeName, new CrayonScriptExpressionShape(shapeName));

		shapeName = mxConstants.CRAYONSCRIPT_SHAPE_GT;
		putShape(shapeName, new CrayonScriptExpressionShape(shapeName));

		shapeName = mxConstants.CRAYONSCRIPT_SHAPE_GT_OR_EQUALS;
		putShape(shapeName, new CrayonScriptExpressionShape(shapeName));

		shapeName = mxConstants.CRAYONSCRIPT_SHAPE_LT;
		putShape(shapeName, new CrayonScriptExpressionShape(shapeName));

		shapeName = mxConstants.CRAYONSCRIPT_SHAPE_LT_OR_EQUALS;
		putShape(shapeName, new CrayonScriptExpressionShape(shapeName));

		shapeName = mxConstants.CRAYONSCRIPT_SHAPE_AND;
		putShape(shapeName, new CrayonScriptExpressionShape(shapeName));

		shapeName = mxConstants.CRAYONSCRIPT_SHAPE_OR;
		putShape(shapeName, new CrayonScriptExpressionShape(shapeName));

		shapeName = mxConstants.CRAYONSCRIPT_SHAPE_NOT;
		putShape(shapeName, new CrayonScriptExpressionShape(shapeName));

		shapeName = mxConstants.CRAYONSCRIPT_SHAPE_MOD;
		putShape(shapeName, new CrayonScriptExpressionShape(shapeName));

		shapeName = mxConstants.CRAYONSCRIPT_SHAPE_MIN;
		putShape(shapeName, new CrayonScriptExpressionShape(shapeName));

		shapeName = mxConstants.CRAYONSCRIPT_SHAPE_MAX;
		putShape(shapeName, new CrayonScriptExpressionShape(shapeName));

		shapeName = mxConstants.CRAYONSCRIPT_SHAPE_RUN_PARALLEL;
		putShape(shapeName, new CrayonScriptParallelShape(shapeName));

		shapeName = mxConstants.CRAYONSCRIPT_SHAPE_RUN_SEQUENTIAL;
		putShape(shapeName, new CrayonScriptSequentialShape(shapeName));

		shapeName = mxConstants.CRAYONSCRIPT_SHAPE_RUN_SINGLE;
		putShape(shapeName, new CrayonScriptRunShape(shapeName));

		shapeName = mxConstants.CRAYONSCRIPT_SHAPE_IF;
		putShape(shapeName, new CrayonScriptIfShape(shapeName));

		shapeName = mxConstants.CRAYONSCRIPT_SHAPE_ELSE_IF;
		putShape(shapeName, new CrayonScriptElseIfShape(shapeName));

		shapeName = mxConstants.CRAYONSCRIPT_SHAPE_WHILE;
		putShape(shapeName, new CrayonScriptWhileShape(shapeName));

		shapeName = mxConstants.CRAYONSCRIPT_SHAPE_FOR;
		putShape(shapeName, new CrayonScriptForShape(shapeName));

		shapeName =mxConstants.CRAYONSCRIPT_SHAPE_MARKER;
		putShape(shapeName, new CrayonScriptMarkerShape(shapeName));

		shapeName = mxConstants.CRAYONSCRIPT_SHAPE_WAIT_FOR;
		putShape(mxConstants.CRAYONSCRIPT_SHAPE_WAIT_FOR, new CrayonScriptWaitForShape(shapeName));

		shapeName = mxConstants.CRAYONSCRIPT_SHAPE_TEMPLATE;
		putShape(shapeName, new CrayonScriptGraphTemplateShape(shapeName));

		shapeName = mxConstants.SHAPE_CONNECTOR;
		putShape(shapeName, new mxConnectorShape());

		putTextShape(TEXT_SHAPE_DEFAULT, new mxDefaultTextShape());
	}

	/**
	 * Optional renderer pane to be used for HTML label rendering.
	 */
	protected CellRendererPane rendererPane;

	/**
	 * Global graphics handle to the image.
	 */
	protected Graphics2D g;

	/**
	 * Constructs a new graphics canvas with an empty graphics object.
	 */
	public mxGraphics2DCanvas()
	{
		this(null);
	}

	/**
	 * Constructs a new graphics canvas for the given graphics object.
	 */
	public mxGraphics2DCanvas(Graphics2D g)
	{
		this.g = g;

		// Initializes the cell renderer pane for drawing HTML markup
		try
		{
			rendererPane = new CellRendererPane();
		}
		catch (Exception e)
		{
			log.log(Level.WARNING, "Failed to initialize renderer pane", e);
		}
	}

	/**
	 * 
	 */
	public static void putShape(String name, mxIShape shape)
	{
		shapes.put(name, shape);
	}

	/**
	 *
	 */
	public static mxIShape getShape(String name)
	{
		mxIShape shape = shapes.get(name);

		if (shape == null && name != null)
		{
			shape = mxStencilRegistry.getStencil(name);
		}

		return shape;
	}

	/**
	 * 
	 */
	public static mxIShape getShape(Map<String, Object> style)
	{
		String name = mxUtils.getString(style, mxConstants.STYLE_SHAPE, null);
		return getShape(name);
	}

	/**
	 * 
	 */
	public static void putTextShape(String name, mxITextShape shape)
	{
		textShapes.put(name, shape);
	}

	/**
	 * 
	 */
	public mxITextShape getTextShape(Map<String, Object> style, boolean html)
	{
		String name;

		if (html)
		{
			name = TEXT_SHAPE_HTML;
		}
		else
		{
			name = TEXT_SHAPE_DEFAULT;
		}

		return textShapes.get(name);
	}

	/**
	 * 
	 */
	public CellRendererPane getRendererPane()
	{
		return rendererPane;
	}

	/**
	 * Returns the graphics object for this canvas.
	 */
	public Graphics2D getGraphics()
	{
		return g;
	}

	/**
	 * Sets the graphics object for this canvas.
	 */
	public void setGraphics(Graphics2D g)
	{
		this.g = g;
	}

	/*
	 * (non-Javadoc)
	 * @see com.mxgraph.canvas.mxICanvas#drawCell()
	 */
	public Object drawCell(mxCellState state)
	{
		Map<String, Object> style = state.getStyle();
		mxIShape shape = getShape(style);

		if (g != null && shape != null)
		{
			// Creates a temporary graphics instance for drawing this shape
			float opacity = mxUtils.getFloat(style, mxConstants.STYLE_OPACITY,
					100);
			Graphics2D previousGraphics = g;
			g = createTemporaryGraphics(style, opacity, state);

			// Paints the shape and restores the graphics object
			shape.paintShape(this, state);
			g.dispose();
			g = previousGraphics;
		}

		return shape;
	}

	/*
	 * (non-Javadoc)
	 * @see com.mxgraph.canvas.mxICanvas#drawLabel()
	 */
	public Object drawLabel(String text, mxCellState state, boolean html)
	{
		Map<String, Object> style = state.getStyle();
		mxITextShape shape = getTextShape(style, html);

		if (g != null && shape != null && drawLabels && text != null
				&& text.length() > 0)
		{
			// Creates a temporary graphics instance for drawing this shape
			float opacity = mxUtils.getFloat(style,
					mxConstants.STYLE_TEXT_OPACITY, 100);
			Graphics2D previousGraphics = g;
			g = createTemporaryGraphics(style, opacity, null);

			// Draws the label background and border
			Color bg = mxUtils.getColor(style,
					mxConstants.STYLE_LABEL_BACKGROUNDCOLOR);
			Color border = mxUtils.getColor(style,
					mxConstants.STYLE_LABEL_BORDERCOLOR);
			paintRectangle(state.getLabelBounds().getRectangle(), bg, border);

			// Paints the label and restores the graphics object
			shape.paintShape(this, text, state, style);
			g.dispose();
			g = previousGraphics;
		}

		return shape;
	}

	/**
	 * 
	 */
	public void drawImage(Rectangle bounds, String imageUrl)
	{
		drawImage(bounds, imageUrl, PRESERVE_IMAGE_ASPECT, false, false);
	}

	/**
	 * 
	 */
	public void drawImage(Rectangle bounds, String imageUrl,
			boolean preserveAspect, boolean flipH, boolean flipV)
	{
		if (imageUrl != null && bounds.getWidth() > 0 && bounds.getHeight() > 0)
		{
			Image img = loadImage(imageUrl);

			if (img != null)
			{
				int w, h;
				int x = bounds.x;
				int y = bounds.y;
				Dimension size = getImageSize(img);

				if (preserveAspect)
				{
					double s = Math.min(bounds.width / (double) size.width,
							bounds.height / (double) size.height);
					w = (int) (size.width * s);
					h = (int) (size.height * s);
					x += (bounds.width - w) / 2;
					y += (bounds.height - h) / 2;
				}
				else
				{
					w = bounds.width;
					h = bounds.height;
				}

				Image scaledImage = (w == size.width && h == size.height) ? img
						: img.getScaledInstance(w, h, IMAGE_SCALING);

				if (scaledImage != null)
				{
					AffineTransform af = null;

					if (flipH || flipV)
					{
						af = g.getTransform();
						int sx = 1;
						int sy = 1;
						int dx = 0;
						int dy = 0;

						if (flipH)
						{
							sx = -1;
							dx = -w - 2 * x;
						}

						if (flipV)
						{
							sy = -1;
							dy = -h - 2 * y;
						}

						g.scale(sx, sy);
						g.translate(dx, dy);
					}

					drawImageImpl(scaledImage, x, y);

					// Restores the previous transform
					if (af != null)
					{
						g.setTransform(af);
					}
				}
			}
		}
	}

	/**
	 * Implements the actual graphics call.
	 */
	protected void drawImageImpl(Image image, int x, int y)
	{
		g.drawImage(image, x, y, null);
	}

	/**
	 * Returns the size for the given image.
	 */
	protected Dimension getImageSize(Image image)
	{
		return new Dimension(image.getWidth(null), image.getHeight(null));
	}

	/**
	 * 
	 */
	public void paintPolyline(mxPoint[] points, boolean rounded)
	{
		if (points != null && points.length > 1)
		{
			mxPoint pt = points[0];
			mxPoint pe = points[points.length - 1];

			double arcSize = mxConstants.LINE_ARCSIZE * scale;

			GeneralPath path = new GeneralPath();
			path.moveTo((float) pt.getX(), (float) pt.getY());

			// Draws the line segments
			for (int i = 1; i < points.length - 1; i++)
			{
				mxPoint tmp = points[i];
				double dx = pt.getX() - tmp.getX();
				double dy = pt.getY() - tmp.getY();

				if ((rounded && i < points.length - 1) && (dx != 0 || dy != 0))
				{
					// Draws a line from the last point to the current
					// point with a spacing of size off the current point
					// into direction of the last point
					double dist = Math.sqrt(dx * dx + dy * dy);
					double nx1 = dx * Math.min(arcSize, dist / 2) / dist;
					double ny1 = dy * Math.min(arcSize, dist / 2) / dist;

					double x1 = tmp.getX() + nx1;
					double y1 = tmp.getY() + ny1;
					path.lineTo((float) x1, (float) y1);

					// Draws a curve from the last point to the current
					// point with a spacing of size off the current point
					// into direction of the next point
					mxPoint next = points[i + 1];

					// Uses next non-overlapping point
					while (i < points.length - 2 && Math.round(next.getX() - tmp.getX()) == 0 && Math.round(next.getY() - tmp.getY()) == 0)
					{
						next = points[i + 2];
						i++;
					}
					
					dx = next.getX() - tmp.getX();
					dy = next.getY() - tmp.getY();

					dist = Math.max(1, Math.sqrt(dx * dx + dy * dy));
					double nx2 = dx * Math.min(arcSize, dist / 2) / dist;
					double ny2 = dy * Math.min(arcSize, dist / 2) / dist;

					double x2 = tmp.getX() + nx2;
					double y2 = tmp.getY() + ny2;

					path.quadTo((float) tmp.getX(), (float) tmp.getY(),
							(float) x2, (float) y2);
					tmp = new mxPoint(x2, y2);
				}
				else
				{
					path.lineTo((float) tmp.getX(), (float) tmp.getY());
				}

				pt = tmp;
			}

			path.lineTo((float) pe.getX(), (float) pe.getY());
			g.draw(path);
		}
	}

	/**
	 *
	 */
	public void paintRectangle(Rectangle bounds, Color background, Color border)
	{
		if (background != null)
		{
			g.setColor(background);
			fillShape(bounds);
		}

		if (border != null)
		{
			g.setColor(border);
			g.draw(bounds);
		}
	}

	/**
	 *
	 */
	public void fillShape(Shape shape)
	{
		fillShape(shape, false);
	}

	/**
	 *
	 */
	public void fillShape(Shape shape, boolean shadow)
	{
		int shadowOffsetX = (shadow) ? mxConstants.SHADOW_OFFSETX : 0;
		int shadowOffsetY = (shadow) ? mxConstants.SHADOW_OFFSETY : 0;

		if (shadow)
		{
			// Saves the state and configures the graphics object
			Paint p = g.getPaint();
			Color previousColor = g.getColor();
			g.setColor(mxSwingConstants.SHADOW_COLOR);
			g.translate(shadowOffsetX, shadowOffsetY);

			// Paints the shadow
			fillShape(shape, false);

			// Restores the state of the graphics object
			g.translate(-shadowOffsetX, -shadowOffsetY);
			g.setColor(previousColor);
			g.setPaint(p);
		}

		g.fill(shape);
	}

	/**
	 * 
	 */
	public Stroke createStroke(Map<String, Object> style)
	{
		double width = mxUtils
				.getFloat(style, mxConstants.STYLE_STROKEWIDTH, 1) * scale;
		boolean dashed = mxUtils.isTrue(style, mxConstants.STYLE_DASHED);
		if (dashed)
		{
			float[] dashPattern = mxUtils.getFloatArray(style,
					mxConstants.STYLE_DASH_PATTERN,
					mxConstants.DEFAULT_DASHED_PATTERN, " ");
			float[] scaledDashPattern = new float[dashPattern.length];

			for (int i = 0; i < dashPattern.length; i++)
			{
				scaledDashPattern[i] = (float) (dashPattern[i] * scale * width);
			}

			return new BasicStroke((float) width, BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_MITER, 10.0f, scaledDashPattern, 0.0f);
		}
		else
		{
			return new BasicStroke((float) width);
		}
	}

	/**
	 * 
	 */
	public Paint createFillPaint(mxRectangle bounds, Map<String, Object> style)
	{
		Color fillColor = mxUtils.getColor(style, mxConstants.STYLE_FILLCOLOR);
		Paint fillPaint = null;

		if (fillColor != null)
		{
			Color gradientColor = mxUtils.getColor(style,
					mxConstants.STYLE_GRADIENTCOLOR);

			if (gradientColor != null)
			{
				String gradientDirection = mxUtils.getString(style,
						mxConstants.STYLE_GRADIENT_DIRECTION);

				float x1 = (float) bounds.getX();
				float y1 = (float) bounds.getY();
				float x2 = (float) bounds.getX();
				float y2 = (float) bounds.getY();

				if (gradientDirection == null
						|| gradientDirection
								.equals(mxConstants.DIRECTION_SOUTH))
				{
					y2 = (float) (bounds.getY() + bounds.getHeight());
				}
				else if (gradientDirection.equals(mxConstants.DIRECTION_EAST))
				{
					x2 = (float) (bounds.getX() + bounds.getWidth());
				}
				else if (gradientDirection.equals(mxConstants.DIRECTION_NORTH))
				{
					y1 = (float) (bounds.getY() + bounds.getHeight());
				}
				else if (gradientDirection.equals(mxConstants.DIRECTION_WEST))
				{
					x1 = (float) (bounds.getX() + bounds.getWidth());
				}

				fillPaint = new GradientPaint(x1, y1, fillColor, x2, y2,
						gradientColor, true);
			}
		}

		return fillPaint;
	}

	/**
	 * 
	 */
	public Graphics2D createTemporaryGraphics(Map<String, Object> style,
			float opacity, mxRectangle bounds)
	{
		Graphics2D temporaryGraphics = (Graphics2D) g.create();

		// Applies the default translate
		temporaryGraphics.translate(translate.getX(), translate.getY());

		// Applies the rotation on the graphics object
		if (bounds != null)
		{
			double rotation = mxUtils.getDouble(style,
					mxConstants.STYLE_ROTATION, 0);

			if (rotation != 0)
			{
				temporaryGraphics.rotate(Math.toRadians(rotation),
						bounds.getCenterX(), bounds.getCenterY());
			}
		}

		// Applies the opacity to the graphics object
		if (opacity != 100)
		{
			temporaryGraphics.setComposite(AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER, opacity / 100));
		}

		return temporaryGraphics;
	}

}
