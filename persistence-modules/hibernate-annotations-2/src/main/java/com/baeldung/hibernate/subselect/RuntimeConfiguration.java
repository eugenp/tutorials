package com.baeldung.hibernate.subselect;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.Instant;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Subselect;

@Data
@Accessors(chain = true)
@Entity
// language=sql
@Subselect(value =
    "SELECT\n"
    + "      ss.id,\n"
    + "      ss.attr_key,\n"
    + "      ss.val,\n"
    + "      ss.created_at\n"
    + "    FROM system_settings AS ss\n"
    + "    INNER JOIN (\n"
    + "      SELECT\n"
    + "        ss2.attr_key as k2,\n"
    + "        MAX(ss2.created_at) as ca2\n"
    + "      FROM system_settings ss2\n"
    + "      GROUP BY ss2.attr_key\n"
    + "    ) AS t ON t.k2 = ss.attr_key AND t.ca2 = ss.created_at\n"
    + "    WHERE ss.type = 'SYSTEM' AND ss.active IS TRUE\n")
public class RuntimeConfiguration {

  @Id
  private Long id;

  @Column(name = "attr_key")
  private String key;

  @Column(name = "val")
  private String value;

  @Column(name = "created_at")
  private Instant createdAt;
}
