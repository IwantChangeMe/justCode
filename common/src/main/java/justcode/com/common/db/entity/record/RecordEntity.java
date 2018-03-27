package justcode.com.common.db.entity.record;


import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 日记实体类
 */
@Entity
public class RecordEntity implements Parcelable {
    @Id(autoincrement = true)
    private Long id;
    private String title;
    private String connent;
    private String timeStr;
    private long date;
    @Generated(hash = 1275038389)
    public RecordEntity(Long id, String title, String connent, String timeStr,
            long date) {
        this.id = id;
        this.title = title;
        this.connent = connent;
        this.timeStr = timeStr;
        this.date = date;
    }
    @Generated(hash = 867663846)
    public RecordEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getConnent() {
        return this.connent;
    }
    public void setConnent(String connent) {
        this.connent = connent;
    }
    public String getTimeStr() {
        return this.timeStr;
    }
    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }
    public long getDate() {
        return this.date;
    }
    public void setDate(long date) {
        this.date = date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.title);
        dest.writeString(this.connent);
        dest.writeString(this.timeStr);
        dest.writeLong(this.date);
    }

    protected RecordEntity(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.title = in.readString();
        this.connent = in.readString();
        this.timeStr = in.readString();
        this.date = in.readLong();
    }

    public static final Parcelable.Creator<RecordEntity> CREATOR = new Parcelable.Creator<RecordEntity>() {
        @Override
        public RecordEntity createFromParcel(Parcel source) {
            return new RecordEntity(source);
        }

        @Override
        public RecordEntity[] newArray(int size) {
            return new RecordEntity[size];
        }
    };
}
