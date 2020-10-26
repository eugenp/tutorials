package com.baeldung.equalshashcode;

/* (non-Javadoc)
* This class overrides equals, but it doesn't override hashCode.
*
* To see which problems this leads to:
* TeamUnitTest.givenMapKeyWithoutHashCode_whenSearched_thenReturnsWrongValue
*/
class WrongTeam {

    String city;
    String department;

    WrongTeam(String city, String department) {
        this.city = city;
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof WrongTeam))
            return false;
        WrongTeam otherTeam = (WrongTeam)o;
        return this.city == otherTeam.city
          && this.department == otherTeam.department;
    }

}
