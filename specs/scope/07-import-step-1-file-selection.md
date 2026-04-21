# Screen 07 - Import Step 1 - File Selection

## Status

- State: Approved
- Priority: High
- Primary sources:
  - `specs/ai-context.md`
  - `specs/implementation-plan.md`
  - `specs/portlens-specs.html`

## Objective

Start the import flow with format selection and early deduplication for PDF imports.

## Included Scope

- import modal
- 3-step stepper
- mode selection
- drop zone
- note-number deduplication for PDF

## Out of Scope

- persistence
- operation review

## Dependencies

- `00-shared-foundations.md`
- initial file read
- brokerage-note number extraction

## Skill Alignment

- `android-compose-ui` for full-screen modal, stepper, and drag-and-drop UI
- `android-presentation-mvi` for import step 1 state/actions/events
- `android-data-layer` for file ingestion boundaries and duplicate lookup contracts
- `android-error-handling` for typed file-read and duplicate-note failures
- `android-testing` for mode selection and early validation cases

## Implementation Sequence

1. Build the modal shell and stepper.
2. Build import mode selection.
3. Build file selection and validation.
4. Run early PDF note-number extraction and deduplication.
5. Route either to review or interruption state.

## Screen Parts

### Part 1 - Modal Structure

#### Tasks

- [ ] Task 1.1 Define a full-screen import modal
- [ ] Task 1.2 Define a 3-step stepper
- [ ] Task 1.3 Define cancel and close behavior
- [ ] Task 1.4 Define safe return behavior to the originating screen

#### Rules

- abandoning the flow must not cause side effects

#### Acceptance Criteria

- the user understands this is an import subflow

### Part 2 - Import Type Selection

#### Tasks

- [ ] Task 2.1 Define the PDF option
- [ ] Task 2.2 Define the CSV option
- [ ] Task 2.3 Define selected-state behavior
- [ ] Task 2.4 Define mode-specific helper copy

#### Rules

- the difference between formats must be explicit

#### Acceptance Criteria

- the user can choose the mode without ambiguity

### Part 3 - Upload

#### Tasks

- [ ] Task 3.1 Define the drop zone
- [ ] Task 3.2 Define click-to-select behavior
- [ ] Task 3.3 Define type and size restrictions
- [ ] Task 3.4 Define loading or busy state while the file is being inspected

#### Rules

- there is no alternative text-field entry path

#### Acceptance Criteria

- valid files are accepted clearly

### Part 4 - PDF Deduplication

#### Tasks

- [ ] Task 4.1 Extract the note number before deeper processing
- [ ] Task 4.2 Check whether the note already exists
- [ ] Task 4.3 Stop the flow if the note already exists
- [ ] Task 4.4 Define the next state when the source is CSV and note deduplication does not apply

#### Rules

- note number is the primary deduplication key for PDF imports

#### Acceptance Criteria

- a duplicate note never reaches Step 2

### Part 5 - Specific Errors

#### Tasks

- [ ] Task 5.1 Define the error for missing note number
- [ ] Task 5.2 Define the error for unrecognized format
- [ ] Task 5.3 Define the error for invalid file
- [ ] Task 5.4 Define recovery guidance for each error state

#### Rules

- generic errors are not allowed

#### Acceptance Criteria

- the user understands exactly why the file failed

## States and Exceptions

- invalid file
- PDF without note number
- invalid CSV
- note already exists

## Final Checklist

- [ ] Current-month fiscal status is visible or one click away
- [ ] Monetary values always include context
- [ ] Gains/losses use explicit sign and semantic color
- [ ] Focus is visible
- [ ] Applicable shortcuts are validated
- [ ] Light theme is considered
