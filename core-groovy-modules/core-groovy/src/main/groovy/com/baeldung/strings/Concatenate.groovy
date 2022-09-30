package com.baeldung.strings;

class Concatenate {
    String first = 'Hello'
    String last = 'Groovy'
  
    String doSimpleConcat() {
        return 'My name is ' + first + ' ' + last
    }

    String doConcatUsingGString() {
        return "My name is $first $last"
    }

    String doConcatUsingGStringClosures() {
        return "My name is ${-> first} ${-> last}"
    }

    String doConcatUsingStringConcatMethod() {
        return 'My name is '.concat(first).concat(' ').concat(last)
    }

    String doConcatUsingLeftShiftOperator() {
        return 'My name is ' << first << ' ' << last
    }
    
    String doConcatUsingArrayJoinMethod() {
        return ['My name is', first, last].join(' ')
    }

    String doConcatUsingArrayInjectMethod() {
        return  [first,' ', last]
          .inject(new StringBuffer('My name is '), { initial, name -> initial.append(name); return initial }).toString()
    }
 
    String doConcatUsingStringBuilder() {
        return new StringBuilder().append('My name is ').append(first).append(' ').append(last)
    }

    String doConcatUsingStringBuffer() {
        return new StringBuffer().append('My name is ').append(first).append(' ').append(last)
    }
}