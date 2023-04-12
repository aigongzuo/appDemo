package com.kang.appdemo.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {
    private String name;
    private int age;

    private GroupDetailBo mGroupDetailBo;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
//        mGroupDetailBo = new GroupDetailBo("groupName");
    }

    protected Person(Parcel in) {
        name = in.readString();
        age = in.readInt();
        mGroupDetailBo = in.readParcelable(GroupDetailBo.class.getClassLoader());
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

//    public GroupDetailBo getGroupDetailBo() {
//        return mGroupDetailBo;
//    }
//
//    public void setGroupDetailBo(GroupDetailBo groupDetailBo) {
//        mGroupDetailBo = groupDetailBo;
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
//        dest.writeInt(age);
//        dest.writeParcelable(mGroupDetailBo, flags);
    }
}

