package com.baeldung.evaluation.adapters.input;

import com.baeldung.evaluation.adapters.UseCaseInputAdapter;
import com.baeldung.evaluation.hexagon.api.IUseCasesApi;

public class RealInputAdapter implements UseCaseInputAdapter {
        private final IUseCasesApi applicationApi;

        public RealInputAdapter(IUseCasesApi applicationApi) {
                this.applicationApi = applicationApi;
        }

        public boolean execute(){
                System.out.println("Execution in real input adapter, using application api.");

                applicationApi.execute("Command From a Real Adapter.");

                return true;
        }
}
