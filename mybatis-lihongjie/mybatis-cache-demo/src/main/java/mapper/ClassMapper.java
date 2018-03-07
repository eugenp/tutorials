package mapper;

import org.apache.ibatis.annotations.Param;

public interface ClassMapper {

    int updateClassName(@Param("name") String className, @Param("id") int id);
}
