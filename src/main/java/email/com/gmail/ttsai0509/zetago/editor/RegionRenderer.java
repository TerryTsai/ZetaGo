package email.com.gmail.ttsai0509.zetago.editor;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.google.inject.Inject;
import email.com.gmail.ttsai0509.zetago.core.common.Grid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RegionRenderer {

    @Inject private Grid grid;
    @Inject private Camera camera;
    @Inject private ModelBatch batch;
    @Inject private Environment environment;
    @Inject private Map<String, Model> models;
    @Inject private List<ModelInstance> instances;

    public void prepare() {
        Color[] colors = new Color[] {Color.BLUE, Color.GREEN, Color.WHITE};

        for (int k = 0; k < grid.depth; k++) {
            for (int j = 0; j < grid.height; j++) {
                for (int i = 0; i < grid.width; i++) {
                    if (grid.get(i, j, k) == 999) {
                        int idx = (int) ((float) j / (float) grid.height * 3f);
                        Material material = new Material(ColorAttribute.createDiffuse(colors[idx]));
                        ModelInstance instance = new ModelInstance(models.get("rect"));
                        instance.transform.trn(i + 0.5f, j + 0.5f, k + 0.5f);
                        instance.materials.get(0).set(material);
                        instances.add(instance);
                    }
                }
            }
        }
    }

    public void render() {
        batch.begin(camera);
        Vector3 position = new Vector3();
        for (ModelInstance instance : instances) {
            instance.transform.getTranslation(position);
            if (camera.frustum.pointInFrustum(position))
                batch.render(instance, environment);
        }
        batch.end();
    }

}
