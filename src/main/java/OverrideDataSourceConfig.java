import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.ITypeConvert;

/**
 *
 */
public class OverrideDataSourceConfig extends DataSourceConfig {

    private ITypeConvert typeConvert;

    @Override
    public ITypeConvert getTypeConvert() {
        if (null == typeConvert) {
            ITypeConvert tc = super.getTypeConvert();

            switch (getDbType()) {
                case MARIADB:
                case MYSQL:
                    tc = new OverrideMySqlTypeConvert();
                    break;
                default:
                    break;
            }

            typeConvert = tc;
        }
        return typeConvert;
    }
}
