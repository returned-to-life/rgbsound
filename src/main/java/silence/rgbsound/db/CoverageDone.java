package silence.rgbsound.db;

import java.io.Serializable;
import java.sql.Date;

public class CoverageDone implements Serializable {
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCoverageMapId() {
        return coverageMapId;
    }

    public void setCoverageMapId(long coverageMapId) {
        this.coverageMapId = coverageMapId;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    long id;
    long coverageMapId;
    int stepIndexA;
    int stepIndexB;
    long userId;
    Date timestamp;
    String comment;
}
