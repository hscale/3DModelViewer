package BasicModels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class ShaderStoreFlocking {
    public final Shader basicFlatShader = Shader.create("../shaders/basic_lighting2_vertex.glsl", "../shaders/lighting_materials_lamp_fragment.glsl", Optional.empty(),true, true);
    //    private final Shader standardShader = new Shader("../shaders/shadows_vertex.glsl", "../shaders/shadows_fragment.glsl");
//    public final Shader standardShader = new Shader("../shaders/shadows_vertex.glsl", "../shaders/shadows_fragment.glsl", false);
    public final Shader standardShader = Shader.create("../shaders/model_vertex.glsl", "../shaders/flocking_fragment.glsl", Optional.empty(), false, true);
//    public final Shader debugShader = Shader.create("../shaders/debug_normals_vertex.glsl", "../shaders/debug_normals_fragment.glsl", Optional.of("../shaders/debug_normals_geometry.glsl"), false, true);
//public final Shader debugShader = Shader.create("../shaders/model_vertex.glsl", "../shaders/model_fragment.glsl", Optional.empty(), false, true);
//    public final Shader debugShader = Shader.create("../shaders/debug_normals_vertex.glsl", "../shaders/debug_normals_fragment.glsl", Optional.of("../shaders/debug_normals_geometry.glsl"), false, true);
    //    private final Shader standardShader = new Shader("../shaders/lighting_materials_vertex.glsl", "../shaders/lighting_materials2_fragment.glsl");
    public final Shader shadowGenShader = Shader.create("../shaders/shadow_mapping.vtx", "../shaders/empty.frag", true);
    public final Shader passthroughShader = Shader.create("../shaders/passthrough_vertex.glsl", "../shaders/passthrough_fragment.glsl", true);
    public final Shader renderDepthMapShader = Shader.create("../shaders/passthrough_vertex.glsl", "../shaders/render_depth_map_fragment.glsl", true);

    private final ArrayList<Shader> shaders = new ArrayList<>(Arrays.asList(basicFlatShader, standardShader, shadowGenShader, passthroughShader, renderDepthMapShader));

    public ShaderStoreFlocking() {
        basicFlatShader.addVariable(ShaderVariable.changesEveryRun("projectionMatrix"));
        basicFlatShader.addVariable(ShaderVariable.changesEveryRun("viewMatrix"));
        basicFlatShader.addVariable(ShaderVariable.changesEveryRun("modelMatrix"));
        basicFlatShader.addVariable(ShaderVariable.changesEveryRun("lamp_Color"));

        for (Shader shader: new Shader[] { standardShader }){
            shader.addVariable(ShaderVariable.changesEveryRun("projectionMatrix"));
            shader.addVariable(ShaderVariable.changesEveryRun("viewMatrix"));
            shader.addVariable(ShaderVariable.changesEveryRun("modelMatrix"));
            shader.addVariable(ShaderVariable.changesEveryRun("lightSpaceMatrixDir"));
            shader.addVariable(ShaderVariable.changesInfrequently("dirLight.enabled"));
            shader.addVariable(ShaderVariable.changesInfrequently("dirLight.direction"));
            shader.addVariable(ShaderVariable.changesInfrequently("dirLight.ambient"));
            shader.addVariable(ShaderVariable.changesInfrequently("dirLight.diffuse"));
            shader.addVariable(ShaderVariable.changesInfrequently("dirLight.specular"));
            shader.addVariable(ShaderVariable.changesInfrequently("dirLight.shadowsEnabled"));
            shader.addVariable(ShaderVariable.changesInfrequently("dirLight.shadowMap"));
            for (int i = 0; i < BrightLighting.MAX_POINT_LIGHTS; i++) {
                shader.addVariable(ShaderVariable.changesEveryRun("lightSpaceMatrixes[" + i + "]"));

                shader.addVariable(ShaderVariable.changesInfrequently("pointLights[" + i + "].enabled"));
                shader.addVariable(ShaderVariable.changesInfrequently("pointLights[" + i + "].position"));
                shader.addVariable(ShaderVariable.changesInfrequently("pointLights[" + i + "].constant"));
                shader.addVariable(ShaderVariable.changesInfrequently("pointLights[" + i + "].linear"));
                shader.addVariable(ShaderVariable.changesInfrequently("pointLights[" + i + "].quadratic"));
                shader.addVariable(ShaderVariable.changesInfrequently("pointLights[" + i + "].ambient"));
                shader.addVariable(ShaderVariable.changesInfrequently("pointLights[" + i + "].diffuse"));
                shader.addVariable(ShaderVariable.changesInfrequently("pointLights[" + i + "].specular"));
                shader.addVariable(ShaderVariable.changesInfrequently("pointLights[" + i + "].shadowsEnabled"));
                shader.addVariable(ShaderVariable.changesInfrequently("pointLights[" + i + "].shadowMap"));
            }
            shader.addVariable(ShaderVariable.changesEveryRun("viewPos"));
            shader.addVariable(ShaderVariable.changesEveryRun("material.ambient"));
            shader.addVariable(ShaderVariable.changesEveryRun("material.diffuse"));
            shader.addVariable(ShaderVariable.changesEveryRun("material.specular"));
            shader.addVariable(ShaderVariable.changesEveryRun("material.shininess"));
            shader.addVariable(ShaderVariable.changesInfrequently("shadowsEnabled"));
        }

        shadowGenShader.addVariable(ShaderVariable.changesEveryRun("lightSpaceMatrix"));
        shadowGenShader.addVariable(ShaderVariable.changesEveryRun("modelMatrix"));

        renderDepthMapShader.addVariable(ShaderVariable.changesEveryRun("depthMap"));

    }

    public void reset() {
        shaders.forEach(shader -> shader.reset());
    }

//    public Shader create(String vertexResourceFilename, String fragmentResourceFilename) {
//        Shader out = new Shader(vertexResourceFilename, fragmentResourceFilename, false);
//        shaders.add(out);
//        return out;
//    }
//
//    public Shader create(String vertexResourceFilename, String fragmentResourceFilename, boolean ignoreUnknownVariables) {
//        Shader out = new Shader(vertexResourceFilename, fragmentResourceFilename, ignoreUnknownVariables);
//        shaders.add(out);
//        return out;
//    }


}