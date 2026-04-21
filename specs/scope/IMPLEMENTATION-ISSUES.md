# PortLens - Implementation Issues

## Status

- State: Approved
- Priority: High
- Primary sources:
  - `specs/scope/README.md`
  - `specs/scope/*.md`

## Objective

Provide a GitHub-issues style execution tracker derived from the scope files, already aligned with the Android/KMP implementation skills.

## Global UI Rule

- [ ] Build UI as stateless composables by default, with state hoisted to ViewModels or parent containers.
- [ ] Keep `Root` composables responsible for ViewModel wiring and keep `Screen` composables pure.
- [ ] Add `@Preview` coverage for every meaningful screen and reusable UI block so visual refinement can happen in isolation.
- [ ] Use realistic sample preview data rather than empty placeholders unless the preview is specifically for an empty state.

## Phase 1 - Shared Foundations

### Issue: Define shared application foundations

**Scope file**: `00-shared-foundations.md`

**Implementation standards**:
- `android-module-structure`
- `android-compose-ui`
- `android-presentation-mvi`
- `android-navigation`
- `android-di-koin`

**Checklist**:
- [ ] Define target module placement for shared shell, shared domain contracts, and shared presentation helpers
- [ ] Define sidebar, top bar, and mini exemption card as reusable app-shell elements
- [ ] Define stateless component boundaries for shared shell elements
- [ ] Define BRL, date, competence, and tabular-number formatting rules
- [ ] Define semantic color usage for exempt, taxable, gain, loss, and warning states
- [ ] Define keyboard shortcuts and visible focus behavior
- [ ] Define reusable validation and error-message patterns
- [ ] Add preview coverage for shared shell components and visual states
- [ ] Validate dark and light theme parity for shared UI primitives

## Phase 2 - Manual Flow

### Issue: Build Dashboard / Overview

**Scope file**: `01-dashboard-overview.md`

**Implementation standards**:
- `android-compose-ui`
- `android-presentation-mvi`
- `android-navigation`
- `android-testing`

**Checklist**:
- [ ] Implement dashboard screen state, actions, events, and ViewModel
- [ ] Implement root composable and pure screen composable
- [ ] Keep dashboard UI blocks stateless and callback-driven
- [ ] Build header and competence context
- [ ] Build main exemption card with progress and remaining margin
- [ ] Build supporting month metrics
- [ ] Build latest transactions preview block
- [ ] Add preview coverage and screen/UI tests for main states

### Issue: Build Transactions screen

**Scope file**: `02-transactions.md`

**Implementation standards**:
- `android-compose-ui`
- `android-presentation-mvi`
- `android-data-layer`
- `android-error-handling`
- `android-testing`

**Checklist**:
- [ ] Implement transaction ledger screen state, actions, events, and ViewModel
- [ ] Keep the ledger screen composable stateless with state hoisted to the ViewModel
- [ ] Implement filtered summary cards
- [ ] Implement dense ledger table with derived columns
- [ ] Implement inline edit behavior and recalculation flow
- [ ] Implement CSV export action and success feedback
- [ ] Add previews for key ledger states and row variants
- [ ] Add ViewModel tests for filtering and recalculation behavior
- [ ] Add Compose UI tests for key table states

### Issue: Build New Transaction side panel

**Scope file**: `05-new-transaction-side-panel.md`

**Implementation standards**:
- `android-compose-ui`
- `android-presentation-mvi`
- `android-error-handling`
- `android-testing`

**Checklist**:
- [ ] Implement panel root/state/actions/events/ViewModel contract
- [ ] Keep form and preview sections stateless and previewable
- [ ] Implement form inputs and validation
- [ ] Implement live fiscal preview from draft input
- [ ] Implement save, cancel, and keyboard shortcuts
- [ ] Persist draft-critical fields if process-death handling is needed
- [ ] Add previews for valid, invalid, and borderline fiscal states
- [ ] Add tests for preview and validation behavior

### Issue: Build Empty state

**Scope file**: `06-empty-state.md`

**Implementation standards**:
- `android-compose-ui`
- `android-presentation-mvi`
- `android-navigation`
- `android-testing`

**Checklist**:
- [ ] Implement empty-state screen within the main shell
- [ ] Implement primary CTA to manual entry
- [ ] Implement secondary CTA to import flow
- [ ] Implement product value highlights
- [ ] Add previews for first-run and light-theme empty-state variants
- [ ] Add UI tests for CTA visibility and first-run state

## Phase 3 - Analytical Views

### Issue: Build Current Portfolio screen

**Scope file**: `03-current-portfolio.md`

**Implementation standards**:
- `android-compose-ui`
- `android-presentation-mvi`
- `android-data-layer`
- `android-testing`

