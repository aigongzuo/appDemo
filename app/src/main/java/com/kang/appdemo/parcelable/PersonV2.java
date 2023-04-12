package com.kang.appdemo.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class PersonV2 implements Parcelable {
    private String name;
    private int age;

    private int sex;

    private GroupDetailBo mGroupDetailBo;

    public PersonV2(String name, int age) {
        this.name = name;
        this.age = age;
        mGroupDetailBo = new GroupDetailBo("groupName");
    }

    protected PersonV2(Parcel in) {
        name = in.readString();
        age = in.readInt();
        mGroupDetailBo = in.readParcelable(GroupDetailBo.class.getClassLoader());
        sex = in.readInt();
    }

    public static final Creator<PersonV2> CREATOR = new Creator<PersonV2>() {
        @Override
        public PersonV2 createFromParcel(Parcel in) {
            return new PersonV2(in);
        }

        @Override
        public PersonV2[] newArray(int size) {
            return new PersonV2[size];
        }
    };

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public GroupDetailBo getGroupDetailBo() {
        return mGroupDetailBo;
    }

    public void setGroupDetailBo(GroupDetailBo groupDetailBo) {
        mGroupDetailBo = groupDetailBo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeParcelable(mGroupDetailBo, flags);
        dest.writeInt(sex);
    }
}

