package com.baeldung.crdt;

import com.netopyr.wurmloch.crdt.GCounter;
import com.netopyr.wurmloch.crdt.GSet;
import com.netopyr.wurmloch.crdt.LWWRegister;
import com.netopyr.wurmloch.crdt.PNCounter;
import com.netopyr.wurmloch.store.LocalCrdtStore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CRDTUnitTest {

    @Test
    public void givenGrowOnlySet_whenTwoReplicasDiverge_thenShouldMergeItWithoutAConflict() {
        // given
        final LocalCrdtStore crdtStore1 = new LocalCrdtStore();
        final LocalCrdtStore crdtStore2 = new LocalCrdtStore();
        crdtStore1.connect(crdtStore2);

        final GSet<String> replica1 = crdtStore1.createGSet("ID_1");
        final GSet<String> replica2 = crdtStore2.<String> findGSet("ID_1").get();

        // when
        replica1.add("apple");
        replica2.add("banana");

        // then
        assertThat(replica1).contains("apple", "banana");
        assertThat(replica2).contains("apple", "banana");

        // when
        crdtStore1.disconnect(crdtStore2);

        replica1.add("strawberry");
        replica2.add("pear");

        assertThat(replica1).contains("apple", "banana", "strawberry");
        assertThat(replica2).contains("apple", "banana", "pear");

        crdtStore1.connect(crdtStore2);

        // then
        assertThat(replica1).contains("apple", "banana", "strawberry", "pear");
        assertThat(replica2).contains("apple", "banana", "strawberry", "pear");
    }

    @Test
    public void givenIncrementOnlyCounter_whenTwoReplicasDiverge_thenShouldMergeIt() {
        // given
        final LocalCrdtStore crdtStore1 = new LocalCrdtStore();
        final LocalCrdtStore crdtStore2 = new LocalCrdtStore();
        crdtStore1.connect(crdtStore2);

        final GCounter replica1 = crdtStore1.createGCounter("ID_1");
        final GCounter replica2 = crdtStore2.findGCounter("ID_1").get();

        // when
        replica1.increment();
        replica2.increment(2L);

        // then
        assertThat(replica1.get()).isEqualTo(3L);
        assertThat(replica2.get()).isEqualTo(3L);

        // when
        crdtStore1.disconnect(crdtStore2);

        replica1.increment(3L);
        replica2.increment(5L);

        assertThat(replica1.get()).isEqualTo(6L);
        assertThat(replica2.get()).isEqualTo(8L);

        crdtStore1.connect(crdtStore2);

        // then
        assertThat(replica1.get()).isEqualTo(11L);
        assertThat(replica2.get()).isEqualTo(11L);
    }

    @Test
    public void givenPNCounter_whenReplicasDiverge_thenShouldMergeWithoutAConflict() {
        // given
        final LocalCrdtStore crdtStore1 = new LocalCrdtStore();
        final LocalCrdtStore crdtStore2 = new LocalCrdtStore();
        crdtStore1.connect(crdtStore2);

        final PNCounter replica1 = crdtStore1.createPNCounter("ID_1");
        final PNCounter replica2 = crdtStore2.findPNCounter("ID_1").get();

        // when
        replica1.increment();
        replica2.decrement(2L);

        // then
        assertThat(replica1.get()).isEqualTo(-1L);
        assertThat(replica2.get()).isEqualTo(-1L);

        // when
        crdtStore1.disconnect(crdtStore2);

        replica1.decrement(3L);
        replica2.increment(5L);

        assertThat(replica1.get()).isEqualTo(-4L);
        assertThat(replica2.get()).isEqualTo(4L);

        crdtStore1.connect(crdtStore2);

        // then
        assertThat(replica1.get()).isEqualTo(1L);
        assertThat(replica2.get()).isEqualTo(1L);
    }

    @Test
    public void givenLastWriteWinsStrategy_whenReplicasDiverge_thenAfterMergeShouldKeepOnlyLastValue() {
        // given
        final LocalCrdtStore crdtStore1 = new LocalCrdtStore("N_1");
        final LocalCrdtStore crdtStore2 = new LocalCrdtStore("N_2");
        crdtStore1.connect(crdtStore2);

        final LWWRegister<String> replica1 = crdtStore1.createLWWRegister("ID_1");
        final LWWRegister<String> replica2 = crdtStore2.<String> findLWWRegister("ID_1").get();

        // when
        replica1.set("apple");
        replica2.set("banana");

        // then
        assertThat(replica1.get()).isEqualTo("banana");
        assertThat(replica2.get()).isEqualTo("banana");

        // when
        crdtStore1.disconnect(crdtStore2);

        replica1.set("strawberry");
        replica2.set("pear");

        assertThat(replica1.get()).isEqualTo("strawberry");
        assertThat(replica2.get()).isEqualTo("pear");

        crdtStore1.connect(crdtStore2);

        // then
        assertThat(replica1.get()).isEqualTo("pear");
        assertThat(replica2.get()).isEqualTo("pear");
    }
}
