package silence.rgbsound.client.control;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MapCellCounter {
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStepIndexA() {
        return stepIndexA;
    }

    public void setStepIndexA(int stepIndexA) {
        this.stepIndexA = stepIndexA;
    }

    public int getStepIndexB() {
        return stepIndexB;
    }

    public void setStepIndexB(int stepIndexB) {
        this.stepIndexB = stepIndexB;
    }

    private int count;
    private int stepIndexA;
    private int stepIndexB;

    public static final class MapCellCounterMapper implements RowMapper<MapCellCounter> {
        @Override
        public MapCellCounter mapRow(ResultSet resultSet, int i) throws SQLException {
            MapCellCounter res = new MapCellCounter();
            res.setCount(resultSet.getInt("count"));
            res.setStepIndexA(resultSet.getInt("step_index_a"));
            res.setStepIndexB(resultSet.getInt("step_index_b"));
            return res;
        }
    }
}
