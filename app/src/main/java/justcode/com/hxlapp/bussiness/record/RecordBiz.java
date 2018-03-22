package justcode.com.hxlapp.bussiness.record;


import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

import justcode.com.common.BaseApplication;
import justcode.com.common.db.DaoSession;
import justcode.com.common.db.entity.record.RecordEntity;
import justcode.com.common.db.entity.record.RecordEntityDao;
import justcode.com.hxlapp.ui.home.MainActivity;

public class RecordBiz {
    MainActivity activity;
    RecordEntityDao recordEntityDao;

    public RecordBiz(MainActivity activity) {
        this.activity = activity;
        BaseApplication application = (BaseApplication) activity.getApplication();
        DaoSession daoSession = application.getDaoSession();
        recordEntityDao = daoSession.getRecordEntityDao();
    }

    /**
     * 写入数据库
     *
     * @param recordEntity
     */
    public void write2DB(RecordEntity recordEntity) {
        recordEntityDao.insert(recordEntity);
    }

    /**
     * 查询指定时间的记录
     *
     * @param time
     * @return
     */
    public List<RecordEntity> getRecordEntitys(String time) {
        return recordEntityDao.queryRaw("where TIME_STR=?", time);
    }

    /**
     * 查询，某个时间后的记录
     */
    public List<RecordEntity> getRecordEntitys(long time) {
        QueryBuilder<RecordEntity> recordEntityQueryBuilder = recordEntityDao.queryBuilder();
        return recordEntityQueryBuilder.where(RecordEntityDao.Properties.Date.gt(time)).build().list();
    }

    /**
     * 查询某个时间段的记录
     *
     * @param start
     * @param end
     * @return
     */
    public List<RecordEntity> getRecordEntitys(long start, long end) {
        QueryBuilder<RecordEntity> recordEntityQueryBuilder = recordEntityDao.queryBuilder();
        WhereCondition and = recordEntityQueryBuilder.and(RecordEntityDao.Properties.Date.ge(start), RecordEntityDao.Properties.Date.lt(end));
        return recordEntityQueryBuilder.where(and).build().list();
    }

    /**
     * 查询所有记录
     *
     * @return
     */
    public List<RecordEntity> getRecordEntityAll() {
        return recordEntityDao.loadAll();
    }

    /**
     * 更新（修改记录）
     */
    public void updateRecordEntity(RecordEntity entity) {
        recordEntityDao.update(entity);
    }

    /**
     * 删除记录
     */
    public void delRecordEntity(RecordEntity entity) {
        recordEntityDao.delete(entity);
    }
}
