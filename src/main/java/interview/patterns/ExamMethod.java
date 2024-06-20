package interview.patterns;

public class ExamMethod {

    boolean examine(QA quest, QA answer) {
        return quest.getAnswer().equals(answer.getAnswer());
    }
}
