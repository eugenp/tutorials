package com.baeldung.versioncomparison;

import org.junit.Test;

import com.fasterxml.jackson.core.Version;
import com.vdurmont.semver4j.Semver;
import com.vdurmont.semver4j.Semver.VersionDiff;

import org.apache.maven.artifact.versioning.ComparableVersion;
import org.gradle.util.VersionNumber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VersionComparisonUnitTest {
    
    @Test
    public void givenVersionStrings_whenUsingMavenArtifact_thenCompareVersions() {
        ComparableVersion version1_1 = new ComparableVersion("1.1");
        ComparableVersion version1_2 = new ComparableVersion("1.2");
        ComparableVersion version1_3 = new ComparableVersion("1.3");

        assertTrue(version1_1.compareTo(version1_2) < 0);
        assertTrue(version1_3.compareTo(version1_2) > 0);
        
        ComparableVersion version1_1_0 = new ComparableVersion("1.1.0"); 
        assertEquals(version1_1.compareTo(version1_1_0), 0);

        ComparableVersion version1_1_alpha = new ComparableVersion("1.1-alpha");
        assertTrue(version1_1.compareTo(version1_1_alpha) > 0);
        
        ComparableVersion version1_1_beta = new ComparableVersion("1.1-beta");
        ComparableVersion version1_1_milestone = new ComparableVersion("1.1-milestone");
        ComparableVersion version1_1_rc = new ComparableVersion("1.1-rc");
        ComparableVersion version1_1_snapshot = new ComparableVersion("1.1-snapshot");
        
        assertTrue(version1_1_alpha.compareTo(version1_1_beta) < 0);
        assertTrue(version1_1_beta.compareTo(version1_1_milestone) < 0);
        assertTrue(version1_1_rc.compareTo(version1_1_snapshot) < 0);
        assertTrue(version1_1_snapshot.compareTo(version1_1) < 0);        
        
        ComparableVersion version1_1_c = new ComparableVersion("1.1-c");
        ComparableVersion version1_1_z = new ComparableVersion("1.1-z");
        ComparableVersion version1_1_1 = new ComparableVersion("1.1.1");
        
        assertTrue(version1_1_c.compareTo(version1_1_z) < 0);
        assertTrue(version1_1_z.compareTo(version1_1_1) < 0);
    }
    
    @Test
    public void givenVersionStrings_whenUsingGradle_thenCompareVersions() {
        VersionNumber version1_1 = VersionNumber.parse("1.1");
        VersionNumber version1_2 = VersionNumber.parse("1.2");
        VersionNumber version1_3 = VersionNumber.parse("1.3");

        assertTrue(version1_1.compareTo(version1_2) < 0);
        assertTrue(version1_3.compareTo(version1_2) > 0);
        
        VersionNumber version1_1_0 = VersionNumber.parse("1.1.0");
        assertEquals(version1_1.compareTo(version1_1_0), 0);
        
        VersionNumber version1_1_1_1_alpha = VersionNumber.parse("1.1.1.1-alpha");
        assertTrue(version1_1.compareTo(version1_1_1_1_alpha) < 0);
        
        VersionNumber version1_1_beta = VersionNumber.parse("1.1.0.0-beta"); 
        assertTrue(version1_1_beta.compareTo(version1_1_1_1_alpha) < 0);

        VersionNumber version1_1_1_snapshot = VersionNumber.parse("1.1.1-snapshot");
        assertTrue(version1_1_1_1_alpha.compareTo(version1_1_1_snapshot) < 0);
    }
    
    @Test
    public void givenVersionStrings_whenUsingJackson_thenCompareVersions() {
        Version version1_1 = new Version(1, 1, 0, null, null, null);
        Version version1_2 = new Version(1, 2, 0, null, null, null);
        Version version1_3 = new Version(1, 3, 0, null, null, null);
        
        assertTrue(version1_1.compareTo(version1_2) < 0);
        assertTrue(version1_3.compareTo(version1_2) > 0);
        
        Version version1_1_1 = new Version(1, 1, 1, null, null, null);
        assertTrue(version1_1.compareTo(version1_1_1) < 0);
        
        Version version1_1_maven = new Version(1, 1, 0, null, "org.apache.maven", null);
        Version version1_1_gradle = new Version(1, 1, 0, null, "org.gradle", null);
        assertTrue(version1_1_maven.compareTo(version1_1_gradle) < 0);
        
        Version version1_1_snapshot = new Version(1, 1, 0, "snapshot", null, null);
        assertEquals(version1_1.compareTo(version1_1_snapshot), 0);
        
        assertTrue(version1_1_snapshot.isSnapshot());
    }
    
    @Test
    public void givenVersionStrings_whenUsingSemver_thenCompareVersions() {
        Semver version1_1 = new Semver("1.1.0");
        Semver version1_2 = new Semver("1.2.0");
        Semver version1_3 = new Semver("1.3.0");

        assertTrue(version1_1.compareTo(version1_2) < 0);
        assertTrue(version1_3.compareTo(version1_2) > 0);
        
        Semver version1_1_alpha = new Semver("1.1.0-alpha");
        assertTrue(version1_1.isGreaterThan(version1_1_alpha));
        
        Semver version1_1_beta = new Semver("1.1.0-beta");
        Semver version1_1_milestone = new Semver("1.1.0-milestone");
        Semver version1_1_rc = new Semver("1.1.0-rc");
        Semver version1_1_snapshot = new Semver("1.1.0-snapshot");
        
        assertTrue(version1_1_alpha.isLowerThan(version1_1_beta));
        assertTrue(version1_1_beta.compareTo(version1_1_milestone) < 0);
        assertTrue(version1_1_rc.compareTo(version1_1_snapshot) < 0);
        assertTrue(version1_1_snapshot.compareTo(version1_1) < 0);        
        
        assertTrue(version1_1.isEqualTo("1.1.0"));
        
        assertEquals(version1_1.diff("2.1.0"), VersionDiff.MAJOR);
        assertEquals(version1_1.diff("1.2.3"), VersionDiff.MINOR);
        assertEquals(version1_1.diff("1.1.1"), VersionDiff.PATCH);
        
        assertTrue(version1_1.isStable());
        assertFalse(version1_1_alpha.isStable());
    }

    @Test
    public void givenVersionStrings_whenUsingCustomVersionCompare_thenCompareVersions() {
        assertTrue(VersionCompare.compareVersions("1.0.1", "1.1.2") < 0);
        assertTrue(VersionCompare.compareVersions("1.0.1", "1.10") < 0);
        assertTrue(VersionCompare.compareVersions("1.1.2", "1.0.1") > 0);
        assertTrue(VersionCompare.compareVersions("1.1.2", "1.2") < 0); 
        assertEquals(VersionCompare.compareVersions("1.3.0", "1.3"), 0); 
    }
    
}
