package com.mxgraph.view;

import com.mxgraph.crayonscript.shapes.ColorCode;
import com.mxgraph.examples.swing.GraphEditor;
import com.mxgraph.examples.swing.editor.EditorPalette;
import com.mxgraph.model.CellFrameEnum;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;

public class mxCrayonScriptNotebook {

    Document notebookDocument = null;
    ArrayList<mxCrayonScriptFunctionPage> functionPages = new ArrayList<>();
    ArrayList<mxCrayonScriptGraphPage> graphPages = new ArrayList<>();
    mxCrayonScriptUserPrefPage userPrefPage = null;

    public mxCrayonScriptNotebook(String resourceRoot) {
        this.notebookDocument = loadPageDocument(String.format("%s/CrayonScript.notebook", resourceRoot));
        ArrayList<Document> functionDocuments = loadPageDocuments(String.format("%s/functions", resourceRoot));
        for (Document functionDocument: functionDocuments) {
            functionPages.add(new mxCrayonScriptFunctionPage(functionDocument));
        }
        ArrayList<Document> graphDocuments = loadPageDocuments(String.format("%s/graphs", resourceRoot));
        for (Document graphDocument: graphDocuments) {
            graphPages.add(new mxCrayonScriptGraphPage(graphDocument));
        }
        Document userPrefsDocument = loadPageDocument(String.format("%s/CrayonScript.userprefs", resourceRoot));
        userPrefPage = new mxCrayonScriptUserPrefPage(userPrefsDocument);
    }

    private ArrayList<Document> loadPageDocuments(String resourceRoot) {
        ArrayList<Document> documents = new ArrayList<>();
        URL pagesUrl = mxCrayonScriptNotebook.class.getResource(resourceRoot);
        try {
            if (pagesUrl != null) {
                File eventsFile = new File(pagesUrl.toURI());
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                for (File eventFile : eventsFile.listFiles()) {
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    Document document = db.parse(eventFile);
                    documents.add(document);
                }
            }
        } catch (ParserConfigurationException | URISyntaxException e) {
            throw new IllegalStateException(e);
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }
        return documents;
    }

