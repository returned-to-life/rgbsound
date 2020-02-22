package silence.rgbsound.db.dao.jdbc;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import silence.rgbsound.client.control.MapCellCounter;
import silence.rgbsound.db.CoverageDone;
import silence.rgbsound.db.CoverageMap;
import silence.rgbsound.db.Found;
import silence.rgbsound.db.dao.CoverageDoneDao;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CoverageDoneDaoJdbc implements CoverageDoneDao {
    private DataSource dataSource;
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public int getCoverageMax(int mapIndex)
    {
        String sql = "select max(cov_count) from (select count(1) cov_count from coverage_done where coverage_map_id = :map_id group by step_index_a, step_index_b)";
        HashMap<String, Object> names = new HashMap<>();
        names.put("map_id", mapIndex);
        try {
            int res = jdbcTemplate.queryForObject(sql, names, Integer.class);
            return res;
        }
        catch (NullPointerException ex) {
            return 0;
        }
    }

    @Override
    public int getCellCoverageCount(int mapIndex, int stepIndexA, int stepIndexB) {
        String sql = "select count(1) from coverage_done where coverage_map_id = :map_id and step_index_a = :step_a and step_index_b = :step_b";
        HashMap<String, Object> names = new HashMap<>();
        names.put("map_id", mapIndex);
        names.put("step_a", stepIndexA);
        names.put("step_b", stepIndexB);
        return jdbcTemplate.queryForObject(sql, names, Integer.class);
    }

    @Override
    public List<MapCellCounter> getAllCellsCoverageCount(int mapIndex) {
        String sql = "select count(1) as count, step_index_a, step_index_b from coverage_done where coverage_map_id = :map_id " +
                "group by step_index_a, step_index_b";
        HashMap<String, Object> names = new HashMap<>();
        names.put("map_id", mapIndex);
        return jdbcTemplate.query(sql, names, new MapCellCounter.MapCellCounterMapper());
    }

    public class InsertCoverageDone extends SqlUpdate {
        public static final String INSERT_COVERAGE_DONE =
                "insert into coverage_done (coverage_map_id, step_index_a, step_index_b, user_id, timestamp, comment)" +
                        " values (:coverage_map_id, :step_index_a, :step_index_b, :user_id, :timestamp, :comment)";
        public InsertCoverageDone(DataSource dataSource) {
            super(dataSource, INSERT_COVERAGE_DONE);
            declareParameter(new SqlParameter("coverage_map_id", Types.INTEGER));
            declareParameter(new SqlParameter("step_index_a", Types.INTEGER));
            declareParameter(new SqlParameter("step_index_b", Types.INTEGER));
            declareParameter(new SqlParameter("user_id", Types.INTEGER));
            declareParameter(new SqlParameter("timestamp", Types.TIMESTAMP));
            declareParameter(new SqlParameter("comment", Types.VARCHAR));
            setGeneratedKeysColumnNames(new String("id"));
            setReturnGeneratedKeys(true);
        }
    }
    public class InsertCoverageFounds extends BatchSqlUpdate {
        public static final String INSERT_COVERAGE_FOUND =
                "insert into found (coverage_done_id, freq_a, freq_b, phase_matters) " +
                        "values (:coverage_done_id, :freq_a, :freq_b, :phase_matters)";

        public static final int BATCH_SIZE = 10;

        public InsertCoverageFounds(DataSource dataSource) {
            super(dataSource, INSERT_COVERAGE_FOUND);
            declareParameter(new SqlParameter("coverage_done_id", Types.INTEGER));
            declareParameter(new SqlParameter("freq_a", Types.DOUBLE));
            declareParameter(new SqlParameter("freq_b", Types.DOUBLE));
            declareParameter(new SqlParameter("phase_matters", Types.BOOLEAN));
            setBatchSize(BATCH_SIZE);
        }
    }
    InsertCoverageFounds insertCoverageFounds;
    InsertCoverageDone insertCoverageDone;

    @Override
    public void insertCoverageDoneWithFounds(CoverageDone coverageDone) {
        insertCoverageFounds = new InsertCoverageFounds(dataSource);
        insertCoverageDone = new InsertCoverageDone(dataSource);

        HashMap<String, Object> namesDone = new HashMap<>();
        namesDone.put("coverage_map_id", coverageDone.getCoverageMapId());
        namesDone.put("step_index_a", coverageDone.getStepIndexA());
        namesDone.put("step_index_b", coverageDone.getStepIndexB());
        namesDone.put("user_id", coverageDone.getUserId());
        namesDone.put("timestamp", coverageDone.getTimestamp());
        namesDone.put("comment", coverageDone.getComment());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        insertCoverageDone.updateByNamedParam(namesDone, keyHolder);
        coverageDone.setId(keyHolder.getKey().longValue());

        List<Found> founds = coverageDone.getFounds();
        if (founds != null) {
            for (Found f : founds) {
                HashMap<String, Object> names = new HashMap<>();
                names.put("coverage_done_id", coverageDone.getId());
                names.put("freq_a", f.getFreqA());
                names.put("freq_b", f.getFreqB());
                names.put("phase_matters", f.isPhaseMatters());
                insertCoverageFounds.updateByNamedParam(names);
            }
            insertCoverageFounds.flush();
        }
    }
}
