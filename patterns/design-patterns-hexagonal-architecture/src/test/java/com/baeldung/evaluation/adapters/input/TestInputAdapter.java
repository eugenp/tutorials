package com.baeldung.evaluation.adapters.input;

import com.baeldung.evaluation.adapters.UseCaseInputAdapter;
import com.baeldung.evaluation.hexagon.api.IApplicationApi;

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
