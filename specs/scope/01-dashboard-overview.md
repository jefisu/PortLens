# Screen 01 - Dashboard / Overview

## Status

- State: Approved
- Priority: High
- Primary sources:
  - `specs/ai-context.md`
  - `specs/implementation-plan.md`
  - `specs/portlens-specs.html`

## Objective

Answer the product's central question quickly for the current competence.

## Included Scope

- main exemption card
- current-month metrics
- latest transactions list
- update timestamp context
- access to new transaction entry

## Out of Scope

- full inline editing of the ledger
- advanced charts

## Dependencies

- `00-shared-foundations.md`
- monthly sale volume calculation
- realized gain calculation
- monthly fiscal status calculation

## Skill Alignment

- `android-compose-ui` for dashboard cards, previews, and accessibility
- `android-presentation-mvi` for `DashboardState`, `DashboardAction`, `DashboardEvent`, and `DashboardViewModel`
- `android-navigation` for navigation to the Transactions surface when needed
- `android-testing` for ViewModel and UI-state coverage

## Implementation Sequence

1. Build the page header and competence context.
2. Build the main exemption card and progress display.
3. Add supporting month metrics.
4. Add the latest transactions list and navigation link.
5. Validate empty, exempt, and taxable states.

## Screen Parts

### Part 1 - Header

#### Tasks

- [ ] Task 1.1 Define the page title
- [ ] Task 1.2 Show the last updated timestamp
- [ ] Task 1.3 Show the new transaction action
- [ ] Task 1.4 Ensure the current competence is readable without entering another screen

#### Rules

- context must be immediately legible

#### Acceptance Criteria

- the user understands the active competence at a glance

### Part 2 - Main Exemption Card

#### Tasks

- [ ] Task 2.1 Show current-month sale volume
- [ ] Task 2.2 Show the current fiscal status
- [ ] Task 2.3 Show progress against the R$ 20,000 limit
- [ ] Task 2.4 Show the remaining tax-free margin
- [ ] Task 2.5 Define the visual state for exempt month
- [ ] Task 2.6 Define the visual state for near-limit month
- [ ] Task 2.7 Define the visual state for taxable month

#### Rules

- this card has the highest visual hierarchy on the screen

#### Acceptance Criteria

- the user understands in seconds whether the month is still exempt

### Part 3 - Current-Month Metrics

#### Tasks

- [ ] Task 3.1 Show percentage of limit consumed
- [ ] Task 3.2 Show remaining margin
- [ ] Task 3.3 Show realized gain
- [ ] Task 3.4 Show estimated tax
- [ ] Task 3.5 Define metric ordering by fiscal importance

#### Rules

- each metric must include unit and context

#### Acceptance Criteria

- supporting metrics complement the main card without creating confusion

### Part 4 - Latest Transactions

#### Tasks

- [ ] Task 4.1 Show a short list of recent transactions
- [ ] Task 4.2 Define the minimum useful columns
- [ ] Task 4.3 Define a CTA to the full Transactions screen
- [ ] Task 4.4 Define the maximum row count before truncation

#### Rules

- this section bridges the macro view and operational detail

#### Acceptance Criteria

- the user can move naturally from summary to specific operation

## States and Exceptions

- no transactions
- month with no sales
- taxable month

## Final Checklist

- [ ] Current-month fiscal status is visible or one click away
- [ ] Monetary values always include context
- [ ] Gains/losses use explicit sign and semantic color
- [ ] Focus is visible
- [ ] Applicable shortcuts are validated
- [ ] Light theme is considered
