#!/bin/sh
# detekt CLI wrapper — downloads the CLI jar on first use and caches it.
# Includes detekt-formatting for auto-formatting rules.
# Usage: ./scripts/bin/detekt.sh [detekt args...]
set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
ROOT_DIR="$(cd "$SCRIPT_DIR/../.." && pwd)"
# shellcheck source=../.versions
. "$SCRIPT_DIR/../.versions"

CACHE_DIR="$ROOT_DIR/.gradle/cli"
CLI_JAR="$CACHE_DIR/detekt-cli-$DETEKT_VERSION-all.jar"
FMT_JAR="$CACHE_DIR/detekt-formatting-$DETEKT_VERSION.jar"
TWITTER_JAR="$CACHE_DIR/twitter-detekt-compose-$TWITTER_DETEKT_COMPOSE_VERSION.jar"
TWITTER_COMMON_JAR="$CACHE_DIR/twitter-compose-rules-common-$TWITTER_DETEKT_COMPOSE_VERSION.jar"
TWITTER_CORE_DETEKT_JAR="$CACHE_DIR/twitter-core-detekt-$TWITTER_DETEKT_COMPOSE_VERSION.jar"
TWITTER_CORE_COMMON_JAR="$CACHE_DIR/twitter-core-common-$TWITTER_DETEKT_COMPOSE_VERSION.jar"

_download_if_missing() {
    local file="$1"
    local url="$2"
    if [ ! -f "$file" ]; then
        echo "⬇  Downloading $(basename "$file")..."
        mkdir -p "$CACHE_DIR"
        curl -sSL -o "$file" "$url"
    fi
}

_download_if_missing "$CLI_JAR" \
    "https://github.com/detekt/detekt/releases/download/v$DETEKT_VERSION/detekt-cli-$DETEKT_VERSION-all.jar"

_download_if_missing "$FMT_JAR" \
    "https://github.com/detekt/detekt/releases/download/v$DETEKT_VERSION/detekt-formatting-$DETEKT_VERSION.jar"

_download_if_missing "$TWITTER_COMMON_JAR" \
    "https://repo1.maven.org/maven2/com/twitter/compose/rules/common/$TWITTER_DETEKT_COMPOSE_VERSION/common-$TWITTER_DETEKT_COMPOSE_VERSION.jar"

_download_if_missing "$TWITTER_CORE_DETEKT_JAR" \
    "https://repo1.maven.org/maven2/com/twitter/compose/rules/core-detekt/$TWITTER_DETEKT_COMPOSE_VERSION/core-detekt-$TWITTER_DETEKT_COMPOSE_VERSION.jar"

_download_if_missing "$TWITTER_CORE_COMMON_JAR" \
    "https://repo1.maven.org/maven2/com/twitter/compose/rules/core-common/$TWITTER_DETEKT_COMPOSE_VERSION/core-common-$TWITTER_DETEKT_COMPOSE_VERSION.jar"

_download_if_missing "$TWITTER_JAR" \
    "https://repo1.maven.org/maven2/com/twitter/compose/rules/detekt/$TWITTER_DETEKT_COMPOSE_VERSION/detekt-$TWITTER_DETEKT_COMPOSE_VERSION.jar"

exec java -jar "$CLI_JAR" \
    --plugins "$FMT_JAR,$TWITTER_COMMON_JAR,$TWITTER_CORE_DETEKT_JAR,$TWITTER_CORE_COMMON_JAR,$TWITTER_JAR" \
    --config "$ROOT_DIR/config/detekt/detekt.yml" \
    --input "$ROOT_DIR" \
    --excludes "**/build/**,**/generated/**,**/resources/**,**/*.kts,**/build-logic/**" \
    "$@"
