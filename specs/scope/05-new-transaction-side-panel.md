# Screen 05 - New Transaction Side Panel

## Status

- State: Approved
- Priority: High
- Primary sources:
  - `specs/ai-context.md`
  - `specs/implementation-plan.md`
  - `specs/portlens-specs.html`

## Objective

Allow fast manual entry with a live fiscal preview without leaving the current screen.

## Included Scope

- right-side panel opening
- base form
- live fiscal preview
- keyboard confirmation

## Out of Scope

- separate manual-entry page
- multi-step manual-entry wizard

## Dependencies

- `00-shared-foundations.md`
- transaction contracts
- instant preview calculations

## Skill Alignment

- `android-compose-ui` for side-panel composition, text fields, and previews
- `android-presentation-mvi` for form state, actions, events, and ViewModel
- `android-error-handling` for typed validation failures and user feedback
- `android-testing` for draft, preview, and submission behaviors

## Implementation Sequence

1. Build panel open and close behavior.
2. Build the core form fields and validations.
3. Add live preview blocks fed by draft input.
4. Add confirm and cancel behavior.
5. Validate keyboard-only entry flow.

## Screen Parts

### Part 1 - Open and Close Behavior

#### Tasks

- [ ] Task 1.1 Define opening via button
- [ ] Task 1.2 Define opening via `N`
- [ ] Task 1.3 Define closing via `Esc`
- [ ] Task 1.4 Define closing via cancel action
- [ ] Task 1.5 Define how unsaved input is handled on close

#### Rules

- the panel must not change route
- the underlying screen context should remain perceptible

#### Acceptance Criteria

- the user can open and close the panel without losing context

### Part 2 - Form

#### Tasks

- [ ] Task 2.1 Define operation type selector
- [ ] Task 2.2 Define date input
- [ ] Task 2.3 Define ticker input
- [ ] Task 2.4 Define quantity input
- [ ] Task 2.5 Define unit price input
- [ ] Task 2.6 Define inline validation copy for each field

#### Rules

- validations must be field-specific
- purchase, sale, and repurchase must all be supported

#### Acceptance Criteria

- the form accepts only valid entries

### Part 3 - Live Fiscal Preview

#### Tasks

- [ ] Task 3.1 Show operation volume
- [ ] Task 3.2 Show estimated gain/loss
- [ ] Task 3.3 Show weighted average before and after
- [ ] Task 3.4 Show monthly sale volume after the operation
- [ ] Task 3.5 Show impact on fiscal status
- [ ] Task 3.6 Show remaining margin
- [ ] Task 3.7 Define preview behavior when the draft is incomplete

#### Rules

- the preview updates while the user types

#### Acceptance Criteria

- the user can make a fiscal decision before saving

### Part 4 - Confirmation

#### Tasks

- [ ] Task 4.1 Define the confirm CTA
- [ ] Task 4.2 Define the `Enter` shortcut
- [ ] Task 4.3 Define persistence and recalculation after confirmation
- [ ] Task 4.4 Define post-save success handling and panel reset

#### Rules

- confirmation is only available when the input is valid

#### Acceptance Criteria

- saving a transaction updates the underlying screen correctly

## States and Exceptions

- partial preview
- invalid input
- unknown ticker
- repurchase outside the D+1 rule

## Final Checklist

- [ ] Current-month fiscal status is visible or one click away
- [ ] Monetary values always include context
- [ ] Gains/losses use explicit sign and semantic color
- [ ] Focus is visible
- [ ] Applicable shortcuts are validated
- [ ] Light theme is considered
