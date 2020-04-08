package com.baeldung.application.controller.cli;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.domain.AppMetadata;
import com.baeldung.domain.service.ChgRequestService;

@Component
public class ChgReqController {
    private static final Logger LOG = LoggerFactory.getLogger(ChgReqController.class);

    private final ChgRequestService chgRequestService;

    @Autowired
    public ChgReqController(ChgRequestService chgRequestService) {
        this.chgRequestService = chgRequestService;
    }

    public void createChangeRequest() {
        LOG.info("<<Create crq>>");
        var id = createCRQ();
        beginCRQ(id);
        doneCRQ(id);
    }

    private UUID createCRQ() {
        LOG.info("Generating a new app meta data");
        var charlie = new AppMetadata("charlie", "2.8.1", "random jira link", "prod-us-east-2", "rolling out new feature");
        LOG.info("Creating crq with charlie");
        return chgRequestService.createChgRequest(charlie);
    }

    private void beginCRQ(UUID id) {
        LOG.info("Transition inprogress with charlie");
        chgRequestService.beginChgRequest(id);
    }

    private void doneCRQ(UUID id) {
        LOG.info("Transition done with charlie");
        chgRequestService.doneChgRequest(id);
    }
}
