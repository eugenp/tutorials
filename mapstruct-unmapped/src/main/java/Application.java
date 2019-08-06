import org.mapstruct.factory.Mappers;

import java.util.Date;

public class Application {

    public static void main(String[] args) {
        Document entity  = new Document();
        entity.setId(1);
        entity.setTitle("Invoice 13/42");
        entity.setCreationTime(new Date());
        entity.setModificationTime(new Date());

        DocumentMapper mapper = Mappers.getMapper(DocumentMapper.class);

        DocumentDTO documentDTo = mapper.documentToDocumentDTO(entity);
    }

}
