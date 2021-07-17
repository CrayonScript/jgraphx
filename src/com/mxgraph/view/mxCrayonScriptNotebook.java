package com.mxgraph.view;

import com.mxgraph.crayonscript.shapes.ColorCode;
import com.mxgraph.crayonscript.shapes.CrayonScriptOnEventShape;
import com.mxgraph.examples.swing.GraphEditor;
import com.mxgraph.examples.swing.editor.EditorPalette;
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
import java.util.ArrayList;

public class mxCrayonScriptNotebook {

    URL eventsUrl = null;
    URL functionsUrl = null;
    URL graphsUrl = null;
    URL objectsUrl = null;
    URL userPrefsUrl = null;

    public void setEventsUrl(URL url) { eventsUrl = url; }

    public void setFunctionsUrl(URL url) { functionsUrl = url; }

    public void setGraphsUrl(URL url) { graphsUrl = url; }

    public void setObjectsUrl(URL url) { objectsUrl = url; }

    public void setUserPrefsUrl(URL url) { userPrefsUrl = url; }

    public ArrayList<mxCrayonScriptEvent> parseEvents() {
        return null;
    }

    public ArrayList<mxCrayonScriptFunction> parseFunctions() {
        return null;
    }

    public ArrayList<mxCrayonScriptGraph> parseGraphs() {
        return null;
    }

    public ArrayList<mxCrayonScriptObject> parseObjects() {
        return null;
    }

    public mxCrayonScriptUserPrefs parseUserPrefs() {
        return null;
    }

    public void loadObjectsPalette(mxGraphComponent graphComponent, EditorPalette objectsPalette)
    {
        String name = mxConstants.CRAYONSCRIPT_NEW_OBJECT;
        // TODO: fix me to open a new graph editor file
        mxCell cell = graphComponent.createControlShape(name);
        URL iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/New.png");
        ImageIcon icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        objectsPalette.addTemplate(name, icon, cell);
    }

    public void loadFunctionsPalette(mxGraphComponent graphComponent, EditorPalette functionsPalette)
    {
        String name = mxConstants.CRAYONSCRIPT_NEW_FUNCTION;
        // TODO: fix me to open a new graph editor file
        mxCell cell = graphComponent.createControlShape(name);
        URL iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/New.png");
        ImageIcon icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        functionsPalette.addTemplate(name, icon, cell);
    }

    public void loadEventsPalette(mxGraphComponent graphComponent, EditorPalette eventsPalette)
    {
        String name = mxConstants.CRAYONSCRIPT_NEW_EVENT;
        // TODO: fix me to open a new graph editor file
        mxCell cell = graphComponent.createControlShape(name);
        URL iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/New.png");
        ImageIcon icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        eventsPalette.addTemplate(name, icon, cell);
    }

    public void loadGraphPalette(mxGraphComponent graphComponent, EditorPalette graphPalette) {
        String name = mxConstants.CRAYONSCRIPT_NEW_GRAPH;
        // TODO: fix me to open a new graph editor file
        mxCell cell = graphComponent.createControlShape(name);
        URL iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/New.png");
        ImageIcon icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        graphPalette.addTemplate(name, icon, cell);

        name = mxConstants.CRAYONSCRIPT_MAIN_GRAPH;
        // TODO: fix me to open a new graph editor file
        cell = graphComponent.createControlShape(name);
        iconUrl = GraphEditor.class.getResource("/com/mxgraph/crayonscript/images/MainGraph.png");
        icon = new GraphEditor.CustomImageIcon(iconUrl, ColorCode.DEFAULT_COLOR.color).imageIcon;
        graphPalette.addTemplate(name, icon, cell);
    }

    public void loadGraph(mxGraphComponent graphComponent) {
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
