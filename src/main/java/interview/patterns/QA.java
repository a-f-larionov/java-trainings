package interview.patterns;

public class QA {

    public final static QA empty = new QA("-", "-");
    private String question;
    private String answer;

    QA(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
