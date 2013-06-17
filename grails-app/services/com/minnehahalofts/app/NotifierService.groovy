package com.minnehahalofts.app

class NotifierService {
    static transactional = false

    def mailService

    def informSubmission(inquiry, email, sub) {
        mailService.sendMail {
            to email
            subject sub
            body inquiry
        }
    }

    def contactUser(userEmail, title, content){
        mailService.sendMail {
            to userEmail
            subject title
            body content
        }
    }

    def registerRequest(objIns){
        switch(objIns.class.toString().split('\\.')[-1]){
            case 'Review':
                log.warn('rentUni: ' + objIns.rentalUnit);
                String message = 'From: ' + objIns.submittedBy + '\n Rental Unit: ' + objIns.rentalUnit + '\n Content: ' + objIns.content
                informSubmission( message, 'contact@kapasoft.com', 'Review Received')
                break;
            case 'Inquiry':
                String message = 'From: ' + objIns.userEmail + '\n Rental Unit: ' + objIns.rentalUnit + '\n Content: ' + objIns.inqContent
                informSubmission( message, 'contact@kapasoft.com', 'Inquiry Received')
                contactUser(objIns.userEmail,"Thank You For Inquiry", "Thank you. We received your inquiry and we will respond as soon as possible")
                break;
            case 'Contact':
                String message = 'From: ' + objIns.name + ' (' + objIns.email + ' and ' + objIns.phone + ')' + '\n ' + '\n Message: ' + objIns.message
                informSubmission( message, 'contact@kapasoft.com', 'ContactUs Received')
                break;
        }
        log.warn('*******New insert');
    }
}
