(import com.baeldung.rules.jess.model.*)
(deftemplate Question       (declare (from-class Question)))
(deftemplate Answer       (declare (from-class Answer)))

(defrule avoid-overdraft "Give $50 to anyone overdrawn"
    ?q <- (Question { balance < 0 })
    =>
    (add (new Answer "Overdrawn bonus" (+ ?q.balance 50))))
