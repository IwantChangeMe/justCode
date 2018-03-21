package justcode.com.hxlapp.bussiness.record;


import java.util.Date;

/**
 * 日记实体类
 */
public class RecordEntity {

    private String title;
    private String connent;
    private String timeStr;
    private Date date;
    private String impPath;

    public RecordEntity(String title, String connent, String timeStr, Date date) {
        this.title = title;
        this.connent = connent;
        this.timeStr = timeStr;
        this.date = date;
    }

    public RecordEntity() {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImpPath() {
        return impPath;
    }

    public void setImpPath(String impPath) {
        this.impPath = impPath;
    }
}
