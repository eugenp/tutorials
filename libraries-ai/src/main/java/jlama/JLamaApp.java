package jlama;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import com.github.tjake.jlama.model.AbstractModel;
import com.github.tjake.jlama.model.ModelSupport;
import com.github.tjake.jlama.model.functions.Generator;
import com.github.tjake.jlama.safetensors.DType;
import com.github.tjake.jlama.safetensors.prompt.PromptContext;
import com.github.tjake.jlama.util.Downloader;

class JLamaApp {

    public static void main(String[] args) throws IOException {

        // available models: https://huggingface.co/tjake
        AbstractModel model = loadModel("./models", "tjake/Llama-3.2-1B-Instruct-JQ4");

        PromptContext prompt = PromptContext.of("Why are llamas so cute?");

        Generator.Response response = model.generateBuilder()
            .session(UUID.randomUUID())
            .promptContext(prompt)
            .ntokens(256)
            .temperature(0.3f)
            .generate();

        System.out.println(response.responseText);
    }

    static AbstractModel loadModel(String workingDir, String model) throws IOException {
        File localModelPath = new Downloader(workingDir, model).huggingFaceModel();

        return ModelSupport.loadModel(localModelPath, DType.F32, DType.I8);
    }

}
