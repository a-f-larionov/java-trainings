package interview.patterns;

public abstract class AbstractInteviewFactory {

    protected AbstractParticipantFactory participantFactory;

    abstract AbstractInterview create();

    public void setParticipantFactory(AbstractParticipantFactory participantFactory) {
        this.participantFactory = participantFactory;
    }
}
