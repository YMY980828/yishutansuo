package util;

import android.os.Parcel;
import android.os.Parcelable;

public class Stu implements Parcelable {
    private String username;
    private int age;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Stu(){

    }
    protected Stu(Parcel in) {
        username = in.readString();
        age = in.readInt();
    }

    public static final Creator<Stu> CREATOR = new Creator<Stu>() {
        @Override
        public Stu createFromParcel(Parcel in) {
            return new Stu(in);
        }

        @Override
        public Stu[] newArray(int size) {
            return new Stu[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(username);
        parcel.writeInt(age);
    }



}
