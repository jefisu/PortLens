# Screen 08 - Import Step 2 - Review

## Status

- State: Approved
- Priority: High
- Primary sources:
  - `specs/ai-context.md`
  - `specs/implementation-plan.md`
  - `specs/portlens-specs.html`

## Objective

Allow full human review of extracted operations before any data is persisted.

## Included Scope

- contextual file/note header
- status summary
- editable review table
- partial row selection
- conflict handling
- projected fiscal impact

## Out of Scope

- final persistence
- automatic conflict resolution

## Dependencies

- Step 1 completed
- extracted operations available
- per-row status classification

## Skill Alignment

- `android-compose-ui` for editable review table, status semantics, and footer layout
- `android-presentation-mvi` for review-state orchestration and user decisions
- `android-error-handling` for typed row-validation and conflict states
- `android-testing` for edit flows, conflict gating, and fiscal projection behavior

## Implementation Sequence

1. Build the contextual header and status summary.
2. Build the editable review table.
3. Add row selection and partial import behavior.
4. Add explicit conflict handling.
5. Add projected fiscal impact in the footer.
6. Gate progression to Step 3 on review completeness.

## Screen Parts

### Part 1 - Contextual Header

#### Tasks

- [ ] Task 1.1 Show the file name
- [ ] Task 1.2 Show the import type
- [ ] Task 1.3 Show note metadata when the source is PDF
- [ ] Task 1.4 Define the fallback header when note metadata is unavailable

#### Rules

- source identification must be visible at the top

#### Acceptance Criteria

- the user always knows what is being reviewed

### Part 2 - Status Summary

#### Tasks

- [ ] Task 2.1 Show ready row count
- [ ] Task 2.2 Show review-needed row count
- [ ] Task 2.3 Show conflict row count
- [ ] Task 2.4 Define how counts react to unchecked rows

#### Rules

- each status has its own visual semantics

#### Acceptance Criteria

- the distribution of issues is easy to understand quickly

### Part 3 - Review Table

#### Tasks

- [ ] Task 3.1 Define the review columns
- [ ] Task 3.2 Allow per-cell editing
- [ ] Task 3.3 Allow unchecking rows for partial import
- [ ] Task 3.4 Highlight rows that need review or have conflicts
- [ ] Task 3.5 Define row-level validation feedback after edits
- [ ] Task 3.6 Define how edited values affect status transitions

#### Rules

- no row is persisted at this stage

#### Acceptance Criteria

- the user can correct and select only the rows they want to import

### Part 4 - Residual Conflicts

#### Tasks

- [ ] Task 4.1 Define conflict criteria against existing manual entries
- [ ] Task 4.2 Require an explicit user decision
- [ ] Task 4.3 Disallow silent overwrite
- [ ] Task 4.4 Define UI copy for ignore vs replace or equivalent explicit actions

#### Rules

- residual conflicts must never be auto-resolved

#### Acceptance Criteria

- conflicts are only resolved through explicit user choice

### Part 5 - Projected Fiscal Impact

#### Tasks

- [ ] Task 5.1 Show sale volume before and after import
- [ ] Task 5.2 Show whether the month stays exempt or becomes taxable
- [ ] Task 5.3 Show limit consumption percentage after import
- [ ] Task 5.4 Define the most-affected-month selection rule when multiple months are touched

#### Rules

- the footer must show the most affected month

#### Acceptance Criteria

- the user understands the fiscal effect before confirming

## States and Exceptions

- no selected rows
- pending conflict
- invalid cell value
- import would make the month taxable

## Final Checklist

- [ ] Current-month fiscal status is visible or one click away
- [ ] Monetary values always include context
- [ ] Gains/losses use explicit sign and semantic color
- [ ] Focus is visible
- [ ] Applicable shortcuts are validated
- [ ] Light theme is considered
