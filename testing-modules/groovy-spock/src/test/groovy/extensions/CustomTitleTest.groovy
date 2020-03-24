package extensions

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title


@Title("""This title is easy to read for humans""")
class CustomTitleTest extends Specification {

}

@Narrative("""
    as a user
    i want to save favourite items 
    and then get the list of them
""")
class NarrativeDescriptionTest extends Specification {

}
