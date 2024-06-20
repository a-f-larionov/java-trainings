package interview.patterns;

public class JavaInterviewFactory extends AbstractInteviewFactory {

    private static JavaInterviewFactory instance;

    private JavaInterviewFactory() {
    }

    public static synchronized JavaInterviewFactory getInstance() {
        if (instance == null) {
            instance = new JavaInterviewFactory();
        }
        return instance;
    }

    @Override
    JavaInterview create() {

        var interview = new JavaInterview();

        interview.setInterviewer(participantFactory.createInterviewer());
        interview.setInterviewee(participantFactory.createInterviewee());

        return interview;
    }
}
