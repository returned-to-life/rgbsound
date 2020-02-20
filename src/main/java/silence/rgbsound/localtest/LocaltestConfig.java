package silence.rgbsound.localtest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import silence.rgbsound.audio.PlaySound;
import silence.rgbsound.client.control.*;
import silence.rgbsound.client.forms.MainRunTestsetForm;
import silence.rgbsound.client.forms.MakeDecisionForm;
import silence.rgbsound.client.forms.PickTestsetForm;
import silence.rgbsound.db.dao.CoverageDoneDao;
import silence.rgbsound.db.dao.CoverageMapDao;
import silence.rgbsound.db.dao.FoundDao;
import silence.rgbsound.db.dao.jdbc.CoverageDoneDaoJdbc;
import silence.rgbsound.db.dao.jdbc.CoverageMapDaoJdbc;
import silence.rgbsound.db.dao.jdbc.FoundDaoJdbc;
import silence.rgbsound.link.Communicator;
import silence.rgbsound.link.CommunicatorMockDB;
import silence.rgbsound.link.CommunicatorMockRandom;
import silence.rgbsound.wavefile.WaveFileWriter;

import javax.sql.DataSource;

@Configuration
public class LocaltestConfig {

    @Bean
    public MainRunTestsetForm testsetForm() {
        MainRunTestsetForm mtf = new MainRunTestsetForm();
        mtf.setTestsetController(controller());
        mtf.onLoad();
        return mtf;
    }

    @Bean
    @Scope(value = "prototype")
    public Testset testset() {
        return pickTestsetDialog().getResult();
    }

    @Bean
    @Scope(value="prototype")
    public PickTestsetForm pickTestsetDialog() {
        PickTestsetForm dialog = new PickTestsetForm();
        dialog.setController(pickController());
        dialog.onLoad();
        dialog.pack();
        dialog.setVisible(true);
        return dialog;
    }

    @Bean
    public PickTestsetController pickController() {
        PickTestsetController ctrl = new PickTestsetController();
        ctrl.setCommunicator(communicator());
        return ctrl;
    }

    @Bean
    public Communicator communicatorRandom() {
        CommunicatorMockRandom comm = new CommunicatorMockRandom();
        comm.setCoverageCounter(counter());
        return comm;
    }

    @Bean
    public CoverageMapDao coverageMapDao() {
        CoverageMapDaoJdbc map = new CoverageMapDaoJdbc();
        map.setDataSource(dataSource());
        return map;
    }

    @Bean
    public CoverageDoneDao coverageDoneDao() {
        CoverageDoneDaoJdbc done = new CoverageDoneDaoJdbc();
        done.setDataSource(dataSource());
        return done;
    }

    @Bean
    public FoundDao foundDao() {
        FoundDaoJdbc found = new FoundDaoJdbc();
        found.setDataSource(dataSource());
        return found;
    }

    @Bean
    public Communicator communicator() {
        CommunicatorMockDB comm = new CommunicatorMockDB();
        comm.setCoverageCounter(counter());
        comm.setCoverageMapDao(coverageMapDao());
        comm.setCoverageDoneDao(coverageDoneDao());
        comm.setFoundDao(foundDao());
        return comm;
    }

    @Bean
    public DataSource dataSource() {
        try {
            EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder();
            return dbBuilder.setType(EmbeddedDatabaseType.H2)
                    .addScripts("classpath:create_schema.sql", "classpath:insert_data.sql").build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean
    public MakeDecisionForm decisionForm() {
        MakeDecisionForm mdf = new MakeDecisionForm();
        mdf.setCommunicator(communicator());
        return mdf;
    }

    @Bean
    public CoverageCounter counter() {
        return new CoverageCounter();
    }

    @Bean
    public RunTestsetController controller() {
        RunTestsetController control = new RunTestsetController();
        control.LoadTestset(testset());
        control.setAmpCursor(new AmpCursor(8000));
        control.setProjectMaxAmp(8000);
        control.setProjectRate(44100);
        control.setPlaySound(player());
        control.setWaveWriter(waveWriter());
        return control;
    }

    @Bean
    public String tmpdirPath() { //return "#{systemProperties'java.io.tmpdir'}";
        return "/home/rtl/tmp/"; };

    @Bean
    public PlaySound player() {
        return new PlaySound(tmpdirPath());
    }

    @Bean
    @Scope(value="prototype")
    public WaveFileWriter waveWriter() {
        WaveFileWriter wf = new WaveFileWriter(16, 44100, 2);
        wf.setFileDirectory(tmpdirPath());
        return wf;
    }
}
