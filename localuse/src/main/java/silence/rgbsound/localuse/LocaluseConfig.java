package silence.rgbsound.localuse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
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
import silence.rgbsound.wavefile.WaveFileWriter;

import javax.sql.DataSource;
import java.sql.Driver;

@Configuration
@PropertySource("classpath:jdbc.properties")
public class LocaluseConfig {

    @Value("${dbdriverClassName}")
    private String dbDriverClassName;
    @Value("${dburl}")
    private String dbUrl;
    @Value("${dbuser}")
    private String dbUser;
    @Value("${dbpass}")
    private String dbPass;

    @Value("./tmp/")
    public String tmpdirPath;

    @Lazy
    @Bean
    public DataSource dataSource() {
        try {
            SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
            Class<? extends Driver> driver = (Class<? extends Driver>) Class.forName(dbDriverClassName);
            dataSource.setDriverClass(driver);
            dataSource.setUrl(dbUrl);
            dataSource.setUsername(dbUser);
            dataSource.setPassword(dbPass);
            return dataSource;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

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
    public PlaySound player() {
        return new PlaySound(tmpdirPath);
    }

    @Bean
    @Scope(value="prototype")
    public WaveFileWriter waveWriter() {
        WaveFileWriter wf = new WaveFileWriter(16, 44100, 2);
        wf.setFileDirectory(tmpdirPath);
        return wf;
    }
}
