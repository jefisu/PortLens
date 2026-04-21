# PortLens - AI Context

Use this file as the compact project brief for implementation work. It is a distilled companion to `specs/portlens-specs.html` and `specs/implementation-plan.md`.

## Product in One Paragraph

PortLens is a desktop tool for Brazilian retail investors who use the monthly stock-sale tax exemption to legally raise cost basis. The product's single job is to answer: **"Can I sell this position right now without paying tax?"** Every design and implementation decision should reinforce fiscal clarity, dense but legible data display, and fast keyboard-driven entry.

## Primary User Workflow

1. Record transactions manually or import them from PDF/CSV.
2. Let the system calculate weighted average cost, realized gain/loss, and monthly sale volume.
3. See whether the current month is still exempt under the R$ 20,000 rule.
4. Use the Current Portfolio view to identify tax-safe sale quantities.
5. Review month-by-month fiscal history in Monthly Summary.

## Non-Negotiable Product Question

The current-month exemption status is the most important datum in the app. It must be visible or one click away from any core screen.

## Canonical v1 Screens

1. Dashboard / Overview
2. Transactions
3. Current Portfolio
4. Monthly Summary
5. New Transaction side panel
6. Empty state
7. Import Step 1 - File selection
8. Import Step 2 - Review
9. Import Step 3 - Confirmation/result
10. Transactions with traceability enabled
11. Duplicate-note interruption state

## Core Calculations

For each transaction, PortLens derives:

- operation volume
- weighted average price before the operation
- quantity and total cost before and after
- new weighted average price after the operation
- realized gain or loss on sales only
- accumulated monthly sale volume across all tickers
- exemption status for the month

## Critical Business Rules

1. The R$ 20,000 exemption limit is monthly.
2. The limit sums stock sales across all tickers, not per asset.
3. The exemption applies only to regular stocks, not FIIs, ETFs, or BDRs.
4. A repurchase must happen on D+1 to qualify as swing trade rather than day trade.
5. Repurchase changes weighted average price by weighting the repurchase price with the remaining balance.
6. Even exempt operations must still be declared in the IRPF.

## Import Contract

Import is a 3-step full-screen modal: file -> review -> confirm.

### Step 1

- Accept PDF brokerage note or PortLens CSV.
- For PDFs, extract the brokerage-note number before any deeper processing.
- If that note number already exists, halt the flow immediately.
- Show the duplicate-note interruption state.

### Step 2

- Show every extracted operation in a review table.
- Each row has one status: Ready, Review, or Conflict.
- Allow individual cell edits.
- Allow partial import by unchecking rows.
- Never auto-resolve conflicts.
- Show projected fiscal impact in the footer.

### Step 3

- Persist only after explicit confirmation.
- Attach note number metadata to PDF-imported rows.
- Recalculate weighted average, sale volume, gain/loss, and exemption status.
- Show imported, skipped, and corrected counts.

## Traceability Contract

- PDF-imported operations persist the brokerage-note number.
- The Transactions table has an optional note-number column.
- That column is hidden by default.
- Hovering a linked row shows note number, issue date, and broker.
- Manually entered rows show `-` in that field.
- CSV export must preserve the note-number field.

## Interaction Rules

- The UI is dark-first, data-dense, and keyboard-driven.
- Manual entry happens in a side panel, not a separate page.
- The entry form must provide live fiscal preview while the user types.
- Error messages must be specific, not generic.
- Primary shortcuts: `N`, `Esc`, `Enter`.
- Light theme must be functional, not just inverted colors.

## Acceptance Heuristics

Use these as quick checks while implementing:

- no monetary value without unit or context
- gains/losses include explicit sign and semantic color
- monetary values use monospace/tabular digits
- focus is visible on all interactive elements
- no imported row is persisted before Step 3 confirmation
- duplicate-note detection happens before PDF processing
- residual conflicts always require explicit user choice

## Out of Scope for v1

- responsiveness below 960px
- theme customization beyond light/dark toggle
- advanced charts such as candles, heatmaps, or IBOV comparison
- decorative animation
- authentication
- multi-user support

## Source of Truth

- `specs/portlens-specs.html` is the canonical product and screen specification.
- `specs/implementation-plan.md` is the descriptive implementation reference.
- When in doubt, prefer the behavior and wording implied by the HTML spec.
