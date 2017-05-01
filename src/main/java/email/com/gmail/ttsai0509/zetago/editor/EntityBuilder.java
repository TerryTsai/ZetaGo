package email.com.gmail.ttsai0509.zetago.editor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.google.inject.Inject;
import email.com.gmail.ttsai0509.zetago.core.action.Move;
import email.com.gmail.ttsai0509.zetago.core.common.Grid;
import email.com.gmail.ttsai0509.zetago.core.common.Math;
import email.com.gmail.ttsai0509.zetago.core.component.Component;
import email.com.gmail.ttsai0509.zetago.core.component.Position;

import java.util.List;
import java.util.Map;

public class EntityBuilder {

    @Inject private Move move;
    @Inject private Map<String, Model> models;
    @Inject private List<ModelInstance> modelInstances;
    @Inject private Map<String, Component> components;
    @Inject private Grid grid;

    private long id = 1;

    public long create(String model, int x, int z) {

        final ModelInstance instance = new ModelInstance(models.get(model));
        modelInstances.add(instance);

        final Position position = new Position();
        components.put(id + Position.class.getSimpleName(), position);
        position.bind(p -> instance.transform.setTranslation(p.getX() + 0.5f, p.getY() + 0.5f, p.getZ() + 0.5f));

        for (int j = 0; j < grid.height; j++) {
            if (grid.get(x, j, z) == 1) {
                position.set(x, j, z);
            }
        }

        int targetX = Math.random.nextInt(grid.width);
        int targetZ = Math.random.nextInt(grid.depth);
        for (int j = 0; j < grid.height; j++) {
            if (grid.get(targetX, j, targetZ) == 1) {
                move.submit(position, targetX, j, targetZ);
            }
        }

        return id++;
    }

}
