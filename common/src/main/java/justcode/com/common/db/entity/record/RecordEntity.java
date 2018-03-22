package justcode.com.common.db.entity.record;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 日记实体类
 */
@Entity
public class RecordEntity {
    @Id
    private long id;
    private String title;
    private String connent;
    private String timeStr;
    private long date;
    private String impPath;

    public RecordEntity(String title, String connent, String timeStr, long date) {
        this.title = title;
        this.connent = connent;
        this.timeStr = timeStr;
        this.date = date;
    }

    public RecordEntity() {
    }

    @Generated(hash = 1484530347)
    public RecordEntity(long id, String title, String connent, String timeStr,
            long date, String impPath) {
        this.id = id;
        this.title = title;
        this.connent = connent;
        this.timeStr = timeStr;
        this.date = date;
        this.impPath = impPath;
    }
    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getConnent() {
        return connent;
    }

    public void setConnent(String connent) {
        this.connent = connent;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getImpPath() {
        return impPath;
    }

    public void setImpPath(String impPath) {
        this.impPath = impPath;
    }
}
