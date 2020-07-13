package com.baeldung.rules.jess;

import com.baeldung.rules.jess.model.Answer;
import com.baeldung.rules.jess.model.Question;
import jess.Filter;
import jess.JessException;
import jess.Rete;

import java.util.Iterator;

public class JessWithData {
    public static final String RULES_BONUS_FILE = "bonus.clp";
    public static void main(String[] args) {
        Rete engine = new Rete();
        try {
            engine.reset();

            engine.batch(RULES_BONUS_FILE);

            prepareData(engine);

            engine.run();

            checkResults(engine);

        } catch (JessException e) {
            e.printStackTrace();
        }
    }

    private static void checkResults(Rete engine) {
        Iterator results = engine.getObjects(new Filter.ByClass(Answer.class));
        while (results.hasNext()) {
            Answer answer = (Answer) results.next();
            System.out.println(answer.toString());
        }
    }

    private static void prepareData(Rete engine) throws JessException {
        Question question = new Question("Can I have a bonus?", -5);
        System.out.println(question.toString());
        engine.add(question);
    }
}
