/**
 * Created by kber0001 on 28/06/2015.
 */
public class Paint {
    Integer id;
    Type type;
    boolean isLocked;

    public Paint(Integer id, Type type, boolean isLocked) {
        this.id = id;
        this.type = type;
        this.isLocked = isLocked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public enum Type {
         M,G;
    }
}
