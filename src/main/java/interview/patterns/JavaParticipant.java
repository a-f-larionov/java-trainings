package interview.patterns;

import java.util.List;

public class JavaParticipant extends AbstractParticipant {

    JavaParticipant(String name) {
        super(name);
        qaList = List.of(
                new QA("quest", "answer")
        );
        System.out.println("Java Participant created: " + name);
    }

}
