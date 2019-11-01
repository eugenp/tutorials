package com.baeldung.wicket.examples.cafeaddress;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CafeAddress extends WebPage {
    private String selectedCafe;
    private Address address;
    private Map<String, Address> cafeNamesAndAddresses = new HashMap<>();

    public CafeAddress(final PageParameters parameters) {
        super(parameters);
        initCafes();

        ArrayList<String> cafeNames = new ArrayList<>(cafeNamesAndAddresses.keySet());
        selectedCafe = cafeNames.get(0);
        address = new Address(cafeNamesAndAddresses.get(selectedCafe).getAddress());

        final Label addressLabel = new Label("address", new PropertyModel<String>(this.address, "address"));
        addressLabel.setOutputMarkupId(true);

        final DropDownChoice<String> cafeDropdown = new DropDownChoice<>("cafes", new PropertyModel<>(this, "selectedCafe"), cafeNames);
        cafeDropdown.add(new AjaxFormComponentUpdatingBehavior("onchange") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                String name = (String) cafeDropdown.getDefaultModel().getObject();
                address.setAddress(cafeNamesAndAddresses.get(name).getAddress());
                target.add(addressLabel);
            }
        });

        add(addressLabel);
        add(cafeDropdown);

    }

    private void initCafes() {
        this.cafeNamesAndAddresses.put("Linda's Cafe", new Address("35 Bower St."));
        this.cafeNamesAndAddresses.put("Old Tree", new Address("2 Edgware Rd."));
    }

    class Address implements Serializable {
        private String sAddress = "";

        public Address(String address) {
            this.sAddress = address;
        }

        String getAddress() {
            return this.sAddress;
        }

        void setAddress(String address) {
            this.sAddress = address;
        }
    }
}
