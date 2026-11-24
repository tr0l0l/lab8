package ETU.WebGWT.shared;

import java.io.Serializable;

public class BusReader implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String fio;
    private String faculty;
    private boolean enrolled;

    public BusReader() {
    }

    public BusReader(String fio, String faculty, boolean enrolled) {
        this.fio = fio;
        this.faculty = faculty;
        this.enrolled = enrolled;
    }

    public String getFio() {
        return fio;
    }

    public String getFaculty() {
        return faculty;
    }

    public boolean isEnrolled() {
        return enrolled;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
}