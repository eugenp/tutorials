package extensions

import mocks.ItemService
import spock.lang.Specification
import spock.lang.Subject


class SubjectTest extends Specification {

    @Subject
    ItemService itemService // initialization here...

}
