package interview.patterns;

public class Main {

    public static void main(String[] args) {

        //@todo di container
        AbstractInteviewFactory javaInterviewFactory = JavaInterviewFactory.getInstance();
        AbstractParticipantFactory javaInterviewParticipantFactory = JavaInterviewParticipantFactory.getInstance();
        javaInterviewFactory.setParticipantFactory(javaInterviewParticipantFactory);

        client(javaInterviewFactory);

    }

    public static void client(AbstractInteviewFactory inteviewFactory) {
        AbstractInterview inteview =
                inteviewFactory.create();

        inteview.open();
        inteview.doQA();
        inteview.close();
    }
}