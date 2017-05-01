package email.com.gmail.ttsai0509.zetago.editor;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.google.inject.Inject;
import email.com.gmail.ttsai0509.zetago.core.action.Move;
import email.com.gmail.ttsai0509.zetago.core.common.Grid;
import email.com.gmail.ttsai0509.zetago.core.common.PickRay;
import email.com.gmail.ttsai0509.zetago.core.common.Point;
import email.com.gmail.ttsai0509.zetago.core.component.Component;
import email.com.gmail.ttsai0509.zetago.core.component.Position;

import java.util.Map;

public class CameraInputAdapter extends InputAdapter {

    @Inject private Camera camera;
    @Inject private Grid grid;
    @Inject private Map<String, Component> components;
    @Inject private long player;
    @Inject private Move move;

    final long[] keys = new long[4];

    public void update() {

        long ws = keys[0] - keys[2];
        long ad = keys[1] - keys[3];
        if (ws > 0) {
            camera.position.z -= 1; //camera.position.add(camera.direction);
        } else if (ws < 0) {
            camera.position.z += 1; //camera.position.sub(camera.direction);
        }
        if (ad < 0) {
            camera.position.add(new Vector3(camera.direction).crs(camera.up));
        } else if (ad > 0) {
            camera.position.sub(new Vector3(camera.direction).crs(camera.up));
        }

        camera.update();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        final Position position = (Position) components.get(player + Position.class.getSimpleName());

        Ray ray = camera.getPickRay(screenX, screenY);
        Vector3 s = ray.origin;
        Vector3 e = ray.getEndPoint(new Vector3(), 100f);
        Point result = PickRay.gridPick(
                999, s.x, s.y, s.z, e.x, e.y, e.z,
                grid.width, grid.height, grid.depth, grid.getRaw()
        );

        if (result != null && position != null) {
            move.submit(position, result.x, result.y + 1, result.z);
        }

        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                keys[0] = System.nanoTime();
                return true;
            case Input.Keys.A:
                keys[1] = System.nanoTime();
                return true;
            case Input.Keys.S:
                keys[2] = System.nanoTime();
                return true;
            case Input.Keys.D:
                keys[3] = System.nanoTime();
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                keys[0] = 0;
                return true;
            case Input.Keys.A:
                keys[1] = 0;
                return true;
            case Input.Keys.S:
                keys[2] = 0;
                return true;
            case Input.Keys.D:
                keys[3] = 0;
                return true;
            default:
                return false;
        }
    }
}
