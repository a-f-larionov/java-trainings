package interview.patterns;

import java.util.List;
import java.util.Random;

public abstract class AbstractParticipant {
    protected String name;

    public List<QA> qaList;

    private int qaScore = 0;
    private int qaCount = 0;

    ExamMethod examDelegat = new ExamMethod();

    public AbstractParticipant(String name) {
        this.name = name;
    }

    public void sayHello() {
        say("Hello !");
    }

    public void sayGoodBay() {
        say("Goodbye!");
    }

    public QA doQuest() {
        qaCount++;
        var qa = qaList.get((new Random()).nextInt(qaList.size()));
        say("Question is: " + qa.getQuestion());
        return qa;
    }

    public QA doAnswer(String questionText) {
        var qa = qaList.stream()
                .filter(qa_ -> qa_.getQuestion().equals(questionText))
                .findFirst()
                .orElse(QA.empty);

        say("Answer is: " + qa.getAnswer());
        return qa;
    }

    private void say(String speach) {
        System.out.println(name + " say: " + speach);
    }

    public void examine(QA quest, QA answer) {
        if (examDelegat.examine(quest, answer)) {
            qaScore++;
        }
    }

    public void sayResult() {
        System.out.println(" score: " + qaScore + " of " + qaCount);
    }
}
