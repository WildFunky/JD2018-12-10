package by.it.dziomin.jd02_08;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class DOMParcer {
    public static void main(String[] args) {
        String fileName = "src\\by\\it\\dziomin\\jd02_08\\CarRent.xml";
        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder=factory.newDocumentBuilder();
            Document doc=builder.parse(fileName);
            Element el=doc.getDocumentElement();
            printDom("", el);
        } catch (Exception e) {
            System.out.print("Ошибка! " + e.toString());
        }
    }

    private static void printDom(String prefix, Node node) {
        String text=node.getNodeValue();
        if (text!=null) {
            System.out.println(prefix + text.trim());
        }
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            printDom(prefix+node.getNodeName() + " > ", children.item(i));
        }
    }
}

