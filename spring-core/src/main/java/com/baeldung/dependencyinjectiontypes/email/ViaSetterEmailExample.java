package com.baeldung.dependencyinjectiontypes.email;


class ViaSetterEmailExample {

    private EmailService emailService;

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    boolean sendEmail(String content){
        return emailService.sendEmail(content);
    }
}
