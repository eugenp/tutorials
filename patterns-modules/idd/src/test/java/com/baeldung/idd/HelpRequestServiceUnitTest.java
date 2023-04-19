package com.baeldung.idd;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class HelpRequestServiceUnitTest {

    HelpRequestService testHelpRequestService = new HelpRequestService() {
        @Override
        public HelpRequestDTO createHelpRequest(CreateHelpRequestDTO createHelpRequestDTO) {
            return new HelpRequestDTO(HelpRequestStatus.OPEN);
        }

        @Override
        public List<HelpRequestDTO> findAllByStatus(HelpRequestStatus status) {
            return List.of(new HelpRequestDTO(HelpRequestStatus.OPEN));
        }

        @Override
        public HelpRequestDTO updateHelpRequest(UpdateHelpRequestDTO updateHelpRequestDTO) {
            return new HelpRequestDTO(HelpRequestStatus.OPEN);
        }
    };

    @Test
    public void givenHelpRequest_whenCreateHelpRequest_thenHelpRequestIsCreated() {
        // given
        CreateHelpRequestDTO createHelpRequestDTO = new CreateHelpRequestDTO(HelpRequestStatus.OPEN);

        // when
        HelpRequestDTO helpRequestDTO = testHelpRequestService.createHelpRequest(createHelpRequestDTO);

        // then
        Assertions.assertThat(helpRequestDTO).isNotNull();
        Assertions.assertThat(helpRequestDTO.getStatus()).isEqualTo(HelpRequestStatus.OPEN);
    }

    @Test
    public void givenHelpRequestList_whenFindAllByStatus_shouldContainOnlyStatus() {
        HelpRequestService helpRequestService = new HelpRequestServiceImpl();
        List<HelpRequestDTO> allByStatusOpen = helpRequestService.findAllByStatus(HelpRequestStatus.OPEN);
        Assertions.assertThat(allByStatusOpen).extracting(HelpRequestDTO::getStatus).containsOnly(HelpRequestStatus.OPEN);
    }

}
