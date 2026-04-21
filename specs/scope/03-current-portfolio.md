# Screen 03 - Current Portfolio

## Status

- State: Approved
- Priority: Medium
- Primary sources:
  - `specs/ai-context.md`
  - `specs/implementation-plan.md`
  - `specs/portlens-specs.html`

## Objective

Consolidate current holdings per ticker and show how much can be sold without exceeding the monthly tax-free margin.

## Included Scope

- portfolio summary
- portfolio evolution block
- holdings table
- tax-safe sale suggestions

## Out of Scope

- investment recommendations
- advanced charting

## Dependencies

- `00-shared-foundations.md`
- consolidated holdings by ticker
- remaining competence margin
- sale suggestion calculation

## Skill Alignment

- `android-compose-ui` for summary cards, table layout, and lightweight chart presentation
- `android-presentation-mvi` for portfolio screen state and actions
- `android-data-layer` for consolidated holdings and suggestion inputs
- `android-testing` for suggestion logic and empty/quote-missing states

## Implementation Sequence

1. Build the portfolio summary block.
2. Add the simplified evolution view.
3. Build the holdings table.
4. Add tax-safe sale suggestion logic.
5. Validate quote-missing and zero-margin cases.

## Screen Parts

### Part 1 - Portfolio Summary

#### Tasks

- [ ] Task 1.1 Show total portfolio value
- [ ] Task 1.2 Show cumulative gain
- [ ] Task 1.3 Show return percentage
- [ ] Task 1.4 Show active ticker count
- [ ] Task 1.5 Define refresh timestamp or quote recency display

#### Rules

- positive and negative performance must be visually clear

#### Acceptance Criteria

- the user understands overall size and performance quickly

### Part 2 - Portfolio Evolution

#### Tasks

- [ ] Task 2.1 Define the 12-month evolution block
- [ ] Task 2.2 Define the primary series and optional weighted-average reference
- [ ] Task 2.3 Keep visual scope constrained to v1 needs
- [ ] Task 2.4 Define the fallback state when no history exists

#### Rules

- this must not become an advanced analytics module

#### Acceptance Criteria

- the chart provides context without distracting from fiscal utility

### Part 3 - Holdings Table

#### Tasks

- [ ] Task 3.1 Define the current position columns
- [ ] Task 3.2 Show weighted average and quote
- [ ] Task 3.3 Show market value
- [ ] Task 3.4 Show return by asset
- [ ] Task 3.5 Show sale suggestion
- [ ] Task 3.6 Define sorting order for positions

#### Rules

- suggestions must consider volume already sold in the selected month

#### Acceptance Criteria

- the table connects current holdings to remaining fiscal margin

### Part 4 - Sale Suggestion Logic

#### Tasks

- [ ] Task 4.1 Define suggested quantity calculation
- [ ] Task 4.2 Define suggested sale volume calculation
- [ ] Task 4.3 Define zero-suggestion cases
- [ ] Task 4.4 Define full-position sale cases
- [ ] Task 4.5 Define messaging that distinguishes fiscal safety from investment advice

#### Rules

- this is a fiscal suggestion, not an investment recommendation

#### Acceptance Criteria

- the user understands how much can be sold without breaking the limit

## States and Exceptions

- no holdings
- missing quote
- zero remaining margin
- asset price above remaining margin

## Final Checklist

- [ ] Current-month fiscal status is visible or one click away
- [ ] Monetary values always include context
- [ ] Gains/losses use explicit sign and semantic color
- [ ] Focus is visible
- [ ] Applicable shortcuts are validated
- [ ] Light theme is considered
