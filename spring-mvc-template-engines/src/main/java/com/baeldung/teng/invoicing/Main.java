package com.baeldung.teng.invoicing;

import com.baeldung.teng.invoicing.domain.InvoiceRepository;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STRawGroupDir;

public class Main {

    public static void main(String[] args) throws Exception {
        STGroup.trackCreationEvents = true;

        STGroup group = new STRawGroupDir(
            "/Users/octavian/Workspace/tutorials/spring-mvc-template-engines/src/main/webapp/WEB-INF/view/st", "UTF-8",
            '$', '$');

        ST st = group.getInstanceOf("invoice");
        st.add("invoice", new InvoiceRepository().getInvoice("0213"));
        //st.inspect();
        System.out.println(st.render());
    }
}
