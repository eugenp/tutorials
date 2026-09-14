package gson.exception;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class ConferencePojoAdapter extends TypeAdapter<ConferencePojo> {

    @Override
    public void write(JsonWriter out, ConferencePojo value) throws IOException {
        throw new UnsupportedOperationException("This adapter is for deserialization only!");
    }

    @Override
    public ConferencePojo read(JsonReader in) throws IOException {
        String name = null;
        int numberOfParticipants = 0;

        in.beginObject();
        while (in.hasNext()) {
            String key = in.nextName();
            if ("name".equals(key)) {
                name = in.nextString();
            } else if ("numberOfParticipants".equals(key)) {
                numberOfParticipants = in.nextInt();
            } else {
                in.skipValue();
            }
        }
        in.endObject();

        ConferencePojo result = new ConferencePojo();
        result.setName(name);
        result.setNumberOfParticipants(numberOfParticipants);

        return result;
    }
}
