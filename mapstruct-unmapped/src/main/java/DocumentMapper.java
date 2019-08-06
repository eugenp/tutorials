import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface DocumentMapper {
        @Mappings({
                @Mapping(target = "id", source = "id"),
                @Mapping(target = "title", source = "title")
        })
        DocumentDTO documentToDocumentDTO(Document document);

        @Mappings({
                @Mapping(target = "id", source = "id"),
                @Mapping(target = "title", source = "title")
        })
        Document documentDTOToDocument(DocumentDTO documentDTO);
}
