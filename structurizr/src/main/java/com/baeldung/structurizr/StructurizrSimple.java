package com.baeldung.structurizr;

import java.io.File;
import java.io.StringWriter;

import com.structurizr.Workspace;
import com.structurizr.analysis.ComponentFinder;
import com.structurizr.analysis.ReferencedTypesSupportingTypesStrategy;
import com.structurizr.analysis.SourceCodeComponentFinderStrategy;
import com.structurizr.analysis.SpringComponentFinderStrategy;
import com.structurizr.io.WorkspaceWriterException;
import com.structurizr.io.plantuml.PlantUMLWriter;
import com.structurizr.model.Component;
import com.structurizr.model.Container;
import com.structurizr.model.Model;
import com.structurizr.model.Person;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.model.Tags;
import com.structurizr.view.ComponentView;
import com.structurizr.view.ContainerView;
import com.structurizr.view.Routing;
import com.structurizr.view.Shape;
import com.structurizr.view.Styles;
import com.structurizr.view.SystemContextView;
import com.structurizr.view.View;
import com.structurizr.view.ViewSet;

public class StructurizrSimple {

    public static final String PAYMENT_TERMINAL = "Payment Terminal";
    public static final String FRAUD_DETECTOR = "Fraud Detector";
    public static final String SOFTWARE_SYSTEM_VIEW = "SoftwareSystemView";
    public static final String CONTAINER_VIEW = "ContainerView";
    public static final String COMPONENT_VIEW = "ComponentView";
    public static final String JVM2_COMPONENT_VIEW = "JVM2ComponentView";

    public static void main(String[] args) throws Exception {
        Workspace workspace = getSoftwareSystem();

        addContainers(workspace);
        addComponents(workspace);
        addSpringComponents(workspace);
        exportToPlantUml(workspace.getViews().getViewWithKey(SOFTWARE_SYSTEM_VIEW));
        exportToPlantUml(workspace.getViews().getViewWithKey(CONTAINER_VIEW));
        exportToPlantUml(workspace.getViews().getViewWithKey(COMPONENT_VIEW));

        exportToPlantUml(workspace.getViews().getViewWithKey(JVM2_COMPONENT_VIEW));

        addStyles(workspace.getViews());
        //uploadToExternal(workspace);
    }

    private static void addSpringComponents(Workspace workspace) throws Exception {
        Container jvm2 = workspace.getModel().getSoftwareSystemWithName(PAYMENT_TERMINAL).getContainerWithName("JVM-2");
        findComponents(jvm2);

        ComponentView view = workspace.getViews().createComponentView(jvm2, JVM2_COMPONENT_VIEW, "JVM2ComponentView");
        view.addAllComponents();

    }

    private static void findComponents(Container jvm) throws Exception {
        ComponentFinder componentFinder = new ComponentFinder(
                jvm,
                "com.baeldung.structurizr",
                new SpringComponentFinderStrategy(
                        new ReferencedTypesSupportingTypesStrategy()
                ),
                new SourceCodeComponentFinderStrategy(new File("."), 150));

        componentFinder.findComponents();
    }

    private static void addComponents(Workspace workspace) {
        Model model = workspace.getModel();

        SoftwareSystem paymentTerminal = model.getSoftwareSystemWithName(PAYMENT_TERMINAL);
        Container jvm1 = paymentTerminal.getContainerWithName("JVM-1");

        Component jaxrs = jvm1.addComponent("jaxrs-jersey", "restful webservice implementation", "rest");
        Component gemfire = jvm1.addComponent("gemfire", "Clustered Cache Gemfire", "cache");
        Component hibernate = jvm1.addComponent("hibernate", "Data Access Layer", "jpa");

        jaxrs.uses(gemfire, "");
        gemfire.uses(hibernate, "");

        ComponentView componentView = workspace.getViews().createComponentView(jvm1, COMPONENT_VIEW, "JVM Components");
        componentView.addAllComponents();
    }

    private static void addContainers(Workspace workspace) {
        Model model = workspace.getModel();

        SoftwareSystem paymentTerminal = model.getSoftwareSystemWithName(PAYMENT_TERMINAL);
        Container f5 = paymentTerminal.addContainer("Payment Load Balancer", "Payment Load Balancer", "F5");
        Container jvm1 = paymentTerminal.addContainer("JVM-1", "JVM-1", "Java Virtual Machine");
        Container jvm2 = paymentTerminal.addContainer("JVM-2", "JVM-2", "Java Virtual Machine");
        Container jvm3 = paymentTerminal.addContainer("JVM-3", "JVM-3", "Java Virtual Machine");
        Container oracle = paymentTerminal.addContainer("oracleDB", "Oracle Database", "RDBMS");

        f5.uses(jvm1, "route");
        f5.uses(jvm2, "route");
        f5.uses(jvm3, "route");

        jvm1.uses(oracle, "storage");
        jvm2.uses(oracle, "storage");
        jvm3.uses(oracle, "storage");

        ContainerView view = workspace.getViews().createContainerView(paymentTerminal, CONTAINER_VIEW, "Container View");
        view.addAllContainers();
    }

    private static void exportToPlantUml(View view) throws WorkspaceWriterException {
        StringWriter stringWriter = new StringWriter();
        PlantUMLWriter plantUMLWriter = new PlantUMLWriter();
        plantUMLWriter.write(view, stringWriter);
        System.out.println(stringWriter.toString());
    }

    private static Workspace getSoftwareSystem() {
        Workspace workspace = new Workspace("Payment Gateway", "Payment Gateway");
        Model model = workspace.getModel();

        Person user = model.addPerson("Merchant", "Merchant");
        SoftwareSystem paymentTerminal = model.addSoftwareSystem(PAYMENT_TERMINAL, "Payment Terminal");
        user.uses(paymentTerminal, "Makes payment");
        SoftwareSystem fraudDetector = model.addSoftwareSystem(FRAUD_DETECTOR, "Fraud Detector");
        paymentTerminal.uses(fraudDetector, "Obtains fraud score");

        ViewSet viewSet = workspace.getViews();

        SystemContextView contextView = viewSet.createSystemContextView(workspace.getModel().getSoftwareSystemWithName(PAYMENT_TERMINAL), SOFTWARE_SYSTEM_VIEW, "Payment Gateway Diagram");
        contextView.addAllElements();

        return workspace;
    }

    private static void addStyles(ViewSet viewSet) {
        Styles styles = viewSet.getConfiguration().getStyles();
        styles.addElementStyle(Tags.ELEMENT).color("#000000");
        styles.addElementStyle(Tags.PERSON).background("#ffbf00").shape(Shape.Person);
        styles.addElementStyle(Tags.CONTAINER).background("#facc2E");
        styles.addRelationshipStyle(Tags.RELATIONSHIP).routing(Routing.Orthogonal);

        styles.addRelationshipStyle(Tags.ASYNCHRONOUS).dashed(true);
        styles.addRelationshipStyle(Tags.SYNCHRONOUS).dashed(false);
    }
}
