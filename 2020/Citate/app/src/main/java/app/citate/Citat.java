package app.citate;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.IdRes;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "citat")
public class Citat implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String autor;
    private String text;
    private int nrAprecieri;
    @Ignore
    private Category category;

    public Citat(String autor, String text, int nrAprecieri) {
        this.autor = autor;
        this.text = text;
        this.nrAprecieri = nrAprecieri;
    }


    public Citat(String autor, String text, int nrAprecieri, Category category) {
        this.autor = autor;
        this.text = text;
        this.nrAprecieri = nrAprecieri;
        this.category = category;
    }

    protected Citat(Parcel in) {
        autor = in.readString();
        text = in.readString();
        nrAprecieri = in.readInt();
        category = Category.valueOf(in.readString());
    }

    public static final Creator<Citat> CREATOR = new Creator<Citat>() {
        @Override
        public Citat createFromParcel(Parcel in) {
            return new Citat(in);
        }

        @Override
        public Citat[] newArray(int size) {
            return new Citat[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNrAprecieri() {
        return nrAprecieri;
    }

    public void setNrAprecieri(int nrAprecieri) {
        this.nrAprecieri = nrAprecieri;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Citat{" +
                "autor='" + autor + '\'' +
                ", text='" + text + '\'' +
                ", nrAprecieri=" + nrAprecieri +
                ", category=" + category +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.autor);
        dest.writeString(this.text);
        dest.writeInt(this.nrAprecieri);
        dest.writeString(String.valueOf(this.category));
    }
}
