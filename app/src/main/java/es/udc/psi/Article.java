package es.udc.psi;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Article implements Parcelable {
    String title, subTitle, description;

    public Article(String title, String subTitle, String description) {
        this.title = title;
        this.subTitle = subTitle;
        this.description = description;
    }
    public Article(int i){
        this.title = "title" + i;
        this.subTitle = "subTitle" + i;
        this.description = "description" + i;
    }

    protected Article(Parcel in) {
        title = in.readString();
        subTitle = in.readString();
        description = in.readString();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {

        dest.writeString(title);
        dest.writeString(subTitle);
        dest.writeString(description);
    }
}
