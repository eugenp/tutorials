package com.baeldung.hexagonal.adapters.input;

import com.baeldung.hexagonal.adapters.UseCaseInputAdapter;
import com.baeldung.hexagonal.api.IApplicationApi;

public class TestInputAdapter implements UseCaseInputAdapter {
        private final IApplicationApi applicationApi;

        public TestInputAdapter(IApplicationApi applicationApi){
                this.applicationApi = applicationApi;
        }

        public boolean execute(){
                applicationApi.execute("Command From Input Test Adapter.");
                return true;
        }
}
