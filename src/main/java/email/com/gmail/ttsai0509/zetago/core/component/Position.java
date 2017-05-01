package email.com.gmail.ttsai0509.zetago.core.component;

public class Position extends Component {

    public interface ChangeListener {
        void onChange(Position position);
    }

    private ChangeListener listener;
    private int x, y, z;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public boolean at(int x, int y, int z) {
        return this.x == x && this.y == y && this.z == z;
    }

    public void set(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;

        if (listener != null) {
            listener.onChange(this);
        }
    }

    public void bind(ChangeListener listener) {
        this.listener = listener;
    }

    public void unbind() {
        this.listener = null;
    }

}
