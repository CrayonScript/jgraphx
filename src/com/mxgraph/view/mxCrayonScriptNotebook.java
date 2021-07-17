package com.mxgraph.view;

import com.mxgraph.crayonscript.shapes.CrayonScriptOnEventShape;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

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
