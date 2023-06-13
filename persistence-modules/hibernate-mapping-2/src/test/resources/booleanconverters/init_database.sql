CREATE TABLE Question (
    id UUID,
    content VARCHAR,
    correctAnswer CHAR,
    shouldBeAsked CHAR,
    isEasy TINYINT,
    wasAskedBefore CHAR,
    PRIMARY KEY (id)
)
