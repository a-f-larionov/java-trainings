package interview.patterns;

public abstract class AbstractParticipantFactory {

    abstract AbstractParticipant createInterviewer();

    abstract AbstractParticipant createInterviewee();
}
