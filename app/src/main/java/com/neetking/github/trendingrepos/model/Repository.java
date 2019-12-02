package com.neetking.github.trendingrepos.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by neetking on 02/12/2019.
 */

public class Repository implements Parcelable {

    public Repository(Parcel in) {
        mDescription=in.readString();
        mName=in.readString();
        mStarsNumber=in.readInt();
    }

    @SerializedName("owner")
    @Expose
    private Owner owner;
    public Owner getOwner() {
        return owner;
    }

    @SerializedName("description")
    @Expose
    private String mName;

    @SerializedName("name")
    @Expose
    private String mDescription;

    @SerializedName("stargazers_count")
    @Expose
    private float mStarsNumber;




    public String getmName() {
        return mName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public float getmStarsNumber() {
        return mStarsNumber;
    }

    public void setmStarsNumber(float mStarsNumber) {
        this.mStarsNumber = mStarsNumber;
    }

    public class Owner {
        @SerializedName("login")
        private String login;
        @SerializedName("avatar_url")
        private String avatar_url;

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getAvatar_url() {
            return avatar_url;
        }
    }

    public static final Creator<Repository> CREATOR = new Creator<Repository>() {
        @Override
        public Repository createFromParcel(Parcel in) {
            return new Repository(in);
        }

        @Override
        public Repository[] newArray(int size) {
            return new Repository[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mDescription);
        parcel.writeString(mName);
        parcel.writeFloat(mStarsNumber);
    }


}

