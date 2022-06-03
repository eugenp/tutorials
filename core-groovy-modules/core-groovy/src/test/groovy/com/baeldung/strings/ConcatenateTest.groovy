import com.baeldung.strings.Concatenate;

class ConcatenateTest extends GroovyTestCase {
    
    void testSimpleConcat() {
        def name = new Concatenate()
        name.first = 'Joe';
        name.last = 'Smith';
        def expected = 'My name is Joe Smith'
        assertToString(name.doSimpleConcat(), expected)
    }
    
    void testConcatUsingGString() {
        def name = new Concatenate()
        name.first = "Joe";
        name.last = "Smith";
        def expected = "My name is Joe Smith"
        assertToString(name.doConcatUsingGString(), expected)
    }
    
    void testConcatUsingGStringClosures() {
        def name = new Concatenate()
        name.first = "Joe";
        name.last = "Smith";
        def expected = "My name is Joe Smith"
        assertToString(name.doConcatUsingGStringClosures(), expected)
    }
    
    void testConcatUsingStringConcatMethod() {
        def name = new Concatenate()
        name.first = "Joe";
        name.last = "Smith";
        def expected = "My name is Joe Smith"
        assertToString(name.doConcatUsingStringConcatMethod(), expected)
    }

    void testConcatUsingLeftShiftOperator() {
        def name = new Concatenate()
        name.first = "Joe";
        name.last = "Smith";
        def expected = "My name is Joe Smith"
        assertToString(name.doConcatUsingLeftShiftOperator(), expected)
    }

    void testConcatUsingArrayJoinMethod() {
        def name = new Concatenate()
        name.first = "Joe";
        name.last = "Smith";
        def expected = "My name is Joe Smith"
        assertToString(name.doConcatUsingArrayJoinMethod(), expected)
    }

    void testConcatUsingArrayInjectMethod() {
        def name = new Concatenate()
        name.first = "Joe";
        name.last = "Smith";
        def expected = "My name is Joe Smith"
        assertToString(name.doConcatUsingArrayInjectMethod(), expected)
    }

    void testConcatUsingStringBuilder() {
        def name = new Concatenate()
        name.first = "Joe";
        name.last = "Smith";
        def expected = "My name is Joe Smith"
        assertToString(name.doConcatUsingStringBuilder(), expected)
    }

    void testConcatUsingStringBuffer() {
        def name = new Concatenate()
        name.first = "Joe";
        name.last = "Smith";
        def expected = "My name is Joe Smith"
        assertToString(name.doConcatUsingStringBuffer(), expected)
    }

    void testConcatMultilineUsingStringConcatMethod() {
        def name = new Concatenate()
        name.first = '''Joe
                        Smith
                        ''';
        name.last = 'Junior';
        def expected = '''My name is Joe
                        Smith
                         Junior''';
        assertToString(name.doConcatUsingStringConcatMethod(), expected)
    }

    void testGStringvsClosure(){
        def first = "Joe";
        def last = "Smith";
        def eagerGString = "My name is $first $last"
        def lazyGString = "My name is ${-> first} ${-> last}"

        assert eagerGString == "My name is Joe Smith"
        assert lazyGString == "My name is Joe Smith"
        first = "David";
        assert eagerGString == "My name is Joe Smith"
        assert lazyGString == "My name is David Smith"
    }  
}
