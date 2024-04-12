package com.baeldung.xml.tohashmap;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.underscore.U;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;

public class XmlToHashMap {

    public Map<String, Employee> xmlToHashMapUsingXstream(String xml) {
        XStream xStream = new XStream();
        xStream.alias("employees", List.class);
        xStream.alias("employee", Employee.class);
        xStream.addPermission(AnyTypePermission.ANY);
        List<Employee> employees = (List<Employee>) xStream.fromXML(xml);
        return employees.stream()
            .collect(Collectors.toMap(Employee::getId, Function.identity()));
    }

    public Map<String, Employee> xmlToHashMapUsingUnderscore(String xml) {
        Map<String, Employee> employeeMap = new HashMap<>();
        Map<String, Object> employeeList = (Map<String, Object>) U.fromXmlMap(xml)
            .get("employees");
        List<LinkedHashMap<String, String>> list = (List<LinkedHashMap<String, String>>) employeeList.get("employee");
        parseXmlToMap(employeeMap, list);
        return employeeMap;
    }

    public Map<String, Employee> xmlToHashMapUsingJackson(String xml) throws JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();
        Map<String, Employee> employeeMap = new HashMap<>();
        Map<String, Object> map = xmlMapper.readValue(xml, Map.class);
        List<LinkedHashMap<String, String>> list = (List<LinkedHashMap<String, String>>) map.get("employee");
        parseXmlToMap(employeeMap, list);
        return employeeMap;
    }

    public Map<String, Employee> xmlToHashMapUsingJAXB(String xmlData) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Employees.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Employees employees = (Employees) unmarshaller.unmarshal(new StringReader(xmlData));
        return employees.getEmployeeList()
            .stream()
            .collect(Collectors.toMap(Employee::getId, Function.identity()));
    }

    public Map<String, Employee> xmlToHashMapUsingDOMParserXpath(String xmlData) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xmlData)));

        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression xPathExpr = xpath.compile("/employees/employee");
        NodeList nodes = (NodeList) xPathExpr.evaluate(doc, XPathConstants.NODESET);

        HashMap<String, Employee> map = new HashMap<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Element node = (Element) nodes.item(i);
            Employee employee = new Employee();
            employee.setId(node.getElementsByTagName("id")
                .item(0)
                .getTextContent());
            employee.setFirstName(node.getElementsByTagName("firstName")
                .item(0)
                .getTextContent());
            employee.setLastName(node.getElementsByTagName("lastName")
                .item(0)
                .getTextContent());
            map.put(employee.getId(), employee);
        }
        return map;
    }

    private static void parseXmlToMap(Map<String, Employee> employeeMap, List<LinkedHashMap<String, String>> list) {
        list.forEach(empMap -> {
            Employee employee = new Employee();
            for (Map.Entry<String, String> key : empMap.entrySet()) {
                switch (key.getKey()) {
                case "id":
                    employee.setId(key.getValue());
                    break;
                case "firstName":
                    employee.setFirstName(key.getValue());
                    break;
                case "lastName":
                    employee.setLastName(key.getValue());
                    break;
                default:
                    break;
                }
            }
            employeeMap.put(employee.getId(), employee);
        });
    }
}
