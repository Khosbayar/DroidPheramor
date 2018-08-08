package khosbayar.hs.com.droidpheramor.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String email;
    private String password;
    private String fullName;
    private String zip;
    private String feet;
    private String inch;
    private int gender;
    private String dob;
    private boolean interestMale;
    private boolean interestFemale;
    private int ageRangeMin;
    private int ageRangeMax;
    private int race;
    private int religion;
    private Uri img;

    //write object values to parcel for storage
    public void writeToParcel(Parcel dest, int flags) {
        //write all properties to the parcle
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(fullName);
        dest.writeString(zip);
        dest.writeString(feet);
        dest.writeString(inch);
        dest.writeInt(gender);
        dest.writeString(dob);
        dest.writeValue(interestMale);
        dest.writeValue(interestFemale);
        dest.writeInt(ageRangeMin);
        dest.writeInt(ageRangeMax);
        dest.writeInt(race);
        dest.writeInt(religion);
        dest.writeValue(img);

    }

    //constructor used for parcel
    public User(Parcel parcel) {
        //read and set saved values from parcel
        email = parcel.readString();
        password = parcel.readString();
        fullName = parcel.readString();
        zip = parcel.readString();
        feet = parcel.readString();
        inch = parcel.readString();
        gender = parcel.readInt();
        dob = parcel.readString();
        interestMale = (Boolean) parcel.readValue(Boolean.class.getClassLoader());
        interestFemale = (Boolean) parcel.readValue(Boolean.class.getClassLoader());
        ageRangeMin = parcel.readInt();
        ageRangeMax = parcel.readInt();
        race = parcel.readInt();
        religion = parcel.readInt();
        img = (Uri) parcel.readValue(Uri.class.getClassLoader());

    }

    public User(){}

    //creator - used when un-parceling our parcle (creating the object)
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {

        @Override
        public User createFromParcel(Parcel parcel) {
            return new User(parcel);
        }

        @Override
        public User[] newArray(int size) {
            return new User[0];
        }
    };

    //return hashcode of object
    public int describeContents() {
        return hashCode();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getFeet() {
        return feet;
    }

    public void setFeet(String feet) {
        this.feet = feet;
    }

    public String getInch() {
        return inch;
    }

    public void setInch(String inch) {
        this.inch = inch;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public boolean isInterestMale() {
        return interestMale;
    }

    public void setInterestMale(boolean interestMale) {
        this.interestMale = interestMale;
    }

    public boolean isInterestFemale() {
        return interestFemale;
    }

    public void setInterestFemale(boolean interestFemale) {
        this.interestFemale = interestFemale;
    }

    public int getAgeRangeMin() {
        return ageRangeMin;
    }

    public void setAgeRangeMin(int ageRangeMin) {
        this.ageRangeMin = ageRangeMin;
    }

    public int getAgeRangeMax() {
        return ageRangeMax;
    }

    public void setAgeRangeMax(int ageRangeMax) {
        this.ageRangeMax = ageRangeMax;
    }

    public int getRace() {
        return race;
    }

    public void setRace(int race) {
        this.race = race;
    }

    public int getReligion() {
        return religion;
    }

    public void setReligion(int religion) {
        this.religion = religion;
    }

    public Uri getImg() {
        return img;
    }

    public void setImg(Uri img) {
        this.img = img;
    }
}
