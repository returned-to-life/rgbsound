package silence.rgbsound.db.dao.jdbc;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import silence.rgbsound.db.CoverageMap;
import silence.rgbsound.db.dao.CoverageMapDao;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class CoverageMapDaoJdbc implements CoverageMapDao {

    private DataSource dataSource;
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public static final class CoverageMapMapper implements RowMapper<CoverageMap> {
        @Override
        public CoverageMap mapRow(ResultSet resultSet, int i) throws SQLException {
            CoverageMap res = new CoverageMap();
            res.setId(resultSet.getLong("id"));
            res.setFreqStart(resultSet.getDouble("freq_start"));
            res.setFreqEnd(resultSet.getDouble("freq_end"));
            res.setStepFactor(resultSet.getDouble("step_factor"));
            res.setStepWidth(resultSet.getInt("step_width"));
            return res;
        }
    }

    @Override
    public CoverageMap getCoverageMap(int index) {
        String sql = "select id, freq_start, freq_end, step_factor, step_width from coverage_map where id = :map_id";
        HashMap<String, Object> names = new HashMap<>();
        names.put("map_id", index);
        List<CoverageMap> res = jdbcTemplate.query(sql, names, new CoverageMapMapper());
        return res.get(0);
    }
}
