# Shared Foundations

## Status

- State: Approved
- Priority: High
- Primary sources:
  - `specs/ai-context.md`
  - `specs/implementation-plan.md`
  - `specs/portlens-specs.html`

## Objective

Define the shared shell, fiscal data presentation, interactions, and contracts reused across all screens.

## Included Scope

- app shell
- primary navigation
- top bar and active competence
- mini exemption status card
- number, color, and table standards
- global shortcuts
- specific error messaging

## Out of Scope

- detailed PDF parsing logic
- persistence details unique to each flow
- visual refinements specific to a single screen

## Dependencies

- fiscal business rules
- transaction contract
- base design system

## Skill Alignment

- `android-module-structure` for shared module boundaries and ownership
- `android-compose-ui` for app-shell composables and visual interaction patterns
- `android-presentation-mvi` for screen contracts built on top of these shared foundations
- `android-navigation` for route assembly expectations
- `android-di-koin` for root-level ViewModel injection
- `android-testing` for shared UI and ViewModel validation strategy

## Implementation Sequence

1. Define the shared shell layout and route framing.
2. Lock number formatting, semantic colors, and table density rules.
3. Define keyboard shortcuts and focus behavior.
4. Define reusable validation and error-message patterns.
5. Validate dark and light theme parity before moving to screen-specific work.

## Screen Parts

### Part 1 - App Shell

#### Tasks

- [ ] Task 1.1 Define the sidebar with the 4 main screens
- [ ] Task 1.2 Define active/inactive navigation states
- [ ] Task 1.3 Define a shared top bar with the active competence
- [ ] Task 1.4 Define the global primary action for new transaction entry
- [ ] Task 1.5 Define the mini exemption card as a persistent access point

#### Rules

- current-month status must be visible or one click away
- the shell must remain present even in the empty state

#### Acceptance Criteria

- navigation is consistent across screens
- competence context is visible throughout the main product flow

### Part 2 - Data Standards

#### Tasks

- [ ] Task 2.1 Define BRL currency formatting
- [ ] Task 2.2 Define date and competence formatting
- [ ] Task 2.3 Define mono/tabular numeric typography usage
- [ ] Task 2.4 Define sign conventions for gains and losses
- [ ] Task 2.5 Define semantic colors for exempt, taxable, warning, gain, and loss states
- [ ] Task 2.6 Define dense-table spacing, alignment, and sticky header rules

#### Rules

- no monetary value without unit or context
- gains and losses must always display an explicit sign

#### Acceptance Criteria

- financial numbers are legible and consistent
- gain and loss states are never visually ambiguous

### Part 3 - Interaction Standards

#### Tasks

- [ ] Task 3.1 Define the `N` shortcut
- [ ] Task 3.2 Define the `Esc` shortcut
- [ ] Task 3.3 Define the `Enter` shortcut
- [ ] Task 3.4 Define visible focus for all interactive elements
- [ ] Task 3.5 Define specific validation and error messages
- [ ] Task 3.6 Define hover, selected, and disabled interaction states
- [ ] Task 3.7 Define the minimum light-theme contrast expectations

#### Rules

- the product is keyboard-driven
- generic error messages are not allowed

#### Acceptance Criteria

- shortcuts work in the correct contexts
- focus is always visible

## States and Exceptions

- no data
- validation errors
- file read errors
- light theme support

## Final Checklist

- [ ] Current-month fiscal status is visible or one click away
- [ ] Monetary values always include context
- [ ] Gains/losses use explicit sign and semantic color
- [ ] Focus is visible
- [ ] Applicable shortcuts are validated
- [ ] Light theme is considered
