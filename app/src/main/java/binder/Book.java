package binder;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    public int bookId; public String bookName;
    public double bookPrice;
    public Book (int bookId,String bookName,double bookPrice){
            this.bookId=bookId;
            this.bookName=bookName;
            this.bookPrice=bookPrice;
    }
    protected Book(Parcel in) {
            in.readInt();
            in.readString();
            in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookId);
        dest.writeString(bookName);
        dest.writeDouble(bookPrice);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }


    };


}
