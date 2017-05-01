package email.com.gmail.ttsai0509.zetago.editor.module;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

public class ConfigModule extends AbstractModule {

    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    List<ModelInstance> modelInstances() {
        return new ArrayList<>();
    }

    @Provides
    @Singleton
    ModelBatch modelBatch() {
        return new ModelBatch();
    }

    @Provides
    @Singleton
    AssetManager assetManager() {
        return new AssetManager(FileHandle::new);
    }

    @Provides
    @Singleton
    Environment environment() {
        Environment environment = new Environment();
        //environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1f, 1f, 1f, 1f));
        return environment;
    }

    @Provides
    @Singleton
    Camera camera() {
        Camera camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(0, 80, 80);
        camera.lookAt(0, 20, 0);
        camera.near = 1f;
        camera.far = 100f;
        camera.update();
        return camera;
    }

}
