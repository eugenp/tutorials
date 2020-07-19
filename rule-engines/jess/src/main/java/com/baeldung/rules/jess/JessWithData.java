package com.baeldung.rules.jess;

import com.baeldung.rules.jess.model.Answer;
import com.baeldung.rules.jess.model.Question;
import jess.Filter;
import jess.JessException;
import jess.Rete;
import lombok.extern.java.Log;

import java.util.Iterator;

@Log
public class JessWithData {
    public static final String RULES_BONUS_FILE = "bonus.clp";

    public static void main(String[] args) throws JessException {
        Rete engine = new Rete();
        engine.reset();

        engine.batch(RULES_BONUS_FILE);

        prepareData(engine);

        engine.run();

        checkResults(engine);
    }

    private static void checkResults(Rete engine) {
        Iterator results = engine.getObjects(new Filter.ByClass(Answer.class));
        while (results.hasNext()) {
            Answer answer = (Answer) results.next();
            log.info(answer.toString());
        }
    }

    private static void prepareData(Rete engine) throws JessException {
        Question question = new Question("Can I have a bonus?", -5);
        log.info(question.toString());
        engine.add(question);
    }
}
