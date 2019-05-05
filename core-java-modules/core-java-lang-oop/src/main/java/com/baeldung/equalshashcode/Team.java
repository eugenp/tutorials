package com.baeldung.equalshashcode;

class Team {

    final String city;
    final String department;

    Team(String city, String department) {
        this.city = city;
        this.department = department;
    }

    @Override
    public final boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Team))
            return false;
        Team otherTeam = (Team)o;
        boolean cityEquals = (this.city == null && otherTeam.city == null)
          || this.city != null && this.city.equals(otherTeam.city);
        boolean departmentEquals = (this.department == null && otherTeam.department == null)
          || this.department != null && this.department.equals(otherTeam.department);
        return cityEquals && departmentEquals;
    }

    @Override
    public final int hashCode() {
        int result = 17;
        if (city != null) {
            result = 31 * result + city.hashCode();
        }
        if (department != null) {
            result = 31 * result + department.hashCode();
        }
        return result;
    }

}
