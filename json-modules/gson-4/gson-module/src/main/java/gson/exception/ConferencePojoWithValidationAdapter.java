package gson.exception;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class ConferencePojoWithValidationAdapter extends TypeAdapter<ConferencePojoWithValidation> {

    @Override
    public void write(JsonWriter out, ConferencePojoWithValidation value) throws IOException {
        throw new UnsupportedOperationException("This adapter is for deserialization only!");
    }

    @Override
    public ConferencePojoWithValidation read(JsonReader in) throws IOException {
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

        return new ConferencePojoWithValidation(name, numberOfParticipants);
    }
}
