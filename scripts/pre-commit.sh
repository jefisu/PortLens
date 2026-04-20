#!/bin/sh
set -e

ROOT_DIR="$(git rev-parse --show-toplevel)"
SCRIPTS_DIR="$ROOT_DIR/scripts"

# ── Guard: block direct commits to main/master ────────────────────────────────
check_branch() {
    BRANCH=$(git rev-parse --abbrev-ref HEAD)
    case "$BRANCH" in
        main|master)
            echo "❌ Direct commits to '$BRANCH' are not allowed."
            exit 1
            ;;
        *)
            echo "🌿 Branch: $BRANCH"
            ;;
    esac
}

# ── ktlint: format changed Kotlin files ──────────────────────────────────────
run_ktlint() {
    echo "🔍 Running ktlint format..."
    "$SCRIPTS_DIR/bin/ktlint.sh" --format \
        "$(git diff --cached --name-only --diff-filter=ACMR | grep '\.kt$' | tr '\n' ' ')" \
        2>/tmp/ktlint-out || true

    if "$SCRIPTS_DIR/bin/ktlint.sh" \
        "$(git diff --cached --name-only --diff-filter=ACMR | grep '\.kt$' | tr '\n' ' ')" \
        > /tmp/ktlint-out 2>&1; then
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

# ── Module graphs: regenerate when inter-module dependencies change ───────────
run_module_graphs() {
    if git diff --cached --name-only | grep -q 'build\.gradle\.kts$'; then
        echo "📦 build.gradle.kts changed — regenerating module graphs..."
        if ! command -v dot > /dev/null 2>&1; then
            echo "⚠️  Graphviz 'dot' not found — skipping graph generation."
            echo "   Install with: brew install graphviz"
            return 0
        fi
        if bash "$ROOT_DIR/generateModuleGraphs.sh" > /tmp/modulegraph-out 2>&1; then
            git add "$ROOT_DIR/docs/images/graphs/" 2>/dev/null || true
            rm -f /tmp/modulegraph-out
            echo "✅ Module graphs updated."
        else
            cat /tmp/modulegraph-out
            rm -f /tmp/modulegraph-out
            echo "💥 Module graph generation failed."
            exit 1
        fi
    fi
}

# ── DependencyGuard: regenerate baseline ─────────────────────────────────────
run_dependency_guard() {
    echo "🔍 Regenerating dependency baseline..."
    if "$ROOT_DIR/gradlew" -p "$ROOT_DIR" dependencyGuardBaseline > /tmp/depguard-out 2>&1; then
        rm -f /tmp/depguard-out
        echo "✅ Dependency baseline updated."
    else
        cat /tmp/depguard-out
        rm -f /tmp/depguard-out
        echo "💥 dependencyGuardBaseline failed."
        exit 1
    fi
}

# ── Main ──────────────────────────────────────────────────────────────────────
check_branch
run_module_graphs
run_ktlint
run_detekt
run_dependency_guard

GIT_USERNAME=$(git config user.name)
echo "✅ Ready to commit, $GIT_USERNAME!"
