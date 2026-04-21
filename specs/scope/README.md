# PortLens - Scope Breakdown

## Status

- State: Approved
- Priority: High
- Primary sources:
  - `specs/portlens-specs.html`
  - `specs/implementation-plan.md`
  - `specs/ai-context.md`

## Objective

Organize the v1 scope by screen and by internal screen parts before implementation starts.

## Included Scope

- breakdown by canonical screen
- tasks by screen section
- dependencies and acceptance criteria
- alignment with fiscal rules and import flow rules

## Out of Scope

- low-level engineering backlog
- estimates
- infrastructure tasks unrelated to the user-facing screens

## Dependencies

- `00-shared-foundations.md`
- core product specifications

## Recommended Order

1. `00-shared-foundations.md`
2. `01-dashboard-overview.md`
3. `02-transactions.md`
4. `05-new-transaction-side-panel.md`
5. `06-empty-state.md`
6. `03-current-portfolio.md`
7. `04-monthly-summary.md`
8. `07-import-step-1-file-selection.md`
9. `08-import-step-2-review.md`
10. `09-import-step-3-confirmation-result.md`
11. `10-transactions-traceability.md`
12. `11-duplicate-note-interruption.md`

## Execution Strategy

1. Build shared UI and data conventions first.
2. Ship the manual flow end-to-end before analytical views.
3. Add analytical screens on top of a stable transaction base.
4. Add import and traceability only after transaction persistence and recalculation are reliable.
5. Validate global UX rules last across all screens.

## Cross-Screen Index

| File | Surface | Priority | State | Depends on | Suggested phase |
| --- | --- | --- | --- | --- | --- |
| `00-shared-foundations.md` | Shared foundations | High | Approved | Core specs | Phase 1 |
| `01-dashboard-overview.md` | Dashboard / Overview | High | Approved | `00-shared-foundations.md` | Phase 2 |
| `02-transactions.md` | Transactions | High | Approved | `00-shared-foundations.md` | Phase 2 |
| `05-new-transaction-side-panel.md` | New Transaction side panel | High | Approved | `00-shared-foundations.md`, `02-transactions.md` | Phase 2 |
| `06-empty-state.md` | Empty state | High | Approved | `00-shared-foundations.md`, `05-new-transaction-side-panel.md` | Phase 2 |
| `03-current-portfolio.md` | Current Portfolio | Medium | Approved | `00-shared-foundations.md`, `02-transactions.md` | Phase 3 |
| `04-monthly-summary.md` | Monthly Summary | Medium | Approved | `00-shared-foundations.md`, `02-transactions.md` | Phase 3 |
| `07-import-step-1-file-selection.md` | Import Step 1 | High | Approved | `00-shared-foundations.md`, `02-transactions.md` | Phase 4 |
| `08-import-step-2-review.md` | Import Step 2 | High | Approved | `07-import-step-1-file-selection.md` | Phase 4 |
| `09-import-step-3-confirmation-result.md` | Import Step 3 | High | Approved | `08-import-step-2-review.md`, `02-transactions.md` | Phase 4 |
| `10-transactions-traceability.md` | Transactions traceability state | Medium | Approved | `02-transactions.md`, `09-import-step-3-confirmation-result.md` | Phase 4 |
| `11-duplicate-note-interruption.md` | Duplicate note interruption | High | Approved | `07-import-step-1-file-selection.md` | Phase 4 |

## Skill Coverage

| Skill | Scope usage |
| --- | --- |
| `android-module-structure` | Defines where shared, feature, and app wiring code should live |
| `android-compose-ui` | Governs composables, previews, accessibility, lazy tables/lists, and visual interaction rules |
| `android-presentation-mvi` | Governs screen `State`, `Action`, `Event`, `ViewModel`, `Root`, and `Screen` structure |
| `android-navigation` | Governs route objects, feature nav graphs, and `:app` assembly |
| `android-data-layer` | Governs data sources, repositories, DTOs, entities, and mappers for transaction/import flows |
| `android-error-handling` | Governs `Result`, typed errors, and error-to-UI mapping |
| `android-di-koin` | Governs Koin modules and `koinViewModel()` injection |
| `android-testing` | Governs ViewModel tests, Compose UI tests, and fake-based test strategy |
| `commit-patterns` | Governs the final commit message for these documentation changes |

## Implementation Standards

- Every screen should be implemented with the `android-presentation-mvi` pattern.
- Every screen composable should follow `Root` plus `Screen` separation and use `collectAsStateWithLifecycle()`.
- Feature UI should be built as stateless composables whenever possible, receiving state and callbacks instead of owning business state.
- Every meaningful screen or reusable UI block should provide an `@Preview` so it can be reviewed in isolation before integration.
- Navigation should be type-safe and assembled in `:app` using the `android-navigation` pattern.
- Shared code should move to `:core:*`; feature-specific code should stay inside `:feature:<name>:*` modules.
- Data access for import, transactions, and summaries should use interfaces in domain and implementations in data.
- Expected failures should use typed `Result` errors, not thrown exceptions.
- ViewModels should be injected with Koin at the root composable only.
- Each critical screen or ViewModel should have tests aligned with `android-testing`.

## Phase Summary

| Phase | Goal | Files |
| --- | --- | --- |
| Phase 1 | Shared contracts and shell | `00-shared-foundations.md` |
| Phase 2 | Manual transaction loop | `01-dashboard-overview.md`, `02-transactions.md`, `05-new-transaction-side-panel.md`, `06-empty-state.md` |
| Phase 3 | Analytical views | `03-current-portfolio.md`, `04-monthly-summary.md` |
| Phase 4 | Import and traceability | `07-import-step-1-file-selection.md`, `08-import-step-2-review.md`, `09-import-step-3-confirmation-result.md`, `10-transactions-traceability.md`, `11-duplicate-note-interruption.md` |
| Phase 5 | Final validation | All files |

## Tracking Files

- `README.md` is the scope index and execution map.
- `IMPLEMENTATION-ISSUES.md` is the GitHub-issues style checklist derived from these scopes.

## Precedence

- `specs/portlens-specs.html`
- `specs/implementation-plan.md`
- `specs/ai-context.md`
- files in this directory

## Status Legend

- `Draft`
- `Approved`
- `In progress`
- `Done`
