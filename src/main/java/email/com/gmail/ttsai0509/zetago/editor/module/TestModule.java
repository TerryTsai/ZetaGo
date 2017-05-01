package email.com.gmail.ttsai0509.zetago.editor.module;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import email.com.gmail.ttsai0509.zetago.core.action.Move;
import email.com.gmail.ttsai0509.zetago.core.common.Grid;
import email.com.gmail.ttsai0509.zetago.core.common.PathFinder;
import email.com.gmail.ttsai0509.zetago.core.common.PathFinder2;
import email.com.gmail.ttsai0509.zetago.core.component.Component;
import email.com.gmail.ttsai0509.zetago.core.component.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Move.class);
    }

    @Provides
    @Singleton
    long player(
            Map<String, Model> models,
            List<ModelInstance> modelInstances,
            Environment environment,
            Map<String, Component> components,
            Grid grid
    ) {

        long id = 0;

        final ModelInstance instance = new ModelInstance(models.get("cone"));
        modelInstances.add(instance);

        final PointLight light = new PointLight().set(Color.WHITE, 0, 0, 0, 100f);
        environment.add(light);

        final Position position = new Position();
        components.put(id + Position.class.getSimpleName(), position);
        position.bind(p -> {
            instance.transform.setTranslation(p.getX() + 0.5f, p.getY() + 0.5f, p.getZ() + 0.5f);
            light.position.set(p.getX() + 0.5f, p.getY() + 10.5f, p.getZ() + 0.5f);
        });

        for (int j = 0; j < grid.height; j++) {
            if (grid.get(0, j, 0) == 1) {
                position.set(0, j, 0);
            }
        }

        return id;
    }

    @Provides
    @Singleton
    Grid region() {
        return new Grid.Builder(200, 100, 200)
                .simplex(200, 30, 200, 0, 0, 0)
                .simplex(50, 70, 50, 0, 0, 0)
                .build();
    }

    @Provides
    @Singleton
    Map<String, Model> models() {
        Map<String, Model> models = new HashMap<>();
        models.put("cube_lg", new ModelBuilder().createBox(0.9f, 0.9f, 0.9f,
                new Material(ColorAttribute.createDiffuse(Color.CYAN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
        models.put("cone", new ModelBuilder().createCone(1f, 2f, 1f, 20,
                new Material(ColorAttribute.createDiffuse(Color.CYAN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
        models.put("cube_md", new ModelBuilder().createBox(0.75f, 0.75f, 0.75f,
                new Material(ColorAttribute.createDiffuse(Color.CHARTREUSE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
        models.put("rect", new ModelBuilder().createRect(
                -0.3f, 0.0f, 0.3f, 0.3f, 0.0f, 0.3f,
                0.3f, 0.0f, -0.3f, -0.3f, 0.0f, -0.3f,
                0.0f, 1.0f, 0.0f,
                new Material(ColorAttribute.createDiffuse(Color.CHARTREUSE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
        models.put("diamond", new ModelBuilder().createRect(
                0f, 0f, 0.1f, 0.1f, 0f, 0f,
                0f, 0f, -0.1f, -0.1f, 0f, 0f,
                0.0f, 1.0f, 0.0f,
                new Material(ColorAttribute.createDiffuse(Color.CHARTREUSE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
        return models;
    }

    @Provides
    @Singleton
    PathFinder pathFinder(Grid grid) {
        return new PathFinder(grid.width, grid.height, grid.depth);
    }

    @Provides
    @Singleton
    PathFinder2 pathFinder2(Grid grid) {
        return new PathFinder2(grid.width, grid.height, grid.depth);
    }

    @Provides
    @Singleton
    Map<String, Component> components() {
        return new HashMap<>();
    }
}
