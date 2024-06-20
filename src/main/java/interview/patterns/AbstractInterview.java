package interview.patterns;

import java.time.LocalDateTime;

public abstract class AbstractInterview {

    private LocalDateTime begin;
    private AbstractParticipant interviewer;
    private AbstractParticipant interviewee;


    public void setBegin(LocalDateTime begin) {
        this.begin = begin;
    }

    public void setInterviewer(AbstractParticipant interviewer) {
        this.interviewer = interviewer;
    }

    public void setInterviewee(AbstractParticipant interviewee) {
        this.interviewee = interviewee;
    }

    public void open() {
        interviewer.sayHello();
        interviewee.sayHello();
    }

    public void doQA() {
        QA quest = interviewer.doQuest();
        QA answer = interviewee.doAnswer(quest.getQuestion());
        interviewer.examine(quest, answer);
    }

    public void close() {
        interviewer.sayGoodBay();
        interviewee.sayGoodBay();

        interviewer.sayResult();
    }
}
