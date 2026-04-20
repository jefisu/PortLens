#!/bin/sh
# ktlint CLI wrapper — downloads the binary on first use and caches it.
# Usage: ./scripts/bin/ktlint.sh [ktlint args...]
set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
# shellcheck source=../.versions
. "$SCRIPT_DIR/../.versions"

CACHE_DIR="$(cd "$SCRIPT_DIR/../.." && pwd)/.gradle/cli"
BIN="$CACHE_DIR/ktlint-$KTLINT_VERSION"

if [ ! -f "$BIN" ]; then
    echo "⬇  Downloading ktlint $KTLINT_VERSION..."
    mkdir -p "$CACHE_DIR"
    curl -sSL \
        -o "$BIN" \
        "https://github.com/pinterest/ktlint/releases/download/$KTLINT_VERSION/ktlint"
    chmod +x "$BIN"
    echo "✅ ktlint $KTLINT_VERSION ready."
fi

exec "$BIN" "$@"
