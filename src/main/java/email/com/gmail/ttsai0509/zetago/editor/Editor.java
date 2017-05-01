package email.com.gmail.ttsai0509.zetago.editor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import email.com.gmail.ttsai0509.zetago.editor.module.ConfigModule;
import email.com.gmail.ttsai0509.zetago.editor.module.TestModule;

import java.util.Map;

public class Editor extends ApplicationAdapter {

    private EntityBuilder builder;
    private RegionRenderer renderer;
    private CameraInputAdapter controller;

    @Inject private ModelBatch modelBatch;
    @Inject private AssetManager assetManager;
    @Inject private Map<String, Model> models;

    @Override
    public void create() {
        Injector injector = Guice.createInjector(
                new TestModule(),
                new ConfigModule());
        injector.injectMembers(this);

        builder = injector.getInstance(EntityBuilder.class);
        for (int i = 0; i < 4; i++)
            builder.create("cone", 0, 0);

        renderer = injector.getInstance(RegionRenderer.class);
        renderer.prepare();

        controller = injector.getInstance(CameraInputAdapter.class);
        Gdx.input.setInputProcessor(controller);

    }

    @Override
    public void render() {
        Gdx.graphics.setTitle("FPS: " + Gdx.graphics.getFramesPerSecond());

        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        controller.update();
        renderer.render();
    }

    @Override
    public void dispose() {
        models.forEach((s,m) -> m.dispose());
        modelBatch.dispose();
        assetManager.dispose();
    }

    public static void main(String[] args) {
        new LwjglApplication(new Editor(), new LwjglApplicationConfiguration());
    }
}
