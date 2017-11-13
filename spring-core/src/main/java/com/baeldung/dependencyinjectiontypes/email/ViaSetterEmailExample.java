package com.baeldung.dependencyinjectiontypes.email;

/**
 * Created by CodeCouple.pl
 */
class ViaConstructorEmailExample {

    private final EmailService emailService;

    ViaConstructorEmailExample(EmailService emailService) {
        this.emailService = emailService;
    }

    boolean sendEmail(String content){
        return emailService.sendEmail(content);
    }
}
