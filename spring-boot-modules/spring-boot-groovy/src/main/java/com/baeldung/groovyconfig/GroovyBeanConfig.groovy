package com.baeldung.groovyconfig;

beans {
    javaPesronBean(JavaPersonBean) {
        firstName = 'John'
        lastName = 'Doe'
        age ='32'
        eyesColor = 'blue'
        hairColor='black'
    }

    bandsBean(BandsBean) { bean->
        bean.scope = "singleton"
        bandsList=['Nirvana', 'Pearl Jam', 'Foo Fighters']
    }

    registerAlias("bandsBean","bands")
}
