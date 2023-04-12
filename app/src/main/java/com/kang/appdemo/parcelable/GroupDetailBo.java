package com.kang.appdemo.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class GroupDetailBo implements Parcelable {
    private String groupName;

    public GroupDetailBo(String groupName) {
        this.groupName = groupName;
    }

    protected GroupDetailBo(Parcel in) {
        groupName = in.readString();
    }

    public static final Creator<GroupDetailBo> CREATOR = new Creator<GroupDetailBo>() {
        @Override
        public GroupDetailBo createFromParcel(Parcel in) {
            return new GroupDetailBo(in);
        }

        @Override
        public GroupDetailBo[] newArray(int size) {
            return new GroupDetailBo[size];
        }
    };


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(groupName);
    }
}

