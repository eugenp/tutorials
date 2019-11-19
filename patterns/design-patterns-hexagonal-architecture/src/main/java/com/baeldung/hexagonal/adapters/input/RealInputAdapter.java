package com.baeldung.hexagonal.adapters.input;

import com.baeldung.hexagonal.adapters.UseCaseInputAdapter;
import com.baeldung.hexagonal.api.IApplicationApi;

public class RealInputAdapter implements UseCaseInputAdapter {
        private final IApplicationApi applicationApi;

        public RealInputAdapter(IApplicationApi applicationApi) {
                this.applicationApi = applicationApi;
        }

        public boolean execute(){
                System.out.println("Execution in real input adapter, using application api.");

                applicationApi.execute("Command From a Real Adapter.");

                return true;
        }
}
