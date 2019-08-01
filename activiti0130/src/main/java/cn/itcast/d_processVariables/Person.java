package cn.itcast.d_processVariables;

import java.io.Serializable;

public class Person implements Serializable{
    
    
    
    /**
     * 
     */
    private static final long serialVersionUID = 3816444255375694117L;

    private long id;
    
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    } 
    
    
}
