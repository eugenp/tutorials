package com.baeldung.idd;

import java.util.List;

public class HelpRequestServiceImpl implements HelpRequestService {

    @Override
    public HelpRequestDTO createHelpRequest(CreateHelpRequestDTO createHelpRequestDTO) {
        // here goes the implementation
        return new HelpRequestDTO(createHelpRequestDTO.getStatus());
    }

    @Override
    public List<HelpRequestDTO> findAllByStatus(HelpRequestStatus status) {
        // here goes the implementation
        return List.of(new HelpRequestDTO(status), new HelpRequestDTO(status));
    }

    @Override
    public HelpRequestDTO updateHelpRequest(UpdateHelpRequestDTO updateHelpRequestDTO) {
        // here goes the implementation
        return new HelpRequestDTO(updateHelpRequestDTO.getStatus());
    }
}
