import java.util.ListResourceBundle;

/**
 * Created by elbershayz on 13/11/17.
 */
public class MyResources extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"ThisTime",Main.getTime()}
        };
    }
}
