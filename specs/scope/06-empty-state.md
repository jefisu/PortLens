# Screen 06 - Empty State

## Status

- State: Approved
- Priority: High
- Primary sources:
  - `specs/ai-context.md`
  - `specs/implementation-plan.md`
  - `specs/portlens-specs.html`

## Objective

Guide first-time use without leaving the main application shell.

## Included Scope

- empty state within the shell
- CTA for manual entry
- CTA for import
- short value propositions

## Out of Scope

- separate onboarding flow
- complex guided walkthrough

## Dependencies

- `00-shared-foundations.md`
- manual-entry panel opening
- import flow entry point

## Skill Alignment

- `android-compose-ui` for first-run layout, CTA semantics, and previews
- `android-presentation-mvi` for empty/non-empty screen state transitions
- `android-navigation` for routing into import flow if separated by route
- `android-testing` for CTA visibility and first-run assertions

## Implementation Sequence

1. Keep the full application shell visible with no data.
2. Build the main empty-state message and visual.
3. Add primary and secondary entry CTAs.
4. Add short product value highlights.
5. Validate first-run clarity in dark and light themes.

## Screen Parts

### Part 1 - Base Structure

#### Tasks

- [ ] Task 1.1 Keep the sidebar visible
- [ ] Task 1.2 Keep the top bar visible
- [ ] Task 1.3 Show no-data state in the side mini-card
- [ ] Task 1.4 Define the default competence display when no data exists

#### Rules

- the shell must remain intact

#### Acceptance Criteria

- the user still recognizes the app structure without data

### Part 2 - Main Message

#### Tasks

- [ ] Task 2.1 Define the main headline
- [ ] Task 2.2 Define short product-value copy
- [ ] Task 2.3 Define the visual empty-state support
- [ ] Task 2.4 Define the empty-state copy tone for first-time users

#### Rules

- the message must push the user toward a useful action

#### Acceptance Criteria

- the empty state does not feel like a dead end

### Part 3 - CTAs

#### Tasks

- [ ] Task 3.1 Define the primary CTA for new transaction entry
- [ ] Task 3.2 Define the secondary CTA for file import
- [ ] Task 3.3 Define hierarchy between the two actions
- [ ] Task 3.4 Define keyboard access to the primary CTA

#### Rules

- manual entry and import are both valid first actions

#### Acceptance Criteria

- the user knows how to begin immediately

### Part 4 - Product Value Highlights

#### Tasks

- [ ] Task 4.1 Show automatic calculation benefit
- [ ] Task 4.2 Show monthly limit tracking benefit
- [ ] Task 4.3 Show sale suggestion benefit
- [ ] Task 4.4 Define maximum content density so the state stays welcoming

#### Rules

- value highlights must stay concise

#### Acceptance Criteria

- the empty state explains why the product is useful, not just what to click

## States and Exceptions

- light theme
- shell with no data
- CTA unavailable because of error

## Final Checklist

- [ ] Current-month fiscal status is visible or one click away
- [ ] Monetary values always include context
- [ ] Gains/losses use explicit sign and semantic color
- [ ] Focus is visible
- [ ] Applicable shortcuts are validated
- [ ] Light theme is considered
