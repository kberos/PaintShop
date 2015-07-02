import java.util.ArrayList;
import java.util.List;

/**
 * Created by kber0001 on 28/06/2015.
 */
public class Customer {
    String id;
    boolean isSatisfied;
    List<Paint> paints = new ArrayList<Paint>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSatisfied() {
        return isSatisfied;
    }

    public void setSatisfied(boolean isSatisfied) {
        this.isSatisfied = isSatisfied;
    }

    public List<Paint> getPaints() {
        return paints;
    }

    public void setPaints(List<Paint> paints) {
        this.paints = paints;
    }

}
