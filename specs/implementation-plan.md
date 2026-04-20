# PortLens - Implementation Plan v1.1

## Product Context

PortLens is a desktop portfolio-tracking tool for individual Brazilian investors using the **monthly capital-gains tax exemption strategy** to legally raise the cost basis of their stock positions.

The product has one job:

**"Can I sell this position right now without paying tax?"**

The user records every transaction through manual entry, CSV, or PDF brokerage note. PortLens computes weighted average cost, accumulated monthly sale volume, realized gain/loss, and the current exemption status in real time. The interface is dark-first, data-dense, and keyboard-driven because the target user comes to PortLens to look at numbers, not decorative motion.

---

## v1 Scope

Version 1 must cover the full operational loop:

- Manual transaction entry
- PDF brokerage-note import
- CSV import and export using the PortLens format
- Automatic calculation of weighted average cost, realized gain/loss, monthly sale volume, and exemption status
- Consolidated current portfolio view
- Monthly fiscal summary by competence
- Traceability for PDF-imported operations through brokerage-note number
- Review-before-persist import workflow

---

## Product Surface

### 1. Dashboard / Overview

The entry page gathers the most important information for the current month:

- Main monthly exemption card
- Total sale volume for the current month
- Percentage of the R$ 20,000 monthly limit already consumed
- Remaining tax-free sale margin
- Realized gain for the month
- Estimated income tax for the month
- Latest transactions list

The current-month exemption status must always be visible or one click away.

---

### 2. Module 1 - Transactions (Lancamentos)

This is the full ledger of portfolio operations.

Each transaction contains:

- Date
- Ticker
- Type: `Compra` / `Venda` / `Recompra`
- Quantity
- Unit price

From those entries the system derives, per operation and in real time:

- Operation volume
- Weighted average price before the operation
- Quantity and total cost held before and after the operation
- New weighted average price after the operation
- Realized gain or loss, when applicable
- Accumulated sale volume in the current month
- Exemption status for the selected month

The Transactions screen includes:

- Full operations table
- Filters by competence and ticker
- Summary metrics for the current period
- CSV export action
- Optional traceability column for brokerage-note number

---

### 3. Module 2 - Current Portfolio (Carteira Atual)

This screen provides a consolidated per-ticker view containing:

- Current quantity held
- Current weighted average price
- User-provided quote
- Market value
- Unrealized gain
- Return percentage
- Suggested sale quantity that stays within the remaining exemption margin for the month

Sale suggestions must consider the volume already sold in the selected competence.

---

### 4. Module 3 - Monthly Summary (Competencias)

This screen shows one row per month/year containing:

- Total sale volume for the month
- Exemption status
- Realized gain for the month
- Estimated tax
- Total quantity sold
- Total quantity repurchased

Terminology note: **competencia** is the monthly Brazilian fiscal period surfaced in the UI.

---

### 5. New Transaction Side Panel

Manual transaction entry happens in a right-side panel that opens without leaving the current screen.

The panel allows the user to:

- Choose the operation type
- Fill date, ticker, quantity, and unit price
- Review a live fiscal preview before confirming

The live preview must show:

- Operation volume
- Estimated gain/loss for the operation
- Weighted average before and after
- Monthly sale volume after the operation
- Impact on exemption status
- Remaining monthly tax-free margin

Primary shortcuts:

- `N` to open the panel
- `Esc` to close it
- `Enter` to confirm the transaction

---

### 6. Empty State

When no transactions exist, the application shows a first-run state that guides the user to either:

- Register the first transaction manually
- Or start an import flow

This state must preserve the main application shell instead of replacing it with a separate onboarding flow.

---

## Import Flow

Import is a critical product surface and behaves as its own subflow.

### 7. Import - Step 1: File Selection

PortLens accepts two import formats:

- **PDF brokerage note**
- **CSV exported by PortLens itself**

Step-1 requirements:

- Full-screen modal flow with a 3-step stepper
- Explicit import-mode selection
- File upload through a drop zone
- No data persistence in this step

#### PDF-specific requirements

Before full processing begins, the system must:

- Extract the **brokerage-note number**
- Check whether that note already exists in the database

If the note number already exists:

- The flow must stop in Step 1
- No operations may be processed further
- The user must see the "note already imported" interruption state

---

### 8. Import - Step 2: Review

This is the central and most critical step of the import flow.

The system must show the extracted operations in a review table, with file or note identification visible in the header.

Each row must have an extraction status:

- **Ready**: confidently extracted, no ambiguity
- **Review**: incomplete read or low-confidence extraction
- **Conflict**: operation already exists through manual entry with matching relevant fields

Step-2 requirements:

- Allow individual cell editing
- Allow unchecking rows for partial import
- Require explicit conflict decisions
- Never silently overwrite data
- Show a footer summary with:
  - ready count
  - review count
  - conflict count
- Show projected fiscal impact for the most affected month

The projected fiscal feedback must communicate:

- sale volume before and after import
- whether the month remains exempt or becomes taxable
- how much of the monthly limit will be consumed after import

No operation may be persisted in this step.

---

### 9. Import - Step 3: Confirmation and Result

Persistence happens only after explicit confirmation.

On confirmation, the system must:

