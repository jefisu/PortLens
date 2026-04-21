# Screen 10 - Transactions with Traceability

## Status

- State: Approved
- Priority: Medium
- Primary sources:
  - `specs/ai-context.md`
  - `specs/implementation-plan.md`
  - `specs/portlens-specs.html`

## Objective

Make the origin of PDF-imported operations explicit inside the Transactions screen.

## Included Scope

- optional note-number column
- column toggle
- note display per row
- metadata tooltip
- manual vs imported distinction

## Out of Scope

- manual editing of the note number
- re-import of already registered notes

## Dependencies

- `02-transactions.md`
- persistent note metadata
- CSV export including the note field

## Skill Alignment

- `android-compose-ui` for optional-column behavior and metadata tooltip patterns
- `android-presentation-mvi` for traceability-enabled transaction presentation state
- `android-data-layer` for note metadata persistence and export compatibility
- `android-testing` for imported/manual row assertions

## Implementation Sequence

1. Add optional note metadata to the transaction presentation model.
2. Add the column toggle and hidden-by-default behavior.
3. Add per-row note rendering and manual-row fallback.
4. Add the metadata tooltip.
5. Validate export preservation and read-only behavior.

## Screen Parts

### Part 1 - Column Activation

#### Tasks

- [ ] Task 1.1 Define the control to enable the note column
- [ ] Task 1.2 Ensure the column is hidden by default
- [ ] Task 1.3 Define persistence of the column visibility preference if desired in v1

#### Rules

- traceability must not clutter the default table view

#### Acceptance Criteria

- the user can enable the column only when needed for auditing

### Part 2 - Per-Row Display

#### Tasks

- [ ] Task 2.1 Show note number for imported rows
- [ ] Task 2.2 Show `-` for manual rows
- [ ] Task 2.3 Show lightweight manual/imported origin indicators
- [ ] Task 2.4 Define truncation or overflow behavior for long note identifiers

#### Rules

- the note is secondary metadata, not the primary datum

#### Acceptance Criteria

- the origin of each row is clear

### Part 3 - Metadata Tooltip

#### Tasks

- [ ] Task 3.1 Show note number on hover/focus
- [ ] Task 3.2 Show issue date
- [ ] Task 3.3 Show broker
- [ ] Task 3.4 Define tooltip placement near table edges

#### Rules

- the tooltip must be reachable via hover and focus

#### Acceptance Criteria

- the user can audit the source without opening another screen

### Part 4 - Integrity

#### Tasks

- [ ] Task 4.1 Keep the field read-only
- [ ] Task 4.2 Preserve the field in CSV export
- [ ] Task 4.3 Define fallback behavior when imported metadata is incomplete

#### Rules

- traceability must survive round-trip export/import

#### Acceptance Criteria

- note metadata remains intact across export/import flows

## States and Exceptions

- manual row
- imported row
- column hidden
- tooltip unavailable due to incomplete metadata

## Final Checklist

- [ ] Current-month fiscal status is visible or one click away
- [ ] Monetary values always include context
- [ ] Gains/losses use explicit sign and semantic color
- [ ] Focus is visible
- [ ] Applicable shortcuts are validated
- [ ] Light theme is considered
