import com.baomidou.mybatisplus.annotation.DbType;

/**
 * @author Yegua
 */
public enum DbTypeEnum {

    MYSQL(DbType.MYSQL, "com.mysql.cj.jdbc.Driver"),

    POSTGRE_SQL(DbType.POSTGRE_SQL, "org.postgresql.Driver");

    private final DbType db;
    private final String dbDriver;

    DbTypeEnum(DbType dbType, String dbDriver) {
        this.db = dbType;
        this.dbDriver = dbDriver;
    }

    public DbType getDb() {
        return db;
    }

    public String getDbDriver() {
        return dbDriver;
    }

}