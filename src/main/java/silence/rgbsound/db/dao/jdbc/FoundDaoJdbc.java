package silence.rgbsound.db.dao.jdbc;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import silence.rgbsound.db.dao.FoundDao;

import javax.sql.DataSource;
import java.util.HashMap;

public class FoundDaoJdbc implements FoundDao {
    private DataSource dataSource;
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate =  new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public int getFoundMax(int mapIndex) {
        String sql = "select max(cov_count) from (" +
                "select count(1) cov_count " +
                "from found f inner join coverage_done cd on (f.coverage_done_id = cd.id) " +
                "where coverage_map_id = :map_id group by step_index_a, step_index_b)";
        HashMap<String, Object> names = new HashMap<>();
        names.put("map_id", mapIndex);
        try {
            return jdbcTemplate.queryForObject(sql, names, Integer.class);
        }
        catch (NullPointerException ex) {
            return 0;
        }
    }

    @Override
    public int getCellFoundCount(int mapIndex, int stepIndexA, int stepIndexB) {
        String sql = "select count(1) from found f inner join coverage_done cd on (f.coverage_done_id = cd.id) " +
                "where coverage_map_id = :map_id and step_index_a = :step_a and step_index_b = :step_b";
        HashMap<String, Object> names = new HashMap<>();
        names.put("map_id", mapIndex);
        names.put("step_a", stepIndexA);
        names.put("step_b", stepIndexB);
        return jdbcTemplate.queryForObject(sql, names, Integer.class);
    }
}
