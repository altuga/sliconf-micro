package javaday.istanbul.sliconf.micro.steps.survey;

import cucumber.api.PendingException;
import cucumber.api.java.tr.Fakat;
import cucumber.api.java.tr.Ozaman;
import javaday.istanbul.sliconf.micro.CucumberConfiguration;
import javaday.istanbul.sliconf.micro.survey.model.Question;
import javaday.istanbul.sliconf.micro.survey.model.QuestionOption;
import javaday.istanbul.sliconf.micro.survey.service.SurveyService;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@Slf4j
@ContextConfiguration(classes = {CucumberConfiguration.class})
@WebAppConfiguration
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class AAA7 {

    @Autowired
    SurveyService surveyService;
    @Autowired
    private InitialData initialData;

    @Fakat("^Etkinlik sahibi anketteki sorularin ismini boş bırakmış ise$")
    public void etkinlikSahibiAnkettekiSorularinIsminiBoşBırakmışIse() throws Throwable {

        Question newQuestion = new Question();
        newQuestion.setText(null);
        newQuestion.setId("question2Id");
        List<Question> questions = new ArrayList<>();
        questions.add(newQuestion);
        QuestionOption questionOption1 = new QuestionOption();
        QuestionOption questionOption2 = new QuestionOption();
        questionOption1.setText("Seçenek 01"); questionOption1.setId("questionOption1Id");
        questionOption2.setText("Seçenek 02"); questionOption2.setId("questionOption2Id");
        List<QuestionOption> questionOptions = new ArrayList<>();
        questionOptions.add(questionOption1);
        questionOptions.add(questionOption2);
        newQuestion.setOptions(questionOptions);
        initialData.survey.setQuestions(questions);

        initialData.survey.getQuestions()
                .forEach(question -> assertThat(question.getText(), isEmptyOrNullString()));
    }

    @Ozaman("^Sistem anketi kayıt etmez ve ön tarafa soruların isimleri boş olamaz gibi bir hata mesajı gönderilir$")
    public void sistemAnketiKayıtEtmezVeÖnTarafaSorularınIsimleriBoşOlamazGibiBirHataMesajıGönderilir() throws Throwable {
        String exceptedErrorMessage = "Survey question text can not be empty! ";
        assertTrue(initialData.checkErrorMessages(exceptedErrorMessage, initialData.survey));

    }
}