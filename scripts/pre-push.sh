#!/bin/sh
set -e

ROOT_DIR="$(git rev-parse --show-toplevel)"
SCRIPTS_DIR="$ROOT_DIR/scripts"

# ── Guard: block direct pushes to main/master ─────────────────────────────────
check_branch() {
    BRANCH=$(git rev-parse --abbrev-ref HEAD)
    case "$BRANCH" in
        main|master)
            echo "❌ Direct pushes to '$BRANCH' are not allowed. Open a pull request instead."
            exit 1
            ;;
        *)
            echo "🌿 Branch: $BRANCH"
            ;;
    esac
}

# ── ktlint: final format check ────────────────────────────────────────────────
run_ktlint() {
    echo "🔍 Running ktlint..."
    if "$SCRIPTS_DIR/bin/ktlint.sh" "**/*.kt" > /tmp/ktlint-out 2>&1; then
        rm -f /tmp/ktlint-out
        echo "✅ ktlint: no issues."
    else
        cat /tmp/ktlint-out
        rm -f /tmp/ktlint-out
        echo "💥 ktlint: issues found. Run './scripts/bin/ktlint.sh --format' to fix."
        exit 1
    fi
}

# ── detekt: static analysis ───────────────────────────────────────────────────
run_detekt() {
    echo "🔍 Running detekt..."
    if "$SCRIPTS_DIR/bin/detekt.sh" > /tmp/detekt-out 2>&1; then
        rm -f /tmp/detekt-out
        echo "✅ detekt: no issues."
    else
        cat /tmp/detekt-out
        rm -f /tmp/detekt-out
        echo "💥 detekt: issues found."
        exit 1
    fi
}

# ── DependencyGuard: verify baseline unchanged ────────────────────────────────
run_dependency_guard() {
    echo "🔍 Checking dependency guard..."
    if "$ROOT_DIR/gradlew" -p "$ROOT_DIR" dependencyGuard > /tmp/depguard-out 2>&1; then
        rm -f /tmp/depguard-out
        echo "✅ Dependencies unchanged."
    else
        cat /tmp/depguard-out
        rm -f /tmp/depguard-out
        echo "💥 Dependency drift detected. Run './gradlew dependencyGuardBaseline' to update."
        exit 1
    fi
}

# ── Main ──────────────────────────────────────────────────────────────────────
check_branch
run_ktlint
run_detekt
run_dependency_guard

GIT_USERNAME=$(git config user.name)
echo "✅ Ready to push, $GIT_USERNAME!"
