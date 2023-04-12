package com.kang.appdemo.parcelable;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class ParcelableDemo {
    public static void LoadDemo(Intent intent) {
        try {
//
//            Person myParcelable = new Person("Alice", 25);
//            Parcel parcel = Parcel.obtain();
//            myParcelable.writeToParcel(parcel, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
//            parcel.setDataPosition(0);
//            Person createdFromParcel = Person.CREATOR.createFromParcel(parcel);

            // 创建 Person 对象
//            GroupDetailBo bo = new GroupDetailBo("Group A", "Description A");
//            Person person = new Person("Alice", 25);
//            Log.e("kang", "hashCode:" + person.hashCode());
//            Bundle bundle = new Bundle();
//            bundle.putParcelable("user", person);
//            intent.putExtras(bundle);



//            System.out.println(newPerson.getGroupDetail().getGroupName()); // 输出 "Group A"
//            System.out.println(newPerson.getGroupDetail().getDescription()); // 输出 "Description A"
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void getData(Intent intent) {
//        Bundle bundle = intent.getExtras();
//        Person outPerson = bundle.getParcelable("user");
//        Log.e("kang", "hashCode:" + outPerson.hashCode());
//        Log.e("kang", "getGroupName:" + outPerson.getGroupDetailBo().getGroupName());
//        Log.e("kang", "equal:" +(person == outPerson));
    }
}