**Checklist**:
- [ ] Implement portfolio screen state, actions, events, and ViewModel
- [ ] Keep summary, chart, and holdings blocks stateless and independently previewable
- [ ] Implement portfolio summary block
- [ ] Implement simplified evolution block
- [ ] Implement holdings table and tax-safe sale suggestions
- [ ] Add previews for gain, loss, and zero-margin portfolio states
- [ ] Add tests for suggestion logic and edge cases

### Issue: Build Monthly Summary screen

**Scope file**: `04-monthly-summary.md`

**Implementation standards**:
- `android-compose-ui`
- `android-presentation-mvi`
- `android-data-layer`
- `android-testing`

**Checklist**:
- [ ] Implement monthly summary screen state, actions, events, and ViewModel
- [ ] Implement year filter and yearly summary cards
- [ ] Implement competence table and progress semantics
- [ ] Add previews for exempt, taxable, and no-activity year states
- [ ] Add tests for taxable and no-activity states

## Phase 4 - Import and Traceability

### Issue: Build Import Step 1

**Scope file**: `07-import-step-1-file-selection.md`

**Implementation standards**:
- `android-compose-ui`
- `android-presentation-mvi`
- `android-data-layer`
- `android-error-handling`
- `android-testing`

**Checklist**:
- [ ] Implement import step 1 state, actions, events, and ViewModel
- [ ] Implement modal shell and stepper
- [ ] Implement import mode selection and file picking
- [ ] Implement early PDF note extraction
- [ ] Implement duplicate-note lookup and routing logic
- [ ] Add previews for PDF mode, CSV mode, and file-error states
- [ ] Add tests for invalid file and duplicate-note cases

### Issue: Build Import Step 2

**Scope file**: `08-import-step-2-review.md`

**Implementation standards**:
- `android-compose-ui`
- `android-presentation-mvi`
- `android-error-handling`
- `android-testing`

**Checklist**:
- [ ] Implement review state, actions, events, and ViewModel
- [ ] Implement editable review table and row selection
- [ ] Implement status counters and row status transitions
- [ ] Implement explicit residual conflict handling
- [ ] Implement projected fiscal impact footer
- [ ] Add previews for ready, review, and conflict-heavy states
- [ ] Add tests for conflict gating and row edits

### Issue: Build Import Step 3

**Scope file**: `09-import-step-3-confirmation-result.md`

**Implementation standards**:
- `android-compose-ui`
- `android-presentation-mvi`
- `android-data-layer`
- `android-error-handling`
- `android-testing`

**Checklist**:
- [ ] Implement confirmation/result state, actions, events, and ViewModel
- [ ] Persist only selected rows
- [ ] Attach note metadata to imported PDF rows
- [ ] Trigger fiscal recalculation after persistence
- [ ] Implement result summary and exit CTAs
- [ ] Add previews for exempt-result and taxable-result variants
- [ ] Add tests for partial import and final fiscal impact

### Issue: Build traceability state in Transactions

**Scope file**: `10-transactions-traceability.md`

**Implementation standards**:
- `android-compose-ui`
- `android-presentation-mvi`
- `android-data-layer`
- `android-testing`

**Checklist**:
- [ ] Extend transaction presentation state with traceability metadata
- [ ] Implement hidden-by-default note column
- [ ] Implement note metadata tooltip
- [ ] Preserve traceability in CSV export
- [ ] Add previews for manual-row and imported-row traceability variants
- [ ] Add tests for manual vs imported rows

### Issue: Build Duplicate Note interruption state

**Scope file**: `11-duplicate-note-interruption.md`

**Implementation standards**:
- `android-compose-ui`
- `android-presentation-mvi`
- `android-data-layer`
- `android-error-handling`
- `android-testing`

**Checklist**:
- [ ] Implement interruption state and blocking behavior
- [ ] Implement duplicate note details payload
- [ ] Implement CTA to view related transactions
- [ ] Implement safe cancel path
- [ ] Add previews for the interruption state with realistic imported-note metadata
- [ ] Add tests that guarantee no duplicate persistence occurs

## Phase 5 - Final Validation

### Issue: Validate global quality criteria

**Implementation standards**:
- `android-compose-ui`
- `android-presentation-mvi`
- `android-testing`

**Checklist**:
- [ ] Validate current-month status visibility across all core surfaces
- [ ] Validate monospace/tabular numeric presentation
- [ ] Validate semantic gain/loss presentation
- [ ] Validate focus visibility on all interactive elements
- [ ] Validate primary shortcuts `N`, `Esc`, and `Enter`
- [ ] Validate light theme usability
- [ ] Validate import persistence only happens in Step 3
- [ ] Validate note deduplication occurs before PDF processing

## Commit Guidance

- Use `commit-patterns` when creating the commit for these documentation files.
- Since the change is documentation-only, prefer a `docs(...)` commit.
