package interview.patterns;

public class JavaInterviewParticipantFactory extends AbstractParticipantFactory {

    private static JavaInterviewParticipantFactory instance;

    private JavaInterviewParticipantFactory() {
    }

    public static synchronized JavaInterviewParticipantFactory getInstance() {
        if (instance == null) {
            instance = new JavaInterviewParticipantFactory();
        }
        return instance;
    }

    @Override
    AbstractParticipant createInterviewer() {
        var interviewer = new JavaParticipant("Jhon");
        return interviewer;
    }

    @Override
    AbstractParticipant createInterviewee() {
        var interviwee = new JavaParticipant("Partik");
        return interviwee;
    }
}
