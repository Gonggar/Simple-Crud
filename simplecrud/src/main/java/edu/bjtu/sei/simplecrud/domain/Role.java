package edu.bjtu.sei.simplecrud.domain;



public class Role {

    private Long id;
    private String name;
    private String notes;
    

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setNotes(String note) {
        this.notes = note;
    }

    public String getNotes() {
        return notes;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + 
                ", notes='" + notes +
                '\'' +
                '}';
    }
}
