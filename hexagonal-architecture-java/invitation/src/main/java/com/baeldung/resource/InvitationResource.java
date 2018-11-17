package com.baeldung.resource;

import com.baeldung.domain.Invitation;
import com.baeldung.service.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invitation")
public class InvitationResource {

    @Autowired
    InvitationService invitationService;

    @PostMapping
    public ResponseEntity createBirthdayInvitation(@RequestBody Invitation invitation) {

        Invitation invitationResponse = invitationService.createInvitation(invitation);

        if (null != invitationResponse) {
            return new ResponseEntity(invitationResponse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity("Issue while writing invitation message", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity getBirthdayInvitation(@RequestParam String invitationId) {

        Invitation invitationResponse = invitationService.getInvitation(invitationId);

        if (null != invitationResponse) {
            return new ResponseEntity(invitationResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity("No invitation exists", HttpStatus.BAD_REQUEST);
        }
    }

}
