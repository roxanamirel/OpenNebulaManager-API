package parsers;

import models.ServerModelON;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class OpenNebulaInfoXMLParser {

    public static ServerModelON parseServerInfo(String info) throws Exception {

        InputStream stream = new ByteArrayInputStream(info.getBytes());
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.parse(stream);

        Node state = document.getElementsByTagName("STATE").item(0);
        Node name = document.getElementsByTagName("NAME").item(0);
        Node id = document.getElementsByTagName("ID").item(0);

        Node memUsage = document.getElementsByTagName("USED_MEM").item(0);
        Node cpuUsage = document.getElementsByTagName("USED_CPU").item(0);
        Node diskUsage = document.getElementsByTagName("USED_DISK").item(0);
        Node currentCPUSpeed = document.getElementsByTagName("CPUSPEED").item(0);


        Node cpuMAX = document.getElementsByTagName("MAX_CPU").item(0);
        Node cpuMAXFrequency = document.getElementsByTagName("CPUSPEED").item(0);
        Node memMAX = document.getElementsByTagName("MAX_MEM").item(0);
        Node diskMAX = document.getElementsByTagName("MAX_DISK").item(0);


        ServerModelON serverModel = new ServerModelON();

        serverModel.setCloudID(Integer.parseInt(id.getTextContent()));

        serverModel.setName(name.getTextContent());

        serverModel.setState(Integer.parseInt(state.getTextContent()));

        serverModel.setCpuFrequency(Float.parseFloat(currentCPUSpeed.getTextContent()));

        serverModel.setTotalCpu((Integer.parseInt(cpuMAX.getTextContent()) / 100)
                * Integer.parseInt(cpuMAXFrequency.getTextContent()));
        serverModel.setUsedCpu(Integer.parseInt(cpuUsage.getTextContent()));

        serverModel.setTotalMem(Integer.parseInt(memMAX.getTextContent()));
        serverModel.setUsedMem(Integer.parseInt(memUsage.getTextContent()) / 1000);

        serverModel.setTotalDisk(Integer.parseInt(diskMAX.getTextContent()));
        serverModel.setUsedDisk(Integer.parseInt(diskUsage.getTextContent()));

        return serverModel;
    }
}
