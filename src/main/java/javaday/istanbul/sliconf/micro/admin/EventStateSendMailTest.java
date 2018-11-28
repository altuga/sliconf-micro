package javaday.istanbul.sliconf.micro.admin;

import javaday.istanbul.sliconf.micro.Template.Service.TemplateRepositoryService;
import javaday.istanbul.sliconf.micro.Template.Template;
import javaday.istanbul.sliconf.micro.admin.AdminMailService;
import javaday.istanbul.sliconf.micro.event.model.Event;
import javaday.istanbul.sliconf.micro.mail.IMailSendService;
import javaday.istanbul.sliconf.micro.mail.MailMessageProvider;
import javaday.istanbul.sliconf.micro.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
@RequiredArgsConstructor
@Component

public class EventStateSendMailTest {
    private final TemplateRepositoryService tempService;
    private final AdminMailService adminMailService;
    private final MailMessageProvider mailMessageProvider;

    @Autowired
    @Qualifier("gandiMailSendService")
    private IMailSendService mailSendService;
    @Value("${spring.profiles.active}")
    private String activeProfile;
    public ResponseMessage sendEmailUpcomingEvents(String email,String mailTitle,String templateCode) {

        ResponseMessage responseMessage = new ResponseMessage();
        email = mailMessageProvider.getMessage("email");
        mailTitle = mailMessageProvider.getMessage("mailTitle");

        Template template1 = tempService.findByCode(templateCode);//şablon kodu ile şablon çekiyoruz
        if(Objects.isNull(template1)||template1.getCode().isEmpty())
        {
            String mailBody=mailMessageProvider.getMessage("errorMailBody");
            responseMessage = mailSendService.sendMail(email, mailTitle, mailBody, new String[]{}, new String[]{});
            responseMessage.setMessage(mailBody);
            return responseMessage;
        }

        String mailBody = template1.getContent();

        LocalDateTime today7 =  LocalDateTime.now().plusDays(7);       //thisweekvariable
        LocalDateTime today14 = LocalDateTime.now().plusDays(14);      //nextweekvariable

        Set<Event> events = adminMailService.getEvents();
        if(Objects.isNull(events)||events.isEmpty())
        {
            mailBody=mailMessageProvider.getMessage("errorEventBody");
            responseMessage = mailSendService.sendMail(email, mailTitle, mailBody, new String[]{}, new String[]{});
            responseMessage.setMessage(mailBody);
            return responseMessage;
        }
        String nextWeek ="";   //hold next week
        String thisWeek ="";   //hold this week

        for (Event event:events)
        {
            if (event.getStartDate().compareTo(today7) <= 0)
            {
                thisWeek = thisWeek + "<li>"+event.getName() + "</li>";          //get events of this week
            }

        }
        for (Event event:events)
        {
            if(event.getStartDate().compareTo(today14) <= 0 && event.getStartDate().compareTo(today7) > 0 )
        {
            nextWeek = nextWeek+"<li>"+event.getName()+"</li>";           //get events of next week
        }
        }

        if  (Objects.isNull(thisWeek) || thisWeek.isEmpty())
        {
            thisWeek += "<li>No events to display for now </li>";           //when is not event
        }

        mailBody = mailBody.replace(mailMessageProvider.getMessage("targetThisWeek"), thisWeek);   //event of this week

        if (Objects.isNull(nextWeek) || nextWeek.isEmpty())
        {
            nextWeek += "<li>No events to display for now</li>";
        }

        mailBody = mailBody.replace(mailMessageProvider.getMessage("targetNextWeek"), nextWeek);    //event of next week
        String finalMailBody = mailBody.replace("{Name}","Nursel C.");
        responseMessage = mailSendService.sendMail(email, mailTitle, finalMailBody, new String[]{}, new String[]{});

        return responseMessage;
    }

}