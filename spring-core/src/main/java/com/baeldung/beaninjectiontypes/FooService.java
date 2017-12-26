package com.baeldung.beaninjectiontypes;

public class FooService {

    private FooCollaborator1 fooCollaborator1;
    private FooCollaborator2 fooCollaborator2;

    public FooService(FooCollaborator1 fooCollaborator1) {
      this.fooCollaborator1 = fooCollaborator1;
    }

    public void setFooCollaborator2(FooCollaborator2 fooCollaborator2) {
      this.fooCollaborator2 = fooCollaborator2;
    }

    public FooCollaborator1 getFooCollaborator1() {
      return fooCollaborator1;
    }

    public FooCollaborator2 getFooCollaborator2() {
      return fooCollaborator2;
    }
}
