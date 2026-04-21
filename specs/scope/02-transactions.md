# Screen 02 - Transactions

## Status

- State: Approved
- Priority: High
- Primary sources:
  - `specs/ai-context.md`
  - `specs/implementation-plan.md`
  - `specs/portlens-specs.html`

## Objective

Act as the complete ledger of operations with fiscal visibility per row and per period.

## Included Scope

- header and actions
- filters by competence and ticker
- period summary metrics
- full ledger table
- inline editing
- CSV export

## Out of Scope

- note re-import
- automatic resolution of import conflicts

## Dependencies

- `00-shared-foundations.md`
- per-transaction calculations
- round-trip CSV export

## Skill Alignment

- `android-compose-ui` for dense tables, edit states, and accessibility
- `android-presentation-mvi` for ledger screen state and user actions
- `android-data-layer` for transaction data-source/repository boundaries and mappers
- `android-error-handling` for typed validation and persistence/export failures
- `android-testing` for recalculation, filtering, and edit-flow coverage

## Implementation Sequence

1. Build header actions and competence filters.
2. Build summary cards tied to the filtered period.
3. Build the ledger table with derived columns.
4. Add inline editing and recalculation.
5. Add CSV export and final table states.

## Screen Parts

### Part 1 - Header and Actions

#### Tasks

- [ ] Task 1.1 Define the screen title
- [ ] Task 1.2 Define quick competence tabs or selector
- [ ] Task 1.3 Define the filters action
- [ ] Task 1.4 Define the CSV export action
- [ ] Task 1.5 Define the new transaction action
- [ ] Task 1.6 Define ticker filter entry point and selected-filter feedback

#### Rules

- secondary actions must not compete with the primary action

#### Acceptance Criteria

- the user can filter and export without ambiguity

### Part 2 - Summary Cards

#### Tasks

- [ ] Task 2.1 Show total operation count
- [ ] Task 2.2 Show sale volume
- [ ] Task 2.3 Show realized gain/loss
- [ ] Task 2.4 Show fiscal status for the selected period
- [ ] Task 2.5 Define how summary cards respond to empty filtered states

#### Rules

- cards must reflect active filters

#### Acceptance Criteria

- summary and table always remain coherent

### Part 3 - Main Table

#### Tasks

- [ ] Task 3.1 Define the base columns
- [ ] Task 3.2 Define right alignment for numeric values
- [ ] Task 3.3 Define sticky table header behavior
- [ ] Task 3.4 Define purchase row display behavior
- [ ] Task 3.5 Define sale row display behavior
- [ ] Task 3.6 Define repurchase row display behavior
- [ ] Task 3.7 Define totals footer behavior
- [ ] Task 3.8 Define row highlight behavior for edited or selected rows

#### Rules

- the table must be dense and legible
- derived columns must reflect real-time calculations

#### Acceptance Criteria

- each row explains the operational and fiscal effect of the transaction

### Part 4 - Inline Editing

#### Tasks

- [ ] Task 4.1 Define per-cell editing activation
- [ ] Task 4.2 Define field-specific validations
- [ ] Task 4.3 Define recalculation after edit
- [ ] Task 4.4 Define save, cancel, and invalid-edit behavior
- [ ] Task 4.5 Define how dependent rows are refreshed after an edit

#### Rules

- feedback must be specific
- generic errors are not allowed

#### Acceptance Criteria

- editing a cell updates derived values correctly

### Part 5 - CSV Export

#### Tasks

- [ ] Task 5.1 Define direct export with no intermediate confirmation
- [ ] Task 5.2 Define importer-compatible format
- [ ] Task 5.3 Define success feedback
- [ ] Task 5.4 Define export behavior when filters are active

#### Rules

- export must preserve traceability

#### Acceptance Criteria

- the exported CSV can be imported back into the product

## States and Exceptions

- empty table
- filter with no results
- export failure
- manual vs imported row

## Final Checklist

- [ ] Current-month fiscal status is visible or one click away
- [ ] Monetary values always include context
- [ ] Gains/losses use explicit sign and semantic color
- [ ] Focus is visible
- [ ] Applicable shortcuts are validated
- [ ] Light theme is considered
