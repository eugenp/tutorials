package com.baeldung.termsqueries;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.MultiBucketBase;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsAggregate;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.baeldung.termsqueries.config.ElasticsearchConfiguration;
import com.baeldung.termsqueries.model.User;
import com.baeldung.termsqueries.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This Manual test requires: Elasticsearch instance running on localhost:9200.
 * <p>
 * The following docker command can be used:
 * docker run -d --name elastic-test -p 9200:9200 -e "discovery.type=single-node" -e "xpack.security.enabled=false" docker.elastic.co/elasticsearch/elasticsearch:8.9.0
 */
@Slf4j
@SpringBootTest
@ContextConfiguration(classes = ElasticsearchConfiguration.class)
public class ElasticSearchTermsQueriesManualTest {

    @Autowired
    private ElasticsearchClient elasticsearchLowLevelClient;

    @Autowired
    private UserRepository userRepository;


    @Test
    void givenAdminRoleAndActiveStatusFilter_whenSearch_thenReturnsOnlyActiveAdmins() throws Exception {
        Query roleQuery = TermQuery.of(t -> t.field("role").value("admin"))._toQuery();
        Query activeQuery = TermQuery.of(t -> t.field("is_active").value(true))._toQuery();
        Query boolQuery = BoolQuery.of(b -> b.filter(roleQuery).filter(activeQuery))._toQuery();
        SearchRequest request = SearchRequest.of(s -> s.index("users").query(boolQuery));

        SearchResponse<Map> response = elasticsearchLowLevelClient.search(request, Map.class);
        assertThat(response.hits().hits())
          .hasSize(1)
          .first()
          .extracting(Hit::source)
          .satisfies(source -> {
            assertThat(source)
              .isNotNull()
              .containsValues("1", "Alice", "admin", true);
          });
    }

    @Test
    void givenActiveUsers_whenAggregateByRole_thenReturnsRoleCounts() throws Exception {
        Query activeQuery = TermQuery.of(t -> t.field("is_active").value(true))._toQuery();

        Aggregation aggregation = Aggregation.of(a -> a
          .terms(t -> t.field("role.keyword")));

        SearchRequest request = SearchRequest.of(s -> s
          .index("users")
          .query(activeQuery)
          .aggregations("by_role", aggregation));

        SearchResponse<Void> response = elasticsearchLowLevelClient.search(request, Void.class);

        StringTermsAggregate rolesAggregate = response.aggregations().get("by_role").sterms();

        assertThat(rolesAggregate.buckets().array())
          .extracting(b -> b.key().stringValue())
          .containsExactlyInAnyOrder("admin", "user", "manager");

        assertThat(rolesAggregate.buckets().array())
          .extracting(MultiBucketBase::docCount)
          .contains(1L, 1L, 1L);
    }

    @Test
    void givenAdminRoleAndActiveStatusFilter_whenSearchUsingRepository_thenReturnsOnlyActiveAdmins() throws Exception {
        List<User> users = userRepository.findByRoleAndIsActive("admin", true);

        assertThat(users)
          .hasSize(1)
          .first()
          .satisfies(user -> {
            assertThat(user.getId()).isEqualTo("1");
            assertThat(user.getName()).isEqualTo("Alice");
            assertThat(user.getRole()).isEqualTo("admin");
            assertThat(user.getIsActive()).isTrue();
          });
    }
}
