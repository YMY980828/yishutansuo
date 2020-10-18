package util;

import java.io.Serializable;

public class User implements Serializable {
    //非必须
    private static final long serialVersionUID = 519067123721295773L;
    public int userId;
    public String userName;
    public boolean isMale;

    public User(){}
    public User(int id, String name,boolean isMale)
    {
        this.userName=name;
        this.isMale=isMale;
        this.userId=id;
    }


}
