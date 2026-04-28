package com.jefisu.portlens

import com.jefisu.portlens.core.domain.CompetenceMonth

sealed interface AppShellAction {
    data object OnCompetenceClick : AppShellAction
    data object OnCompetenceMenuDismiss : AppShellAction
    data class OnCompetenceSelected(val competence: CompetenceMonth) : AppShellAction
    data object OnNewTransactionClick : AppShellAction
    data object OnCloseTransactionPanel : AppShellAction
}
