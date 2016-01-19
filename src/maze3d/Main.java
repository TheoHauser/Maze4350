package maze3d;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.system.AppSettings;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Main extends SimpleApplication {

    static Dimension screen;
    BitmapText text;
    int count = 0;
    float tpfSum;
    Node sNode, cameraTarget;
    Material mat, mat1;
    Geometry geomLarge, geomSmall, geomGround;
    public Maze maze;

    public static void main(String[] args) {
        Main app = new Main();
        initAppScreen(app);
        app.start();
    }
    
    @Override
    public void simpleInitApp() {
        initGui();
        initMaterial();
        initLightandShadow();
        initCam();
        //
        buildMaze(5,5);
    }

    // -------------------------------------------------------------------------
    private void buildMaze(int cols, int rows) {
        maze = new Maze(cols, rows, true);
        //int i = 0, j = 0;
        for(int i = 0; i < cols; i++){
            for(int j = 0; j < rows; j++){
                MazeCell mc = new MazeCell(maze, i, j, mat1);
                mc.setLocalTranslation(2*j, 0, 2*i);
                rootNode.attachChild(mc);
                
            }
        }
        //
        // INSERT CODE HERE!
        //

    }

    @Override
    public void simpleUpdate(float tpf) {
    }

    // -------------------------------------------------------------------------
    // Inits
    // -------------------------------------------------------------------------
    private static void initAppScreen(SimpleApplication app) {
        AppSettings aps = new AppSettings(true);
        screen = Toolkit.getDefaultToolkit().getScreenSize();
        screen.width *= 0.75;
        screen.height *= 0.75;
        aps.setResolution(screen.width, screen.height);
        app.setSettings(aps);
        app.setShowSettings(false);
    }

    private void initMaterial() {
        mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Ambient", ColorRGBA.Red);
        mat.setColor("Diffuse", ColorRGBA.Green);
        mat.setColor("Specular", ColorRGBA.White);
        mat.setFloat("Shininess", 12f); // shininess from 1-128

        mat1 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat1.setBoolean("UseMaterialColors", true);
        mat1.setColor("Ambient", new ColorRGBA(0.5f, 0.25f, 0.05f, 1.0f));
        mat1.setColor("Diffuse", new ColorRGBA(0.5f, 0.25f, 0.05f, 1.0f));
        mat1.setColor("Specular", ColorRGBA.Gray);
        mat1.setFloat("Shininess", 2f); // shininess from 1-128
    }

    private void initGui() {
        setDisplayFps(true);
        setDisplayStatView(false);
    }

    private void initLightandShadow() {
        // Light1: white, directional
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.5f, -0.7f, 0.9f)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);

        // Light2: white, directional
        DirectionalLight sun2 = new DirectionalLight();
        sun2.setDirection((new Vector3f(0.5f, -0.7f, -0.9f)).normalizeLocal());
        sun2.setColor(ColorRGBA.White);
        rootNode.addLight(sun2);

        // Light 3: Ambient, gray
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(new ColorRGBA(0.3f, 0.3f, 0.3f, 1.0f));
        rootNode.addLight(ambient);
    }

    private void initCam() {
        cam.setLocation(new Vector3f(1f, 5f, 6f));
        cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
    }
}