- Persist the selected operations
- Attach the brokerage-note number to all PDF-imported operations
- Recalculate weighted average price, sale volume, gain/loss, and exemption status
- Show a final import result summary

The result screen must show:

- Number of imported operations
- Number of skipped operations
- Number of reviewed or corrected operations
- Final impact on the fiscal status of the affected competence
- A CTA that brings the user to the imported transactions

---

### 10. Interruption State - Note Already Imported

When a PDF note already exists in the database, the application must stop the import flow in Step 1 and show:

- Note number
- Previous import date
- Number of operations generated by that note
- CTA to view transactions linked to that note
- CTA to cancel the flow

There is no re-import option.

If the user needs to fix a transaction from that note, the correction must happen by editing the existing transaction row directly.

---

## Traceability and Export

### 11. Traceability by Brokerage-Note Number

The brokerage-note number is persistent metadata for operations imported via PDF.

Requirements:

- Read-only field
- Visible in an optional Transactions-table column
- Column hidden by default
- Hover tooltip containing:
  - note number
  - issue date
  - broker
- Manually entered operations must display `-`

This traceability layer makes the source of imported transactions explicit.

---

### 12. CSV Export

CSV export is an action on the Transactions screen.

Requirements:

- Direct action, with no intermediate confirmation step
- Success feedback after export
- Full compatibility with the importer
- Inclusion of the brokerage-note-number field to preserve traceability

The exported CSV uses the same format accepted by the importer, guaranteeing round-trip compatibility.

---

## Critical Business Rules

1. The **R$ 20,000 limit is monthly** and sums all stock sales across every ticker in that month.
2. The exemption applies only to **regular stocks**, not FIIs, ETFs, or BDRs.
3. A **repurchase** must occur on **D+1** to qualify as swing trade instead of day trade.
4. The new weighted average price after a repurchase is calculated by weighting the repurchase price with the remaining position balance.
5. Even when exempt, the operation must still be **declared in the IRPF**.
6. For PDFs, the **brokerage-note number is the primary deduplication key**.
7. Residual conflicts between import and manual entries must never be auto-resolved.
8. No import may persist any data before the final confirmation step.

---

## UX and Interaction Requirements

### Principles

- Fiscal clarity above everything
- High information density with strong visual organization
- Real-time feedback during entry and import review
- Keyboard-first operation
- Dark-first interface with no decorative excess

### Interaction requirements

- Current-month exemption status visible or one click away from any core screen
- Real-time fiscal preview in manual entry
- Specific error messages, never generic ones
- Visible focus state on every interactive element
- Working primary shortcuts: `N`, `Esc`, `Enter`
- Functional light theme, not a naive color inversion

---

## Canonical v1 Screens and States

Version 1 explicitly covers these canonical surfaces:

1. Dashboard / Overview
2. Transactions
3. Current Portfolio
4. Monthly Summary
5. New Transaction side panel
6. Empty state
7. Import - Step 1
8. Import - Step 2
9. Import - Step 3
10. Transactions with traceability enabled
11. Duplicate-note interruption state

---

## Acceptance Criteria

Before calling a screen or flow done, validate:

- Current-month exemption status is visible or one click away
- Every monetary value uses a monospace font with tabular digits
- Gains and losses include explicit sign and consistent semantic color
- No value is shown without unit or context
- Focus is visible on every interactive element
- Primary keyboard shortcuts work
- Light theme is tested and usable
- Text contrast reaches AA and critical data reaches AAA
- Dense tables maintain row height of at least 44px
- Empty state has a clear CTA
- Error messages are specific, never generic
- Import flow persists nothing before Step 3 confirmation
- PDF deduplication by note number happens before processing
- Duplicate-note state shows note number, import date, and operation count
- Import Step 2 shows note identification in the header
- Import Step 2 shows projected fiscal impact in the footer
- Residual conflicts require explicit user decision
- PDF-imported operations expose note traceability through tooltip
- Note-number column is hidden by default and can be enabled
- CSV export preserves the note-number field

---

## Out of Scope for v1

To avoid scope creep, the following are explicitly excluded from v1:

- Responsiveness below `960px`
- Theme customization beyond light/dark toggle
- Advanced charts such as candles, heatmaps, or IBOV comparison
- Decorative or three-dimensional animation
- Authentication
- Multi-user support

---

## Suggested Build Order

### Phase 1 - Foundations

- Data modeling for operations, competences, and fiscal calculations
- Weighted average, gain/loss, and monthly exemption rules
- Persistence structure
- Base support for brokerage-note traceability

### Phase 2 - Manual Flow

- Dashboard
- Transactions screen
- New Transaction side panel with live preview
- Empty state

### Phase 3 - Analytical Views

- Current Portfolio
- Monthly Summary
- Sale suggestions based on remaining monthly exemption margin

### Phase 4 - Import and Traceability

- PDF and CSV import
- Multi-step import review flow
- Deduplication by brokerage-note number
- Duplicate-note interruption state
- Optional traceability column
- CSV export with round-trip compatibility

### Phase 5 - Quality and Hardening

- Keyboard shortcuts
- Light theme
- Accessibility checks
- Visual polish
- Final acceptance-criteria validation
