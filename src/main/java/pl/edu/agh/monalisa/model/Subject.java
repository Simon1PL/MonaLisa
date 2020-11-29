package pl.edu.agh.monalisa.model;

import java.util.Collection;
import java.util.HashSet;

public class Subject {
    public String name;
    private Collection<Lab> labs = new HashSet<>();
    private Year year;

    public Subject(String name) {
        this.name = name;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public void addLab(Lab subject) {
        this.labs.add(subject);
    }

    public Collection<Lab> getLabs() {
        return labs;
    }
}
