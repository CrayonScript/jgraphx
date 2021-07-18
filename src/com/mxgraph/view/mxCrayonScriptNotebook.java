package com.mxgraph.view;

import com.mxgraph.crayonscript.shapes.ColorCode;
import com.mxgraph.crayonscript.shapes.CrayonScriptOnEventShape;
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

    URL eventsUrl = null;
    URL functionsUrl = null;
    URL graphsUrl = null;
    URL objectsUrl = null;
    URL userPrefsUrl = null;
    URL notebookUrl = null;

    public mxCrayonScriptNotebook(String resourceRoot)
    {
        eventsUrl = mxCrayonScriptNotebook.class.getResource(String.format("%s/events", resourceRoot));
        functionsUrl = mxCrayonScriptNotebook.class.getResource(String.format("%s/functions", resourceRoot));
        graphsUrl = mxCrayonScriptNotebook.class.getResource(String.format("%s/graphs", resourceRoot));
        objectsUrl = mxCrayonScriptNotebook.class.getResource(String.format("%s/objects", resourceRoot));
        userPrefsUrl = mxCrayonScriptNotebook.class.getResource(String.format("%s/CrayonScript.userprefs", resourceRoot));
        notebookUrl = mxCrayonScriptNotebook.class.getResource(String.format("%s/CrayonScript.notebook", resourceRoot));
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

    public void loadEvents(mxGraphComponent graphComponent, EditorPalette eventsPalette)
    {
        String name = mxConstants.CRAYONSCRIPT_NEW_EVENT;
        // TODO: fix me to open a new graph editor file
        mxCell cell = graphComponent.createControlShape(name);
        URL iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/New.png");
        ImageIcon icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        eventsPalette.addTemplate(name, icon, cell);
    }

    public void loadGraph(mxGraphComponent graphComponent, EditorPalette graphsPalette) {
        String name = mxConstants.CRAYONSCRIPT_NEW_GRAPH;
        // TODO: fix me to open a new graph editor file
        mxCell cell = graphComponent.createControlShape(name);
        URL iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/New.png");
        ImageIcon icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        graphsPalette.addTemplate(name, icon, cell);

        name = mxConstants.CRAYONSCRIPT_MAIN_GRAPH;
        // TODO: fix me to open a new graph editor file
        cell = graphComponent.createControlShape(name);
        iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/MainGraph.png");
        icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        graphsPalette.addTemplate(name, icon, cell);
    }

    public void loadBlocks(mxGraphComponent graphComponent, EditorPalette blocksPalette)
    {
        String variableSymbol = "[]";

        String name = mxConstants.CRAYONSCRIPT_SHAPE_ASSIGN;
        mxCell cell = graphComponent.createAssignmentShape(name, MessageFormat.format("{0} = {1}", variableSymbol, variableSymbol));
        URL iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/Assign.png");
        ImageIcon icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
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
        cell = graphComponent.createExtensionShape(name, mxConstants.CRAYONSCRIPT_SHAPE_PARALLEL);
        iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/ParallelVExtender2.png");
        icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        blocksPalette.addTemplate(name, icon, cell);

        name = mxConstants.CRAYONSCRIPT_SHAPE_SEQUENTIAL_EXTENSION;
        cell = graphComponent.createExtensionShape(name, mxConstants.CRAYONSCRIPT_SHAPE_SEQUENTIAL);
        iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/SequentialVExtender2.png");
        icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        blocksPalette.addTemplate(name, icon, cell);

        name = mxConstants.CRAYONSCRIPT_SHAPE_PARALLEL;
        cell = graphComponent.createStackShape(name);
        iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/Parallel.png");
        icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        blocksPalette.addTemplate(name, icon, cell);

        name = mxConstants.CRAYONSCRIPT_SHAPE_SEQUENTIAL;
        cell = graphComponent.createStackShape(name);
        iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/Sequential.png");
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

    static class mxCrayonScriptEvent {

    }

    static class mxCrayonScriptFunction {

    }

    static class mxCrayonScriptGraph {

    }

    static class mxCrayonScriptObject {

    }

    static class mxCrayonScriptUserPrefs {

    }

//    try {
//        File eventsFile = new File(eventsUrl.toURI());
//        File functionsFile = new File(functionsUrl.toURI());
//        File graphsFile = new File(graphsUrl.toURI());
//        File objectsFile = new File(objectsUrl.toURI());
//        File userPrefsFile = new File(userPrefsUrl.toURI());
//
//        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//
//        for (File eventFile : eventsFile.listFiles()) {
//            DocumentBuilder db = dbf.newDocumentBuilder();
//            Document doc = db.parse(eventFile);
//            new mxCrayonScriptNotebook.mxCrayonScriptEvent(doc);
//        }
//        for (File functionFile : functionsFile.listFiles()) {
//
//        }
//        for (File graphFile : graphsFile.listFiles()) {
//
//        }
//        for (File objectFile : objectsFile.listFiles()) {
//
//        }
//
//    } catch (
//    ParserConfigurationException e) {
//        throw new IllegalStateException(e);
//    } catch (
//    URISyntaxException e) {
//        throw new IllegalStateException(e);
//    } catch (
//    IOException e) {
//        e.printStackTrace();
//    } catch (
//    SAXException e) {
//        e.printStackTrace();
//    }

}
