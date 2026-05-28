#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT_DIR"

./mvnw -q -DskipTests package

./mvnw -q -f javase/pom.xml dependency:build-classpath -Dmdep.includeScope=test -Dmdep.outputFile=/tmp/dailyroutine-cp.txt
BASE_CP="$(cat /tmp/dailyroutine-cp.txt)"
CP="$ROOT_DIR/common/target/test-classes:$ROOT_DIR/common/target/classes:$ROOT_DIR/javase/target/test-classes:$ROOT_DIR/javase/target/classes:$BASE_CP"
APP_HOME_BASE="${DAILY_ROUTINE_TEST_HOME_BASE:-/tmp/dailyroutine-cn1test}"
mkdir -p "$APP_HOME_BASE"

TESTS=("$@")
if [[ ${#TESTS[@]} -eq 0 ]]; then
    TESTS=(
        com.baeldung.cn1tutorial.service.EnvironmentLoggingTest
        com.baeldung.cn1tutorial.service.LocalizationBundleTest
        com.baeldung.cn1tutorial.tests.HomeSummaryUiTest
        com.baeldung.cn1tutorial.tests.ActivityCreationUiTest
        com.baeldung.cn1tutorial.tests.PlaceSearchUiTest
        com.baeldung.cn1tutorial.tests.SettingsLocalizationUiTest
        com.baeldung.cn1tutorial.tests.NativeLogsFallbackUiTest
        com.baeldung.cn1tutorial.tests.AboutFormUiTest
        com.baeldung.cn1tutorial.tests.ActivityActionsUiTest
        com.baeldung.cn1tutorial.tests.EdtCrashHandlingUiTest
        com.baeldung.cn1tutorial.tests.CorruptStorageCrashUiTest
        com.baeldung.cn1tutorial.tests.NetworkRetryLogicTest
        com.baeldung.cn1tutorial.tests.NetworkServerErrorDialogTest
        com.baeldung.cn1tutorial.tests.MenuCommandsUiTest
    )
fi

for test_case in "${TESTS[@]}"; do
    echo "=== $test_case ==="
    safe_name="$(printf %s "$test_case" | tr '.:$' '_')"
    test_home="$APP_HOME_BASE/$safe_name-$$"
    mkdir -p "$test_home"
    java \
        -Djava.awt.headless=true \
        -Dcn1.javase.implementation=jmf \
        -Ddailyroutine.appHome="file://$test_home/" \
        -cp "$CP" \
        com.codename1.impl.javase.TestRunner \
        com.baeldung.cn1tutorial.DailyRoutine \
        -testCases "$test_case" \
        -quietMode \
        -junitXML
done
