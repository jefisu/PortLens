# Screen 11 - Duplicate Note Interruption

## Status

- State: Approved
- Priority: High
- Primary sources:
  - `specs/ai-context.md`
  - `specs/implementation-plan.md`
  - `specs/portlens-specs.html`

## Objective

Safely interrupt the import flow when a PDF brokerage note already exists in the database.

## Included Scope

- blocking behavior in Step 1
- interruption message
- existing note details
- CTA to view related transactions
- CTA to cancel

## Out of Scope

- re-import
- automatic merge
- overwriting existing data

## Dependencies

- `07-import-step-1-file-selection.md`
- lookup by note number
- filter by note in the Transactions screen

## Skill Alignment

- `android-compose-ui` for interruption-state messaging and CTA presentation
- `android-presentation-mvi` for interruption-state orchestration and navigation events
- `android-data-layer` for duplicate-note lookup and prior-import metadata
- `android-error-handling` for early duplicate interruption as a typed failure path
- `android-testing` for non-persistence guarantees and navigation behavior

## Implementation Sequence

1. Trigger early duplicate-note detection during Step 1.
2. Replace normal progression with the interruption state.
3. Show prior import details and operation count.
4. Add safe exit and audit CTAs.
5. Validate that no duplicate data can be created from this state.

## Screen Parts

### Part 1 - Detection and Blocking

#### Tasks

- [ ] Task 1.1 Interrupt the flow when an existing note is found
- [ ] Task 1.2 Prevent access to Step 2
- [ ] Task 1.3 Guarantee zero additional persistence
- [ ] Task 1.4 Define direct linkage between the duplicate lookup result and this state

#### Rules

- deduplication happens before deeper processing

#### Acceptance Criteria

- a duplicate note never creates new operations

### Part 2 - Interruption Content

#### Tasks

- [ ] Task 2.1 Show the note number
- [ ] Task 2.2 Show the previous import date
- [ ] Task 2.3 Show the number of generated operations
- [ ] Task 2.4 Show the related competence when applicable
- [ ] Task 2.5 Define the explanatory copy for why re-import is blocked

#### Rules

- the message must communicate safety, not a generic failure

#### Acceptance Criteria

- the user understands why the flow stopped

### Part 3 - Available Actions

#### Tasks

- [ ] Task 3.1 Define the CTA to view transactions from that note
- [ ] Task 3.2 Define the CTA to cancel the flow
- [ ] Task 3.3 Make the absence of re-import explicit
- [ ] Task 3.4 Define filtered navigation behavior for the note-specific transaction view

#### Rules

- the correction path is editing existing rows directly

#### Acceptance Criteria

- the user has a clear next step without duplicating data

## States and Exceptions

- already imported note
- note filter returns no unexpected results
- partial metadata from previous import

## Final Checklist

- [ ] Current-month fiscal status is visible or one click away
- [ ] Monetary values always include context
- [ ] Gains/losses use explicit sign and semantic color
- [ ] Focus is visible
- [ ] Applicable shortcuts are validated
- [ ] Light theme is considered