    private Document loadPageDocument(String resourceRoot) {
        URL pagesUrl = mxCrayonScriptNotebook.class.getResource(resourceRoot);
        try {
            File documentFile = new File(pagesUrl.toURI());
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(documentFile);
            return document;
        } catch (ParserConfigurationException | URISyntaxException e) {
            throw new IllegalStateException(e);
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void loadObjects(mxGraphComponent graphComponent, EditorPalette objectsPalette)
    {
        String name = mxConstants.CRAYONSCRIPT_NEW_OBJECT;
        // TODO: fix me to open a new graph editor file
        mxCell cell = graphComponent.createControlShape(name);
        URL iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/New.png");
        ImageIcon icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        objectsPalette.addTemplate(name, icon, cell);
    }

    public void loadFunctions(mxGraphComponent graphComponent, EditorPalette functionsPalette)
    {
        String name = mxConstants.CRAYONSCRIPT_NEW_FUNCTION;
        // TODO: fix me to open a new graph editor file
        mxCell cell = graphComponent.createControlShape(name);
        URL iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/New.png");
        ImageIcon icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        functionsPalette.addTemplate(name, icon, cell);
    }

    public void loadGraph(mxGraphComponent graphComponent, EditorPalette graphsPalette) {
        String name = mxConstants.CRAYONSCRIPT_MAIN_GRAPH;
        // TODO: fix me to open a new graph editor file
        mxCell cell = graphComponent.createControlShape(name);
        URL iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/MainGraph.png");
        ImageIcon icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        graphsPalette.addTemplate(name, icon, cell);
    }

    public void loadBlocks(mxGraphComponent graphComponent, EditorPalette blocksPalette)
    {
        String variableSymbol = "[]";

        String name = mxConstants.CRAYONSCRIPT_SHAPE_EVENT;
        // TODO: fix me to open a new graph editor file
        mxCell cell = graphComponent.createEventShape(name, MessageFormat.format("{0} . {1}", variableSymbol, variableSymbol));
        URL iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/Event.png");
        ImageIcon icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        blocksPalette.addTemplate(name, icon, cell);

        name = mxConstants.CRAYONSCRIPT_SHAPE_ASSIGN;
        cell = graphComponent.createAssignmentShape(name, MessageFormat.format("{0} = {1}", variableSymbol, variableSymbol));
        iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/Assign.png");
        icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        blocksPalette.addTemplate(name, icon, cell);

        name = mxConstants.CRAYONSCRIPT_SHAPE_PROPERTY;
        cell = graphComponent.createPropertyShape(name, MessageFormat.format("{0} . {1}", variableSymbol, variableSymbol));
        iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/Property.png");
        icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        blocksPalette.addTemplate(name, icon, cell);

        name = mxConstants.CRAYONSCRIPT_SHAPE_FUNCTION;
        cell = graphComponent.createFunctionShape(name, MessageFormat.format("function", variableSymbol, variableSymbol));
        iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/Function.png");
        icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        blocksPalette.addTemplate(name, icon, cell);

        name = mxConstants.CRAYONSCRIPT_SHAPE_EQUALS;
        cell = graphComponent.createExpressionShape(name, MessageFormat.format("( {0} == {1} )", variableSymbol, variableSymbol));
        iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/Equals.png");
        icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        blocksPalette.addTemplate(name, icon, cell);

        name = mxConstants.CRAYONSCRIPT_SHAPE_NOTEQUALS;
        cell = graphComponent.createExpressionShape(name, MessageFormat.format("( {0} != {1} )", variableSymbol, variableSymbol));
        iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/NotEquals.png");
        icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        blocksPalette.addTemplate(name, icon, cell);

        name = mxConstants.CRAYONSCRIPT_SHAPE_GT;
        cell = graphComponent.createExpressionShape(name, MessageFormat.format("( {0} > {1} )", variableSymbol, variableSymbol));
        iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/GreaterThan.png");
        icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        blocksPalette.addTemplate(name, icon, cell);

        name = mxConstants.CRAYONSCRIPT_SHAPE_GT_OR_EQUALS;
        cell = graphComponent.createExpressionShape(name, MessageFormat.format("( {0} >= {1} )", variableSymbol, variableSymbol));
        iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/GreaterThanOrEquals.png");
        icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        blocksPalette.addTemplate(name, icon, cell);

        name = mxConstants.CRAYONSCRIPT_SHAPE_LT;
        cell = graphComponent.createExpressionShape(name, MessageFormat.format("( {0} < {1} )", variableSymbol, variableSymbol));
        iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/LessThan.png");
        icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        blocksPalette.addTemplate(name, icon, cell);

        name = mxConstants.CRAYONSCRIPT_SHAPE_LT_OR_EQUALS;
        cell = graphComponent.createExpressionShape(name, MessageFormat.format("( {0} <= {1} )", variableSymbol, variableSymbol));
        iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/LessThanOrEquals.png");
        icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        blocksPalette.addTemplate(name, icon, cell);

        name = mxConstants.CRAYONSCRIPT_SHAPE_AND;
        cell = graphComponent.createExpressionShape(name, MessageFormat.format("( {0} and {1} )", variableSymbol, variableSymbol));
        iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/And.png");
        icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        blocksPalette.addTemplate(name, icon, cell);

        name = mxConstants.CRAYONSCRIPT_SHAPE_OR;
        cell = graphComponent.createExpressionShape(name, MessageFormat.format("( {0} or {1} )", variableSymbol, variableSymbol));
        iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/Or.png");
        icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        blocksPalette.addTemplate(name, icon, cell);

        name = mxConstants.CRAYONSCRIPT_SHAPE_NOT;
        cell = graphComponent.createExpressionShape(name, MessageFormat.format("( not {0} )", variableSymbol));
        iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/Not.png");
        icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        blocksPalette.addTemplate(name, icon, cell);

        name = mxConstants.CRAYONSCRIPT_SHAPE_MOD;
        cell = graphComponent.createExpressionShape(name, MessageFormat.format("( {0} mod {1} )", variableSymbol, variableSymbol));
        iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/Mod.png");
        icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        blocksPalette.addTemplate(name, icon, cell);

        name = mxConstants.CRAYONSCRIPT_SHAPE_MIN;
        cell = graphComponent.createExpressionShape(name, MessageFormat.format("( min({0}, {1}) )", variableSymbol, variableSymbol));
        iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/Min.png");
        icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        blocksPalette.addTemplate(name, icon, cell);

        name = mxConstants.CRAYONSCRIPT_SHAPE_MAX;
        cell = graphComponent.createExpressionShape(name, MessageFormat.format("( max({0}, {1}) )", variableSymbol, variableSymbol));
        iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/Max.png");
        icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        blocksPalette.addTemplate(name, icon, cell);

        name = mxConstants.CRAYONSCRIPT_SHAPE_PARALLEL_EXTENSION;
        cell = graphComponent.createExtensionShape(name, mxConstants.CRAYONSCRIPT_SHAPE_RUN_PARALLEL);
        iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/ParallelVExtender.png");
        icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        blocksPalette.addTemplate(name, icon, cell);

        name = mxConstants.CRAYONSCRIPT_SHAPE_SEQUENTIAL_EXTENSION;
        cell = graphComponent.createExtensionShape(name, mxConstants.CRAYONSCRIPT_SHAPE_RUN_SEQUENTIAL);
        iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/SequentialVExtender.png");
        icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        blocksPalette.addTemplate(name, icon, cell);

        name = mxConstants.CRAYONSCRIPT_SHAPE_RUN_PARALLEL;
        cell = graphComponent.createStackShape(name);
        iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/Parallel.png");
        icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        blocksPalette.addTemplate(name, icon, cell);

        name = mxConstants.CRAYONSCRIPT_SHAPE_RUN_SEQUENTIAL;
        cell = graphComponent.createStackShape(name);
        iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/Sequential.png");
        icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        blocksPalette.addTemplate(name, icon, cell);

        name = mxConstants.CRAYONSCRIPT_SHAPE_RUN_SINGLE;
        cell = graphComponent.createStackShape(name);
        iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/Run.png");
        icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        blocksPalette.addTemplate(name, icon, cell);

        name = mxConstants.CRAYONSCRIPT_SHAPE_IF;
        cell = graphComponent.createControlShape(name);
        iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/If.png");
        icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        blocksPalette.addTemplate(name, icon, cell);

        name = mxConstants.CRAYONSCRIPT_SHAPE_ELSE_IF;
        cell = graphComponent.createControlShape(name);
        cell.setDropTargets(CellFrameEnum.INNER_1, CellFrameEnum.INNER_2);
        iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/ElseIf.png");
        icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        blocksPalette.addTemplate(name, icon, cell);

        name = mxConstants.CRAYONSCRIPT_SHAPE_WHILE;
        cell = graphComponent.createControlShape(name);
        iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/While.png");
        icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        blocksPalette.addTemplate(name, icon, cell);

        name = mxConstants.CRAYONSCRIPT_SHAPE_FOR;
        cell = graphComponent.createControlShape(name);
        iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/For.png");
        icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        blocksPalette.addTemplate(name, icon, cell);

        name = mxConstants.CRAYONSCRIPT_SHAPE_WAIT_FOR;
        cell = graphComponent.createWaitForShape(name, name);
        iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/WaitFor.png");
        icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        blocksPalette.addTemplate(name, icon, cell);
    }

    public void showGraph(mxGraphComponent graphComponent)
    {
        graphComponent.addTemplateCell();
        graphComponent.setConnectable(false);
        graphComponent.setZoomPolicy(mxGraphComponent.ZOOM_POLICY_WIDTH);
        graphComponent.setPreferPageSize(true);
        graphComponent.setPageScale(1.4);
        graphComponent.setVerticalPageCount(5);
        graphComponent.zoomTo(0.6, true);
        graphComponent.repaint();
    }

    static class mxCrayonScriptFunctionPage extends mxCrayonScriptAbstractPage {
        mxCrayonScriptFunctionPage(Document document) {
            super(document);
        }
    }

    static class mxCrayonScriptGraphPage extends mxCrayonScriptAbstractPage {
        mxCrayonScriptGraphPage(Document document) {
            super(document);
        }
    }

    static class mxCrayonScriptUserPrefPage extends mxCrayonScriptAbstractPage {
        mxCrayonScriptUserPrefPage(Document document) {
            super(document);
        }
    }

    static class mxCrayonScriptAbstractPage {
        mxCrayonScriptAbstractPage(Document document) {

        }
    }

}
