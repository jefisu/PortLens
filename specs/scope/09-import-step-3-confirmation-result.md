# Screen 09 - Import Step 3 - Confirmation / Result

## Status

- State: Approved
- Priority: High
- Primary sources:
  - `specs/ai-context.md`
  - `specs/implementation-plan.md`
  - `specs/portlens-specs.html`

## Objective

Persist the selected operations and clearly communicate the import result.

## Included Scope

- final confirmation
- persistence
- note number linkage
- fiscal recalculation
- result summary
- exit CTAs

## Out of Scope

- additional review within this step
- reprocessing the original file

## Dependencies

- Step 2 completed
- final row selection
- fiscal recalculation engine

## Skill Alignment

- `android-compose-ui` for result presentation and post-import CTA layout
- `android-presentation-mvi` for confirmation/result state and one-off events
- `android-data-layer` for persistence and metadata attachment
- `android-error-handling` for typed persistence/recalculation failures
- `android-testing` for partial import and final-state assertions

## Implementation Sequence

1. Confirm the reviewed selection.
2. Persist selected rows and attach traceability metadata.
3. Run fiscal recalculation.
4. Build the result summary cards.
5. Build exit paths back to verification screens.

## Screen Parts

### Part 1 - Persistence

#### Tasks

- [ ] Task 1.1 Persist selected rows only
- [ ] Task 1.2 Attach the note number to PDF-imported rows
- [ ] Task 1.3 Guarantee that nothing before this step was persisted
- [ ] Task 1.4 Define behavior when zero rows remain selected at confirmation time

#### Rules

- explicit confirmation is required

#### Acceptance Criteria

- persistence happens only here

### Part 2 - Recalculate Fiscal Base

#### Tasks

- [ ] Task 2.1 Recalculate weighted average
- [ ] Task 2.2 Recalculate sale volume
- [ ] Task 2.3 Recalculate realized gain/loss
- [ ] Task 2.4 Recalculate exemption status
- [ ] Task 2.5 Define recalculation scope when multiple competences are affected

#### Rules

- the final result must reflect the updated data set

#### Acceptance Criteria

- post-import calculations are internally consistent

### Part 3 - Result Summary

#### Tasks

- [ ] Task 3.1 Show imported count
- [ ] Task 3.2 Show skipped count
- [ ] Task 3.3 Show reviewed/corrected count
- [ ] Task 3.4 Define labels for user-deselected vs conflict-skipped rows

#### Rules

- the reason for differences between extracted and imported counts should be understandable

#### Acceptance Criteria

- the user can audit what was included and excluded

### Part 4 - Final Fiscal Impact

#### Tasks

- [ ] Task 4.1 Show final month volume
- [ ] Task 4.2 Show final fiscal status
- [ ] Task 4.3 Show remaining margin when applicable
- [ ] Task 4.4 Show the IRPF reminder
- [ ] Task 4.5 Define the final messaging when the month becomes taxable

#### Rules

- the result must answer the fiscal impact of the import

#### Acceptance Criteria

- the user understands the final state of the affected month

### Part 5 - Exit Paths

#### Tasks

- [ ] Task 5.1 Define the CTA to Dashboard
- [ ] Task 5.2 Define the CTA to imported Transactions
- [ ] Task 5.3 Define filtered-navigation behavior to the imported rows

#### Rules

- the primary CTA should lead to the most useful validation destination

#### Acceptance Criteria

- the user can continue auditing after the flow ends

## States and Exceptions

- partial import
- rejected conflict
- corrected row
- month remains exempt or becomes taxable

## Final Checklist

- [ ] Current-month fiscal status is visible or one click away
- [ ] Monetary values always include context
- [ ] Gains/losses use explicit sign and semantic color
- [ ] Focus is visible
- [ ] Applicable shortcuts are validated
- [ ] Light theme is considered
