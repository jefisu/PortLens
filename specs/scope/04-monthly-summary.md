# Screen 04 - Monthly Summary

## Status

- State: Approved
- Priority: Medium
- Primary sources:
  - `specs/ai-context.md`
  - `specs/implementation-plan.md`
  - `specs/portlens-specs.html`

## Objective

Show fiscal history by competence for monthly and yearly review.

## Included Scope

- year filter
- yearly summary cards
- monthly consolidated table

## Out of Scope

- advanced drill-down analytics
- full IRPF reporting workflows

## Dependencies

- `00-shared-foundations.md`
- monthly aggregation by competence
- estimated tax calculation

## Skill Alignment

- `android-compose-ui` for yearly summary cards and competence table rendering
- `android-presentation-mvi` for summary screen state and filter actions
- `android-data-layer` for monthly aggregation contracts
- `android-testing` for filter logic and taxable-month rendering

## Implementation Sequence

1. Build the header and year filter.
2. Add yearly summary cards.
3. Build the monthly competence table.
4. Add progress and status semantics.
5. Validate taxable and no-activity cases.

## Screen Parts

### Part 1 - Header and Year Filter

#### Tasks

- [ ] Task 1.1 Define the screen title
- [ ] Task 1.2 Define year selection
- [ ] Task 1.3 Define an all-years view if retained in scope
- [ ] Task 1.4 Define default year selection behavior

#### Rules

- the year filter must affect both summary and table

#### Acceptance Criteria

- the time context of the analysis is clear

### Part 2 - Yearly Summary Cards

#### Tasks

- [ ] Task 2.1 Show yearly sale volume
- [ ] Task 2.2 Show yearly realized gain
- [ ] Task 2.3 Show yearly estimated tax
- [ ] Task 2.4 Show exempt month count
- [ ] Task 2.5 Define behavior when the selected year has no taxable months

#### Rules

- the cards must summarize the selected year only

#### Acceptance Criteria

- the user can compare fiscal performance for the selected year quickly

### Part 3 - Monthly Competence Table

#### Tasks

- [ ] Task 3.1 Define the monthly columns
- [ ] Task 3.2 Show sale volume and status
- [ ] Task 3.3 Show sold and repurchased quantity
- [ ] Task 3.4 Show gain and estimated tax
- [ ] Task 3.5 Show monthly progress against the R$ 20,000 threshold
- [ ] Task 3.6 Define row ordering and default sort
- [ ] Task 3.7 Define the no-activity row state

#### Rules

- taxable months must stand out clearly

#### Acceptance Criteria

- the table supports month-by-month fiscal auditing

## States and Exceptions

- year with no activity
- month with no sales
- taxable month
- zero estimated tax

## Final Checklist

- [ ] Current-month fiscal status is visible or one click away
- [ ] Monetary values always include context
- [ ] Gains/losses use explicit sign and semantic color
- [ ] Focus is visible
- [ ] Applicable shortcuts are validated
- [ ] Light theme is considered
