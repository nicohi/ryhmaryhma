package vinkkeri;

import java.util.concurrent.TimeoutException;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import vinkkeri.main.Main;

/**
 * @author Olli K. Kärki
 */
public class AppTest extends ApplicationTest {

    @BeforeClass
    public static void setupHeadlessMode() {
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        System.setProperty("java.awt.headless", "true");
    }

    @Before
    public void setUp() throws Exception {
        ApplicationTest.launch(Main.class);
    }

    @Override
    public void start(Stage stage) {
        stage.show();
    }

    public <T extends Node> T find(final String query) {
        return (T) lookup(query).queryAll().iterator().next();
    }

    public void type(String str) {
        String split[] = str.split("");
        for (String s : split) {
            super.type(KeyCode.getKeyCode(s));
        }
    }

    @After
    public void afterTest() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

}
